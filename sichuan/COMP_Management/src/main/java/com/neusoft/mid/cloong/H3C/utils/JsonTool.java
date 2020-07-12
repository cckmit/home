package com.neusoft.mid.cloong.H3C.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonTool {

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 将POJO转换为JSON字符串对应的字节数组
     * @param obj 要转换的对象
     * @return JSON字符串对应的字节数组
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static byte[] encode(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
        byte[] bytes = mapper.writeValueAsBytes(obj);
        return bytes;
    }

    /**
     * 将POJO转换为JSON字符串
     * @param obj 要转换的对象
     * @return JSON字符串
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static String encodeAsString(Object obj) throws JsonGenerationException, JsonMappingException, IOException {
        return mapper.writeValueAsString(obj);
    }

    /**
     * 将JSON字符反序列化为POJO
     * @param json 要转换为POJO的JSON字符串
     * @param className POJO对应的Class
     * @return 序列化后的POJO
     * @throws JsonToolException
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static Object decode(String json, Class<?> className) throws JsonGenerationException,
            JsonMappingException, IOException {
        return mapper.readValue(json.getBytes("UTF-8"), className);
    }

}
