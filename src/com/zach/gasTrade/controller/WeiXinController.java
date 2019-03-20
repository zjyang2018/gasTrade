package com.zach.gasTrade.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.common.cache.CacheService;
import com.common.utils.StringUtil;
import com.common.wx.TokenUtil;
import com.common.wx.WeiXinConstant;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.DataResult;
import com.zach.gasTrade.common.PageResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.dto.AdminUserDto;
import com.zach.gasTrade.service.AdminUserService;
import com.zach.gasTrade.vo.AdminUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "微信相关api")
@Controller
public class WeiXinController {
	
	@ApiOperation(value = "加载微信公众号js配置 接口", notes = "请求方式:GET\\n返回参数字段说明||appId:公众号appId,timestamp:随机时间戳 ,nonceStr:随机字符串,signature:签名字符串\\n")
	@ResponseBody
	@RequestMapping(value = "/weixin/loadConfig", method = RequestMethod.GET)
	public DataResult wxsdk_config() {
		DataResult result = DataResult.initResult();
		// 2、获取Ticket
		String jsapi_ticket = TokenUtil.getWXTicket();
		// 3、时间戳和随机字符串
		String noncestr = TokenUtil.getNonceStr();
		String timestamp = TokenUtil.getTimeStamp();
		// System.out.println("accessToken:"+accessToken+"\njsapi_ticket:"+jsapi_ticket+"\n时间戳："+timestamp+"\n随机字符串："+noncestr);
		// 5、将参数排序并拼接字符串
		// String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr +
		// "×tamp=" + timestamp + "&url=" + url;
		String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "×tamp=" + timestamp;
		// 6、将字符串进行sha1加密
		// String signature = SHAUtils.SHA1(str);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid", WeiXinConstant.appId);
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("signature", str);
		result.setData(map);
		
		return result;
	}
}
