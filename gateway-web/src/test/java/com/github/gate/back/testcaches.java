package com.github.gate.back;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.gate.back.entity.User;
import com.github.gate.back.mapper.UserMapper;
import com.github.gate.back.service.CacheLoadable;
import com.github.gate.back.service.CacheTempSevice;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by luozhonghua on 2018/5/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class testcaches {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void findUser() throws Exception {

        String key ="list7";
        exeRunable(key,findCache(key, 1000, TimeUnit.SECONDS, new TypeReference<User>(){}, new CacheLoadable<User>() {
            public User load() {
                User user= userMapper.findUserById(1);
                return user;
            };
        }));


      /*findCache("lisi", 1000, TimeUnit.SECONDS, new TypeReference<User>(){}, new CacheLoadable<User>() {
            public User load() {
                User user= userMapper.findUserById(1);
                System.out.println(user.getUsername()+"---db----"+user.getPassword());
                return user;
            };
        });*/

      /*   User user= userMapper.findUserById(1);
        System.out.println(user.getUsername()+"---db----"+user.getPassword());
        redisTemplate.opsForValue().set(user.getUsername(),user.getPassword(),1000, TimeUnit.SECONDS);
        System.out.println("---cache----"+ redisTemplate.hasKey(user.getUsername()));*/

    }

    private void exeRunable(String key,User user) throws InterruptedException, ExecutionException {
        System.out.println("----程序开始运行----");
        Date date1 = new Date();
        int taskSize = 5;
        // 创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(taskSize);
        // 创建多个有返回值的任务
        List<Future> list = new ArrayList<Future>();
        for (int i = 0; i < taskSize; i++) {
            Callable c = new MyCallable(i + " value:"+JSON.toJSON(user));
            // 执行任务并获取Future对象
            Future f = pool.submit(c);
            // System.out.println(">>>" + f.get().toString());
            list.add(f);
        }
        // 关闭线程池
        pool.shutdown();

        // 获取所有并发任务的运行结果
        for (Future f : list) {
            // 从Future对象上获取任务的返回值，并输出到控制台
            System.out.println(">>>" + f.get().toString());
        }

        Date date2 = new Date();
        System.out.println("----程序结束运行----，程序运行时间【"
                + (date2.getTime() - date1.getTime()) + "毫秒】");
    }


    public <T> T findCache(String key, long expire, TimeUnit unit, TypeReference<T> clazz, CacheLoadable<T> cacheLoadable) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        String json = String.valueOf(opsForValue.get(key));
        if (StringUtils.isNotEmpty(json) && !json.equalsIgnoreCase("null")) {
            System.out.println("+++++++++++++query cache  key:"+key+"++++++++++++++");
            return JSON.parseObject(json, clazz);
        } else {
            synchronized (this) {
                json = String.valueOf(opsForValue.get(key));
                if (StringUtils.isNotEmpty(json) && !json.equalsIgnoreCase("null")) {
                    System.out.println("+++++++++++++query cache  key:"+key+"++++++++++++++");
                    return JSON.parseObject(json, clazz);
                }
                T result = cacheLoadable.load();
                System.out.println("+++++++++++++query db  key:"+key+"++++++++++++++");
                redisTemplate.opsForValue().set(key, JSON.toJSON(result), expire, unit);
                return result;
            }
        }
    }
}


class MyCallable implements Callable<Object> {
    private String taskNum;
    MyCallable(String taskNum) {
        this.taskNum = taskNum;
    }
    public Object call() throws Exception {
        System.out.println(">>>" + taskNum + "任务启动");
        Date dateTmp1 = new Date();
        Thread.sleep(1000);
        long time = new Date().getTime() - dateTmp1.getTime();
        System.out.println(">>>" + taskNum + "任务终止");
        return taskNum + "任务返回运行结果,当前任务时间【" + time + "毫秒】";
    }
}
