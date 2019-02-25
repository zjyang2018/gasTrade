/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zach.gasTrade.dao.OrderInfoDao;
import com.zach.gasTrade.dto.DeliveryMonitorDto;
import com.zach.gasTrade.dto.OrderFinanceStatisticsDto;
import com.zach.gasTrade.dto.OrderListDto;
import com.zach.gasTrade.service.OrderInfoService;
import com.zach.gasTrade.vo.OrderInfoVo;


@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService{
	
	@Autowired
	private OrderInfoDao orderInfoDao;
	
	 /**
     * 总数
     * @param orderListDto
     * @return
     */
    public int getOrderInfoCount(OrderListDto orderListDto){
    	return orderInfoDao.getOrderInfoCount(orderListDto);
    }
    
    /**
     * 监控总数
     * @param map
     * @return
     */
	 public int getDeliveryMonitorCount(Map<String, Object> map) {
		 return orderInfoDao.getDeliveryMonitorCount(map);
	 }
	 
	 /**
	     * 监控分页列表
	     * @param map
	     * @return
	     */
		 public List<DeliveryMonitorDto> getDeliveryMonitorPage(Map<String, Object> map) {
			 return orderInfoDao.getDeliveryMonitorPage(map);
		 }
    
    /**
     * 分页列表
     * @param orderInfoModel
     * @return
     */
	 public List<OrderListDto> getOrderInfoPage(OrderListDto orderListDto){
		 return orderInfoDao.getOrderInfoPage(orderListDto);
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
    	Date nowTime = new Date();
    	orderInfoVo.setUpdateTime(nowTime);
    	
    	return orderInfoDao.update(orderInfoVo);
    }
    
    /**
	 * 删除
	 * @param orderInfoVo
	 */
    public int delete(OrderInfoVo orderInfoVo){
    	return orderInfoDao.delete(orderInfoVo);
    }
    
    /**
  	 * 每日订单数量统计
  	 */
      public OrderFinanceStatisticsDto getOrderDayCount() {
    	  return orderInfoDao.getOrderDayCount();
      }
	
	
}

