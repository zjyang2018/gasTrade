package com.common.cache;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @Project usercenter-common
 * @Description: 缓存处理服务
 * @Company youku
 * @Create 2015年8月2日下午4:52:32
 * @author wangjingao
 * @version 1.0 Copyright (c) 2015 youku, All Rights Reserved.
 */
public interface CacheService {

	/**
	 * 
	 * 方法用途: 放入缓存<br>
	 * 实现步骤: <br>
	 * 
	 * @param key
	 * @param value
	 */
	public void add(String key, Object value);

	/**
	 * 
	 * 方法用途: 放入缓存<br>
	 * 实现步骤: <br>
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 *            必须大于等于0,0为永久,单位秒
	 */
	public <T> void add(String key, T value, int timeout);

	/**
	 * 
	 * 方法用途: 如果缓存不存在则添加<br>
	 * 实现步骤: <br>
	 * 
	 * @param key
	 * @param value
	 */
	public <T> boolean addIfAbsent(String key, T value);

	/**
	 * 
	 * 方法用途: 如果缓存不存在则添加<br>
	 * 实现步骤: <br>
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 *            必须大于等于0,0为永久,单位秒
	 */
	public <T> boolean addIfAbsent(String key, T value, int timeout);

	/**
	 * 
	 * 方法用途: 查询缓存中key对应的value<br>
	 * 查询map.entry的时候，key需要以|分割key和field<br>
	 * 实现步骤: <br>
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public <V> V get(String key);

	/**
	 * 
	 * 方法用途: 查询缓存中是否存在<br>
	 * 实现步骤: <br>
	 * 
	 * @param key
	 * @return
	 */
	public boolean exist(String key);

	/**
	 * 
	 * 方法用途: 删除缓存中对应的值<br>
	 * 实现步骤: <br>
	 * 
	 * @param key
	 */
	public void delete(String key);

	/**
	 * 
	 * 方法用途: 针对incr/incrby指令新增取数方法，由于incr/incrby是字面量操作没有经过jdk序列化，<br>
	 * 老方法get会进行序列化操作反而取不到值 <br>
	 * 实现步骤: <br>
	 * 
	 * @param key
	 * @return
	 */
	public Integer getCount(final String key);

	/**
	 * 
	 * 方法用途: 插入计数器<br>
	 * 实现步骤: <br>
	 * 
	 * @param key
	 * @param value
	 */
	public void incr(String key, int value, int timeout);

	/**
	 * 
	 * incrAndExpireAt:计数并在指定时间失效. <br/>
	 * 
	 * @author William
	 * @param key
	 * @param value
	 * @param date
	 * @since JDK 1.7
	 */
	public void incrAndExpireAt(String key, int value, final Date date);

	/**
	 * 
	 * setnx:redis锁实现. <br/>
	 * 
	 * @author William
	 * @param key
	 * @param value
	 * @return
	 * @since JDK 1.7
	 */
	public Long setnx(final String key, final String value);

	/**
	 * 
	 * expire:设置失效期. <br/>
	 * 
	 * @author William
	 * @param key
	 * @param seconds
	 * @return
	 * @since JDK 1.7
	 */
	public Boolean expire(final String key, final int seconds);

	/**
	 * 删除指令
	 * 
	 * @author William
	 * @param key
	 * @since JDK 1.7
	 */
	public void del(String key);

	/**
	 * 
	 * expireAt:(在某一个时间点失效). <br/>
	 * 
	 * @author William
	 * @param key
	 * @param date
	 * @return
	 * @since JDK 1.7
	 */
	public Boolean expireAt(String key, final Date date);

	/**
	 * 
	 * getExpire:(获取失效时间). <br/>
	 * 
	 * @author William
	 * @param key
	 * @param date
	 * @return
	 * @since JDK 1.7
	 */
	public Long getExpire(String key, final TimeUnit timeUnit);

}