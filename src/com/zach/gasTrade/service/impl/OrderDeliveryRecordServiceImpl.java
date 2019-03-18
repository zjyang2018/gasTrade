/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.seq.SerialGenerator;
import com.zach.gasTrade.dao.OrderDeliveryRecordDao;
import com.zach.gasTrade.dto.DeliveryMonitorDto;
import com.zach.gasTrade.dto.OrderDeliveryCountDto;
import com.zach.gasTrade.service.OrderDeliveryRecordService;
import com.zach.gasTrade.vo.OrderDeliveryRecordVo;

@Service("orderDeliveryRecordService")
public class OrderDeliveryRecordServiceImpl implements OrderDeliveryRecordService {

	@Autowired
	private OrderDeliveryRecordDao orderDeliveryRecordDao;

	/**
	 * 派送监控总数
	 * 
	 * @param orderDeliveryRecordVo
	 * @return
	 */
	public int getDeliveryMonitorCount(OrderDeliveryRecordVo orderDeliveryRecordVo) {
		return orderDeliveryRecordDao.getDeliveryMonitorCount(orderDeliveryRecordVo);
	}

	/**
	 * 派送监控分页列表
	 * 
	 * @param orderDeliveryRecordVo
	 * @return
	 */
	public List<DeliveryMonitorDto> getDeliveryMonitorPage(OrderDeliveryRecordVo orderDeliveryRecordVo) {
		return orderDeliveryRecordDao.getDeliveryMonitorPage(orderDeliveryRecordVo);
	}

	/**
	 * 根据条件查询一条信息
	 * 
	 * @param orderDeliveryRecordVo
	 * @return
	 */
	public OrderDeliveryRecordVo getOrderDeliveryRecordBySelective(OrderDeliveryRecordVo orderDeliveryRecordVo) {
		return orderDeliveryRecordDao.getOrderDeliveryRecordBySelective(orderDeliveryRecordVo);
	}

	/**
	 * 保存
	 * 
	 * @param orderDeliveryRecordVo
	 */
	public int save(OrderDeliveryRecordVo orderDeliveryRecordVo) {
		String id = SerialGenerator.getUUID();
		Date nowTime = new Date();
		orderDeliveryRecordVo.setId(id);
		orderDeliveryRecordVo.setCreateTime(nowTime);

		return orderDeliveryRecordDao.save(orderDeliveryRecordVo);
	}

	/**
	 * 更新
	 * 
	 * @param orderDeliveryRecordVo
	 */
	public int update(OrderDeliveryRecordVo orderDeliveryRecordVo) {
		return orderDeliveryRecordDao.update(orderDeliveryRecordVo);
	}

	/**
	 * 删除
	 * 
	 * @param orderDeliveryRecordVo
	 */
	public int delete(OrderDeliveryRecordVo orderDeliveryRecordVo) {
		return orderDeliveryRecordDao.delete(orderDeliveryRecordVo);
	}

	/**
	 * 派送统计总数
	 * 
	 * @param map
	 * @return
	 */
	public int getOrderDeliveryStatisticsCount(Map<String, Object> map) {
		return orderDeliveryRecordDao.getOrderDeliveryStatisticsCount(map);
	}

	/**
	 * 派送统计分页列表
	 * 
	 * @param map
	 * @return
	 */
	public List<OrderDeliveryCountDto> getOrderDeliveryStatisticsPage(Map<String, Object> map) {
		return orderDeliveryRecordDao.getOrderDeliveryStatisticsPage(map);
	}

	/**
	 * 派送统计列表
	 * 
	 * @param map
	 * @return
	 */
	public List<OrderDeliveryCountDto> getOrderDeliveryStatisticsList(Map<String, Object> map) {
		return orderDeliveryRecordDao.getOrderDeliveryStatisticsList(map);
	}

}
