package com.zach.gasTrade.netpay.xiaomage;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zach.gasTrade.netpay.UnoinPayUtil;

@Component
public class PayService {
	private Logger logger = Logger.getLogger(getClass());

	private static final String devicesecret = "2a60d81b02ff0b6271fef1fab744013df08a4549";

	private static final String DockingDeviceID = "339aduer90fb71";

	private String DeviceID = DataUtil.GetDeviceID(DockingDeviceID, "20190413");

	// 收银员ID
	private int SiteUserID = 198629;

	// 收银员密码
	private String sitePwd = "258287 ";

	private static final String GUID = "13Bxkw2jLP4";

	// 退款授权码
	private static final String Operatepass = "660600";

	private String DockingSecret = "9bf083972d054ac36164e1c96717c1eb0d64513a";

	// 接口请求域
	private String domain = "http://api.docking.aduer.com";

	private APIList apiInstance = new APIList(domain);

	public String pay(String orderId, BigDecimal amount) {
		String result = apiInstance.UnifiedOrder(DeviceID, devicesecret, DockingDeviceID, orderId,
				DataUtil.GetNonceStr(), amount, "wx", SiteUserID, DataUtil.GetTimestamp(), DockingSecret);

		logger.info("订单号:" + orderId + ",支付接口返回==>" + result);
		JSONObject json = JSON.parseObject(result);
		if (1 == json.getIntValue("success")) {
			JSONObject reObj = json.getJSONObject("ReObj");
			return reObj.getString("Url");
		}
		throw new RuntimeException(json.getString("msg"));
	}

	public void refund(String payNo, String refundOrderId, BigDecimal amount) {
		String result = apiInstance.Refund(DeviceID, devicesecret, DockingDeviceID, DataUtil.GetNonceStr(), Operatepass,
				payNo, refundOrderId, amount, DataUtil.GetTimestamp(), DockingSecret);
		JSONObject json = JSON.parseObject(result);
		if (1 != json.getIntValue("success")) {
			throw new RuntimeException(json.getString("msg"));
		}
	}

	public JSONObject queryPayStatus(String orderId) {
		// 调用获取交易订单支付状态接口
		String result = apiInstance.GetPayState(DeviceID, devicesecret, DockingDeviceID, DataUtil.GetNonceStr(),
				orderId, DataUtil.GetTimestamp(), DockingSecret);
		// {"success":1,"msg":"","ReObj":""}
		// 1：成功，0：支付中，2：已退款，-10016：已关闭，-10000：收款失败
		return JSON.parseObject(result);
	}

	public void closeOrderPay(String orderId) {
		// 调用订单关闭接口
		String result = apiInstance.CloseOrder(DeviceID, devicesecret, DockingDeviceID, DataUtil.GetNonceStr(), orderId,
				SiteUserID, DataUtil.GetTimestamp(), DockingSecret);
		JSONObject json = JSON.parseObject(result);
		if (1 != json.getIntValue("success")) {
			throw new RuntimeException(json.getString("msg"));
		}
	}

	public boolean checkSign(Map<String, String> params) {
		String originalSign = params.get("sign");
		String stringA = UnoinPayUtil.buildSignString(params);
		String sign = DataUtil.GetSign(stringA, DockingSecret);
		logger.info("计算签名sign为：" + sign);
		// 返回结果
		return originalSign.equals(sign);
	}

}
