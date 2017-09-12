package redis;

import bean.ResultBean;
import exception.InvalidRequestRuntimeException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.Map;
import java.util.Set;

/**
 * Created by zhangshengchen on 2017/9/12.
 */
@Service
public class RedisTestimpl extends RedisBaseDao implements RedisTest{
    Log log = LogFactory.getLog(this.getClass());
    private final Long INDEX_KEY_DEFAULT = 11111L;
    private Integer expireTime;
    public void setKey(String key ,String value) {
        Jedis jedis = null;
        Long indexKey = INDEX_KEY_DEFAULT;
        try {
            jedis = this.getJedis(indexKey);
            jedis.set(key,value);
            this.closeJedis(jedis, indexKey);
        } catch (JedisConnectionException e) {
            log.error("RedisTestimpl error: " + ExceptionUtils.getStackTrace(e));
            this.closeBrokenJedis(jedis, indexKey);
            throw new InvalidRequestRuntimeException("JedisConnection error:"+ExceptionUtils.getStackTrace(e), ResultBean.INVALID_REQUST, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            log.error("RedisTestimpl error: " + ExceptionUtils.getStackTrace(e));
            this.closeJedis(jedis, indexKey);
            throw new InvalidRequestRuntimeException("JedisConnection error:"+ExceptionUtils.getStackTrace(e), ResultBean.INVALID_REQUST, HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }
    public String getKey(String key) {
        Jedis jedis = null;
        Long indexKey = INDEX_KEY_DEFAULT;
        try {
            jedis = this.getJedis(indexKey);
            String set = jedis.get(key);
            this.closeJedis(jedis, indexKey);
            return set;
        } catch (JedisConnectionException e) {
            log.error("RedisTestimpl error: " + ExceptionUtils.getStackTrace(e));
            this.closeBrokenJedis(jedis, indexKey);
            throw new InvalidRequestRuntimeException("JedisConnection error:"+ExceptionUtils.getStackTrace(e), ResultBean.INVALID_REQUST, HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            log.error("RedisTestimpl error: " + ExceptionUtils.getStackTrace(e));
            this.closeJedis(jedis, indexKey);
            throw new InvalidRequestRuntimeException("JedisConnection error:"+ExceptionUtils.getStackTrace(e), ResultBean.INVALID_REQUST, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
