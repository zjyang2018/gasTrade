/**  
 * Project Name:xiniu-online-core-service  
 * File Name:DistributedLockManger.java  
 * Package Name:com.xiniu.common.cache.redis  
 * Date:2017年2月17日下午5:00:39  
 * Copyright (c) 2017, 汇联基金 All Rights Reserved.  
 *  
*/

package com.common.cache.redis;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.common.context.SpringContextHolder;

import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * ClassName:DistributedLockManger <br/>
 * Description: 分布式锁管理器. <br/>
 * Date: 2017年2月17日 下午5:00:39 <br/>
 * 
 * @author William
 * @version
 * @since JDK 1.7
 * @see
 */
public class DistributedLockManger {
	protected static Logger					logger						= Logger.getLogger( DistributedLockManger.class );
	private static final int				DEFAULT_SINGLE_EXPIRE_TIME	= 3;

	private static RedisCacheService		redisCacheService;

	private static DistributedLockManger	lockManger;

	public DistributedLockManger() {
	}

	public void init() {
		lockManger = this;
	}

	public static DistributedLockManger getInstance() {
		if (lockManger == null) {
			lockManger = new DistributedLockManger();
			redisCacheService = SpringContextHolder.getBean( "redisCacheService" );
		}
		return lockManger;
	}

	/**
	 * 获取锁 如果锁可用 立即返回true， 否则立即返回false，作为非阻塞式锁使用
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean tryLock(String key, String value) {
		try {
			return tryLock( key, value, 0L, null );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false，作为阻塞式锁使用
	 * 
	 * @param key
	 *            锁键
	 * @param value
	 *            被谁锁定
	 * @param timeout
	 *            尝试获取锁时长，建议传递500,结合实践单位，则可表示500毫秒
	 * @param unit，建议传递TimeUnit.MILLISECONDS
	 * @return
	 * @throws InterruptedException
	 */
	@SuppressWarnings("static-access")
	public static boolean tryLock(String key, String value, long timeout, TimeUnit unit) throws InterruptedException {
		// 纳秒
		long begin = System.nanoTime();
		do {
			// LOGGER.debug("{}尝试获得{}的锁.", value, key);
			Long i = getInstance().redisCacheService.setnx( key, value );
			if (i == 1) {
				lockManger.redisCacheService.expire( key, DEFAULT_SINGLE_EXPIRE_TIME );
				// {}成功获取{}的锁,设置锁过期时间为{}秒
				logger.debug( "succeed.recieve.lock,key=" + key + ",value=" + value + ",expire.time=" + DEFAULT_SINGLE_EXPIRE_TIME );
				return true;
			} else {
				// 存在锁 ，但可能获取不到，原因是获取的一刹那间
				// String desc = lockManger.jc.get(key);
				// LOGGER.error("{}正被{}锁定.", key, desc);
			}
			if (timeout == 0) {
				break;
			}
			// 在其睡眠的期间，锁可能被解，也可能又被他人占用，但会尝试继续获取锁直到指定的时间
			Thread.sleep( 100 );
		} while ((System.nanoTime() - begin) < unit.toNanos( timeout ));
		// 因超时没有获得锁
		return false;
	}

	/**
	 * 释放单个锁
	 * 
	 * @param key
	 *            锁键
	 * @param value
	 *            被谁释放
	 */
	@SuppressWarnings("static-access")
	public static void unLock(String key, String value) {
		try {
			getInstance().redisCacheService.del( key );
			logger.debug( "release.lock,key=" + key + ",value=" + value );
		} catch (JedisConnectionException je) {

		} catch (Exception e) {

		}
	}
}
