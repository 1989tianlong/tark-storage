package com.tark.storage.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * Created by jh on 2017/5/6.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, SessionAutoConfiguration.class})
public class RedisApp implements CommandLineRunner {

    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RedisApp.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        User user = User.getUser();
        String key = "user:name:" + user.getUserName();
        System.out.println(key);
        ValueOperations<String, User> ops = this.redisTemplate.opsForValue();
        ops.set(key, user);
        User redisUser = ops.get(key);

        System.out.println(redisUser);
    }
}
