package com.example.app.aiChat;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
/**
 * Ai会话
 * @author ly
 * @since 2026/4/2 20:23
 */
@RestController
public class AiChatController {

    @Autowired
    private RestTemplate restTemplate;

    /***
     * 调用python项目的ai会话接口
     * @param prompt
     * @return
     */
    @PostMapping("/ai/chat")
    public JSONObject callPythonAi(String prompt){

        // 方式一: get调用
//        String url = "http://127.0.0.1:8000/ai/chat1?prompt="+prompt;
//        JSONObject resultObj = restTemplate.getForObject(url, null, JSONObject.class);

        // 方式二: post 表单调用
//        String url = "http://127.0.0.1:8000/ai/chat2";
//        // 构件表单键值对参数
//        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
//        paramMap.add("prompt", prompt);
//        JSONObject resultObj = restTemplate.postForObject(url, paramMap, JSONObject.class);


        // 方式三: JSON调用
        String url = "http://127.0.0.1:8000/ai/chat3";
        JSONObject param = new JSONObject();
        param.put("prompt",prompt);
        JSONObject resultObj = restTemplate.postForObject(url, param, JSONObject.class);

        return resultObj;
    }
}
