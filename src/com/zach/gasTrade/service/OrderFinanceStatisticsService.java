/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service;

import com.zach.gasTrade.vo.OrderFinanceStatisticsVo;
import java.util.List;

public interface OrderFinanceStatisticsService{
	/**
     * 总数
     * @param orderFinanceStatisticsVo
     * @return
     */
	 public int getOrderFinanceStatisticsCount(OrderFinanceStatisticsVo orderFinanceStatisticsVo);
	 
	 /**
     * 分页列表
     * @param orderFinanceStatisticsVo
     * @return
     */
	 public List<OrderFinanceStatisticsVo> getOrderFinanceStatisticsPage(OrderFinanceStatisticsVo orderFinanceStatisticsVo);
	 
	 /**
     * 列表
     * @param orderFinanceStatisticsVo
     * @return
     */
	 public List<OrderFinanceStatisticsVo> getOrderFinanceStatisticsList(OrderFinanceStatisticsVo orderFinanceStatisticsVo);
	 
	 /**
     * 根据条件查询一条信息
     * @param orderFinanceStatisticsVo
     * @return
     */
	 public OrderFinanceStatisticsVo getOrderFinanceStatisticsBySelective(OrderFinanceStatisticsVo orderFinanceStatisticsVo);
	 
	 /**
	 * 保存
	 * @param orderFinanceStatisticsVo
	 */
    public int save(OrderFinanceStatisticsVo orderFinanceStatisticsVo);
    
    /**
	 * 更新
	 * @param orderFinanceStatisticsVo
	 */
    public int update(OrderFinanceStatisticsVo orderFinanceStatisticsVo);
    
    /**
	 * 删除
	 * @param orderFinanceStatisticsVo
	 */
    public int delete(OrderFinanceStatisticsVo orderFinanceStatisticsVo);
    

}

