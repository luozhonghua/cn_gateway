package com.github.gate.back.service;


import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


/**
 * Created by luozhonghua on 2018/5/2.
 */

@Service
public class CacheTempSevice {

    @Autowired
    private RedisTemplate redisTemplate;


    public <T> T findCache(String key, long expire, TimeUnit unit, TypeReference<T> clazz,CacheLoadable<T> cacheLoadable){
        ValueOperations opsForValue= redisTemplate.opsForValue();
        String json=String.valueOf(opsForValue.get(key));
        if(StringUtils.isNotEmpty(json) && !json.equalsIgnoreCase("null")){
            System.out.println("+++++++++++++query cache++++++++++++++");
            return JSON.parseObject(json,clazz);
        }else{
             synchronized (this){
                   json=String.valueOf(opsForValue.get(key));
                 if(StringUtils.isNotEmpty(json) && !json.equalsIgnoreCase("null")) {
                     System.out.println("+++++++++++++query cache++++++++++++++");
                     return JSON.parseObject(json, clazz);
                 }
                 T result= cacheLoadable.load();
                 redisTemplate.opsForValue().set(key, JSON.toJSON(result), 1000, TimeUnit.SECONDS);
                 return  result;
             }
        }
    }


    public    void setCache(String key,Object val){
        try {
            redisTemplate.opsForValue().set(key, val, 1000, TimeUnit.SECONDS);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public    boolean assertCache(String key){
        return   redisTemplate.hasKey(key);
    }

}
