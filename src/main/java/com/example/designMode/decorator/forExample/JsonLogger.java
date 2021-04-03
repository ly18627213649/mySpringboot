package com.example.designMode.decorator.forExample;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;

/**
 * jsonLogger 具体装饰对象
 */
public class JsonLogger extends DecoratorLogger{
    /**
     * 构造方法
     *
     * @param logger
     */
    public JsonLogger(Logger logger) {
        super(logger);
    }

    @Override
    public void info(String msg) {

        // 封装json后打印日志
        JSONObject logJson = new JSONObject();

        logJson.put("message: ", msg);

        logger.info(logJson.toJSONString());
    }

    public void error(String msg){

        // 封装json后打印日志
        JSONObject logJson = new JSONObject();

        logJson.put("message: ",msg);

        logger.debug(logJson.toJSONString());
    }

    public void error(Exception e){

        // 封装json后打印日志
        JSONObject logJson = new JSONObject();

        logJson.put("exceptionName: ",e.getClass().getName());

        String stackTrace = ExceptionUtils.getStackTrace(e);

        logJson.put("stacktrack: ",stackTrace);

        logger.debug(logJson.toJSONString());
    }
}
