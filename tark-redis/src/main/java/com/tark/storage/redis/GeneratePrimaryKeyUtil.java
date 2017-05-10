package com.tark.storage.redis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * 
 *类描述:Redis生成主键工具类
 *
 *@Author:
 *@date:2017年5月8日
 *@Version:1.1.0
 */
@Component
public class GeneratePrimaryKeyUtil {

    private static final Logger logger = Logger.getLogger(GeneratePrimaryKeyUtil.class);
    
    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    public Long generatePrimaryKey(String tableName) {
        String key = RedisConstant.PK_PREFIX + tableName;
        logger.info("Table Primary Key Name:" + key);

        ValueOperations<String, Long> primaryKeyOps = redisTemplate.opsForValue();
        Long primaryKey = primaryKeyOps.increment(key, 1);

        logger.info("Table " + tableName + " primary key is:" + primaryKey);
        return  primaryKey;
    }
}
