package com.common.cache;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.common.cache.exception.CacheValidateException;

public class CacheValidator {

	public static void addValidate(String key, Object value) {
		if (StringUtils.isBlank( key )) {
			throw new CacheValidateException( "key不能为空" );
		}
		if (value == null) {
			throw new CacheValidateException( "value不能为空" );
		}
		if (!(value instanceof Serializable)) {
			throw new CacheValidateException( "value不能序列化" );
		}
	}

	public static void addValidate(String key, Object value, int timeout) {
		addValidate( key, value );
		if (timeout < 0) {
			throw new CacheValidateException( "超时时间不能为负数" );
		}
	}

	public static void mapEntryValidate(Map.Entry<?, ?> entry) {
		if (entry == null) {
			throw new CacheValidateException( "entry不能为空" );
		}
		if (entry.getKey() == null) {
			throw new CacheValidateException( "entry key不能为空" );
		}
		if (!(entry.getKey() instanceof String)) {
			throw new CacheValidateException( "entry key必须是String类型" );
		}
		if (entry.getValue() == null) {
			throw new CacheValidateException( "entry value不能为空" );
		}
	}

}