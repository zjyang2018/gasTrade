package com.zach.gasTrade.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.common.cache.CacheService;
import com.common.cache.redis.RedisCacheService;
import com.common.wx.TokenUtil;
import com.common.wx.WeiXinConstant;
import com.common.wx.WeiXinSignUtil;
import com.zach.gasTrade.common.DataResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "微信相关api")
@Controller
public class WeiXinController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private CacheService cacheService;

	@ApiOperation(value = "加载微信公众号js配置 接口", notes = "请求方式:GET\\n返回参数字段说明||appId:公众号appId,timestamp:随机时间戳 ,nonceStr:随机字符串,signature:签名字符串\\n")
	@RequestMapping(value = "/weixin/loadConfig", method = RequestMethod.GET)
	@ResponseBody
	public DataResult wxsdk_config(HttpServletRequest request) {
		DataResult result = DataResult.initResult();
		// 1、获取请求url
		// String url = request.getRequestURI();
		String url = "http://www.yourtk.com/";
		// 2、获取Ticket
		String jsapi_ticket = TokenUtil.getWXTicket();
		// 3、时间戳和随机字符串
		String noncestr = WeiXinSignUtil.getNonceStr();
		String timestamp = WeiXinSignUtil.getTimeStamp();
		// System.out.println("accessToken:"+accessToken+"\njsapi_ticket:"+jsapi_ticket+"\n时间戳："+timestamp+"\n随机字符串："+noncestr);
		// 5、将参数排序并拼接字符串
		String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
		// 6、将字符串进行sha1加密
		logger.info("sha1加密==前==字符串==>" + str);
		String signature = WeiXinSignUtil.sha1Hex(str);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid", WeiXinConstant.appId);
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("signature", signature);
		result.setData(map);

		return result;
	}

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
		if(cacheService.get(key)!=null) {
			result.setData(cacheService.get(key));
		}
		return result;
	}
}
