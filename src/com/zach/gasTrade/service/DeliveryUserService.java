/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service;

import java.util.List;

import com.zach.gasTrade.dto.DeliveryUserDto;
import com.zach.gasTrade.vo.DeliveryUserVo;

public interface DeliveryUserService{
	/**
     * 总数
     * @param deliveryUserVo
     * @return
     */
	 public int getDeliveryUserCount(DeliveryUserVo deliveryUserVo);
	 
	 /**
     * 分页列表
     * @param deliveryUserVo
     * @return
     */
	 public List<DeliveryUserVo> getDeliveryUserPage(DeliveryUserVo deliveryUserVo);
	 
	 /**
     * 列表
     * @param deliveryUserVo
     * @return
     */
	 public List<DeliveryUserVo> getDeliveryUserList(DeliveryUserVo deliveryUserVo);
	 
	 /**
     * 根据条件查询一条信息
     * @param deliveryUserVo
     * @return
     */
	 public DeliveryUserVo getDeliveryUserBySelective(DeliveryUserVo deliveryUserVo);
	 
	 /**
	 * 保存
	 * @param deliveryUserVo
	 */
    public int save(DeliveryUserDto deliveryUserDto);
    
    /**
	 * 更新
	 * @param deliveryUserVo
	 */
    public int update(DeliveryUserVo deliveryUserVo);
    
    /**
	 * 删除
	 * @param deliveryUserVo
	 */
    public int delete(DeliveryUserVo deliveryUserVo);
    

}

