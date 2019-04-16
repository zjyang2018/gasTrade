package com.common.wx;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.http.HttpsUtil;
import com.common.wx.bean.AccessToken;
import com.common.wx.bean.TemplateData;
import com.common.wx.bean.TemplateMessage;

public class WeiXinUtils {
	private static Logger log = LoggerFactory.getLogger(WeiXinUtils.class);

	/**
	 * 根据微信openId 获取用户是否订阅
	 * 
	 * @param openId
	 *            微信openId
	 * @return 是否订阅该公众号标识
	 */
	public static Integer getSubscribeStateByOpenId(String openId) {
		String tmpurl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
				+ TokenUtil.getCacheWXToken().getAccessToken() + "&openid=" + openId;

		JSONObject result = HttpsUtil.httpsRequest(tmpurl, "GET", null);
		JSONObject resultJson = new JSONObject(result);
		log.error("获取用户是否订阅 errcode:{} errmsg:{}", resultJson.getInteger("errcode"), resultJson.getString("errmsg"));
		String errmsg = (String) resultJson.get("errmsg");
		if (errmsg == null) {
			// 用户是否订阅该公众号标识（0代表此用户没有关注该公众号 1表示关注了该公众号）。
			Integer subscribe = (Integer) resultJson.get("subscribe");
			return subscribe;
		}
		return -1;
	}

	public static void pushWeiXinMsg(String accessToken, TemplateMessage templateMessage) {
		String tmpurl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;

		JSONObject json = new JSONObject();
		json.put("touser", templateMessage.getOpenId());
		json.put("template_id", templateMessage.getTemplateId());
		json.put("url", templateMessage.getUrl());
		json.put("topcolor", templateMessage.getTopcolor());
		json.put("data", templateMessage.getTemplateData());
		log.info("打印参数:" + json.toJSONString());
		try {
			JSONObject result = HttpsUtil.httpsRequest(tmpurl, "POST", json.toJSONString());
			// JSONObject result = new JSONObject();
			// JSONObject resultJson = new JSONObject(result);
			log.info("推送微信消息返回参数：" + result.toJSONString());
			String errmsg = (String) result.get("errmsg");
			if (!"ok".equals(errmsg)) { // 如果为errmsg为ok，则代表发送成功，公众号推送信息给用户了。
				throw new RuntimeException("微信公众号消息推送失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("微信公众号消息推送异常,推送参数为:" + JSON.toJSONString(templateMessage), e);
		}
	}

	public static void main(String[] args) {
		AccessToken access = TokenUtil.getCacheWXToken();
		TemplateMessage templateMessage = TemplateMessage.New();
		templateMessage.setOpenId("oBD9n6PMmJ9znS2AX6AsP-pR_Tzo");
		templateMessage.setTemplateId("ykujHmHTnJTSEK0iJWVQKNq_TooXRdcaOKBsYQMAZLo");
		templateMessage.setUrl("");
		templateMessage.setTopcolor("#696969");

		Map<String, TemplateData> msgData = new HashMap<String, TemplateData>();
		msgData.put("first", new TemplateData("尊敬的客户，您好，您的订单已支付完成。", "#696969"));
		msgData.put("keyword1", new TemplateData("3194TK201903041213026613246939", "#696969"));
		msgData.put("keyword2", new TemplateData("2019/03/12", "#696969"));
		msgData.put("keyword3", new TemplateData("微信支付", "#696969"));
		msgData.put("keyword4", new TemplateData("100.00", "#696969"));
		msgData.put("remark", new TemplateData("查看详情", "#696969"));

		templateMessage.setTemplateData(msgData);
		// 推送消息
		WeiXinUtils.pushWeiXinMsg(access.getAccessToken(), templateMessage);
	}
}