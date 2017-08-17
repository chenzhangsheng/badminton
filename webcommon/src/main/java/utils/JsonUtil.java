package utils;

import exception.PlatformRuntimeException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
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
}
