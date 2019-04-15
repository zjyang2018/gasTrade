package com.zach.gasTrade.netpay.xiaomage;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class APIList {// 本部分是所有开放web api的具体实现逻辑
	private String ApiDomain = "";

	public APIList(String domain) {
		ApiDomain = domain;
	}// 不允许直接修改类内接口域名，构造方法实例APIList(string domain)来间接为其赋值，保证其安全性

	/**
	 * RegisterDevice：设备注册
	 * 
	 * @param DeviceID
	 *            设备标识
	 * @param DeviceName
	 *            设备名称
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param GUID
	 *            商家唯一标识
	 * @param NonceStr
	 *            随机字符串
	 * @param TimeStamp
	 *            时间戳
	 * @return success,Msg,ReObj
	 */
	public String RegisterDevice(String DeviceID, String DeviceName, String DockingDeviceID, String GUID,
			String NonceStr, String TimeStamp, String DockingSecret) {
		// 设备注册请求地址
		String postfix = "/api/RegisterDevice/";
		String RequestURL = ApiDomain + postfix;
		String operarion = "RegisterDevice";

		Map<String, String> urlmap = new HashMap<String, String>();
		urlmap.put("DeviceID", DeviceID);
		urlmap.put("DeviceName", DeviceName);
		urlmap.put("DockingDeviceID", DockingDeviceID);
		urlmap.put("GUID", GUID);
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("TimeStamp", TimeStamp);

		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * SignInOrOut：2.签到/签退
	 * 
	 * @param DeviceID
	 *            设备标识
	 * @param DeviceSecret
	 *            设备秘钥
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param LogID
	 *            签到ID
	 * @param NonceStr
	 *            随机字符串
	 * @param SitePwd
	 *            收银员密码
	 * @param SiteUserID
	 *            收银员账号
	 * @param TimeStamp
	 *            时间戳
	 * @param DockingSecret
	 *            对接秘钥
	 * @return success,Msg,ReObj
	 */
	public String SignInOrOut(String DeviceID, String DeviceSecret, String DockingDeviceID, int LogID, String NonceStr,
			String SitePwd, int SiteUserID, String TimeStamp, String DockingSecret) {
		// 签到/签退请求地址
		String postfix = "/api/SignInOrOut/";
		String RequestURL = ApiDomain + postfix;
		String operarion = "SignInOrOut";

		Map<String, String> urlmap = new HashMap<String, String>();
		urlmap.put("DeviceID", DeviceID);
		urlmap.put("DeviceSecret", DeviceSecret);
		urlmap.put("DockingDeviceID", DockingDeviceID);
		urlmap.put("LogID", String.valueOf(LogID));
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("SitePwd", SitePwd);
		urlmap.put("SiteUserID", String.valueOf(SiteUserID));
		urlmap.put("TimeStamp", TimeStamp);

		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * AcquirePay：3.扫买家码收款
	 * 
	 * @param DeviceID
	 *            设备标识
	 * @param DeviceSecret
	 *            设备秘钥
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param NonceStr
	 *            随机字符串
	 * @param PayMoney
	 *            收款金额
	 * @param ScanPayNo
	 *            付款码
	 * @param SiteUserID
	 *            收银员账号
	 * @param TimeStamp
	 *            时间戳
	 * @param DockingSecret
	 *            对接秘钥
	 * @return success,Msg,ReObj
	 */
	public String AcquirePay(String DeviceID, String DeviceSecret, String DockingDeviceID, String NonceStr,
			BigDecimal PayMoney, String ScanPayNo, int SiteUserID, String TimeStamp, String DockingSecret) {
		// 扫码收款请求地址
		String postfix = "/api/AcquirePay/";
		String RequestURL = ApiDomain + postfix;
		String operarion = "AcquirePay";

		Map<String, String> urlmap = new HashMap<String, String>();
		urlmap.put("DeviceID", DeviceID);
		urlmap.put("DeviceSecret", DeviceSecret);
		urlmap.put("DockingDeviceID", DockingDeviceID);
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("TimeStamp", TimeStamp);
		urlmap.put("PayMoney", String.valueOf(PayMoney));
		urlmap.put("ScanPayNo", ScanPayNo);
		urlmap.put("SiteUserID", String.valueOf(SiteUserID));

		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * QrcodePay：4.出示二维码收款
	 * 
	 * @param DeviceID
	 *            设备标识
	 * @param DeviceSecret
	 *            设备秘钥
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param DockingOrderID
	 *            第三方订单号
	 * @param NonceStr
	 *            随机字符串
	 * @param PayMoney
	 *            付款金额
	 * @param PayType
	 *            支付类型
	 * @param SiteUserID
	 *            收银员账号
	 * @param TimeStamp
	 *            时间戳
	 * @param DockingSecret
	 *            对接秘钥
	 * @return success,Msg,ReObj
	 */
	public String QrcodePay(String DeviceID, String DeviceSecret, String DockingDeviceID, String DockingOrderID,
			String NonceStr, BigDecimal PayMoney, String PayType, int SiteUserID, String TimeStamp,
			String DockingSecret) {
		// 二维码收款请求地址
		String postfix = "/api/QrcodePay/";
		String RequestURL = ApiDomain + postfix;
		String operarion = "QrcodePay";

		Map<String, String> urlmap = new HashMap<String, String>();
		urlmap.put("DeviceID", DeviceID);
		urlmap.put("DeviceSecret", DeviceSecret);
		urlmap.put("DockingDeviceID", DockingDeviceID);
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("TimeStamp", TimeStamp);
		urlmap.put("DockingOrderID", DockingOrderID);
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("PayMoney", String.valueOf(PayMoney));
		urlmap.put("PayType", PayType);
		urlmap.put("SiteUserID", String.valueOf(SiteUserID));

		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * GetPayState：5.获取交易订单支付状态
	 * 
	 * @param DeviceID
	 *            设备标识
	 * @param DeviceSecret
	 *            设备秘钥
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param NonceStr
	 *            随机字符串
	 * @param OrderID
	 *            交易订单号
	 * @param TimeStamp
	 *            时间戳
	 * @param DockingSecret
	 *            对接秘钥
	 * @return success,Msg,ReObj
	 */
	public String GetPayState(String DeviceID, String DeviceSecret, String DockingDeviceID, String NonceStr,
			String OrderID, String TimeStamp, String DockingSecret) {
		// 获取交易订单支付状态请求地址
		String postfix = "/api/GetPayState/";
		String RequestURL = ApiDomain + postfix;
		String operarion = "GetPayState";

		Map<String, String> urlmap = new HashMap<String, String>();
		urlmap.put("DeviceID", DeviceID);
		urlmap.put("DeviceSecret", DeviceSecret);
		urlmap.put("DockingDeviceID", DockingDeviceID);
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("TimeStamp", TimeStamp);
		urlmap.put("OrderID", OrderID);

		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * GetPayInfo：6.获取成功订单明细
	 * 
	 * @param DeviceID
	 *            设备标识
	 * @param DeviceSecret
	 *            设备秘钥
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param NonceStr
	 *            随机字符串
	 * @param OrderID
	 *            交易订单号
	 * @param TimeStamp
	 *            时间戳
	 * @param DockingSecret
	 *            对接秘钥
	 * @return success,Msg,ReObj
	 */
	public String GetPayInfo(String DeviceID, String DeviceSecret, String DockingDeviceID, String NonceStr,
			String OrderID, String TimeStamp, String DockingSecret) {
		// 获取成功订单明细请求地址
		String postfix = "/api/GetPayInfo/";
		String RequestURL = ApiDomain + postfix;
		String operarion = "GetPayInfo";

		Map<String, String> urlmap = new HashMap<String, String>();
		urlmap.put("DeviceID", DeviceID);
		urlmap.put("DeviceSecret", DeviceSecret);
		urlmap.put("DockingDeviceID", DockingDeviceID);
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("TimeStamp", TimeStamp);
		urlmap.put("OrderID", OrderID);

		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Refund：7.退款
	 * 
	 * @param DeviceID
	 *            设备标识
	 * @param DeviceSecret
	 *            设备秘钥
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param NonceStr
	 *            随机字符串
	 * @param OperatePass
	 *            退款授权密码
	 * @param OrderID
	 *            交易订单号
	 * @param RefundOrderID
	 *            退款订单号
	 * @param Remoney
	 *            退款金额
	 * @param TimeStamp
	 *            时间戳
	 * @param DockingSecret
	 *            对接秘钥
	 * @return success,Msg,ReObj
	 */
	public String Refund(String DeviceID, String DeviceSecret, String DockingDeviceID, String NonceStr,
			String OperatePass, String OrderID, String RefundOrderID, BigDecimal Remoney, String TimeStamp,
			String DockingSecret) {
		// 退款签名请求地址
		String postfix = "/api/Refund/";
		String RequestURL = ApiDomain + postfix;
		String operarion = "Refund";

		Map<String, String> urlmap = new HashMap<String, String>();
		urlmap.put("DeviceID", DeviceID);
		urlmap.put("DeviceSecret", DeviceSecret);
		urlmap.put("DockingDeviceID", DockingDeviceID);
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("TimeStamp", TimeStamp);
		urlmap.put("OperatePass", OperatePass);
		urlmap.put("OrderID", OrderID);
		urlmap.put("RefundOrderID", RefundOrderID);
		urlmap.put("Remoney", String.valueOf(Remoney));

		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * TicketUse：8.卡券核销
	 * 
	 * @param DeviceID
	 *            设备标识
	 * @param DeviceSecret
	 *            设备秘钥
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param NonceStr
	 *            随机字符串
	 * @param PayMoney
	 *            核销金额
	 * @param ScanTicketno
	 *            卡券号
	 * @param TimeStamp
	 *            时间戳
	 * @param DockingSecret
	 *            对接秘钥
	 * @return success,Msg,ReObj
	 */
	public String TicketUse(String DeviceID, String DeviceSecret, String DockingDeviceID, String NonceStr,
			BigDecimal PayMoney, String ScanTicketno, String TimeStamp, String DockingSecret) {
		// 卡券核销请求地址
		String postfix = "/api/TicketUse/";
		String RequestURL = ApiDomain + postfix;
		String operarion = "TicketUse";

		Map<String, String> urlmap = new HashMap<String, String>();
		urlmap.put("DeviceID", DeviceID);
		urlmap.put("DeviceSecret", DeviceSecret);
		urlmap.put("DockingDeviceID", DockingDeviceID);
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("TimeStamp", TimeStamp);
		urlmap.put("PayMoney", String.valueOf(PayMoney));
		urlmap.put("ScanTicketno", ScanTicketno);

		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * AggregateOrder：9.聚合二维码收款
	 * 
	 * @param DeviceID
	 *            设备标识
	 * @param DeviceSecret
	 *            设备秘钥
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param DockingOrderID
	 *            第三方订单号
	 * @param NonceStr
	 *            随机字符串
	 * @param PayMoney
	 *            收款金额
	 * @param SiteUserID
	 *            收银员账号
	 * @param TimeStamp
	 *            时间戳
	 * @param DockingSecret
	 *            对接秘钥
	 * @return success,Msg,ReObj
	 */
	public String AggregateOrder(String DeviceID, String DeviceSecret, String DockingDeviceID, String DockingOrderID,
			String NonceStr, BigDecimal PayMoney, int SiteUserID, String TimeStamp, String DockingSecret) {
		// 聚合支付请求地址
		String postfix = "/api/AggregateOrder/";
		String RequestURL = ApiDomain + postfix;
		String operarion = "AggregateOrder";

		Map<String, String> urlmap = new HashMap<String, String>();
		urlmap.put("DeviceID", DeviceID);
		urlmap.put("DeviceSecret", DeviceSecret);
		urlmap.put("DockingDeviceID", DockingDeviceID);
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("TimeStamp", TimeStamp);
		urlmap.put("DockingOrderID", DockingOrderID);
		urlmap.put("PayMoney", String.valueOf(PayMoney));
		urlmap.put("SiteUserID", String.valueOf(SiteUserID));

		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * UnifiedOrder：10.wap收款
	 * 
	 * @param DeviceID
	 *            设备标识
	 * @param DeviceSecret
	 *            设备秘钥
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param DockingOrderID
	 *            交易订单号
	 * @param NonceStr
	 *            随机字符串
	 * @param PayMoney
	 *            支付金额
	 * @param PayType
	 *            支付类型
	 * @param SiteUserID
	 *            收银员账号
	 * @param TimeStamp
	 *            时间戳
	 * @param DockingSecret
	 *            对接秘钥
	 * @return success,Msg,ReObj
	 */
	public String UnifiedOrder(String DeviceID, String DeviceSecret, String DockingDeviceID, String DockingOrderID,
			String NonceStr, BigDecimal PayMoney, String PayType, int SiteUserID, String TimeStamp,
			String DockingSecret) {
		// wap收款请求地址
		String postfix = "/api/UnifiedOrder/";
		String RequestURL = ApiDomain + postfix;
		String operarion = "UnifiedOrder";

		Map<String, String> urlmap = new HashMap<String, String>();
		urlmap.put("DeviceID", DeviceID);
		urlmap.put("DeviceSecret", DeviceSecret);
		urlmap.put("DockingDeviceID", DockingDeviceID);
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("TimeStamp", TimeStamp);
		urlmap.put("DockingOrderID", DockingOrderID);
		urlmap.put("PayMoney", String.valueOf(PayMoney));
		urlmap.put("PayType", PayType);
		urlmap.put("SiteUserID", String.valueOf(SiteUserID));
		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * CloseOrder：11.订单关闭
	 * 
	 * @param DeviceID
	 *            设备标识
	 * @param DeviceSecret
	 *            设备秘钥
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param NonceStr
	 *            随机字符串
	 * @param OrderID
	 *            交易订单号
	 * @param SiteUserID
	 *            收银员账号
	 * @param TimeStamp
	 *            时间戳
	 * @param DockingSecret
	 *            对接秘钥
	 * @return success,Msg,ReObj
	 */
	public String CloseOrder(String DeviceID, String DeviceSecret, String DockingDeviceID, String NonceStr,
			String OrderID, int SiteUserID, String TimeStamp, String DockingSecret) {
		// 订单关闭请求地址
		String postfix = "/api/CloseOrder/";
		String RequestURL = ApiDomain + postfix;
		String operarion = "CloseOrder";

		Map<String, String> urlmap = new HashMap<String, String>();
		urlmap.put("DeviceID", DeviceID);
		urlmap.put("DeviceSecret", DeviceSecret);
		urlmap.put("DockingDeviceID", DockingDeviceID);
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("TimeStamp", TimeStamp);
		// urlmap.put("OrderID", OrderID);
		urlmap.put("DockingOrderID", OrderID);
		urlmap.put("SiteUserID", String.valueOf(SiteUserID));

		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * UseKouBeiCard：12.口碑单品卡券核销
	 * 
	 * @param DeviceID
	 *            设备标识
	 * @param DeviceSecret
	 *            设备秘钥
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param NonceStr
	 *            随机字符串
	 * @param ScanTicketno
	 *            卡券号
	 * @param TimeStamp
	 *            时间戳
	 * @param DockingSecret
	 *            对接秘钥
	 * @return success,Msg,ReObj
	 */
	public String UseKouBeiCard(String DeviceID, String DeviceSecret, String DockingDeviceID, String NonceStr,
			String ScanTicketno, String TimeStamp, String DockingSecret) {
		// 口碑单品卡券核销请求地址
		String postfix = "/api/UseKouBeiCard/";
		String RequestURL = ApiDomain + postfix;
		String operarion = "UseKouBeiCard";

		Map<String, String> urlmap = new HashMap<String, String>();
		urlmap.put("DeviceID", DeviceID);
		urlmap.put("DeviceSecret", DeviceSecret);
		urlmap.put("DockingDeviceID", DockingDeviceID);
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("TimeStamp", TimeStamp);
		urlmap.put("ScanTicketno", ScanTicketno);

		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * DownloadBill：13.对账单
	 * 
	 * @param DeviceID
	 *            设备标识
	 * @param DeviceSecret
	 *            设备秘钥
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param NonceStr
	 *            随机字符串
	 * @param TimeStamp
	 *            时间戳
	 * @param TradeDate
	 *            交易状态
	 * @param DockingSecret
	 *            对接秘钥
	 * @return success,Msg,ReObj
	 */
	public String DownloadBill(String DeviceID, String DeviceSecret, String DockingDeviceID, String NonceStr,
			String TimeStamp, String TradeDate, String DockingSecret) {
		// 请求地址
		String postfix = "/api/DownloadBill/";
		String RequestURL = ApiDomain + postfix;
		String operarion = "DownloadBill";

		Map<String, String> urlmap = new HashMap<String, String>();
		urlmap.put("DeviceID", DeviceID);
		urlmap.put("DeviceSecret", DeviceSecret);
		urlmap.put("DockingDeviceID", DockingDeviceID);
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("TimeStamp", TimeStamp);
		urlmap.put("TradeDate", TradeDate);

		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * AsynchronInform：14.订单异步通知
	 * 
	 * @param NonceStr
	 *            随机字符串
	 * @param OrderID
	 *            交易订单号
	 * @param OrderMoney
	 *            订单金额
	 * @param OrderState
	 *            订单状态
	 * @param PayTime
	 *            支付时间
	 * @param PayType
	 *            支付类型
	 * @param TimeStamp
	 *            时间戳
	 * @param DockingSecret
	 *            对接秘钥
	 * @return success
	 */
	public String AsynchronInform(String NonceStr, String OrderID, BigDecimal OrderMoney, String OrderState,
			String PayTime, String PayType, String TimeStamp, String DockingSecret) {
		// 请求地址
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入合作商请求地址：");
		String RequestURL = scanner.next();
		String operarion = "AsynchronInform";

		Map<String, String> urlmap = new HashMap<String, String>();

		urlmap.put("NonceStr", NonceStr);
		urlmap.put("OrderID", OrderID);
		urlmap.put("OrderMoney", String.valueOf(OrderMoney));
		urlmap.put("OrderState", OrderState);
		urlmap.put("PayTime", PayTime);
		urlmap.put("PayType", PayType);
		urlmap.put("TimeStamp", TimeStamp);

		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * AppletPay：15.小程序支付
	 * 
	 * @param AppID
	 *            小程序AppID
	 * @param DeviceID
	 *            设备标识
	 * @param DeviceSecret
	 *            设备秘钥
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param DockingOrderID
	 *            交易订单号
	 * @param NonceStr
	 *            随机字符串
	 * @param OpenID
	 *            消费者信息
	 * @param PayMoney
	 *            支付金额
	 * @param SiteUserID
	 *            收银员账号
	 * @param TimeStamp
	 *            时间戳
	 * @param DockingSecret
	 *            对接秘钥
	 * @return success,Msg,ReObj
	 */
	public String AppletPay(String AppID, String DeviceID, String DeviceSecret, String DockingDeviceID,
			String DockingOrderID, String NonceStr, String OpenID, BigDecimal PayMoney, int SiteUserID,
			String TimeStamp, String DockingSecret) {
		// 小程序支付请求地址
		String postfix = "/api/AppletPay/";
		String RequestURL = ApiDomain + postfix;
		String operarion = "AppletPay";

		Map<String, String> urlmap = new HashMap<String, String>();
		urlmap.put("DeviceID", DeviceID);
		urlmap.put("DeviceSecret", DeviceSecret);
		urlmap.put("DockingDeviceID", DockingDeviceID);
		urlmap.put("NonceStr", NonceStr);
		urlmap.put("TimeStamp", TimeStamp);
		urlmap.put("AppID", AppID);
		urlmap.put("DockingOrderID", DockingOrderID);
		urlmap.put("OpenID", OpenID);
		urlmap.put("PayMoney", String.valueOf(PayMoney));
		urlmap.put("SiteUserID", String.valueOf(SiteUserID));

		try {
			return ResponseResult(urlmap, operarion, DockingSecret, RequestURL);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 调用接口且接收响应（公共部分）
	 * 
	 * @param urlmap
	 *            参数值顺序表
	 * @param operarion
	 *            接口名
	 * @param DockingSecret
	 *            对接秘钥
	 * @param RequestURL
	 *            接口请求地址
	 * @return 响应结果
	 */
	public static String ResponseResult(Map urlmap, String operarion, String DockingSecret, String RequestURL) {
		// 签名逻辑
		String stringA = stringA(urlmap);// 字母序
		System.out.println(stringA);
		String sign = DataUtil.GetSign(stringA, DockingSecret);
		// 请求响应
		String RequestStr = stringA + "&sign=" + sign;
		String result = null;
		try {
			result = HttpProxy.HttpPost(RequestStr, RequestURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 以下stringA、GetkeyvalueTreemap、GetkeyList、GetvalueList四个方法，共同实现了“读取参数名-》排ASCII序-》拼接saringA”了
	 */

	/* 拼接stringA */
	public static String stringA(Map urlmap) {
		List<Integer> intP = new ArrayList<Integer>();
		intP.add(1);
		intP.add(10);
		intP.add(3);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Set<String> set = urlmap.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			params.add(new BasicNameValuePair(key, urlmap.get(key).toString()));

		}
		Collections.sort(intP, new Comparator<Integer>() {
			public int compare(Integer arg0, Integer arg1) {
				return arg1.compareTo(arg0);
			}
		});
		Collections.sort(params, new Comparator<NameValuePair>() {
			public int compare(NameValuePair arg0, NameValuePair arg1) {
				String name0 = arg0.getName();
				String name1 = arg1.getName();
				return name0.compareToIgnoreCase(name1);
			}
		});
		StringBuilder builder = new StringBuilder();
		for (NameValuePair item : params) {
			String key = item.getName();
			String val = item.getValue();
			builder.append(key + "=" + val);
			builder.append("&");
		}
		String str = builder.toString();
		str = str.substring(0, str.length() - 1); // 去掉最后一个&
		return str;
	}

}
