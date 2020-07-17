package com.koron.inwlms.util;

import com.koron.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @auother:zhongweibin
 * @date:2020-07-17
 * @description:
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate = null;

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object getHashValue(final String key,String hashKey) {

        return redisTemplate.opsForHash().get(key,hashKey);
    }

    /**
     * 写入缓存
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     */
    public boolean setHashValue(final String key, String hashKey,Object value) {
        boolean result = false;
        try {
            setHashValue(key,hashKey,value,Constant.EXPIRE);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置超时时长
     */
    public boolean setExpire(final String key,Long expire){
        boolean result = false;
        try {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     */
    public boolean setHashValue(final String key, String hashKey,Object value,Long expire) {
        boolean result = false;
        try {
            redisTemplate.opsForHash().put(key,hashKey,value);
            redisTemplate.expire(key, Constant.EXPIRE, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新缓存
     */
    public boolean getAndSet(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除hash对应key
     */
    public long deleteHashKey(String key,Object... hashKeys){
       return redisTemplate.opsForHash().delete("hashValue",hashKeys);
    }

    /**
     * 删除缓存
     */
    public boolean delete(final String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
