package com.example.util.vo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excelVo 属性自定义注解
 *
 * @author liyang
 * @since 2019/11/11 16:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface ExcelVOAttribute {

    /**
     * 列索引
     *
     * @return
     */
    public int columnIndex() default 0;

    /**
     * 列名
     *
     * @return
     */
    public String columnName() default "";
}
