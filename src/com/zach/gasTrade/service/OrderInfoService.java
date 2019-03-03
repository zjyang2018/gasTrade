/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service;

import java.util.List;
import java.util.Map;

import com.zach.gasTrade.dto.DeliveryMonitorDto;
import com.zach.gasTrade.dto.OrderFinanceStatisticsDto;
import com.zach.gasTrade.dto.OrderListDto;
import com.zach.gasTrade.vo.OrderInfoVo;

public interface OrderInfoService {
	/**
	 * 总数
	 * 
	 * @param orderListDto
	 * @return
	 */
	public int getOrderInfoCount(OrderListDto orderListDto);

	/**
	 * 分页列表
	 * 
	 * @param orderListDto
	 * @return
	 */
	public List<OrderListDto> getOrderInfoPage(OrderListDto orderListDto);

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
	public OrderFinanceStatisticsDto getOrderDayCount();

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
	public boolean refundAmount(String orderId);

}
