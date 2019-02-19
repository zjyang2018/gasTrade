/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zach.gasTrade.vo.OrderDeliveryRecordVo;

@Repository("orderDeliveryRecordDao")
public interface OrderDeliveryRecordDao {
	
	 /**
     * 总数
     * @param orderDeliveryRecordVo
     * @return
     */
	 public int getOrderDeliveryRecordCount(OrderDeliveryRecordVo orderDeliveryRecordVo);
	 
	 /**
     * 分页列表
     * @param orderDeliveryRecordVo
     * @return
     */
	 public List<OrderDeliveryRecordVo> getOrderDeliveryRecordPage(OrderDeliveryRecordVo orderDeliveryRecordVo);
	 
	 /**
     * 列表
     * @param orderDeliveryRecordVo
     * @return
     */
	 public List<OrderDeliveryRecordVo> getOrderDeliveryRecordList(OrderDeliveryRecordVo orderDeliveryRecordVo);
	 
	 /**
     * 根据条件查询一条信息
     * @param orderDeliveryRecordVo
     * @return
     */
	 public OrderDeliveryRecordVo getOrderDeliveryRecordBySelective(OrderDeliveryRecordVo orderDeliveryRecordVo);
	 
	 /**
	 * 保存
	 * @param orderDeliveryRecordVo
	 */
    public int save(OrderDeliveryRecordVo orderDeliveryRecordVo);
    
    /**
	 * 更新
	 * @param orderDeliveryRecordVo
	 */
    public int update(OrderDeliveryRecordVo orderDeliveryRecordVo);
    
    /**
	 * 删除
	 * @param orderDeliveryRecordVo
	 */
    public int delete(OrderDeliveryRecordVo orderDeliveryRecordVo);

}
