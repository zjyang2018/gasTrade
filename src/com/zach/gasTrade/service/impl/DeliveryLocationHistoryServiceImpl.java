/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.zach.gasTrade.vo.DeliveryLocationHistoryVo;
import com.zach.gasTrade.dao.DeliveryLocationHistoryDao;
import com.zach.gasTrade.service.DeliveryLocationHistoryService;


@Service("deliveryLocationHistoryService")
public class DeliveryLocationHistoryServiceImpl implements DeliveryLocationHistoryService{
	
	@Autowired
	private DeliveryLocationHistoryDao deliveryLocationHistoryDao;
	
	 /**
     * 总数
     * @param deliveryLocationHistoryVo
     * @return
     */
    public int getDeliveryLocationHistoryCount(DeliveryLocationHistoryVo deliveryLocationHistoryVo){
    	return deliveryLocationHistoryDao.getDeliveryLocationHistoryCount(deliveryLocationHistoryVo);
    }
    
    /**
     * 分页列表
     * @param deliveryLocationHistoryVo
     * @return
     */
	 public List<DeliveryLocationHistoryVo> getDeliveryLocationHistoryPage(DeliveryLocationHistoryVo deliveryLocationHistoryVo){
		 return deliveryLocationHistoryDao.getDeliveryLocationHistoryPage(deliveryLocationHistoryVo);
	 }

    /**
     * 列表
     * @param deliveryLocationHistoryVo
     * @return
     */
    public List<DeliveryLocationHistoryVo> getDeliveryLocationHistoryList(DeliveryLocationHistoryVo deliveryLocationHistoryVo){
    	return deliveryLocationHistoryDao.getDeliveryLocationHistoryList(deliveryLocationHistoryVo);
    }
    
    /**
     * 根据条件查询一条信息
     * @param deliveryLocationHistoryVo
     * @return
     */
	 public DeliveryLocationHistoryVo getDeliveryLocationHistoryBySelective(DeliveryLocationHistoryVo deliveryLocationHistoryVo){
		 return deliveryLocationHistoryDao.getDeliveryLocationHistoryBySelective(deliveryLocationHistoryVo);
	 }
	
    /**
	 * 保存
	 * @param deliveryLocationHistoryVo
	 */
    public int save(DeliveryLocationHistoryVo deliveryLocationHistoryVo){
    	
    	return deliveryLocationHistoryDao.save(deliveryLocationHistoryVo);
    }
    
    /**
	 * 更新
	 * @param deliveryLocationHistoryVo
	 */
    public int update(DeliveryLocationHistoryVo deliveryLocationHistoryVo){
    	return deliveryLocationHistoryDao.update(deliveryLocationHistoryVo);
    }
    
    /**
	 * 删除
	 * @param deliveryLocationHistoryVo
	 */
    public int delete(DeliveryLocationHistoryVo deliveryLocationHistoryVo){
    	return deliveryLocationHistoryDao.delete(deliveryLocationHistoryVo);
    }
	
	
}

