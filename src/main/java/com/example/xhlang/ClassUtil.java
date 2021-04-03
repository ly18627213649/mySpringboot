package com.example.xhlang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ClassUtil {

    protected static Logger log = LoggerFactory.getLogger(ClassUtil.class);

    public ClassUtil() {
    }

    public static Object newInstance(Class clazz) throws IllegalAccessException, InstantiationException {
        return clazz.newInstance();
    }

    public static List<Field> getAllFieldList(Class clazz) {
        if (clazz == null) {
            throw new NullPointerException(String.format("%s - %s取得所有字段时出错：%s", ClassUtil.class.getSimpleName(), "getAllFieldList()"));
        } else {
            List<Field> fieldList = new ArrayList();
            Field[] declaredFields = clazz.getDeclaredFields();
            if (declaredFields != null && declaredFields.length > 0) {
                Field[] var3 = declaredFields;
                int var4 = declaredFields.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    Field field = var3[var5];
                    boolean isStatic = Modifier.isStatic(field.getModifiers());
                    if (!isStatic) {
                        fieldList.add(field);
                    }
                }
            }

            return fieldList;
        }
    }
}
