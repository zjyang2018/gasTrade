/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.dao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zach.gasTrade.vo.CustomerUserVo;

@Repository("customerUserDao")
public interface CustomerUserDao {
	
	 /**
     * 总数
     * @param customerUserVo
     * @return
     */
	 public int getCustomerUserCount(CustomerUserVo customerUserVo);
	 
	 /**
     * 分页列表
     * @param customerUserVo
     * @return
     */
	 public List<CustomerUserVo> getCustomerUserPage(CustomerUserVo customerUserVo);
	 
	 /**
     * 列表
     * @param customerUserVo
     * @return
     */
	 public List<CustomerUserVo> getCustomerUserList(CustomerUserVo customerUserVo);
	 
	 /**
     * 根据条件查询一条信息
     * @param customerUserVo
     * @return
     */
	 public CustomerUserVo getCustomerUserBySelective(CustomerUserVo customerUserVo);
	 
	 /**
	 * 保存
	 * @param customerUserVo
	 */
    public int save(CustomerUserVo customerUserVo);
    
    /**
	 * 更新
	 * @param customerUserVo
	 */
    public int update(CustomerUserVo customerUserVo);
    
    /**
	 * 删除
	 * @param customerUserVo
	 */
    public int delete(CustomerUserVo customerUserVo);

}
