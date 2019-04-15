/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.zach.gasTrade.dto.CustomerOrderGenerateInfoDto;
import com.zach.gasTrade.dto.CustomerOrderInfoDto;
import com.zach.gasTrade.dto.DeliveryMonitorDto;
import com.zach.gasTrade.dto.DeliveryOrderInfoDto;
import com.zach.gasTrade.dto.OrderFinanceStatisticsDto;
import com.zach.gasTrade.dto.OrderInfoDetailDto;
import com.zach.gasTrade.dto.OrderListDto;
import com.zach.gasTrade.vo.OrderInfoVo;

public interface OrderInfoService {
	/**
	 * 总数
	 * 
	 * @param map
	 * @return
	 */
	public int getOrderInfoCount(OrderInfoVo orderInfoVo);

	/**
	 * 分页列表
	 * 
	 * @param map
	 * @return
	 */
	public List<OrderListDto> getOrderInfoPage(OrderInfoVo orderInfoVo);

	/**
	 * 列表
	 * 
	 * @param orderInfoVo
	 * @return
	 */
	public List<OrderInfoVo> getOrderInfoList(OrderInfoVo orderInfoVo);

	/**
	 * 根据条件查询一条信息
	 * 
	 * @param orderInfoVo
	 * @return
	 */
	public OrderInfoVo getOrderInfoBySelective(OrderInfoVo orderInfoVo);

	/**
	 * 保存
	 * 
	 * @param orderInfoVo
	 */
	public int save(OrderInfoVo orderInfoVo);

	/**
	 * 更新
	 * 
	 * @param orderInfoVo
	 */
	public int update(OrderInfoVo orderInfoVo);

	/**
	 * 删除
	 * 
	 * @param orderInfoVo
	 */
	public int delete(OrderInfoVo orderInfoVo);

	/**
	 * 监控总数
	 * 
	 * @param map
	 * @return
	 */
	public int getDeliveryMonitorCount(Map<String, Object> map);

	/**
	 * 监控分页列表
	 * 
	 * @param map
	 * @return
	 */
	public List<DeliveryMonitorDto> getDeliveryMonitorPage(Map<String, Object> map);

	/**
	 * 每日订单数量统计
	 */
	public OrderFinanceStatisticsDto getOrderDayCount(String yestody);

	/**
	 * 订单下单
	 * 
	 * @param orderInfoVo
	 */
	public String buy(OrderInfoVo orderInfoVo);

	/**
	 * 支付通知
	 * 
	 * @param orderInfoVo
	 */
	public boolean notifyHandle(Map<String, String> params);

	/**
	 * 退款
	 * 
	 * @param orderInfoVo
	 */
	public void refundAmount(String orderId);

	public int getOrderInfoNumber(OrderInfoVo filterMask);

	public List<CustomerOrderInfoDto> getCustomerOrderInfoPage(OrderInfoVo filterMask);

	public List<DeliveryOrderInfoDto> getDeliveryOrderInfoPage(OrderInfoVo filterMask);

	/**
	 * 订单生成
	 * 
	 * @param orderInfoVo
	 */
	public CustomerOrderGenerateInfoDto orderGenerate(OrderInfoVo filterMask);

	/**
	 * 自动分配订单
	 * 
	 */
	public void autoAllotDeliveryOrder();

	/**
	 * 订单金额汇总
	 * 
	 * @param map
	 * @return
	 */
	public BigDecimal getOrderTotalAmount(OrderInfoVo orderInfoVo);

	/**
	 * 公众号订单详情
	 * 
	 * @param map
	 * @return
	 */
	public OrderInfoDetailDto queryOrderDetailInfo(String orderId);

	/**
	 * 公众号消息推送
	 * 
	 * @param messgeType:10-订单分配,20-派送员接单,30-签收,40-支付
	 * @param orderInfoVo保证有所需信息
	 * @return
	 */
	public void pushWeiXinMessge(String messgeType, OrderInfoVo orderInfoVo);

	/**
	 * 关闭订单
	 * 
	 * @return
	 */
	public void closeOrder();

}
