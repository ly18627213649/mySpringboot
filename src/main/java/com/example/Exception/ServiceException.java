package com.example.Exception;

import com.example.proto.ResultCode;
import lombok.Getter;

/**
 * 自定义业务异常
 * @author ly
 * @since 2022/7/15 17:49
 */
@Getter
public class ServiceException extends RuntimeException {
    private int code;
    private String msg;

    // 手动设置异常
    public ServiceException(ResultCode resultCode, String message) {
        // message用于用户设置抛出错误详情，例如：当前价格-5，小于0
        super(message);
        // 状态码
        this.code = resultCode.code;
        // 状态码配套的msg
        this.msg = resultCode.msg;
    }

    // 默认异常使用APP_ERROR状态码
    public ServiceException(String message) {
        super(message);
        this.code = ResultCode.APP_ERROR.code;
        this.msg = ResultCode.APP_ERROR.msg;
    }
    
}
