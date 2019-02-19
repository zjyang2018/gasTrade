package com.common.cache.redis;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.Jedis;

import com.common.cache.CacheService;
import com.common.cache.CacheValidator;
import com.common.cache.exception.CacheAddException;
import com.common.cache.exception.CacheValidateException;

/**
 * 
 * @Project usercenter-common
 * @Description: 使用redis作为缓存的实现方式
 * @Company 汇联
 * @Create 2015年8月2日下午5:29:30
 * @author wangjingao
 * @version 1.0 Copyright (c) 2015 youku, All Rights Reserved.
 * @version 1.1 Copyright (c) 2015 youku, All Rights
 *          Reserved.incr、expire放在一个lua脚本中执行
 */
public class RedisCacheService implements CacheService {

	private RedisTemplate<Serializable, Serializable> redisTemplate;

	public void add(String key, Object value) {
		CacheValidator.addValidate(key, value);
		add(key, value, 0);
	}

	/**
	 * 
	 * 方法用途: 往缓存放入值,类型包括Map.Entry,Map,Set,List,其他类型将自动序列化为二进制<br>
	 * 实现步骤: <br>
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 */
	public <T> void add(String key, T value, int timeout) {
		CacheValidator.addValidate(key, value, timeout);
		long addedNum = 0;
		if (value instanceof Map.Entry) {
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) value;
			CacheValidator.mapEntryValidate(entry);
			redisTemplate.opsForHash().put(key, entry.getKey(),
					entry.getValue());
			addedNum = 1;
		} else if (value instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) value;
			if (MapUtils.isEmpty(map)) {
				throw new CacheValidateException("map不能为空");
			}
			redisTemplate.opsForHash().putAll(key, (Map<?, ?>) value);
			addedNum = 1;
		} else if (value instanceof Set) {
			Set<?> set = (Set<?>) value;
			if (CollectionUtils.isEmpty(set)) {
				throw new CacheValidateException("set不能为空");
			}
			addedNum = redisTemplate.opsForSet().add(key, set.toArray());
		} else if (value instanceof List) {
			List<?> list = (List<?>) value;
			if (CollectionUtils.isEmpty(list)) {
				throw new CacheValidateException("list不能为空");
			}
			redisTemplate.opsForList().rightPushAll(key, list.toArray());
			addedNum = 1;
		} else {
			redisTemplate.opsForValue().set(key, (Serializable) value);
			addedNum = 1;
		}
		// 设置过期时间
		if (timeout != 0
				&& !redisTemplate.expire(key, timeout, TimeUnit.SECONDS)) {
			throw new CacheAddException(key);
		}
		if (addedNum == 0) {
			throw new CacheAddException(key);
		}
	}

	public <T> boolean addIfAbsent(String key, T value) {
		CacheValidator.addValidate(key, value);
		return addIfAbsent(key, value, 0);
	}

	public <T> boolean addIfAbsent(String key, T value, int timeout) {
		CacheValidator.addValidate(key, value, timeout);
		// notExist为0时已存在key，为1时不存在key
		boolean result = false;
		if (value instanceof Map.Entry) {
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) value;
			CacheValidator.mapEntryValidate(entry);
			result = redisTemplate.opsForHash().putIfAbsent(key,
					entry.getKey(), entry.getValue());
		} else if (value instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) value;
			if (MapUtils.isEmpty(map)) {
				throw new CacheValidateException("map不能为空");
			}
			// 此处有并发问题
			if (exist(key)) {
				return false;
			}
			redisTemplate.opsForHash().putAll(key, map);
			result = true;
		} else if (value instanceof Set) {
			Set<?> set = (Set<?>) value;
			if (CollectionUtils.isEmpty(set)) {
				throw new CacheValidateException("set不能为空");
			}
			// 此处有并发问题
			if (exist(key)) {
				return false;
			}
			redisTemplate.opsForSet().add(key, set.toArray());
			result = true;
		} else if (value instanceof List) {
			List<?> list = (List<?>) value;
			if (CollectionUtils.isEmpty(list)) {
				throw new CacheValidateException("list不能为空");
			}
			// 此处有并发问题
			if (exist(key)) {
				return false;
			}
			redisTemplate.opsForList().rightPushAll(key, list.toArray());
			result = true;
		} else {
			redisTemplate.opsForValue().setIfAbsent(key, (Serializable) value);
			result = true;
		}
		if (!result) {
			return result;
		}
		if (timeout != 0) {
			return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		if (StringUtils.isBlank(key)) {
			throw new CacheValidateException("key不能为空");
		}
		switch (redisTemplate.type(key)) {
		case STRING:
			return (T) redisTemplate.opsForValue().get(key);
		case LIST:
			return (T) redisTemplate.opsForList().range(key, 0, -1);
		case SET:
			return (T) redisTemplate.opsForSet().members(key);
		case ZSET:
			return (T) redisTemplate.opsForZSet().range(key, 0, -1);
		case HASH:
			if (key.split("\\|").length != 2) {
				// throw new CacheValidateException(
				// "查询map.entry需要传入两个Key.在key中以|分割" );
				return (T) redisTemplate.opsForHash().entries(key);
			}
			return (T) redisTemplate.opsForHash().get(key.split("\\|")[0],
					key.split("\\|")[1]);

		case NONE:
			return null;
		default:
			throw new CacheValidateException("redis数据类型错误");
		}
	}

	public boolean exist(String key) {
		if (StringUtils.isBlank(key)) {
			throw new CacheValidateException("key不能为空");
		}
		return redisTemplate.hasKey(key);
	}

	public void delete(String key) {
		if (StringUtils.isBlank(key)) {
			throw new CacheValidateException("key不能为空");
		}
		redisTemplate.delete(key);
	}

	/**
	 * 
	 * 方法用途: 针对incr/incrby指令新增取数方法，由于incr/incrby是字面量操作没有经过jdk序列化，<br>
	 * 老方法get会进行序列化操作反而取不到值 <br>
	 * 实现步骤: <br>
	 * 
	 * @param key
	 * @return
	 */
	public Integer getCount(final String key) {
		if (StringUtils.isBlank(key)) {
			throw new CacheValidateException("key不能为空");
		}
		return redisTemplate.execute(new RedisCallback<Integer>() {

			public Integer doInRedis(RedisConnection connection)
					throws DataAccessException {
				Jedis jedis = (Jedis) connection.getNativeConnection();
				String count = jedis.get(key);
				if (StringUtils.isNotBlank(count)) {
					return Integer.valueOf(count);
				}
				return null;
			}
		});
	}

	public void setRedisTemplate(
			RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void incr(String key, int value, int timeout) {
		if (StringUtils.isBlank(key)) {
			throw new CacheValidateException("key不能为空");
		}
		redisTemplate.opsForValue().increment(key, value);
		// 设置过期时间
		if (timeout != 0
				&& !redisTemplate.expire(key, timeout, TimeUnit.SECONDS)) {
			throw new CacheAddException(key);
		}
	}

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
	public void incrAndExpireAt(String key, int value, final Date date) {
		if (StringUtils.isBlank(key)) {
			throw new CacheValidateException("key不能为空");
		}
		redisTemplate.opsForValue().increment(key, value);
		// 设置过期时间
		if (date != null && !redisTemplate.expireAt(key, date)) {
			throw new CacheAddException(key);
		}
	}

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
	public Long setnx(final String key, final String value) {
		return redisTemplate.execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				Jedis jedis = (Jedis) connection.getNativeConnection();
				return jedis.setnx(key, value);
			}
		});
	}

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
	public Boolean expire(final String key, final int seconds) {
		return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}

	/**
	 * 删除指令
	 * 
	 * @author William
	 * @param key
	 * @since JDK 1.7
	 */
	public void del(String key) {
		redisTemplate.delete(key);
	}

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
	public Boolean expireAt(String key, final Date date) {
		return redisTemplate.expireAt(key, date);
	}

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
	public Long getExpire(String key, final TimeUnit timeUnit) {
		return redisTemplate.getExpire(key, timeUnit);
	}
}