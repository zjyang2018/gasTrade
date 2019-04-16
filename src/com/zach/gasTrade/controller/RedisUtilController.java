package com.zach.gasTrade.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.cache.CacheService;
import com.common.wx.TokenUtil;
import com.zach.gasTrade.common.DataResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "redis工具api")
@Controller
public class RedisUtilController {

	@Autowired
	private CacheService cacheService;

	@ApiOperation(value = "删除redis对应key的值", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "key", value = "redis", required = true, paramType = "query", dataType = "String") })
	@RequestMapping(value = "/redis/deletKeyValue", method = RequestMethod.GET)
	@ResponseBody
	public DataResult deletRedisKeyValue(HttpServletRequest request) {
		DataResult result = DataResult.initResult();
		String key = request.getParameter("key");

		cacheService.del(key);
		String value = cacheService.get(key);
		if (value == null) {
			result.setData("空值");
		} else if ("".equals(value)) {
			result.setData("空字符");
		} else if ("null".equals(value)) {
			result.setData("空字符");
		} else {
			result.setData(value);
		}
		return result;
	}

	@ApiOperation(value = "读取redis对应key的值", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "key", value = "redis", required = true, paramType = "query", dataType = "String") })
	@RequestMapping(value = "/redis/readKeyValue", method = RequestMethod.GET)
	@ResponseBody
	public DataResult readRedisKeyValue(HttpServletRequest request) {
		DataResult result = DataResult.initResult();
		String key = request.getParameter("key");
		if (cacheService.get(key) != null) {
			result.setData(cacheService.get(key));
		}
		return result;
	}

	@ApiOperation(value = "获取token", notes = "")
	@RequestMapping(value = "/redis/getToken", method = RequestMethod.GET)
	@ResponseBody
	public DataResult getToken(HttpServletRequest request) {
		DataResult result = DataResult.initResult();
		TokenUtil.getCacheWXToken();
		return result;
	}

}
