/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zach.gasTrade.vo.OrderInfoVo;

@Repository("orderInfoDao")
public interface OrderInfoDao {
	
	 /**
     * 总数
     * @param orderInfoVo
     * @return
     */
	 public int getOrderInfoCount(OrderInfoVo orderInfoVo);
	 
	 /**
     * 分页列表
     * @param orderInfoVo
     * @return
     */
	 public List<OrderInfoVo> getOrderInfoPage(OrderInfoVo orderInfoVo);
	 
	 /**
     * 列表
     * @param orderInfoVo
     * @return
     */
	 public List<OrderInfoVo> getOrderInfoList(OrderInfoVo orderInfoVo);
	 
	 /**
     * 根据条件查询一条信息
     * @param orderInfoVo
     * @return
     */
	 public OrderInfoVo getOrderInfoBySelective(OrderInfoVo orderInfoVo);
	 
	 /**
	 * 保存
	 * @param orderInfoVo
	 */
    public int save(OrderInfoVo orderInfoVo);
    
    /**
	 * 更新
	 * @param orderInfoVo
	 */
    public int update(OrderInfoVo orderInfoVo);
    
    /**
	 * 删除
	 * @param orderInfoVo
	 */
    public int delete(OrderInfoVo orderInfoVo);

}
