package com.example.mycommunity.config.shiro.cache;

import com.example.mycommunity.utils.ApplicationContextUtils;
import com.example.mycommunity.utils.RedisConstants;
import com.example.mycommunity.utils.ShiroConstants;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

//自定义Redis缓存实现
public class RedisCache<k,v> implements Cache<k,v> {
    RedisTemplate redisTemplate = null;
    private String name;

    public RedisCache(String name){
        this.name = name;
    }

    @Override
    public v get(k k) throws CacheException {
        return (v)getRedisTemplate().opsForHash().get(this.name,k.toString());
    }

    @Override
    public v put(k k, v v) throws CacheException {
        //将查询到结果时，存放到缓存时调用
        getRedisTemplate().opsForHash().put(this.name,k.toString(),v);
        return null;
    }

    @Override
    public v remove(k k) throws CacheException {
        return (v)(getRedisTemplate().opsForHash().delete(this.name,k.toString()));
    }

    @Override
    public void clear() throws CacheException {
        getRedisTemplate().execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                Map<String,String> authenticationMap = redisTemplate.opsForHash().entries(ShiroConstants.AUTHENTICATION_CACHE_NAME);
                authenticationMap.forEach((key,v)->{
                    redisTemplate.opsForHash().delete(ShiroConstants.AUTHENTICATION_CACHE_NAME,key);
                });
                Map<String,String> authorizationMap = redisTemplate.opsForHash().entries(ShiroConstants.AUTHORIZATION_CACHE_NAME);
                authorizationMap.forEach((key,v)->{
                    redisTemplate.opsForHash().delete(ShiroConstants.AUTHORIZATION_CACHE_NAME,key);
                });
                return operations;
            }
        });
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<k> keys() {
        return null;
    }

    @Override
    public Collection<v> values() {
        return null;
    }

    RedisTemplate getRedisTemplate(){
        if (this.redisTemplate == null) {
            RedisTemplate template = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
            this.redisTemplate = template;
        }
        return this.redisTemplate;
    }
}

