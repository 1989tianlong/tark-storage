package com.tark.storage.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 
 *类描述:Redis distributed lock implementation.
 *
 *@Author:
 *@date:2017年5月9日
 *@Version:1.1.0
 */
public class RedisDistributedLock {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);
	
	private RedisTemplate redisTemplate;
	
	private String lockKey;
	
	private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;
	
	/**
	 * 锁超时时间，防止线程入锁后，无限执行等待
	 */
	private int expireMsecs = 60 * 1000;
	
	/**
	 * 锁等待时间，防止线程饥饿
	 */
	private int timeoutMsecs = 10 * 1000;
	
	private volatile boolean locked = false;
	
    /**
     * Detailed constructor with default acquire timeout 10000 msecs and lock expiration of 60000 msecs.
     *
     * @param lockKey lock key (ex. account:1, ...)
     */
    public RedisDistributedLock(RedisTemplate redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = RedisConstant.LOCK_PREFIX + lockKey;
    }

    /**
     * Detailed constructor with default lock expiration of 60000 msecs.
     *
     */
    public RedisDistributedLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs) {
        this(redisTemplate, lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }

    /**
     * Detailed constructor.
     *
     */
    public RedisDistributedLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs, int expireMsecs) {
        this(redisTemplate, lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }

    /**
     * @return lock key
     */
    public String getLockKey() {
        return lockKey;
    }
    
    private String get(final String key) {
    	Object obj = null;
    	try {
    		obj = redisTemplate.execute(new RedisCallback<Object>() {

				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisSerializer serializer = new StringRedisSerializer();
					byte[] data = connection.get(serializer.serialize(key));
					connection.close();
					if(data == null) {
						return null;
					} 
					return serializer.deserialize(data);
				}
    			
			});
    	} catch(Exception e) {
    		logger.error("get redis error, key : ", key);
    	}
    	return obj != null ? obj.toString() : null;
    }

	private boolean setNX(final String key, final String value) {
		Object obj = null;
		
		try {
			obj = redisTemplate.execute(new RedisCallback<Object>() {

				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisSerializer serializer = new StringRedisSerializer();
					Boolean success = connection.setNX(serializer.serialize(key), serializer.serialize(value));
					connection.close();
					return success;
				}
				
			});
			
		} catch(Exception e) {
			logger.error("setNX redis error, key : ", key);
		}
		
		return obj != null ? (Boolean)obj : false; 
	}
	
	private String getSet(final String key, final String value) {
		Object obj = null;
		
		try {
			obj = redisTemplate.execute(new RedisCallback<Object>() {

				@Override
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisSerializer serializer = new StringRedisSerializer();
					Object obj = connection.getSet(serializer.serialize(key), serializer.serialize(value));
					connection.close();
					return obj;
				}
				
			});
			
		} catch(Exception e) {
			logger.error("getSet redis error, key : ", key);
		}
		
		return obj != null ? obj.toString() : null; 
	}
	
    /**
     * 获得 lock.
     * 实现思路: 主要是使用了redis 的setnx命令,缓存了锁.
     * reids缓存的key是锁的key,所有的共享, value是锁的到期时间(注意:这里把过期时间放在value了,没有时间上设置其超时时间)
     * 执行过程:
     * 1.通过setnx尝试设置某个key的值,成功(当前没有这个锁)则返回,成功获得锁
     * 2.锁已经存在则获取锁的到期时间,和当前时间比较,超时的话,则设置新的值
     *
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException in case of thread interruption
     */
	public synchronized boolean lock() throws InterruptedException {
		int timeout = timeoutMsecs;
		while(timeout >= 0) {
			long expires = System.currentTimeMillis() + expireMsecs + 1;
			//锁到期时间
			String expiresStr = String.valueOf(expires);
			
			if (this.setNX(lockKey, expiresStr)) {
				logger.info("lock by setNx key : " + lockKey);
				// lock acquired
				locked = true;
				return true;
			}
			
			String currentValueStr = this.get(lockKey);
			
			//判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过期的
			if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
				//获取上一个锁到期时间，并设置现在锁的到期时间
				//只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
				logger.info("lock is timeout set new expire, key : ", lockKey);
				String oldValueStr = this.getSet(lockKey, expiresStr);
				
				//防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受
				if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
					//[分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
					logger.info("current thread acquired lock key : ", lockKey);
					locked = true;
					return true;
				}
			}
			
			//重试
			timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
			
            /*
            	延迟100 毫秒,  这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
            	只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进行,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
            	使用随机的等待时间可以一定程度上保证公平性
             */
			Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
		}
		
		return false;
	}
	
	public synchronized void unlock() {
		if (locked) {
			redisTemplate.delete(lockKey);
			locked = false;
		}
	}
}
