package com.zach.gasTrade.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.common.cache.CacheService;
import com.common.utils.StringUtil;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.dto.UserDto;

@Controller
public class CommonController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private CacheService cacheService;

	protected UserDto getCurrentUser(HttpServletRequest request) {
		String wxOpenId = request.getHeader("wxOpenId");
		logger.info("Header获取到wxOpenId==>" + wxOpenId);
		if (StringUtil.isNotNullAndNotEmpty(wxOpenId)) {
			return cacheService.get(Constants.USER_INFO_KEY + wxOpenId);
		}
		return null;
	}

}
