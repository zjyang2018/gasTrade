/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service;

import com.zach.gasTrade.vo.DeliveryLocationHistoryVo;
import java.util.List;

public interface DeliveryLocationHistoryService{
	/**
     * 总数
     * @param deliveryLocationHistoryVo
     * @return
     */
	 public int getDeliveryLocationHistoryCount(DeliveryLocationHistoryVo deliveryLocationHistoryVo);
	 
	 /**
     * 分页列表
     * @param deliveryLocationHistoryVo
     * @return
     */
	 public List<DeliveryLocationHistoryVo> getDeliveryLocationHistoryPage(DeliveryLocationHistoryVo deliveryLocationHistoryVo);
	 
	 /**
     * 列表
     * @param deliveryLocationHistoryVo
     * @return
     */
	 public List<DeliveryLocationHistoryVo> getDeliveryLocationHistoryList(DeliveryLocationHistoryVo deliveryLocationHistoryVo);
	 
	 /**
     * 根据条件查询一条信息
     * @param deliveryLocationHistoryVo
     * @return
     */
	 public DeliveryLocationHistoryVo getDeliveryLocationHistoryBySelective(DeliveryLocationHistoryVo deliveryLocationHistoryVo);
	 
	 /**
	 * 保存
	 * @param deliveryLocationHistoryVo
	 */
    public int save(DeliveryLocationHistoryVo deliveryLocationHistoryVo);
    
    /**
	 * 更新
	 * @param deliveryLocationHistoryVo
	 */
    public int update(DeliveryLocationHistoryVo deliveryLocationHistoryVo);
    
    /**
	 * 删除
	 * @param deliveryLocationHistoryVo
	 */
    public int delete(DeliveryLocationHistoryVo deliveryLocationHistoryVo);
    

}

