package com.tark.storage.redis;

/**
 * 
 *类描述:redis 前缀常量接口
 *
 *@Author:
 *@date:2017年5月9日
 *@Version:1.1.0
 */
public interface RedisConstant {
	
	//表主键前缀
	String PK_PREFIX = "WMSPK_";
	
	//分布式锁前缀
	String LOCK_PREFIX = "LOCK_";

}

