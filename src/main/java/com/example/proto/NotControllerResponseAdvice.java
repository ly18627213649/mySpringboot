package com.example.proto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义注解: 不进行统一返回结果集处理
 * ControllerResponseAdvice.Class
 * @author ly
 * @since 2019/7/15 17:36
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotControllerResponseAdvice {


}
