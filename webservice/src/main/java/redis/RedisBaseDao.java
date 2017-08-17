package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * Created by ChenZhangsheng on 2017/8/9.
 */
public class RedisBaseDao {

    private List<JedisPool> jedisPools;  //只有一个jedisPool
    private Integer retryTimes = Integer.valueOf(3);

    public RedisBaseDao() {
    }

    public List<JedisPool> getJedisPools() {
        return this.jedisPools;
    }

    public void setJedisPools(List<JedisPool> jedisPools) {
        this.jedisPools = jedisPools;
    }

    public Jedis getJedis(String key) {
        return (Jedis)((JedisPool)this.jedisPools.get(this.getIndex(key))).getResource();
    }

    public Jedis getJedis(long key) {
        return (Jedis)((JedisPool)this.jedisPools.get(this.getIndex(key))).getResource();
    }

    public int getJedisSize() {
        return this.jedisPools.size();
    }

    public Transaction getTransaction(Jedis jedis) {
        return jedis.multi();
    }

    public void closeJedis(Jedis jedis, String key) {
        ((JedisPool)this.jedisPools.get(this.getIndex(key))).returnResource(jedis);
    }

    public void closeJedis(Jedis jedis, long key) {
        ((JedisPool)this.jedisPools.get(this.getIndex(key))).returnResource(jedis);
    }

    public void closeBrokenJedis(Jedis jedis, String key) {
        if(jedis != null) {
            ((JedisPool)this.jedisPools.get(this.getIndex(key))).returnBrokenResource(jedis);
        }

    }

    public void closeBrokenJedis(Jedis jedis, long key) {
        if(jedis != null) {
            ((JedisPool)this.jedisPools.get(this.getIndex(key))).returnBrokenResource(jedis);
        }

    }

    public void unwatch(Jedis jedis) {
        if(jedis != null) {
            jedis.unwatch();
        }

    }

    public boolean retryLimit(int currentTimes) {
        return currentTimes > this.retryTimes.intValue();
    }

    public Integer getRetryTimes() {
        return this.retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public int getIndex(String key) {
        return Math.abs(key.hashCode()) % this.getPoolSize();
    }

    public int getIndex(long key) {
        long index = key % (long)this.getPoolSize();
        return (int)index;
    }

    public int getPoolSize() {
        return this.jedisPools.size();
    }




}
