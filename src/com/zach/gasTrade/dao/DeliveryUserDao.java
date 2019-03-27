/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zach.gasTrade.vo.DeliveryUserVo;

@Repository("deliveryUserDao")
public interface DeliveryUserDao {

	/**
	 * 总数
	 * 
	 * @param map
	 * @return
	 */
	public int getDeliveryUserCount(Map<String, Object> map);

	/**
	 * 分页列表
	 * 
	 * @param map
	 * @return
	 */
	public List<DeliveryUserVo> getDeliveryUserPage(Map<String, Object> map);

	/**
	 * 列表
	 * 
	 * @param deliveryUserVo
	 * @return
	 */
	public List<DeliveryUserVo> getDeliveryUserList(DeliveryUserVo deliveryUserVo);

	/**
	 * 列表
	 * 
	 * @param deliveryUserVo
	 * @return
	 */
	public List<DeliveryUserVo> selectDeliveryUserList(Map<String, Object> map);

	/**
	 * 根据条件查询一条信息
	 * 
	 * @param deliveryUserVo
	 * @return
	 */
	public DeliveryUserVo getDeliveryUserBySelective(DeliveryUserVo deliveryUserVo);

	/**
	 * 保存
	 * 
	 * @param deliveryUserVo
	 */
	public int save(DeliveryUserVo deliveryUserVo);

	/**
	 * 更新
	 * 
	 * @param deliveryUserVo
	 */
	public int update(DeliveryUserVo deliveryUserVo);

	/**
	 * 删除
	 * 
	 * @param deliveryUserVo
	 */
	public int delete(DeliveryUserVo deliveryUserVo);

}
