package com.example.app.aiChat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import okhttp3.*;

/**
 * AI应用开发学习：
 * Ai会话
 * @author ly
 * @since 2026/4/2 20:23
 */
@Controller
public class AiChatController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * go to ai page
     */
    @GetMapping("/chat")
    public String showChatPage(){
        return "aiChat/chat";
    }

    /**
     * 流式输出,会话
     * @param prompt
     * @param session
     * @throws Exception
     */
    @GetMapping(value = "/stream", produces = "text/event-stream")
    public SseEmitter stream(@RequestParam String prompt, HttpSession session) {
        SseEmitter emitter = new SseEmitter(300000L);

        CompletableFuture.runAsync(() -> {
            try {
                // 1. 获取会话消息
                List<Map<String, String>> messages = (List<Map<String, String>>) session.getAttribute("messages");
                if (messages == null) {
                    messages = Collections.synchronizedList(new ArrayList<>());
                    session.setAttribute("messages", messages);
                }

                // 2. 添加用户消息
                Map<String, String> userMsg = new HashMap<>();
                userMsg.put("role", "user");
                userMsg.put("content", prompt);
                messages.add(userMsg);

                // 3. 构造请求
                Map<String, Object> param = new HashMap<>();
                param.put("messages", messages);
                String jsonBody = new ObjectMapper().writeValueAsString(param);

                // 4. 创建 OkHttp 客户端（关键：readTimeout 设为 0）
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(0, TimeUnit.SECONDS)  // 0 = 无限等待
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .build();

                Request request = new Request.Builder()
                        .url("http://127.0.0.1:8000/ai/chat_stream_messages")
                        .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonBody))
                        .build();

                // 5. 处理流式响应
                StringBuilder fullAnswer = new StringBuilder();

                // 关键：声明为 final 才能在匿名内部类中使用
                final List<Map<String, String>> finalMessages = messages;
                final HttpSession finalSession = session;

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        emitter.completeWithError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try (BufferedReader reader = new BufferedReader(
                                new InputStreamReader(response.body().byteStream(), StandardCharsets.UTF_8))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                if (line.startsWith("data: ")) {
                                    String content = line.substring(6).trim();
                                    // 跳过 [DONE] 的处理，只输出内容
                                    if (!"[DONE]".equals(content) && !content.isEmpty()) {
                                        fullAnswer.append(content);
                                        emitter.send(SseEmitter.event().data(content));
                                    }
                                }
                            }

                            // 读取完所有数据后，直接保存并完成
                            Map<String, String> aiMsg = new HashMap<>();
                            aiMsg.put("role", "assistant");
                            aiMsg.put("content", fullAnswer.toString());
                            finalMessages.add(aiMsg);
                            session.setAttribute("messages", finalMessages);
                            emitter.complete();
                        } catch (Exception e) {
                            emitter.completeWithError(e);
                        }
                    }
                });

            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    /**
     * 带上下文历史,会话
     * @param prompt
     * @param session
     * @param model
     * @return
     */
    @PostMapping("/chat")
    public String chat(@RequestParam String prompt, HttpSession session, Model model) {
        // 获取历史消息
        List<Map<String, String>>  messages = (List<Map<String, String>>)  session.getAttribute("messages");
        if (messages == null){
            messages = new ArrayList<>();
        }

        // 添加用户当前提问
        Map<String,String> userMsg = new HashMap<>();
        userMsg.put("role","user");
        userMsg.put("content",prompt);
        messages.add(userMsg);

        // 调用带上下文的 AI 接口
        String reply = callWithChatMessages(messages);

        // 把 AI 回复也加入历史
        Map<String, String> aiMsg = new HashMap<>();
        aiMsg.put("role", "assistant");
        aiMsg.put("content", reply);
        messages.add(aiMsg);

        // 存入 session
        session.setAttribute("messages", messages);

        // 传到页面
        model.addAttribute("prompt", prompt);
        model.addAttribute("reply", reply);

        return "aiChat/chat";
    }

    /**
     * 带上下文历史的 调用 FastApi
     * @param messages
     * @return
     */
    public String callWithChatMessages(List<Map<String, String>> messages){

        String url = "http://127.0.0.1:8000/ai/chat_messages";

        // 把整个消息传过去
        JSONObject param = new JSONObject();
        param.put("messages",messages);
        JSONObject resultObj = restTemplate.postForObject(url, param, JSONObject.class);

        // 解析回复
        JSONArray jsonArray = resultObj.getJSONArray("choices");
        JSONObject choice = jsonArray.getJSONObject(0);
        JSONObject message = choice.getJSONObject("message");
        String cotent = message.getString("content"); // 回答内容
        String reasoning_content = message.getString("reasoning_content"); //推理内容

        return cotent;
    }

    /**
     * 普通调用 FastApi 接口
     * @param prompt
     * @return
     */
    public String callFastApi(String prompt){
        // 方式一: get调用
//        String url = "http://127.0.0.1:8000/ai/chat1?prompt="+prompt;
//        JSONObject resultObj = restTemplate.getForObject(url, null, JSONObject.class);

        // 方式二: post 表单调用
//        String url = "http://127.0.0.1:8000/ai/chat2";
//        // 构件表单键值对参数
//        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
//        paramMap.add("prompt", prompt);
//        JSONObject resultObj = restTemplate.postForObject(url, paramMap, JSONObject.class);

        // 方式三: JSON调用 FastApi
        String url = "http://127.0.0.1:8000/ai/chat3";
        JSONObject param = new JSONObject();
        param.put("prompt",prompt);
        JSONObject resultObj = restTemplate.postForObject(url, param, JSONObject.class);

        // 解析回复
        JSONArray jsonArray = resultObj.getJSONArray("choices");
        JSONObject choice = jsonArray.getJSONObject(0);
        JSONObject message = choice.getJSONObject("message");
        String cotent = message.getString("content"); // 回答内容
        String reasoning_content = message.getString("reasoning_content"); //推理内容

        return cotent;
    }
}
