/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.TimeUnit;
import com.common.seq.SerialGenerator;
import com.common.utils.DateTimeUtils;
import com.common.utils.MapHelper;
import com.common.utils.StringUtil;
import com.common.wx.TokenUtil;
import com.common.wx.WeiXinUtils;
import com.common.wx.bean.AccessToken;
import com.common.wx.bean.TemplateData;
import com.common.wx.bean.TemplateMessage;
import com.zach.gasTrade.dao.AdminUserDao;
import com.zach.gasTrade.dao.CustomerUserDao;
import com.zach.gasTrade.dao.DeliveryLocationHistoryDao;
import com.zach.gasTrade.dao.DeliveryUserDao;
import com.zach.gasTrade.dao.OrderDeliveryRecordDao;
import com.zach.gasTrade.dao.OrderInfoDao;
import com.zach.gasTrade.dao.ProductDao;
import com.zach.gasTrade.dto.CustomerOrderGenerateInfoDto;
import com.zach.gasTrade.dto.CustomerOrderInfoDto;
import com.zach.gasTrade.dto.DeliveryMonitorDto;
import com.zach.gasTrade.dto.DeliveryOrderInfoDto;
import com.zach.gasTrade.dto.OrderFinanceStatisticsDto;
import com.zach.gasTrade.dto.OrderInfoDetailDto;
import com.zach.gasTrade.dto.OrderListDto;
import com.zach.gasTrade.netpay.xiaomage.PayService;
import com.zach.gasTrade.service.OrderInfoService;
import com.zach.gasTrade.vo.CustomerUserVo;
import com.zach.gasTrade.vo.DeliveryLocationHistoryVo;
import com.zach.gasTrade.vo.DeliveryUserVo;
import com.zach.gasTrade.vo.OrderDeliveryRecordVo;
import com.zach.gasTrade.vo.OrderInfoVo;
import com.zach.gasTrade.vo.ProductVo;

@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private PayService payService;

	@Autowired
	private OrderInfoDao orderInfoDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private CustomerUserDao customerUserDao;

	@Autowired
	private AdminUserDao adminUserDao;

	@Autowired
	private DeliveryUserDao deliveryUserDao;

	@Autowired
	private DeliveryLocationHistoryDao deliveryLocationHistoryDao;

	@Autowired
	private OrderDeliveryRecordDao orderDeliveryRecordDao;

	/**
	 * 总数
	 * 
	 * @param map
	 * @return
	 */
	public int getOrderInfoCount(OrderInfoVo orderInfoVo) {
		return orderInfoDao.getOrderInfoCount(orderInfoVo);
	}

	/**
	 * 监控总数
	 * 
	 * @param map
	 * @return
	 */
	public int getDeliveryMonitorCount(Map<String, Object> map) {
		return orderInfoDao.getDeliveryMonitorCount(map);
	}

	/**
	 * 监控分页列表
	 * 
	 * @param map
	 * @return
	 */
	public List<DeliveryMonitorDto> getDeliveryMonitorPage(Map<String, Object> map) {
		return orderInfoDao.getDeliveryMonitorPage(map);
	}

	/**
	 * 分页列表
	 * 
	 * @param map
	 * @return
	 */
	public List<OrderListDto> getOrderInfoPage(OrderInfoVo orderInfoVo) {
		return orderInfoDao.getOrderInfoPage(orderInfoVo);
	}

	/**
	 * 列表
	 * 
	 * @param orderInfoVo
	 * @return
	 */
	public List<OrderInfoVo> getOrderInfoList(OrderInfoVo orderInfoVo) {
		return orderInfoDao.getOrderInfoList(orderInfoVo);
	}

	/**
	 * 根据条件查询一条信息
	 * 
	 * @param orderInfoVo
	 * @return
	 */
	public OrderInfoVo getOrderInfoBySelective(OrderInfoVo orderInfoVo) {
		return orderInfoDao.getOrderInfoBySelective(orderInfoVo);
	}

	/**
	 * 订单购买
	 * 
	 * @param orderInfoVo
	 */
	public int save(OrderInfoVo orderInfoVo) {

		return orderInfoDao.save(orderInfoVo);
	}

	/**
	 * 订单购买
	 * 
	 * @param orderInfoVo
	 */
	public String buy(OrderInfoVo orderInfoVo) {
		if (StringUtil.isNotNullAndNotEmpty(orderInfoVo.getOrderId())) {
			OrderInfoVo orderInfo = new OrderInfoVo();
			orderInfo.setOrderId(orderInfoVo.getOrderId());
			orderInfo = orderInfoDao.getOrderInfoBySelective(orderInfo);
			if (orderInfo != null) {
				JSONObject pay = this.payService.pay(orderInfoVo.getOrderId(), orderInfo.getAmount());
				orderInfo.setPayNo(pay.getString("OrderID"));

				if (StringUtil.isNotNullAndNotEmpty(orderInfoVo.getCustomerAddress())) {
					orderInfo.setCustomerAddress(orderInfoVo.getCustomerAddress());
				}
				if (StringUtil.isNotNullAndNotEmpty(orderInfoVo.getRemark())) {
					orderInfo.setRemark(orderInfoVo.getRemark());
				}
				orderInfo.setUpdateTime(new Date());
				orderInfoDao.update(orderInfo);
				// 生成支付url
				return pay.getString("Url");
			}
		}
		String customUserId = orderInfoVo.getCustomerUserId();
		CustomerUserVo customerUserVo = new CustomerUserVo();
		customerUserVo.setId(customUserId);
		CustomerUserVo customerUser = customerUserDao.getCustomerUserBySelective(customerUserVo);
		if (customerUser == null) {
			throw new RuntimeException("该用户不存在," + customUserId);
		}
		ProductVo productParam = new ProductVo();
		productParam.setProductId(orderInfoVo.getProductId());
		ProductVo product = productDao.getProductBySelective(productParam);
		if (product == null) {
			throw new RuntimeException("该产品已下架," + orderInfoVo.getProductId());
		}

		// String orderId = UnoinPayUtil.genMerOrderId(PayService.msgSrcId);
		String orderId = SerialGenerator.getOrderId();
		orderInfoVo.setOrderId(orderId);
		orderInfoVo.setAmount(product.getAmount());
		orderInfoVo.setProductName(product.getProductName());
		// 支付状态:10-未支付,20-已支付
		orderInfoVo.setPayStatus("10");
		// 订单状态:10-未支付,20-已支付,30-已分配,40-已接单,50-派送中,60-派送完成
		orderInfoVo.setOrderStatus("10");
		orderInfoVo.setLatitude(customerUser.getLongitude());
		orderInfoVo.setLongitude(customerUser.getLatitude());
		orderInfoVo.setCustomerAddress(customerUser.getAddress());
		Date now = new Date();
		orderInfoVo.setCreateTime(now);
		orderInfoVo.setUpdateTime(now);
		JSONObject pay = this.payService.pay(orderInfoVo.getOrderId(), product.getAmount());
		orderInfoVo.setPayNo(pay.getString("OrderID"));

		int count = orderInfoDao.save(orderInfoVo);
		if (count != 1) {
			throw new RuntimeException("用户下单失败," + JSON.toJSONString(orderInfoVo));
		}
		// 生成支付url

		return pay.getString("Url");
	}

	/**
	 * 自动分配订单
	 * 
	 */
	public void autoAllotDeliveryOrder() {
		OrderInfoVo orderInfoVo = new OrderInfoVo();
		orderInfoVo.setAllotStatus("10");
		orderInfoVo.setPayStatus("20");
		List<OrderInfoVo> list = orderInfoDao.getOrderInfoList(orderInfoVo);
		for (OrderInfoVo bean : list) {
			autoAllotDeliveryOrder(bean);
		}
	}

	/**
	 * 自动分配订单
	 *
	 * @param orderInfoVo
	 */
	@Transactional
	public void autoAllotDeliveryOrder(OrderInfoVo orderInfoVo) {
		logger.info("自动分配订单参数:" + JSON.toJSONString(orderInfoVo));
		try {
			String customerAddress = orderInfoVo.getCustomerAddress();
			if (StringUtil.isNull(customerAddress)) {
				// 发送短信
				logger.info("客户未填写联系地址,订单号为:" + orderInfoVo.getOrderId());
				return;
			}
			String customerLocation = "";
			JSONObject customerJSONObject = MapHelper.addressToLocation(customerAddress);
			if (customerJSONObject != null) {
				String lng = customerJSONObject.getString("lng");
				String lat = customerJSONObject.getString("lat");
				customerLocation = lat + "," + lng;
			}

			List<DeliveryLocationHistoryVo> list = deliveryLocationHistoryDao.getDeliveryLocationHistoryByRecent();
			String deliveryUserId = "";
			String moveLocation = "";
			double minDistance = 0.00;
			for (DeliveryLocationHistoryVo bean : list) {
				// 空坐标过滤掉
				if (StringUtil.isNull(bean.getLatitude()) || StringUtil.isNull(bean.getLongitude())) {
					continue;
				}
				double distance = MapHelper.GetPointDistance(bean.getLatitude() + "," + bean.getLongitude(),
						customerLocation);
				if (minDistance == 0.00) {
					minDistance = distance;
				}
				if (minDistance > distance) {
					minDistance = distance;
					deliveryUserId = bean.getDeliveryUserId();
					moveLocation = bean.getLatitude() + "," + bean.getLongitude();
				}
			}
			if (deliveryUserId.isEmpty()) {
				return;
			}

			orderInfoVo.setAllotDeliveryId(deliveryUserId);
			orderInfoVo.setAllotStatus("20");
			orderInfoVo.setOrderStatus("30");
			Date now = new Date();
			orderInfoVo.setAllotTime(now);
			orderInfoVo.setUpdateTime(now);
			orderInfoDao.update(orderInfoVo);
			// 添加订单派送记录
			OrderDeliveryRecordVo orderDeliveryRecordVo = new OrderDeliveryRecordVo();
			orderDeliveryRecordVo.setId(SerialGenerator.getUUID());
			orderDeliveryRecordVo.setOrderId(orderInfoVo.getOrderId());
			orderDeliveryRecordVo.setAllotTime(now);

			ProductVo productVo = new ProductVo();
			productVo.setProductId(orderInfoVo.getProductId());
			ProductVo product = productDao.getProductBySelective(productVo);
			// AdminUserVo adminUserVo = new AdminUserVo();
			// adminUserVo.setId(product.getCreateUserId());
			// AdminUserVo adminUser = adminUserDao.getAdminUserBySelective(adminUserVo);
			String startLocationAddress = product.getBusinessAddress();
			if (StringUtil.isNotNullAndNotEmpty(startLocationAddress)) {
				JSONObject jSONObject = MapHelper.addressToLocation(startLocationAddress);
				if (jSONObject != null) {
					String lng = jSONObject.getString("lng");
					String lat = jSONObject.getString("lat");
					orderDeliveryRecordVo.setStartLocation(lat + "," + lng);
				}
			}
			String endLocation = orderInfoVo.getLatitude() + "," + orderInfoVo.getLongitude();
			orderDeliveryRecordVo.setEndLocation(endLocation);
			orderDeliveryRecordVo.setMoveLocation(moveLocation);

			orderDeliveryRecordVo.setCreateTime(now);
			orderDeliveryRecordDao.save(orderDeliveryRecordVo);

			// 推送微信消息-派送通知(客户)
			this.pushWeiXinMessge("10", orderInfoVo);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("自动分配订单异常," + JSON.toJSONString(orderInfoVo), e);
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 更新
	 * 
	 * @param orderInfoVo
	 */
	@Transactional
	public int update(OrderInfoVo orderInfoVo) {
		Date nowTime = new Date();
		orderInfoVo.setUpdateTime(nowTime);
		orderInfoDao.update(orderInfoVo);
		if ("50".equals(orderInfoVo.getOrderStatus()) || "60".equals(orderInfoVo.getOrderStatus())) {
			OrderDeliveryRecordVo orderDeliveryRecordVo = new OrderDeliveryRecordVo();
			orderDeliveryRecordVo.setOrderId(orderInfoVo.getOrderId());
			OrderDeliveryRecordVo recordVo = orderDeliveryRecordDao
					.getOrderDeliveryRecordBySelective(orderDeliveryRecordVo);
			if (recordVo != null) {
				if ("50".equals(orderInfoVo.getOrderStatus())) {
					recordVo.setAcceptTime(orderInfoVo.getDeliveryOrderTime());
					recordVo.setDeliveryTime(orderInfoVo.getDeliveryOrderTime());

					// 推送微信消息-派送通知(客户)
					this.pushWeiXinMessge("20", orderInfoVo);
				} else if ("60".equals(orderInfoVo.getOrderStatus())) {
					recordVo.setCompleteTime(orderInfoVo.getDeliveryCompleteTime());

					// 推送微信消息-派送通知(客户)
					this.pushWeiXinMessge("30", orderInfoVo);
				}
				orderDeliveryRecordDao.update(recordVo);
			}
		} else if ("30".equals(orderInfoVo.getOrderStatus())) {
			OrderDeliveryRecordVo param = new OrderDeliveryRecordVo();
			param.setOrderId(orderInfoVo.getOrderId());
			OrderDeliveryRecordVo recordVo = orderDeliveryRecordDao.getOrderDeliveryRecordBySelective(param);
			if (recordVo != null) {
				return 1;
			}
			// 添加订单派送记录
			OrderDeliveryRecordVo orderDeliveryRecordVo = new OrderDeliveryRecordVo();
			orderDeliveryRecordVo.setId(SerialGenerator.getUUID());
			orderDeliveryRecordVo.setOrderId(orderInfoVo.getOrderId());
			orderDeliveryRecordVo.setAllotTime(nowTime);
			String endLocation = orderInfoVo.getLatitude() + "," + orderInfoVo.getLongitude();
			orderDeliveryRecordVo.setEndLocation(endLocation);
			// orderDeliveryRecordVo.setMoveLocation(moveLocation);

			ProductVo productVo = new ProductVo();
			productVo.setProductId(orderInfoVo.getProductId());
			ProductVo product = productDao.getProductBySelective(productVo);
			String startLocationAddress = product.getBusinessAddress();
			if (StringUtil.isNull(startLocationAddress)) {
				JSONObject jSONObject = MapHelper.addressToLocation(startLocationAddress);
				if (jSONObject != null) {
					String lng = jSONObject.getString("lng");
					String lat = jSONObject.getString("lat");
					orderDeliveryRecordVo.setStartLocation(lat + "," + lng);
				}
			}

			orderDeliveryRecordVo.setCreateTime(nowTime);
			orderDeliveryRecordDao.save(orderDeliveryRecordVo);

			// 推送微信消息-派送通知(客户)
			this.pushWeiXinMessge("10", orderInfoVo);
		}

		return 1;
	}

	/**
	 * 删除
	 * 
	 * @param orderInfoVo
	 */
	public int delete(OrderInfoVo orderInfoVo) {
		return orderInfoDao.delete(orderInfoVo);
	}

	/**
	 * 每日订单数量统计
	 */
	public OrderFinanceStatisticsDto getOrderDayCount(String yestody) {

		return orderInfoDao.getOrderDayCount(yestody);
	}

	@Override
	public boolean notifyHandle(Map<String, String> params) {
		boolean checkRet = this.payService.checkSign(params);
		if (checkRet) {
			// String orderId = params.get("merOrderId");
			// String payNo = params.get("seqId");
			// String realPayAmountStr = params.get("buyerPayAmount");
			// String payTimeStr = params.get("payTime");
			// String refundAmountStr = params.get("refundAmount");
			// if (refundAmountStr != null && !refundAmountStr.isEmpty()) {
			// throw new RuntimeException("退款通知不处理," + JSON.toJSONString(params));
			// }
			// String refundOrderId = params.get("refundOrderId");

			String orderId = params.get("DockingOrderID");
			String payNo = params.get("OrderID");
			String realPayAmountStr = params.get("OrderMoney");
			String payTimeStr = params.get("PayTime");
			String payStatus = params.get("OrderState");
			if (!"SUCCESS".equals(payStatus)) {
				throw new RuntimeException("支付失败");
			}
			// String refundAmountStr = params.get("refundAmount");
			// if (refundAmountStr != null && !refundAmountStr.isEmpty()) {
			// throw new RuntimeException("退款通知不处理," + JSON.toJSONString(params));
			// }
			// String refundOrderId = params.get("refundOrderId");

			OrderInfoVo orderInfoVo = new OrderInfoVo();
			orderInfoVo.setOrderId(orderId);
			OrderInfoVo orderInfo = orderInfoDao.getOrderInfoBySelective(orderInfoVo);
			orderInfo.setPayNo(payNo);
			// orderInfo.setRealPayAmount(new BigDecimal(realPayAmountStr).divide(new
			// BigDecimal(100)));
			orderInfo.setRealPayAmount(new BigDecimal(realPayAmountStr));
			orderInfo.setPayStatus("20");
			Date now = new Date();
			orderInfo.setPayTime(new Date());
			orderInfo.setOrderStatus("20");
			orderInfo.setUpdateTime(now);
			int count = orderInfoDao.update(orderInfo);
			if (count != 1) {
				throw new RuntimeException("支付状态通知失败," + JSON.toJSONString(params));
			}
			CustomerUserVo param = new CustomerUserVo();
			param.setId(orderInfo.getCustomerUserId());
			CustomerUserVo customerUserVo = customerUserDao.getCustomerUserBySelective(param);
			if (customerUserVo == null || StringUtil.isNull(customerUserVo.getWxOpenId())) {
				logger.info("没有获取到用户微信openId,不推送消息,订单号为:" + orderId);
				return checkRet;
			}

			// 推送微信消息-支付通知
			this.pushWeiXinMessge("40", orderInfo);
		} else {
			logger.info("校验签名失败," + JSON.toJSONString(params));
		}
		return checkRet;
	}

	@Override
	public void refundAmount(String orderId) {
		OrderInfoVo orderInfoVo = new OrderInfoVo();
		orderInfoVo.setOrderId(orderId);
		OrderInfoVo orderInfo = orderInfoDao.getOrderInfoBySelective(orderInfoVo);
		if (orderInfo == null) {
			throw new RuntimeException(orderId + ",订单不存在");
		}
		// JSONObject result = payService.refund(orderId, orderInfo.getRealPayAmount());
		// logger.info("退款返回参数值:" + result.toJSONString() + ",退款订单号:" + orderId);
		payService.refund(orderInfo.getPayNo(), orderInfo.getOrderId().replace("P", "R"), orderInfo.getRealPayAmount());
	}

	@Override
	public int getOrderInfoNumber(OrderInfoVo filterMask) {
		// TODO Auto-generated method stub
		return orderInfoDao.getOrderInfoNumber(filterMask);
	}

	@Override
	public List<CustomerOrderInfoDto> getCustomerOrderInfoPage(OrderInfoVo filterMask) {
		// TODO Auto-generated method stub
		return orderInfoDao.getCustomerOrderInfoPage(filterMask);
	}

	@Override
	public List<DeliveryOrderInfoDto> getDeliveryOrderInfoPage(OrderInfoVo filterMask) {
		// TODO Auto-generated method stub
		return orderInfoDao.getDeliveryOrderInfoPage(filterMask);
	}

	@Override
	public CustomerOrderGenerateInfoDto orderGenerate(OrderInfoVo filterMask) {
		// TODO Auto-generated method stub
		String orderId = SerialGenerator.getSerialNum();
		Date nowTime = new Date();
		CustomerUserVo customerUserVo = new CustomerUserVo();
		customerUserVo.setId(filterMask.getCustomerUserId());
		CustomerUserVo customerUser = customerUserDao.getCustomerUserBySelective(customerUserVo);
		filterMask.setOrderId(orderId);
		filterMask.setPayStatus("10");
		filterMask.setAllotStatus("10");
		filterMask.setCustomerAddress(customerUser.getAddress());
		filterMask.setLongitude(customerUser.getLongitude());
		filterMask.setLatitude(customerUser.getLatitude());
		filterMask.setOrderStatus("10");
		filterMask.setRemark("");
		filterMask.setCreateTime(nowTime);
		filterMask.setUpdateTime(nowTime);

		int n = orderInfoDao.save(filterMask);
		if (n == 1) {
			CustomerOrderGenerateInfoDto customerOrderGenerateInfoDto = new CustomerOrderGenerateInfoDto();
			customerOrderGenerateInfoDto.setOrderId(orderId);
			customerOrderGenerateInfoDto.setProductAmount(filterMask.getAmount());
			customerOrderGenerateInfoDto.setAddress(customerUser.getAddress());
			customerOrderGenerateInfoDto.setCustomerPhoneNumber(customerUser.getPhoneNumber());
			customerOrderGenerateInfoDto.setRemark("");
			return customerOrderGenerateInfoDto;
		}
		return null;
	}

	public void pushWeiXinMessge(String messgeType, OrderInfoVo orderInfoVo) {
		// messgeType:10-订单分配,20-派送员接单,30-签收,40-支付

		// 推送微信消息-派送通知(客户)
		TemplateMessage templateMessage = TemplateMessage.New();
		try {
			CustomerUserVo customerUserVo = new CustomerUserVo();
			customerUserVo.setId(orderInfoVo.getCustomerUserId());
			customerUserVo = this.customerUserDao.getCustomerUserBySelective(customerUserVo);
			if (customerUserVo == null) {
				throw new RuntimeException("客户信息查询不到," + orderInfoVo.getCustomerUserId());
			}

			AccessToken access = TokenUtil.getCacheWXToken();
			if ("40".equals(messgeType)) {
				templateMessage.setOpenId(customerUserVo.getWxOpenId());
				templateMessage.setTemplateId("ykujHmHTnJTSEK0iJWVQKNq_TooXRdcaOKBsYQMAZLo");
				templateMessage.setUrl("");
				templateMessage.setTopcolor("#696969");

				Map<String, TemplateData> msgData = new HashMap<String, TemplateData>();
				msgData.put("first", new TemplateData("尊敬的客户，您好，您的订单已支付完成。", "#696969"));
				msgData.put("keyword1", new TemplateData(orderInfoVo.getOrderId(), "#696969"));
				msgData.put("keyword2", new TemplateData(
						DateTimeUtils.dateToString(orderInfoVo.getPayTime(), "yyyy-MM-dd hh:mm:ss"), "#696969"));
				msgData.put("keyword3", new TemplateData("微信支付", "#696969"));
				msgData.put("keyword4", new TemplateData(orderInfoVo.getRealPayAmount().toPlainString(), "#696969"));
				msgData.put("remark", new TemplateData("查看详情", "#696969"));
				templateMessage.setTemplateData(msgData);
				// 推送消息
				WeiXinUtils.pushWeiXinMsg(access.getAccessToken(), templateMessage);
			} else if ("20".equals(messgeType)) {
				DeliveryUserVo deliveryUserVo = new DeliveryUserVo();
				deliveryUserVo.setId(orderInfoVo.getAllotDeliveryId());
				deliveryUserVo = this.deliveryUserDao.getDeliveryUserBySelective(deliveryUserVo);
				if (deliveryUserVo == null) {
					throw new RuntimeException("派送员信息查询不到," + orderInfoVo.getAllotDeliveryId());
				}

				templateMessage.setOpenId(customerUserVo.getWxOpenId());
				templateMessage.setTemplateId("pw_w0okvI2znoKOz6Zb2Hqo6AJNfokU0k8mlXyPa2Y8");
				templateMessage.setUrl("");
				templateMessage.setTopcolor("#696969");

				Map<String, TemplateData> msgData = new HashMap<String, TemplateData>();
				msgData.put("first", new TemplateData("尊敬的客户,您的商品正在派送中,请保持电话通畅!", "#696969"));
				msgData.put("keyword1", new TemplateData(deliveryUserVo.getName(), "#696969"));
				msgData.put("keyword2", new TemplateData(deliveryUserVo.getPhoneNumber(), "#696969"));
				msgData.put("remark", new TemplateData("查看详情", "#696969"));
				templateMessage.setTemplateData(msgData);
				// 推送消息
				WeiXinUtils.pushWeiXinMsg(access.getAccessToken(), templateMessage);
			}
		} catch (RuntimeException e) {
			logger.error("微信支付成功通知推送失败,推送参数:" + JSON.toJSONString(templateMessage), e);
		}
	}

	/**
	 * 订单金额汇总
	 * 
	 * @param map
	 * @return
	 */
	public BigDecimal getOrderTotalAmount(OrderInfoVo orderInfoVo) {
		// TODO Auto-generated method stub
		return orderInfoDao.getOrderTotalAmount(orderInfoVo);
	}

	@Override
	public OrderInfoDetailDto queryOrderDetailInfo(String orderId) {
		OrderInfoDetailDto orderInfo = orderInfoDao.queryOrderDetailInfo(orderId);
		if (orderInfo == null) {
			return null;
		}
		return orderInfo;
	}

	@Override
	public void closeOrder() {
		OrderInfoVo orderInfoVo = new OrderInfoVo();
		orderInfoVo.setOrderStatus("10");
		orderInfoVo.setPayStatus("10");
		List<OrderInfoVo> list = orderInfoDao.getOrderInfoList(orderInfoVo);
		for (OrderInfoVo bean : list) {
			Date time = DateTimeUtils.addDateTime(bean.getCreateTime(), TimeUnit.MINUTE, 10);
			if (new Date().after(time)) {
				try {
					this.payService.closeOrderPay(bean.getOrderId());

					bean.setOrderStatus("70");
					bean.setUpdateTime(new Date());
					orderInfoDao.update(bean);
				} catch (Exception e) {
					logger.error("订单关闭操作失败", e);
				}
			}
		}
	}

}
