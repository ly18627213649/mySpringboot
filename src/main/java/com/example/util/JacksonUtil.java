package com.example.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * jackson 工具类
 *
 * @author liyang
 * @since 2019/11/7 10:10
 */
public class JacksonUtil {

    protected static Logger logger = LoggerFactory.getLogger(JacksonUtil.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    static {

        //去掉默认的时间戳格式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        //设置为中国上海时区
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);

        //空值不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //反序列化时，属性不存在的兼容处理
        objectMapper.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        //序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号处理
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    /**
     * json 转 bean
     *
     * @author liyang
     * @since 2019/11/7 10:31
     */
    public static <T> T json2Bean(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("json 转 bean", e);
        }
        return null;
    }

    /**
     * bean 转 json
     *
     * @author liyang
     * @since 2019/11/7 10:32
     */
    public static <T> String object2Json(T object) {

        if (object == null) {
            return null;
        }

        if (object.getClass() == String.class) {
            return object.toString();
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.error("bean 转 json", e);
        }
        return null;
    }

    /**
     * json 转 map<String,Object>
     *
     * @author liyang
     * @since 2019/11/7 10:32
     */
    public static Map<String, Object> json2Map(String json) {

        if (StringUtils.isBlank(json)) {

            return null;
        }

        Map<String, Object> map = null;

        try {
            map = objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            logger.error("json 转 map", e);
            return null;
        }

        return map;
    }

    /**
     * json字符串 转 Map<String, T>
     *
     * @author liyang
     * @since 2019/11/7 11:00
     */
    public static <T> Map<String, T> json2map(String jsonString, Class<T> clazz) {

        Map<String, Map<String, Object>> map = null;
        Map<String, T> result = null;

        try {
            map = (Map<String, Map<String, Object>>) objectMapper.readValue(jsonString, new TypeReference<Map<String,T>>() {
            });

            result = new HashMap<String, T>();

            for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
                result.put(entry.getKey(), map2Bean(entry.getValue(), clazz));
            }
        } catch (IOException e) {
            logger.error("json字符串 转 Map<String, T> 出错", e);
        }

        return result;
    }

    /**
     * Map 转换为 JavaBean
     *
     * @author liyang
     * @since 2019/11/7 11:12
     */
    public static <T> T map2Bean(Map map, Class<T> clazz) {
        if (map == null) {
            return null;
        }

        try {
            return objectMapper.readValue(object2Json(map), clazz);
        } catch (IOException e) {
            logger.error("Map 转换为 JavaBean 失败!", e);
            return null;
        }
    }

    /**
     * 将 object 对象转换为 JavaBean
     *
     * @author liyang
     * @since 2019/11/7 14:06
     */
    public static <T> T object2Bean(Object obj, Class<T> clazz) {

        if (obj == null || clazz == null) {
            return null;
        }

        try {
            return objectMapper.readValue(object2Json(obj), clazz);
        } catch (IOException e) {
            logger.error("将 object 对象转换为 JavaBean 失败!", e);
            return null;
        }
    }

    /**
     * 深度转换 JSON 成 Map
     *
     * @param json
     * @return
     */
    public static Map<String, Object> json2mapDeeply(String json) {
        try {
            return json2MapRecursion(json, objectMapper);
        } catch (Exception e) {
            logger.error("深度转换 JSON 成 map 出错", e);
            return null;
        }
    }

    /**
     * 把 JSON 解析成 Map，如果 Map 内部的 Value 存在 jsonString，继续解析
     *
     * @author liyang
     * @since 2019/11/7 14:43
     */
    private static Map<String, Object> json2MapRecursion(String json, ObjectMapper mapper) throws Exception {
        if (json == null) {
            return null;
        }

        Map<String, Object> map = mapper.readValue(json, Map.class);

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object obj = entry.getValue();
            if (obj != null && obj instanceof String) {
                String str = ((String) obj);

                if (str.startsWith("[")) {
                    List<?> list = json2ListRecursion(str, mapper);
                    map.put(entry.getKey(), list);
                } else if (str.startsWith("{")) {
                    Map<String, Object> mapRecursion = json2MapRecursion(str, mapper);
                    map.put(entry.getKey(), mapRecursion);
                }
            }
        }
        return map;
    }

    /**
     * 把 JSON 解析成 List，如果 List 内部的元素存在 jsonString，继续解析
     *
     * @author liyang
     * @since 2019/11/7 14:44
     */
    private static List<Object> json2ListRecursion(String json, ObjectMapper mapper) throws Exception {
        if (json == null) {
            return null;
        }

        List<Object> list = mapper.readValue(json, List.class);

        for (Object obj : list) {
            if (obj != null && obj instanceof String) {
                String str = (String) obj;
                if (str.startsWith("[")) {
                    obj = json2ListRecursion(str, mapper);
                } else if (obj.toString().startsWith("{")) {
                    obj = json2MapRecursion(str, mapper);
                }
            }
        }

        return list;
    }

    /**
     * json 转 List<Object>
     *
     * @author liyang
     * @since 2019/11/7 10:47
     */
    public static List<Object> json2List(String json) {

        if (StringUtils.isBlank(json)) {
            return null;
        }

        List<Object> list = null;

        try {
            list = objectMapper.readValue(json, List.class);
        } catch (IOException e) {
            logger.error("json 转 List 出错", e);
            return null;
        }

        return list;
    }

    /**
     * 将 JSON 数组字符串  转 List<T>
     *
     * @author liyang
     * @since 2019/11/7 14:43
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz) {
        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        List<T> list = null;
        try {
            list = (List<T>) objectMapper.readValue(jsonArrayStr, javaType);
        } catch (IOException e) {
            logger.error("json 数组转为集合<bean> 出错", e);
        }
        return list;
    }


    /**
     * 获取泛型的 Collection Type
     *
     * @author liyang
     * @since 2019/11/7 14:44
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * json to Collection
     *
     * @param json          json字符串
     * @param typeReference new TypeReference<List<对象>>() {})
     * @author liyang
     * @since 2019/11/7 14:39
     */
    public static <T> T json2Collection(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            logger.error("json to Collection 出错", e);
        }
        return null;
    }


}
