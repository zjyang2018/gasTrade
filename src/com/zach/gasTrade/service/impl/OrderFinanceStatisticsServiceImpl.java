/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.seq.SerialGenerator;
import com.common.utils.DateTimeUtils;
import com.zach.gasTrade.dao.OrderFinanceStatisticsDao;
import com.zach.gasTrade.dto.OrderFinanceStatisticsDto;
import com.zach.gasTrade.service.OrderFinanceStatisticsService;
import com.zach.gasTrade.service.OrderInfoService;
import com.zach.gasTrade.vo.OrderFinanceStatisticsVo;


@Service("orderFinanceStatisticsService")
public class OrderFinanceStatisticsServiceImpl implements OrderFinanceStatisticsService{
	
	@Autowired
	private OrderFinanceStatisticsDao orderFinanceStatisticsDao;
	
	@Autowired
	private OrderInfoService orderInfoService;
	
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
	 */
    public int save(){
    	OrderFinanceStatisticsVo orderFinanceStatisticsVo = new OrderFinanceStatisticsVo();
    	OrderFinanceStatisticsDto orderFinanceStatisticsDto = orderInfoService.getOrderDayCount();
    	String id = SerialGenerator.getUUID();
    	Date nowTime = new Date();
    	orderFinanceStatisticsVo.setId(id);
    	orderFinanceStatisticsVo.setDate(DateTimeUtils.dateToString(nowTime, "yyyy-MM-dd"));
    	orderFinanceStatisticsVo.setCreateTime(nowTime);
    	orderFinanceStatisticsVo.setAvgAmount(orderFinanceStatisticsDto.getAvgAmount());
    	orderFinanceStatisticsVo.setBuyerCount(orderFinanceStatisticsDto.getBuyerCount());
    	orderFinanceStatisticsVo.setDeliveryOrderCount(orderFinanceStatisticsDto.getDeliveryOrderCount());
    	orderFinanceStatisticsVo.setOrderAmount(orderFinanceStatisticsDto.getOrderAmount());
    	orderFinanceStatisticsVo.setOrderCount(orderFinanceStatisticsDto.getOrderCount());    	
    	
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

