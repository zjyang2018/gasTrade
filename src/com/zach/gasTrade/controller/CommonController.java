package com.zach.gasTrade.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.common.cache.CacheService;
import com.common.utils.StringUtil;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.dto.UserDto;
import com.zach.gasTrade.service.CustomerUserService;

@Controller
public class CommonController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private CustomerUserService customerUserService;

	@Autowired
	private CacheService cacheService;

	protected UserDto getCurrentUser(HttpServletRequest request) {
		String wxOpenId = request.getHeader("Authorization");
		logger.info("Header获取到wxOpenId==>" + wxOpenId);
		if (StringUtil.isNotNullAndNotEmpty(wxOpenId)) {
			String userStr = cacheService.get(Constants.USER_INFO_KEY + wxOpenId);
			logger.info("打印用户信息==" + userStr);
			if (StringUtil.isNull(userStr)) {
				UserDto user = customerUserService.getUserInfo(wxOpenId);
				if (user != null) {
					cacheService.add(Constants.USER_INFO_KEY + wxOpenId, JSON.toJSONString(user));
					return user;
				}
			} else {
				return JSON.parseObject(userStr, UserDto.class);
			}
		}
		return null;
	}

	protected String getWxOpenId(HttpServletRequest request) {
		String wxOpenId = request.getHeader("Authorization");
		logger.info("Header获取到wxOpenId==>" + wxOpenId);
		return wxOpenId;
	}

}
