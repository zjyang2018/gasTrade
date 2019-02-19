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


import com.zach.gasTrade.vo.OrderDeliveryRecordVo;
import com.zach.gasTrade.dao.OrderDeliveryRecordDao;
import com.zach.gasTrade.service.OrderDeliveryRecordService;


@Service("orderDeliveryRecordService")
public class OrderDeliveryRecordServiceImpl implements OrderDeliveryRecordService{
	
	@Autowired
	private OrderDeliveryRecordDao orderDeliveryRecordDao;
	
	 /**
     * 总数
     * @param orderDeliveryRecordVo
     * @return
     */
    public int getOrderDeliveryRecordCount(OrderDeliveryRecordVo orderDeliveryRecordVo){
    	return orderDeliveryRecordDao.getOrderDeliveryRecordCount(orderDeliveryRecordVo);
    }
    
    /**
     * 分页列表
     * @param orderDeliveryRecordVo
     * @return
     */
	 public List<OrderDeliveryRecordVo> getOrderDeliveryRecordPage(OrderDeliveryRecordVo orderDeliveryRecordVo){
		 return orderDeliveryRecordDao.getOrderDeliveryRecordPage(orderDeliveryRecordVo);
	 }

    /**
     * 列表
     * @param orderDeliveryRecordVo
     * @return
     */
    public List<OrderDeliveryRecordVo> getOrderDeliveryRecordList(OrderDeliveryRecordVo orderDeliveryRecordVo){
    	return orderDeliveryRecordDao.getOrderDeliveryRecordList(orderDeliveryRecordVo);
    }
    
    /**
     * 根据条件查询一条信息
     * @param orderDeliveryRecordVo
     * @return
     */
	 public OrderDeliveryRecordVo getOrderDeliveryRecordBySelective(OrderDeliveryRecordVo orderDeliveryRecordVo){
		 return orderDeliveryRecordDao.getOrderDeliveryRecordBySelective(orderDeliveryRecordVo);
	 }
	
    /**
	 * 保存
	 * @param orderDeliveryRecordVo
	 */
    public int save(OrderDeliveryRecordVo orderDeliveryRecordVo){
    	
    	return orderDeliveryRecordDao.save(orderDeliveryRecordVo);
    }
    
    /**
	 * 更新
	 * @param orderDeliveryRecordVo
	 */
    public int update(OrderDeliveryRecordVo orderDeliveryRecordVo){
    	return orderDeliveryRecordDao.update(orderDeliveryRecordVo);
    }
    
    /**
	 * 删除
	 * @param orderDeliveryRecordVo
	 */
    public int delete(OrderDeliveryRecordVo orderDeliveryRecordVo){
    	return orderDeliveryRecordDao.delete(orderDeliveryRecordVo);
    }
	
	
}

