package com.caster.security.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class JsonUtil {

    private static ObjectMapper om;

    static {
        om = new ObjectMapper().registerModule(new Jdk8Module());
    }

    public static String toJsonLog(Object obj) {
        try {
            return om.writerWithDefaultPrettyPrinter()
                     .writeValueAsString(obj);
        } catch (Exception ex) {
            log.error("JsonUtil:", ex);
            return Objects.nonNull(obj) ? obj.toString() : "null";
        }
    }

    public static String toJsonLogDefault(Object obj) {
        try {
            return om.writeValueAsString(obj);
        } catch (Exception ex) {
            log.error("JsonUtil:", ex);
            return Objects.nonNull(obj) ? obj.toString() : "null";
        }
    }

    public static String toJson(Object obj) throws JsonProcessingException {
        return om.writerWithDefaultPrettyPrinter()
                 .writeValueAsString(obj);
    }

    public static Map<String,Object> strToMap(String source) throws JsonProcessingException {
        return om.readValue(source, HashMap.class);
    }

    public static ObjectMapper getOMMapper(){
        return JsonMapper.builder() // or different mapper for other format
                .addModule(new ParameterNamesModule())
                .addModule(new Jdk8Module())
                .addModule(new JavaTimeModule())
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                // and possibly other configuration, modules, then:
                .build();
    }
}
