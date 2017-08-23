package utils;

import exception.PlatformRuntimeException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by ChenZhangsheng on 2017/8/15.
 */
public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static <T> T getObjectFromJson(String str, Class<T> cls) {
        try {
            JsonParser e = objectMapper.getJsonFactory().createJsonParser(str);
            T t = objectMapper.readValue(e, cls);
            return t;
        } catch (JsonParseException var4) {
            throw new PlatformRuntimeException("parse json error, json=" + str + ", class=" + cls.getName(), var4);
        } catch (IOException var5) {
            throw new PlatformRuntimeException("parse json error, json=" + str + ", class=" + cls.getName(), var5);
        }
    }
    public static String getJsonFromObject(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonGenerationException var2) {
            throw new PlatformRuntimeException("get json error", var2);
        } catch (JsonMappingException var3) {
            throw new PlatformRuntimeException("get json error", var3);
        } catch (IOException var4) {
            throw new PlatformRuntimeException("get json error", var4);
        }
    }
}
