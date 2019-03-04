package com.zach.gasTrade.netpay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zach.gasTrade.vo.ProductVo;

@Component
public class PayService {
	private Logger logger = Logger.getLogger(getClass());

	// 读取资源配置参数
	@Value("${apiUrl}")
	private String APIurl;
	@Value("${mid}")
	private String mid;
	@Value("${tid}")
	private String tid;
	@Value("${instMid}")
	private String instMid;
	@Value("${msgSrc}")
	private String msgSrc;

	@Value("${msgType_refund}")
	private String msgType_refund;

	@Value("${msgType_secureCancel}")
	private String msgType_secureCancel;

	@Value("${msgType_secureComplete}")
	private String msgType_secureComplete;

	@Value("${msgType_close}")
	private String msgType_close;

	@Value("${msgType_query}")
	private String msgType_query;

	@Value("${md5Key}")
	private String md5Key;

	@Value("${apiUrl_makeOrder}")
	private String apiUrl_makeOrder;

	@Value("${notifyUrl}")
	private String notifyUrl;

	@Value("${returnUrl}")
	private String returnUrl;

	// 公众支付方式类型
	final private static String msgType = "WXPay.jsPay";
	// 订单生成分割值
	final public static String msgSrcId = "3194TK";

	// final private static String apiUrl_makeOrder =
	// "https://qr-test2.chinaums.com/netpay-portal/webpay/pay.do";
	//
	// final private static String notifyUrl =
	// "http://172.27.49.240:8080/publicpay/notifyUrl.do";
	//
	// final private static String returnUrl =
	// "http://172.27.49.240:8080/publicpay/returnUrl.do";

	public String pay(String orderId, BigDecimal amount) {
		// 准备商品信息
		List<ProductVo> goodsList = new ArrayList<ProductVo>();
		ProductVo bean = new ProductVo();
		bean.setProductName("煤气");
		bean.setProductDesc("好运来煤气");
		goodsList.add(bean);
		// goodsList.add(new Goods("0002", "Goods Name", 2L, 200L, "Auto", "goods
		// body"));

		// 组织请求报文，具体参数请参照接口文档，以下仅作模拟
		JSONObject json = new JSONObject();
		json.put("mid", mid);
		json.put("tid", tid);
		json.put("msgType", msgType);
		json.put("msgSrc", msgSrc);
		json.put("instMid", instMid);

		// json.put("merOrderId", Util.genMerOrderId(msgSrcId));
		json.put("merOrderId", orderId);
		// json.put("sceneType", "AND_WAP");
		// json.put("merAppName", "全民付");
		// json.put("merAppId", "http://www.chinaums.com");
		json.put("totalAmount", amount.setScale(2, BigDecimal.ROUND_DOWN).multiply(BigDecimal.valueOf(100)).intValue());
		json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

		// json.put("goods", goodsList);
		json.put("notifyUrl", notifyUrl);
		json.put("returnUrl", returnUrl);
		// 不担保交易
		json.put("secureTransaction", false);

		String url = UnoinPayUtil.makeOrderRequest(json, md5Key, apiUrl_makeOrder);

		return url;
	}

	public Map<String, String> payNotice(String notifyStr, Map<String, Object> param) {
		// String md5Key = "fcAmtnx7MwismjWNhNKdHC44mNXtnEQeJkRrhKJwyrW2ysRR";

		String preNotifyStrs = notifyStr;
		String trueColors = null;
		try {
			trueColors = URLDecoder.decode(preNotifyStrs, "utf-8");
			trueColors = trueColors.replace("¬", "&not");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.info("对支付通知字串进行解码后为:" + trueColors);

		// 将解码后的支付通知字串 转成map
		String[] splitPreStrs = trueColors.split("&");
		String[] subStr = null;
		Map<String, String> map = new HashMap<String, String>();
		for (String str : splitPreStrs) {
			subStr = str.split("=");
			if (subStr[0].equals("sign") || subStr[0] == "sign") {
				logger.info("支付通知中附带签名：" + subStr[0] + "：" + subStr[1]);
				// 组装待签字串时 去除原有待签字串中的sign
				continue;
			}
			if (subStr[0].equals("billQRCode") || subStr[0] == "billQRCode") {
				map.put(subStr[0], subStr[1] + "=" + subStr[2]);
			} else {
				map.put(subStr[0], subStr[1]);
			}
			// System.out.println(str);
		}
		logger.info("将解码后的支付通知字串 转成map为:" + map);

		// 生成待签字串 和 sign
		String preStrNew = UnoinPayUtil.buildSignString(map); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		String preStrNew_md5Key = preStrNew + md5Key;
		logger.info("生成待签字串为：" + preStrNew_md5Key);

		String sign = DigestUtils.md5Hex(UnoinPayUtil.getContentBytes(preStrNew_md5Key)).toUpperCase();
		logger.info("生成签名sign为：" + sign);

		// 返回结果
		String resultStr = null;
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("preStr", preStrNew_md5Key);
		resultMap.put("sign", sign);
		// resultStr = JSONObject.fromObject(resultMap).toString();

		return map;

	}

	/**
	 * 支付通知
	 * 
	 * @param params
	 * @return
	 */
	public boolean checkSign(Map<String, String> params) {
		return UnoinPayUtil.checkSign2(md5Key, params);
	}

	/**
	 * 退款
	 * 
	 * @param params
	 * @return
	 */
	public JSONObject refund(String orderId, BigDecimal amount) {
		// 组织请求报文
		JSONObject json = new JSONObject();
		json.put("mid", mid);
		json.put("tid", tid);
		json.put("msgType", msgType_refund);
		json.put("msgSrc", msgSrc);
		json.put("instMid", instMid);
		json.put("merOrderId", orderId);

		// 是否要在商户系统下单，看商户需求 createBill()

		json.put("refundAmount", amount.multiply(BigDecimal.valueOf(100)).intValue());
		json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

		Map<String, String> paramsMap = UnoinPayUtil.jsonToMap(json);
		paramsMap.put("sign", UnoinPayUtil.makeSign(md5Key, paramsMap));
		logger.info("paramsMap：" + paramsMap);

		String strReqJsonStr = JSON.toJSONString(paramsMap);
		logger.info("strReqJsonStr:" + strReqJsonStr);

		// 调用银商平台获取二维码接口
		HttpURLConnection httpURLConnection = null;
		BufferedReader in = null;
		PrintWriter out = null;
		// OutputStreamWriter out = null;
		Map<String, String> resultMap = new HashMap<String, String>();
		if (!StringUtils.isNotBlank(APIurl)) {
			resultMap.put("errCode", "URLFailed");
			return JSONObject.parseObject(JSON.toJSONString(resultMap));
		}

		try {
			URL url = new URL(APIurl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestProperty("Content_Type", "application/json");
			httpURLConnection.setRequestProperty("Accept_Charset", "UTF-8");
			httpURLConnection.setRequestProperty("contentType", "UTF-8");
			// 发送POST请求参数
			out = new PrintWriter(httpURLConnection.getOutputStream());
			// out = new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8");
			out.write(strReqJsonStr);
			// out.println(strReqJsonStr);
			out.flush();

			// 读取响应
			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				StringBuffer content = new StringBuffer();
				String tempStr = null;
				in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
				while ((tempStr = in.readLine()) != null) {
					content.append(tempStr);
				}
				logger.info("content:" + content.toString());

				// 转换成json对象
				com.alibaba.fastjson.JSONObject respJson = JSON.parseObject(content.toString());
				String resultCode = respJson.getString("errCode");
				if ("SUCCESS".equals(resultCode)) {
					resultMap.put("errCode", resultCode);
					resultMap.put("respStr", respJson.toString());
					return JSONObject.parseObject(JSON.toJSONString(resultMap));
				} else {
					throw new RuntimeException(respJson.getString("errMsg"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("errCode", "HttpURLException");
			resultMap.put("msg", "调用银商接口出现异常：" + e.toString());
			// return JSONObject.parseObject(JSON.toJSONString(resultMap));
			throw new RuntimeException(e.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
			httpURLConnection.disconnect();
		}
		return null;
	}

}
