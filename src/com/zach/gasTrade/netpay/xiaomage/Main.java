package com.zach.gasTrade.netpay.xiaomage;

import java.math.BigDecimal;
import java.util.Scanner;

//本部分实现所有开放接口的请求与响应
public class Main {
	public static void mainTest(String[] args) {
		String domain = "http://api.docking.aduer.com";// 接口请求域
		APIList ApiInstance = new APIList(domain);

		Scanner scanner = new Scanner(System.in);// 定义键盘读取器

		/*
		 * DockingDeviceID,UserStr,DeviceName,GUID,DockingSecret
		 * 以上几个全局变量的读取，可以在第一次手动键入得到设备秘钥devicesecret后，将几个全局变量赋值常量，并将scanner读值语句注释化，简化后续操作
		 */

		// 获取对接注册设备标识开头
		String DockingDeviceID = "339aduer90fb71";// 申请第三方对接表格中有此字段值

		// 获取设备标识
		String UserStr = "20190413";
		String DeviceID = DataUtil.GetDeviceID(DockingDeviceID, UserStr);

		// 获取设备名称
		String DeviceName = "tuoke20190413";

		// 获取商家唯一标识
		String GUID = "13Bxkw2jLP4";// 商户入件成功后可以获取到

		// 获取随机字符串
		String nonceStr = DataUtil.GetNonceStr();

		// 获取时间戳
		String timestamp = DataUtil.GetTimestamp();

		// 获取对接秘钥
		String DockingSecret = "9bf083972d054ac36164e1c96717c1eb0d64513a";// 申请第三方对接表格中有此字段值

		System.out.println(
				" = = = = = = = = = = = = = = = = = = = 全 = = 局 = = 变 = = 量 = = 输 = = 入 = = 结 = = 束 = = = = = = = = = = = = = = = = = = = ");

		// 测试web api接口
		System.out.println("接口选项：");
		System.out.println("1.设备注册；2.签到/签退；3.扫码收款；4.二维码收款；5.获取交易订单支付状态；6.获取成功订单明细；7.退款；8.卡券核销；");
		System.out.println("9.聚合二维码收款；10.wap收款；11.订单关闭；12.口碑单品卡券核销；13.对账单；14.订单异步通知；15.小程序支付");
		System.out.println("请选择要的测试的接口序号（回车键结束）：");
		int UserAction = scanner.nextInt();
		switch (UserAction) {
		case (1):
			// 调用设备注册接口
			ApiInstance.RegisterDevice(DeviceID, DeviceName, DockingDeviceID, GUID, nonceStr, timestamp, DockingSecret);
			System.out.println(">>>>>>>>>>>>>>提示：请妥善备份设备注册返回的参数devicesecret，后续接口需其参与。<<<<<<<<<<<<<<");
			break;
		case (2): {
			// 设备秘钥devicesecret
			System.out.println("请输入设备注册返回的参数devicesecret（回车键结束）");
			String devicesecret = scanner.next();
			// 登录账号SiteUserID
			System.out.println("请输入收银员账号SiteUserID（回车键结束）");
			int SiteUserID = scanner.nextInt();
			// 登陆密码SitePwd
			System.out.println("请输入收银员登陆密码SitePwd（回车键结束）");
			String SitePwd = scanner.next();
			// 操作类型LogID
			System.out.println("请输入操作：0-签到；具体LogID-签退（回车键结束）");
			int LogID = scanner.nextInt();
			// 调用签到/签退接口
			ApiInstance.SignInOrOut(DeviceID, devicesecret, DockingDeviceID, LogID, nonceStr, SitePwd, SiteUserID,
					timestamp, DockingSecret);
		}
			break;
		case (3): {
			// 设备秘钥devicesecret
			System.out.println("请输入设备注册返回的参数devicesecret（回车键结束）");
			String devicesecret = scanner.next();
			// 登录账号SiteUserID
			System.out.println("请输入收银员账号SiteUserID（回车键结束）");
			int SiteUserID = scanner.nextInt();
			// 收款金额PayMoney
			System.out.println("请输入收款金额PayMoney（回车键结束）");
			BigDecimal PayMoney = scanner.nextBigDecimal();
			// 扫描的付款码ScanpayNo（18位）
			System.out.println("请输入付款码ScanpayNo（回车键结束）");
			String ScanpayNo = scanner.next();
			// 调用扫码收款接口
			ApiInstance.AcquirePay(DeviceID, devicesecret, DockingDeviceID, nonceStr, PayMoney, ScanpayNo, SiteUserID,
					timestamp, DockingSecret);
		}
			break;
		case (4): {
			// 设备秘钥devicesecret
			System.out.println("请输入设备注册返回的参数devicesecret（回车键结束）");
			String devicesecret = scanner.next();
			// 登录账号SiteUserID
			System.out.println("请输入收银员账号SiteUserID（回车键结束）");
			int SiteUserID = scanner.nextInt();
			// 收款金额PayMoney
			System.out.println("请输入收款金额PayMoney（回车键结束）");
			BigDecimal PayMoney = scanner.nextBigDecimal();
			// 支付类型PayType
			System.out.println("请输入支付类型PayType：1.微信支付；2.支付宝；3.百度支付（回车键结束）");
			String PayType = scanner.next();
			// 第三方订单号DockingOrderID
			System.out.println("请输入第三方订单号DockingOrderID（回车键结束）");
			String DockingOrderID = scanner.next();
			// 调用二维码收款接口
			ApiInstance.QrcodePay(DeviceID, devicesecret, DockingDeviceID, DockingOrderID, nonceStr, PayMoney, PayType,
					SiteUserID, timestamp, DockingSecret);
		}
			break;
		case (5): {
			// 设备秘钥devicesecret
			System.out.println("请输入设备注册返回的参数devicesecret（回车键结束）");
			String devicesecret = scanner.next();
			// 交易订单号OrderID
			System.out.println("请输入交易订单号OrderID（回车键结束）");
			String OrderID = scanner.next();
			// 调用获取交易订单支付状态接口
			ApiInstance.GetPayState(DeviceID, devicesecret, DockingDeviceID, nonceStr, OrderID, timestamp,
					DockingSecret);
		}
			break;
		case (6): {
			// 设备秘钥devicesecret
			System.out.println("请输入设备注册返回的参数devicesecret（回车键结束）");
			String devicesecret = scanner.next();
			// 交易订单号OrderID
			System.out.println("请输入交易订单号OrderID（回车键结束）");
			String OrderID = scanner.next();
			// 调用获取成功订单明细接口
			ApiInstance.GetPayInfo(DeviceID, devicesecret, DockingDeviceID, nonceStr, OrderID, timestamp,
					DockingSecret);
		}
			break;
		case (7): {
			// 设备秘钥devicesecret
			// System.out.println("请输入设备注册返回的参数devicesecret（回车键结束）");
			// String devicesecret = scanner.next();
			String devicesecret = "2a60d81b02ff0b6271fef1fab744013df08a4549";
			// 交易订单号OrderID
			// System.out.println("请输入交易订单号OrderID（回车键结束）");
			// String OrderID = scanner.next();
			String OrderID = "39008720711513397656";
			// 退款订单号RefundOrderID
			// System.out.println("请输入退款订单号RefundOrderID（回车键结束）");
			// String RefundOrderID = scanner.next();
			String RefundOrderID = "20190414R0000002";
			// 退款金额Remoney
			// System.out.println("请输入退款金额Remoney（回车键结束）");
			// BigDecimal Remoney = scanner.nextBigDecimal();
			BigDecimal Remoney = BigDecimal.valueOf(29.99);
			// 退款授权密码Operatepass
			// System.out.println("请输入退款授权密码Operatepass（回车键结束）");
			// String Operatepass = scanner.next();
			String Operatepass = "660600";
			// 调用退款接口
			ApiInstance.Refund(DeviceID, devicesecret, DockingDeviceID, nonceStr, Operatepass, OrderID, RefundOrderID,
					Remoney, timestamp, DockingSecret);
		}
			break;
		case (8): {
			// 设备秘钥devicesecret
			System.out.println("请输入设备注册返回的参数devicesecret（回车键结束）");
			String devicesecret = scanner.next();
			// 卡券号Scanticketno
			System.out.println("请输入卡券号Scanticketno（回车键结束）");
			String Scanticketno = scanner.next();
			// 核销金额Paymoney
			System.out.println("请输入核销金额Paymoney（回车键结束）");
			BigDecimal PayMoney = scanner.nextBigDecimal();
			// 调用卡券核销接口
			ApiInstance.TicketUse(DeviceID, devicesecret, DockingDeviceID, nonceStr, PayMoney, Scanticketno, timestamp,
					DockingSecret);
		}
			break;
		case (9): {
			// 设备秘钥devicesecret
			System.out.println("请输入设备注册返回的参数devicesecret（回车键结束）");
			String devicesecret = scanner.next();
			// 登录账号SiteUserID
			System.out.println("请输入收银员账号SiteUserID（回车键结束）");
			int SiteUserID = scanner.nextInt();
			// 收款金额PayMoney
			System.out.println("请输入收款金额PayMoney（回车键结束）");
			BigDecimal PayMoney = scanner.nextBigDecimal();
			// 第三方订单号DockingOrderID
			System.out.println("请输入第三方订单号DockingOrderID（回车键结束）");
			String DockingOrderID = scanner.next();
			// 调用聚合支付接口
			ApiInstance.AggregateOrder(DeviceID, devicesecret, DockingDeviceID, DockingOrderID, nonceStr, PayMoney,
					SiteUserID, timestamp, DockingSecret);
		}
			break;
		case (10): {
			// 设备秘钥devicesecret
			// System.out.println("请输入设备注册返回的参数devicesecret（回车键结束）");
			// String devicesecret = scanner.next();
			String devicesecret = "2a60d81b02ff0b6271fef1fab744013df08a4549";
			// 登录账号SiteUserID
			// System.out.println("请输入收银员账号SiteUserID（回车键结束）");
			// int SiteUserID = scanner.nextInt();
			int SiteUserID = 198629;
			// 收款金额PayMoney
			// System.out.println("请输入收款金额PayMoney（回车键结束）");
			// BigDecimal PayMoney = scanner.nextBigDecimal();
			BigDecimal PayMoney = BigDecimal.valueOf(0.01);
			// 第三方订单号DockingOrderID
			// System.out.println("请输入第三方订单号DockingOrderID（回车键结束）");
			// String DockingOrderID = scanner.next();
			String DockingOrderID = "20190414P00000001";
			// 支付类型PayType
			// System.out.println("请输入支付类型PayType：wx-微信支付；ali-支付宝（回车键结束）");
			// String PayType = scanner.next();
			String PayType = "wx";
			// 调用wap支付接口
			ApiInstance.UnifiedOrder(DeviceID, devicesecret, DockingDeviceID, DockingOrderID, nonceStr, PayMoney,
					PayType, SiteUserID, timestamp, DockingSecret);
		}
			break;
		case (11): {
			// 设备秘钥devicesecret
			System.out.println("请输入设备注册返回的参数devicesecret（回车键结束）");
			String devicesecret = scanner.next();
			// 登录账号SiteUserID
			System.out.println("请输入收银员账号SiteUserID（回车键结束）");
			int SiteUserID = scanner.nextInt();
			// 交易订单号OrderID
			System.out.println("请输入交易订单号OrderID（回车键结束）");
			String OrderID = scanner.next();
			// 调用订单关闭接口
			ApiInstance.CloseOrder(DeviceID, devicesecret, DockingDeviceID, nonceStr, OrderID, SiteUserID, timestamp,
					DockingSecret);
		}
			break;
		case (12): {
			// 设备秘钥devicesecret
			System.out.println("请输入设备注册返回的参数devicesecret（回车键结束）");
			String devicesecret = scanner.next();
			// 卡券号Scanticketno
			System.out.println("请输入卡券号Scanticketno（回车键结束）");
			String Scanticketno = scanner.next();
			// 调用口碑单品卡券核销接口
			ApiInstance.UseKouBeiCard(DeviceID, devicesecret, DockingDeviceID, nonceStr, Scanticketno, timestamp,
					DockingSecret);
		}
			break;
		case (13): {
			// 设备秘钥devicesecret
			System.out.println("请输入设备注册返回的参数devicesecret（回车键结束）");
			String devicesecret = scanner.next();
			// 交易日期TradeDate
			System.out.println("请输入交易日期TradeDate，形如20180726（回车键结束）");
			String TradeDate = scanner.next();
			// 调用对账单接口
			ApiInstance.DownloadBill(DeviceID, devicesecret, DockingDeviceID, nonceStr, timestamp, TradeDate,
					DockingSecret);
		}
			break;
		case (14): {
			// 交易订单号OrderID
			System.out.println("请输入交易订单号OrderID（回车键结束）");
			String OrderID = scanner.next();
			// 订单金额OrderMoney
			System.out.println("请输入订单金额OrderMoney（回车键结束）");
			BigDecimal OrderMoney = scanner.nextBigDecimal();
			// 支付时间PayTime
			System.out.println("请输入支付时间PayTime，形如20180726（回车键结束）");
			String PayTime = scanner.next();
			// 订单状态OrderState
			System.out.println("请输入订单状态OrderState：SUCCESS-支付成功；REFUND-已退款；NOTPAY-未支付；CLOSED-已关闭（回车键结束）");
			String OrderState = scanner.next();
			// 支付类型PayType
			System.out.println("请输入支付类型PayType：wx-微信支付；ali-支付宝;other-其他（回车键结束）");
			String PayType = scanner.next();
			// 调用订单异步通知接口
			ApiInstance.AsynchronInform(nonceStr, OrderID, OrderMoney, OrderState, PayTime, PayType, timestamp,
					DockingSecret);
		}
			break;
		case (15): {
			// 设备秘钥devicesecret
			System.out.println("请输入设备注册返回的参数devicesecret（回车键结束）");
			String devicesecret = scanner.next();
			// 登录账号SiteUserID
			System.out.println("请输入收银员账号SiteUserID（回车键结束）");
			int SiteUserID = scanner.nextInt();
			// 收款金额PayMoney
			System.out.println("请输入收款金额PayMoney（回车键结束）");
			BigDecimal PayMoney = scanner.nextBigDecimal();
			// 小程序AppID
			System.out.println("请输入小程序AppID：");
			String AppID = scanner.next();
			// 小程序OpenID
			System.out.println("请输入小程序OpenID：");
			String OpenID = scanner.next();
			// 第三方订单号DockingOrderID
			System.out.println("请输入第三方订单号DockingOrderID（回车键结束）");
			String DockingOrderID = scanner.next();
			// 调用小程序支付接口
			ApiInstance.AppletPay(AppID, DeviceID, devicesecret, DockingDeviceID, DockingOrderID, nonceStr, OpenID,
					PayMoney, SiteUserID, timestamp, DockingSecret);
		}
			break;
		default:
			System.out.println("您输入接口选项不存在，请重新确认后输入");
			break;
		}
		System.out.println(
				" = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
	}
}
