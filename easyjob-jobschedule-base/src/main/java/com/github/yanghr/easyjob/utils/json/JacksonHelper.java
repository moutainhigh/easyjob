package com.github.yanghr.easyjob.utils.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.yanghr.easyjob.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import araf.utils.DateUtils;

/**
 * JacksonHelper.提供静态方法实体到json，json到实体的转换
 *
 * @version Araf v1.0
 * @author Yu Tao, 2014-11-4
 */
public class JacksonHelper {

    private static Logger log = LoggerFactory.getLogger(JacksonHelper.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static ObjectMapper defaultDateDbjectMapper = null;

    /**
     * entity to json.
     *
     * @param entity
     * @return
     */
    public static String entityToJson(Object entity) {
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.setPropertyNamingStrategy(new EasyPropertyNamingStrategy());
        if (entity == null) {
            return "";
        }

        try {
            return objectMapper.writeValueAsString(entity);

        } catch (IOException e) {

            log.error(e.getMessage());
            return "";

        }

    }

    /**
     * json to entity. NOTE: clazz不能是一个内部类
     *
     * @param <T>
     * @param elementClasses
     * @param str
     * @return
     */
    public static <T> T jsonToEntity(Class<?> collectionClass, Class<?> elementClasses,
                                     String str) {
        if (str == null) {
            return null;
        }

        try {
            JavaType javaType = getCollectionType(collectionClass, elementClasses);
//            T result = objectMapper.readValue(str, clazz);

            return objectMapper.readValue(str, javaType);

        } catch (IOException e) {

            log.error(e.getMessage());
            return null;

        }
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses 元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass,
                elementClasses);
    }

    /**
     * json to entity. NOTE: clazz不能是一个内部类
     *
     * @param <T>
     * @param clazz
     * @param str
     * @return
     */
    public static <T> T jsonToEntity(Class<T> clazz, String str) {
        if (str == null) {
            return null;
        }

        try {

            T result = objectMapper.readValue(str, clazz);

            return result;

        } catch (IOException e) {

            log.error(e.getMessage());
            return null;

        }
    }

    /**
     * 通过给定的属性名，读取值.
     *
     * @param json
     * @param propertyName
     * @return
     */
    public static String getValue(String json, String propertyName) {
        try {
            return objectMapper.readTree(json).get(propertyName).toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private static synchronized ObjectMapper generateDefaultDateformatterObjectMapper() {
        if(defaultDateDbjectMapper != null) {
            return defaultDateDbjectMapper;
        }
        // 设置解析JSON工具类
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(Date.class, new DateSerializer(false,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", DateUtils.getDefaultLocale())));
        defaultDateDbjectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
                .registerModule(javaTimeModule)
                .setPropertyNamingStrategy(new EasyPropertyNamingStrategy());
        return defaultDateDbjectMapper;
    }

    /**
     * 转为json格式，默认日期类型转换.
     *
     * @param obj 实体
     * @return json
     */
    public static String entityToJsonWithDefaultDateformat(Object obj) {
        ObjectMapper mapper = generateDefaultDateformatterObjectMapper();

        try {
            return mapper.writeValueAsString(obj);

        } catch (IOException e) {

            log.error(e.getMessage());
            return "";

        }

    }
}