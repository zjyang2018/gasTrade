/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.common.http.HttpUrlClient;
import com.common.seq.SerialGenerator;
import com.common.utils.StringUtil;
import com.zach.gasTrade.dao.CustomerUserDao;
import com.zach.gasTrade.service.CustomerUserService;
import com.zach.gasTrade.vo.CustomerUserVo;

@Service("customerUserService")
public class CustomerUserServiceImpl implements CustomerUserService {

	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private CustomerUserDao customerUserDao;

	/**
	 * 总数
	 * 
	 * @param customerUserVo
	 * @return
	 */
	public int getCustomerUserCount(CustomerUserVo customerUserVo) {
		return customerUserDao.getCustomerUserCount(customerUserVo);
	}

	/**
	 * 分页列表
	 * 
	 * @param customerUserVo
	 * @return
	 */
	public List<CustomerUserVo> getCustomerUserPage(CustomerUserVo customerUserVo) {
		return customerUserDao.getCustomerUserPage(customerUserVo);
	}

	/**
	 * 列表
	 * 
	 * @param customerUserVo
	 * @return
	 */
	public List<CustomerUserVo> getCustomerUserList(CustomerUserVo customerUserVo) {
		return customerUserDao.getCustomerUserList(customerUserVo);
	}

	/**
	 * 根据条件查询一条信息
	 * 
	 * @param customerUserVo
	 * @return
	 */
	public CustomerUserVo getCustomerUserBySelective(CustomerUserVo customerUserVo) {
		return customerUserDao.getCustomerUserBySelective(customerUserVo);
	}

	/**
	 * 保存
	 * 
	 * @param customerUserVo
	 */
	public int save(CustomerUserVo customerUserVo) {
		String id = SerialGenerator.getUUID();
		Date nowTime = new Date();
		customerUserVo.setId(id);
		customerUserVo.setCreateTime(nowTime);
		customerUserVo.setUpdateTime(nowTime);

		return customerUserDao.save(customerUserVo);
	}

	/**
	 * 更新
	 * 
	 * @param customerUserVo
	 */
	public int update(CustomerUserVo customerUserVo) {
		Date nowTime = new Date();
		customerUserVo.setUpdateTime(nowTime);

		return customerUserDao.update(customerUserVo);
	}

	/**
	 * 删除
	 * 
	 * @param customerUserVo
	 */
	public int delete(CustomerUserVo customerUserVo) {
		return customerUserDao.delete(customerUserVo);
	}

	@Override
	public Map<String, Object> getWeiXinUserInfo(String code) {
		Map<String, String> weixinUser = this.oauth2GetOpenid(code);
		String openId = weixinUser.get("openId");
		Map<String, Object> returnData = new HashMap<String, Object>();
		returnData.put("openId", openId);

		CustomerUserVo customerUserVo = new CustomerUserVo();
		customerUserVo.setWxOpenId(openId);
		CustomerUserVo customerUser = this.getCustomerUserBySelective(customerUserVo);
		if (customerUser != null && StringUtil.isNotNullAndNotEmpty(customerUser.getPhoneNumber())) {
			returnData.put("isRegister", true);
			//this.save(customerUserVo);
		} else {
			returnData.put("isRegister", false);
		}

		return returnData;
	}

	private Map<String, String> oauth2GetOpenid(String code) {
		// 自己的配置appid（公众号进行查阅）
		String appid = "wxafd1d8527f4f9985";
		// 自己的配置APPSECRET;（公众号进行查阅）
		String appsecret = "5a876f6ea9485126a6530386ff258d8d";
		// 拼接用户授权接口信息
		String requestUrl = com.zach.gasTrade.common.Constants.GET_WEBAUTH_URL.replace("APPID", appid)
				.replace("SECRET", appsecret).replace("CODE", code);
		// 存储获取到的授权字段信息
		Map<String, String> result = new HashMap<String, String>();
		try {
			JSONObject OpenidJSONO = HttpUrlClient.doGetStr(requestUrl);
			logger.info("获取微信授权返回结果值:" + OpenidJSONO.toString());
			// OpenidJSONO可以得到的内容：access_token expires_in refresh_token openid scope
			String Openid = String.valueOf(OpenidJSONO.get("openid"));
			String AccessToken = String.valueOf(OpenidJSONO.get("access_token"));
			// 用户保存的作用域
			String Scope = String.valueOf(OpenidJSONO.get("scope"));
			String refresh_token = String.valueOf(OpenidJSONO.get("refresh_token"));
			result.put("openId", Openid);
			result.put("AccessToken", AccessToken);
			result.put("scope", Scope);
			result.put("refresh_token", refresh_token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
