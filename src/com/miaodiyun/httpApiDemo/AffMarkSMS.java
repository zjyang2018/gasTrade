package com.miaodiyun.httpApiDemo;

import com.miaodiyun.httpApiDemo.common.Config;
import com.miaodiyun.httpApiDemo.common.HttpUtil;

/**
 * 会员营销短信接口
 * 
 * @ClassName: AffMarkSMS
 * @Description: 会员营销短信接口
 *
 */
public class AffMarkSMS {
	private static String operation = "/affMarkSMS/sendSMS";

	private static String accountSid = Config.ACCOUNT_SID;
	private static String to = "13923469163";
	// 邵总:18969623222
	// 谢总:13798787250
	// 张总:13923469163
	// private static String smsContent = "【能邦科技】您的设备[人体感应器]于2017年12月5日检测到
	// 紧急状态，请及时处理，并通知相关人员采取措施。";
	private static String smsContent = "【能邦科技】您的设备[人体感应器]于[2017-12-5]日检测到异常情况，处于[紧急报警]状态，请通知相关人员采取措施，以便及时处理。";

	/**
	 * 会员营销短信
	 */
	public static void execute() {
		String url = Config.BASE_URL + operation;
		String body = "accountSid=" + accountSid + "&to=" + to + "&smsContent=" + smsContent
				+ HttpUtil.createCommonParam();

		// 提交请求
		String result = HttpUtil.post(url, body);
		System.out.println("result:" + System.lineSeparator() + result);
	}
}
