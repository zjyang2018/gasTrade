package com.zach.gasTrade.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.wx.TokenUtil;
import com.common.wx.WeiXinConstant;
import com.common.wx.WeiXinSignUtil;
import com.zach.gasTrade.common.DataResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "微信相关api")
@Controller
public class WeiXinController {
	private Logger logger = Logger.getLogger(getClass());
	
	@ApiOperation(value = "加载微信公众号js配置 接口", notes = "请求方式:GET\\n返回参数字段说明||appId:公众号appId,timestamp:随机时间戳 ,nonceStr:随机字符串,signature:签名字符串\\n")
	@RequestMapping(value = "/weixin/loadConfig", method = RequestMethod.GET)
	@ResponseBody
	public DataResult wxsdk_config(HttpServletRequest request) {
		DataResult result = DataResult.initResult();
		// 1、获取请求url
		String url = request.getRequestURI();
		// 2、获取Ticket
		String jsapi_ticket = TokenUtil.getWXTicket();
		// 3、时间戳和随机字符串
		String noncestr = WeiXinSignUtil.getNonceStr();
		String timestamp = WeiXinSignUtil.getTimeStamp();
		// System.out.println("accessToken:"+accessToken+"\njsapi_ticket:"+jsapi_ticket+"\n时间戳："+timestamp+"\n随机字符串："+noncestr);
		// 5、将参数排序并拼接字符串
		 String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr +
		 "timestamp=" + timestamp+"url=" + url;
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
}
