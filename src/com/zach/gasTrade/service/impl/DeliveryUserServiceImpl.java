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


import com.zach.gasTrade.vo.DeliveryUserVo;
import com.zach.gasTrade.dao.DeliveryUserDao;
import com.zach.gasTrade.service.DeliveryUserService;


@Service("deliveryUserService")
public class DeliveryUserServiceImpl implements DeliveryUserService{
	
	@Autowired
	private DeliveryUserDao deliveryUserDao;
	
	 /**
     * 总数
     * @param deliveryUserVo
     * @return
     */
    public int getDeliveryUserCount(DeliveryUserVo deliveryUserVo){
    	return deliveryUserDao.getDeliveryUserCount(deliveryUserVo);
    }
    
    /**
     * 分页列表
     * @param deliveryUserVo
     * @return
     */
	 public List<DeliveryUserVo> getDeliveryUserPage(DeliveryUserVo deliveryUserVo){
		 return deliveryUserDao.getDeliveryUserPage(deliveryUserVo);
	 }

    /**
     * 列表
     * @param deliveryUserVo
     * @return
     */
    public List<DeliveryUserVo> getDeliveryUserList(DeliveryUserVo deliveryUserVo){
    	return deliveryUserDao.getDeliveryUserList(deliveryUserVo);
    }
    
    /**
     * 根据条件查询一条信息
     * @param deliveryUserVo
     * @return
     */
	 public DeliveryUserVo getDeliveryUserBySelective(DeliveryUserVo deliveryUserVo){
		 return deliveryUserDao.getDeliveryUserBySelective(deliveryUserVo);
	 }
	
    /**
	 * 保存
	 * @param deliveryUserVo
	 */
    public int save(DeliveryUserVo deliveryUserVo){
    	
    	return deliveryUserDao.save(deliveryUserVo);
    }
    
    /**
	 * 更新
	 * @param deliveryUserVo
	 */
    public int update(DeliveryUserVo deliveryUserVo){
    	return deliveryUserDao.update(deliveryUserVo);
    }
    
    /**
	 * 删除
	 * @param deliveryUserVo
	 */
    public int delete(DeliveryUserVo deliveryUserVo){
    	return deliveryUserDao.delete(deliveryUserVo);
    }
	
	
}

