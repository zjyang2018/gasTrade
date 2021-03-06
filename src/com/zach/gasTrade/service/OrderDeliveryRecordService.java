/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service;

import java.util.List;
import java.util.Map;

import com.zach.gasTrade.dto.DeliveryMonitorDto;
import com.zach.gasTrade.dto.OrderDeliveryCountDto;
import com.zach.gasTrade.vo.OrderDeliveryRecordVo;

public interface OrderDeliveryRecordService {
	/**
     * 派送监控总数
     * @param orderDeliveryRecordVo
     * @return
     */
	 public int getDeliveryMonitorCount(OrderDeliveryRecordVo orderDeliveryRecordVo);
	 	 
	 /**
     * 派送监控分页列表
     * @param orderDeliveryRecordVo
     * @return
     */
	 public List<DeliveryMonitorDto> getDeliveryMonitorPage(OrderDeliveryRecordVo orderDeliveryRecordVo);

	/**
	 * 根据条件查询一条信息
	 * 
	 * @param orderDeliveryRecordVo
	 * @return
	 */
	public OrderDeliveryRecordVo getOrderDeliveryRecordBySelective(OrderDeliveryRecordVo orderDeliveryRecordVo);

	/**
	 * 保存
	 * 
	 * @param orderDeliveryRecordVo
	 */
	public int save(OrderDeliveryRecordVo orderDeliveryRecordVo);

	/**
	 * 更新
	 * 
	 * @param orderDeliveryRecordVo
	 */
	public int update(OrderDeliveryRecordVo orderDeliveryRecordVo);

	/**
	 * 删除
	 * 
	 * @param orderDeliveryRecordVo
	 */
	public int delete(OrderDeliveryRecordVo orderDeliveryRecordVo);

	/**
	 * 派送统计总数
	 * 
	 * @param map
	 * @return
	 */
	public int getDeliveryUserCount(Map<String, Object> map);

	/**
	 * 派送统计分页列表
	 * 
	 * @param map
	 * @return
	 */
	public List<OrderDeliveryCountDto> getOrderDeliveryPage(Map<String, Object> map);

}
