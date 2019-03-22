package com.common.wx;

import java.security.MessageDigest;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.cache.CacheService;
import com.common.context.SpringContextHolder;
import com.common.http.HttpsUtil;
import com.common.utils.StringUtil;
import com.common.wx.bean.AccessToken;

public class TokenUtil {

	private static Logger log = LoggerFactory.getLogger(TokenUtil.class);

	/**
	 * 获得微信 AccessToken access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token。
	 * 开发者需要access_token的有效期目前为2个小时，需定时刷新，重复获取将导致上次获取 的access_token失效。
	 * （此处我是把token存在Redis里面了）
	 */

	public static AccessToken getWXToken() {
		// redis获取access_token
		CacheService cacheService = SpringContextHolder.getBean("redisCacheService");
		AccessToken accessToken = cacheService.get(WeiXinConstant.accessTokenKey);
		log.info("redis获取到accessToken==>" + JSON.toJSONString(accessToken));
		if (accessToken == null) {
			String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
					+ WeiXinConstant.appId + "&secret=" + WeiXinConstant.appSecret;
			JSONObject jsonObject = HttpsUtil.httpsRequest(tokenUrl, "GET", null);
			log.info("获取微信token参数==>jsonObject:" + jsonObject);
			accessToken = new AccessToken();
			if (null != jsonObject) {
				try {
					accessToken.setAccessToken(jsonObject.getString("access_token"));
					accessToken.setExpiresin(jsonObject.getInteger("expires_in"));
					// 保存access_token
					cacheService.add(WeiXinConstant.accessTokenKey, accessToken, accessToken.getExpiresin());
				} catch (Exception e) {
					accessToken = null;
					// 获取token失败
					log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"),
							jsonObject.getString("errmsg"));
				}
			}
		}
		return accessToken;
	}

	/**
	 * 获取jsapiTicket jsapi_ticket是公众号用于调用微信JS接口的临时票据。正常情况下，jsapi_ticket的有效期为7200秒，
	 * 通过access_token来获取。由于获取jsapi_ticket的api调用次数非常有限，频繁刷新jsapi_ticket会导致api调用受限，
	 * 影响自身业务，开发者必须在自己的服务全局缓存jsapi_ticket 。
	 * 
	 * @return
	 */
	public static String getWXTicket() {
		// redis获取ticket
		CacheService cacheService = SpringContextHolder.getBean("redisCacheService");
		String ticket = cacheService.get(WeiXinConstant.ticket);
		log.info("redis获取到ticket==>" + ticket);
		if (StringUtil.isNullOrEmpty(ticket)) {
			AccessToken accessToken = TokenUtil.getWXToken();
			String acess_token = JSONObject.toJSONString(accessToken);
			String urlStr = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + acess_token
					+ "&type=jsapi";
			JSONObject jsonObject = HttpsUtil.httpsRequest(urlStr, "GET", null);
			log.info("获取微信ticket参数==>jsonObject:" + jsonObject);
			if (null != jsonObject) {
				try {
					ticket = jsonObject.getString("ticket");
					int expiresin = jsonObject.getInteger("expires_in");
					// 保存ticket
					cacheService.add(WeiXinConstant.ticket, ticket, expiresin*1000);
				} catch (Exception e) {
					// 获取token失败
					log.error("获取ticket失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"),
							jsonObject.getString("errmsg"));
				}
			}

		}
		return ticket;
	}
}