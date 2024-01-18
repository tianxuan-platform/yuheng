package com.wuyiccc.yuheng.infrastructure.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/10/25 7:23
 * json工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.registerModules(new JavaTimeModule()); // 解决 LocalDateTime 的序列化
    }

    public static String objectToJson(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonData, beanType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            return MAPPER.readValue(jsonData, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
