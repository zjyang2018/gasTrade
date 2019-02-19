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


import com.zach.gasTrade.vo.OrderFinanceStatisticsVo;
import com.zach.gasTrade.dao.OrderFinanceStatisticsDao;
import com.zach.gasTrade.service.OrderFinanceStatisticsService;


@Service("orderFinanceStatisticsService")
public class OrderFinanceStatisticsServiceImpl implements OrderFinanceStatisticsService{
	
	@Autowired
	private OrderFinanceStatisticsDao orderFinanceStatisticsDao;
	
	 /**
     * 总数
     * @param orderFinanceStatisticsVo
     * @return
     */
    public int getOrderFinanceStatisticsCount(OrderFinanceStatisticsVo orderFinanceStatisticsVo){
    	return orderFinanceStatisticsDao.getOrderFinanceStatisticsCount(orderFinanceStatisticsVo);
    }
    
    /**
     * 分页列表
     * @param orderFinanceStatisticsVo
     * @return
     */
	 public List<OrderFinanceStatisticsVo> getOrderFinanceStatisticsPage(OrderFinanceStatisticsVo orderFinanceStatisticsVo){
		 return orderFinanceStatisticsDao.getOrderFinanceStatisticsPage(orderFinanceStatisticsVo);
	 }

    /**
     * 列表
     * @param orderFinanceStatisticsVo
     * @return
     */
    public List<OrderFinanceStatisticsVo> getOrderFinanceStatisticsList(OrderFinanceStatisticsVo orderFinanceStatisticsVo){
    	return orderFinanceStatisticsDao.getOrderFinanceStatisticsList(orderFinanceStatisticsVo);
    }
    
    /**
     * 根据条件查询一条信息
     * @param orderFinanceStatisticsVo
     * @return
     */
	 public OrderFinanceStatisticsVo getOrderFinanceStatisticsBySelective(OrderFinanceStatisticsVo orderFinanceStatisticsVo){
		 return orderFinanceStatisticsDao.getOrderFinanceStatisticsBySelective(orderFinanceStatisticsVo);
	 }
	
    /**
	 * 保存
	 * @param orderFinanceStatisticsVo
	 */
    public int save(OrderFinanceStatisticsVo orderFinanceStatisticsVo){
    	
    	return orderFinanceStatisticsDao.save(orderFinanceStatisticsVo);
    }
    
    /**
	 * 更新
	 * @param orderFinanceStatisticsVo
	 */
    public int update(OrderFinanceStatisticsVo orderFinanceStatisticsVo){
    	return orderFinanceStatisticsDao.update(orderFinanceStatisticsVo);
    }
    
    /**
	 * 删除
	 * @param orderFinanceStatisticsVo
	 */
    public int delete(OrderFinanceStatisticsVo orderFinanceStatisticsVo){
    	return orderFinanceStatisticsDao.delete(orderFinanceStatisticsVo);
    }
	
	
}

