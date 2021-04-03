package com.example.util;

import java.lang.reflect.Field;

public final class AccessibleValueUtil {

    private AccessibleValueUtil() {
    }

    public static void setAccessibleValue(Class<?> clazz, String filedName, Object value)
            throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        Field field = clazz.getDeclaredField(filedName);
        field.setAccessible(true);
        field.set(clazz, value);
    }

    public static Object getAccessibleValue(String filedName, Object target)
            throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException {
        if (target == null)
            return null;
        Field field = target.getClass().getDeclaredField(filedName);
        field.setAccessible(true);
        return field.get(target);
    }
}
