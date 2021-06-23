package com.example.proto;

import java.io.Serializable;

/**
 *  返回结果集 模型
 *
 *  liyang
 *  2019/8/30 23:05
 */
public class ResponseResult implements Serializable, Cloneable{
    // 定义枚举
    public enum RestCode{
        SUCCESS(0, "success"),
        ERROR_INVALID_MOBILE(1, "无效的电话号码"),
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
        ERROR_SESSION_GROUP_DOES_NOT_EXIST(13, "群ID必填项");

        public int code;
        public String msg;

        RestCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    // 属性
    private int code;
    private String msg;
    private Object data;
    private boolean success;

    private ResponseResult(RestCode code, Object data, boolean success) {
        this.code = code.code;
        this.msg = code.msg;
        this.data = data;
        this.success = success;
    }

    private ResponseResult(RestCode code){

        this.code = code.code;
        this.msg = code.msg;
    }


    public static ResponseResult success(Object object){
        return new ResponseResult(RestCode.SUCCESS, object, Boolean.TRUE);
    }

    public static ResponseResult success(RestCode restCode){

        return new ResponseResult(restCode);

    }

    public static ResponseResult success(){

        return new ResponseResult(RestCode.SUCCESS);
    }

    public static ResponseResult error(RestCode restCode){
        return new ResponseResult(restCode, null, Boolean.FALSE);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseResult asFalse(){
        this.success = Boolean.FALSE;
        return this;
    }

    public ResponseResult asTrue(){
        this.success = Boolean.TRUE;
        return this;
    }

    public ResponseResult msg(String msg){
        this.msg = msg;
        return this;
    }

    public ResponseResult data(Object data) {
        this.data = data;
        return this;
    }

    public ResponseResult code(int code) {
        this.code = code;
        return this;
    }
}
