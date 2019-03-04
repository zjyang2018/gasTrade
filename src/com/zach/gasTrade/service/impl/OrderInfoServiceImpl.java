/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zach.gasTrade.dao.CustomerUserDao;
import com.zach.gasTrade.dao.OrderInfoDao;
import com.zach.gasTrade.dao.ProductDao;
import com.zach.gasTrade.dto.DeliveryMonitorDto;
import com.zach.gasTrade.dto.OrderFinanceStatisticsDto;
import com.zach.gasTrade.dto.OrderListDto;
import com.zach.gasTrade.netpay.PayService;
import com.zach.gasTrade.netpay.UnoinPayUtil;
import com.zach.gasTrade.service.OrderInfoService;
import com.zach.gasTrade.vo.CustomerUserVo;
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
	 * @param orderInfoModel
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
			throw new RuntimeException("该用户不存在," + customerUser.getId());
		}
		ProductVo productParam = new ProductVo();
		productParam.setProductId(orderInfoVo.getProductId());
		ProductVo product = productDao.getProductBySelective(productParam);
		if (product == null) {
			throw new RuntimeException("该产品已下架," + orderInfoVo.getProductId());
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
		Date now = new Date();
		orderInfoVo.setCreateTime(now);
		orderInfoVo.setUpdateTime(now);

		String payUrl = this.payService.pay(orderId, product.getAmount());

		int count = orderInfoDao.save(orderInfoVo);
		if (count != 1) {
			throw new RuntimeException("用户下单失败," + JSON.toJSONString(orderInfoVo));
		}

		return payUrl;
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
	public OrderFinanceStatisticsDto getOrderDayCount() {
		return orderInfoDao.getOrderDayCount();
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
			String refundOrderId = params.get("refundOrderId");
			OrderInfoVo orderInfoVo = new OrderInfoVo();
			orderInfoVo.setOrderId(orderId);
			OrderInfoVo orderInfo = orderInfoDao.getOrderInfoBySelective(orderInfoVo);
			orderInfo.setPayNo(payNo);
			orderInfo.setRealPayAmount(new BigDecimal(realPayAmountStr).divide(new BigDecimal(100)));
			orderInfo.setPayStatus("20");
			orderInfo.setPayTime(new Date());
			orderInfo.setOrderStatus("20");
			orderInfo.setUpdateTime(new Date());
			int count = orderInfoDao.update(orderInfo);
			if (count != 1) {
				throw new RuntimeException("支付状态通知失败," + JSON.toJSONString(params));
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

}
