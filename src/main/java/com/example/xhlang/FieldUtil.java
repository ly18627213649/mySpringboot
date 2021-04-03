package com.example.xhlang;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class FieldUtil {

    protected static Logger log = LoggerFactory.getLogger(FieldUtil.class);

    public FieldUtil() {
    }

    public static List<String> getFieldList(Class clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("取得当前类的属性集合(不包括父类)时出错：入参clazz为null!");
        } else {
            Field[] fields = clazz.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                List<String> returnValue = new ArrayList();
                Field[] var3 = fields;
                int var4 = fields.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    Field field = var3[var5];
                    int modifiers = field.getModifiers();
                    if (!Modifier.isStatic(modifiers)) {
                        String fieldName = StringUtils.capitalize(field.getName());
                        returnValue.add(fieldName);
                    }
                }

                return returnValue;
            } else {
                return null;
            }
        }
    }

    public static List<String> getAllFieldList(Class clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("取得当前类的属性集合(包括父类)时出错：入参clazz为null!");
        } else {
            ArrayList fieldList;
            for (fieldList = new ArrayList(); clazz != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
                List<String> _fieldList = getFieldList(clazz);
                fieldList.addAll(_fieldList);
            }

            return fieldList;
        }
    }
}
