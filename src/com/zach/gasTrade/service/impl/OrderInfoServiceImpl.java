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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.seq.SerialGenerator;
import com.common.utils.DateTimeUtils;
import com.common.utils.MapHelper;
import com.common.utils.StringUtil;
import com.common.wx.TokenUtil;
import com.common.wx.WeiXinUtils;
import com.common.wx.bean.AccessToken;
import com.common.wx.bean.TemplateData;
import com.common.wx.bean.TemplateMessage;
import com.zach.gasTrade.dao.CustomerUserDao;
import com.zach.gasTrade.dao.DeliveryLocationHistoryDao;
import com.zach.gasTrade.dao.OrderInfoDao;
import com.zach.gasTrade.dao.ProductDao;
import com.zach.gasTrade.dto.CustomerOrderGenerateInfoDto;
import com.zach.gasTrade.dto.CustomerOrderInfoDto;
import com.zach.gasTrade.dto.DeliveryMonitorDto;
import com.zach.gasTrade.dto.DeliveryOrderInfoDto;
import com.zach.gasTrade.dto.OrderFinanceStatisticsDto;
import com.zach.gasTrade.dto.OrderListDto;
import com.zach.gasTrade.netpay.PayService;
import com.zach.gasTrade.netpay.UnoinPayUtil;
import com.zach.gasTrade.service.OrderInfoService;
import com.zach.gasTrade.vo.CustomerUserVo;
import com.zach.gasTrade.vo.DeliveryLocationHistoryVo;
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
	private DeliveryLocationHistoryDao deliveryLocationHistoryDao;

	/**
	 * 总数
	 * 
	 * @param orderListDto
	 * @return
	 */
	public int getOrderInfoCount(OrderListDto orderListDto) {
		return orderInfoDao.getOrderInfoCount(orderListDto);
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
	 * @param orderListDto
	 * @return
	 */
	public List<OrderListDto> getOrderInfoPage(OrderListDto orderListDto) {
		return orderInfoDao.getOrderInfoPage(orderListDto);
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
		if (StringUtil.isNotNullAndNotEmpty(orderInfoVo.getOrderId())) {
			// 生成支付url
			return this.payService.pay(orderInfoVo.getOrderId(), product.getAmount());
		}
		String orderId = UnoinPayUtil.genMerOrderId(PayService.msgSrcId);
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

		int count = orderInfoDao.save(orderInfoVo);
		if (count != 1) {
			throw new RuntimeException("用户下单失败," + JSON.toJSONString(orderInfoVo));
		}
		// 生成支付url
		return this.payService.pay(orderId, product.getAmount());
	}

	/**
	 * 自动分配订单
	 *
	 * @param orderInfoVo
	 */
	public void autoAllotDeliveryOrder(OrderInfoVo orderInfoVo) {
		List<DeliveryLocationHistoryVo> list = deliveryLocationHistoryDao.getDeliveryLocationHistoryByRecent();
		String deliveryUserId = "";
		double minDistance = 0.00;
		for (DeliveryLocationHistoryVo bean : list) {
			double distance = MapHelper.GetPointDistance(bean.getLatitude() + "," + bean.getLongitude(),
					orderInfoVo.getLatitude() + "," + orderInfoVo.getLongitude());
			if (minDistance > distance) {
				minDistance = distance;
				deliveryUserId = bean.getDeliveryUserId();
			}
		}

		orderInfoVo.setAllotDeliveryId(deliveryUserId);
		orderInfoVo.setAllotStatus("20");
		Date now = new Date();
		orderInfoVo.setAllotTime(now);
		orderInfoVo.setUpdateTime(now);
		orderInfoDao.update(orderInfoVo);
	}

	/**
	 * 更新
	 * 
	 * @param orderInfoVo
	 */
	public int update(OrderInfoVo orderInfoVo) {
		Date nowTime = new Date();
		orderInfoVo.setUpdateTime(nowTime);

		return orderInfoDao.update(orderInfoVo);
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
			String orderId = params.get("merOrderId");
			String payNo = params.get("seqId");
			String realPayAmountStr = params.get("buyerPayAmount");
			String payTimeStr = params.get("payTime");
			String refundAmountStr = params.get("refundAmount");
			if (refundAmountStr != null && !refundAmountStr.isEmpty()) {
				throw new RuntimeException("退款通知不处理," + JSON.toJSONString(params));
			}
			String refundOrderId = params.get("refundOrderId");
			OrderInfoVo orderInfoVo = new OrderInfoVo();
			orderInfoVo.setOrderId(orderId);
			OrderInfoVo orderInfo = orderInfoDao.getOrderInfoBySelective(orderInfoVo);
			orderInfo.setPayNo(payNo);
			orderInfo.setRealPayAmount(new BigDecimal(realPayAmountStr).divide(new BigDecimal(100)));
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
			// 推送微信消息
			TemplateMessage templateMessage = TemplateMessage.New();
			try {
				AccessToken access = TokenUtil.getWXToken();

				templateMessage.setOpenId(customerUserVo.getWxOpenId());
				templateMessage.setTemplateId("ykujHmHTnJTSEK0iJWVQKNq_TooXRdcaOKBsYQMAZLo");
				templateMessage.setUrl("");
				templateMessage.setTopcolor("#696969");

				Map<String, TemplateData> msgData = new HashMap<String, TemplateData>();
				msgData.put("first", new TemplateData("尊敬的客户，您好，您的订单已支付完成。", "#696969"));
				msgData.put("keyword1", new TemplateData(orderId, "#696969"));
				msgData.put("keyword2",
						new TemplateData(DateTimeUtils.dateToString(now, "yyyy-MM-dd hh:mm:ss"), "#696969"));
				msgData.put("keyword3", new TemplateData("微信支付", "#696969"));
				msgData.put("keyword4", new TemplateData(
						new BigDecimal(realPayAmountStr).divide(new BigDecimal(100)).toPlainString(), "#696969"));
				msgData.put("remark", new TemplateData("查看详情", "#696969"));

				templateMessage.setTemplateData(msgData);
				// 推送消息
				WeiXinUtils.pushWeiXinMsg(access.getAccessToken(), templateMessage);
			} catch (RuntimeException e) {
				logger.error("微信支付成功通知推送失败,推送参数:" + JSON.toJSONString(templateMessage), e);
			}

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
		JSONObject result = payService.refund(orderId, orderInfo.getRealPayAmount());
		logger.info("退款返回参数值:" + result.toJSONString() + ",退款订单号:" + orderId);
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

}
