package com.example.proto;

/**
 * 定义返回结果集状态码,枚举
 * @author ly
 * @since 2022/7/15 16:17
 */
public enum ResultCode {

    SUCCESS(0, "success"),
    ERROR_INVALID_MOBILE(1, "无效的电话号码"),
    ERROR_VALIDATE(2,"参数校验失败"),
    ERROR_SEND_SMS_OVER_FREQUENCY(3, "请求验证码太频繁"),
    ERROR_SERVER_ERROR(4, "服务器异常"),
    ERROR_CODE_EXPIRED(5, "验证码已过期"),
    ERROR_CODE_INCORRECT(6, "验证码错误"),
    ERROR_SERVER_CONFIG_ERROR(7, "服务器配置错误"),
    ERROR_SESSION_EXPIRED(8, "会话不存在或已过期"),
    ERROR_SESSION_NOT_VERIFIED(9, "会话没有验证"),
    ERROR_SESSION_NOT_SCANED(10, "会话没有被扫码"),
    ERROR_SESSION_USER_DOES_NOT_EXIST(11, "用户不存在"),
    ERROR_CODE_NOT_GROUP(12 , "此群不存在"),
    ERROR_SESSION_GROUP_DOES_NOT_EXIST(13, "群ID必填项"),
    RESPONSE_PACK_ERROR(1003, "response返回包装失败"),

    APP_ERROR(2000, "业务异常");


    public int code;
    public String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
