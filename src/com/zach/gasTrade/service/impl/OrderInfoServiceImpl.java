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


import com.zach.gasTrade.vo.OrderInfoVo;
import com.zach.gasTrade.dao.OrderInfoDao;
import com.zach.gasTrade.service.OrderInfoService;


@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService{
	
	@Autowired
	private OrderInfoDao orderInfoDao;
	
	 /**
     * 总数
     * @param orderInfoVo
     * @return
     */
    public int getOrderInfoCount(OrderInfoVo orderInfoVo){
    	return orderInfoDao.getOrderInfoCount(orderInfoVo);
    }
    
    /**
     * 分页列表
     * @param orderInfoVo
     * @return
     */
	 public List<OrderInfoVo> getOrderInfoPage(OrderInfoVo orderInfoVo){
		 return orderInfoDao.getOrderInfoPage(orderInfoVo);
	 }

    /**
     * 列表
     * @param orderInfoVo
     * @return
     */
    public List<OrderInfoVo> getOrderInfoList(OrderInfoVo orderInfoVo){
    	return orderInfoDao.getOrderInfoList(orderInfoVo);
    }
    
    /**
     * 根据条件查询一条信息
     * @param orderInfoVo
     * @return
     */
	 public OrderInfoVo getOrderInfoBySelective(OrderInfoVo orderInfoVo){
		 return orderInfoDao.getOrderInfoBySelective(orderInfoVo);
	 }
	
    /**
	 * 保存
	 * @param orderInfoVo
	 */
    public int save(OrderInfoVo orderInfoVo){
    	
    	return orderInfoDao.save(orderInfoVo);
    }
    
    /**
	 * 更新
	 * @param orderInfoVo
	 */
    public int update(OrderInfoVo orderInfoVo){
    	return orderInfoDao.update(orderInfoVo);
    }
    
    /**
	 * 删除
	 * @param orderInfoVo
	 */
    public int delete(OrderInfoVo orderInfoVo){
    	return orderInfoDao.delete(orderInfoVo);
    }
	
	
}

