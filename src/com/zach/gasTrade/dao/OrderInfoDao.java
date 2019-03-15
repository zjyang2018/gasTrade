/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zach.gasTrade.dto.CustomerOrderInfoDto;
import com.zach.gasTrade.dto.DeliveryMonitorDto;
import com.zach.gasTrade.dto.DeliveryOrderInfoDto;
import com.zach.gasTrade.dto.OrderFinanceStatisticsDto;
import com.zach.gasTrade.dto.OrderListDto;
import com.zach.gasTrade.vo.OrderInfoVo;

@Repository("orderInfoDao")
public interface OrderInfoDao {

	/**
	 * 总数
	 * 
	 * @param map
	 * @return
	 */
	public int getOrderInfoCount(Map<String, Object> map);

	/**
	 * 分页列表
	 * 
	 * @param map
	 * @return
	 */
	public List<OrderListDto> getOrderInfoPage(Map<String, Object> map);

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
	public OrderFinanceStatisticsDto getOrderDayCount(@Param("yestody") String yestody);

	public int getOrderInfoNumber(OrderInfoVo filterMask);

	public List<CustomerOrderInfoDto> getCustomerOrderInfoPage(OrderInfoVo filterMask);

	public List<DeliveryOrderInfoDto> getDeliveryOrderInfoPage(OrderInfoVo filterMask);

}
