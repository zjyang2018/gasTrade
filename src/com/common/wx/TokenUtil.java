package com.common.wx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.common.http.HttpsUtil;
import com.common.wx.bean.AccessToken;

public class TokenUtil {

	private static Logger log = LoggerFactory.getLogger(TokenUtil.class);

	/**
	 * 获得微信 AccessToken access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。
	 * 开发者需要access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取 的access_token失效。
	 * （此处我是把token存在Redis里面了）
	 */

	public static AccessToken getWXToken() {

		String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ WeiXinConstant.appId + "&secret=" + WeiXinConstant.appSecret;
		JSONObject jsonObject = HttpsUtil.httpsRequest(tokenUrl, "GET", null);
		System.out.println("获取微信token参数==>jsonObject:" + jsonObject);
		log.info("获取微信token参数==>jsonObject:" + jsonObject);
		AccessToken access_token = new AccessToken();
		if (null != jsonObject) {
			try {
				access_token.setAccessToken(jsonObject.getString("access_token"));
				access_token.setExpiresin(jsonObject.getInteger("expires_in"));
			} catch (Exception e) {
				access_token = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"),
						jsonObject.getString("errmsg"));
			}
		}

		return access_token;
	}

}