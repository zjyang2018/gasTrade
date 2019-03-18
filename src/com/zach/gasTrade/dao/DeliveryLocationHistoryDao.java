/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zach.gasTrade.vo.DeliveryLocationHistoryVo;

@Repository("deliveryLocationHistoryDao")
public interface DeliveryLocationHistoryDao {

	/**
	 * 总数
	 * 
	 * @param deliveryLocationHistoryVo
	 * @return
	 */
	public int getDeliveryLocationHistoryCount(DeliveryLocationHistoryVo deliveryLocationHistoryVo);

	/**
	 * 分页列表
	 * 
	 * @param deliveryLocationHistoryVo
	 * @return
	 */
	public List<DeliveryLocationHistoryVo> getDeliveryLocationHistoryPage(
			DeliveryLocationHistoryVo deliveryLocationHistoryVo);

	/**
	 * 列表
	 * 
	 * @param deliveryLocationHistoryVo
	 * @return
	 */
	public List<DeliveryLocationHistoryVo> getDeliveryLocationHistoryList(
			DeliveryLocationHistoryVo deliveryLocationHistoryVo);

	/**
	 * 根据条件查询一条信息
	 * 
	 * @param deliveryLocationHistoryVo
	 * @return
	 */
	public DeliveryLocationHistoryVo getDeliveryLocationHistoryBySelective(
			DeliveryLocationHistoryVo deliveryLocationHistoryVo);

	/**
	 * 保存
	 * 
	 * @param deliveryLocationHistoryVo
	 */
	public int save(DeliveryLocationHistoryVo deliveryLocationHistoryVo);

	/**
	 * 更新
	 * 
	 * @param deliveryLocationHistoryVo
	 */
	public int update(DeliveryLocationHistoryVo deliveryLocationHistoryVo);

	/**
	 * 删除
	 * 
	 * @param deliveryLocationHistoryVo
	 */
	public int delete(DeliveryLocationHistoryVo deliveryLocationHistoryVo);

	/**
	 * 获取最近记录列表
	 * 
	 * @return
	 */
	public List<DeliveryLocationHistoryVo> getDeliveryLocationHistoryByRecent();

	/**
	 * 获取所有派送员最新记录列表
	 * 
	 * @return
	 */
	public List<DeliveryLocationHistoryVo> queryAllDeliveryNewLocationList(Map<String, Object> param);

}
