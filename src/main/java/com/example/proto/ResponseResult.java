package com.example.proto;

import java.io.Serializable;

/**
 *  返回结果集 模型
 *  @author liyang
 *  @since 2019/8/30 23:05
 */
public class ResponseResult implements Serializable, Cloneable{

    // 属性
    private int code;
    private String msg;
    private Object data;

    public ResponseResult(int code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResponseResult(ResultCode resultCode, Object data) {
        this.code = resultCode.code;
        this.msg = resultCode.msg;
        this.data = data;
    }

    private ResponseResult(ResultCode resultCode){
        this.code = resultCode.code;
        this.msg = resultCode.msg;
        this.data = null;
    }


    // 定义方法
    public static ResponseResult success(Object object){
        return new ResponseResult(ResultCode.SUCCESS, object);
    }

    public static ResponseResult success(ResultCode restCode,Object data){
        return new ResponseResult(restCode,data);
    }

    public static ResponseResult success(ResultCode restCode){
        return new ResponseResult(restCode);
    }

    public static ResponseResult success(){
        return new ResponseResult(ResultCode.SUCCESS);
    }



    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
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
}
