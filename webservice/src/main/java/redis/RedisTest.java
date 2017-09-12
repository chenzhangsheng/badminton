package redis;

import java.util.Map;

/**
 * Created by zhangshengchen on 2017/9/12.
 */
public interface RedisTest {

    public void setKey(String key ,String value);

    public String getKey(String key);
}
