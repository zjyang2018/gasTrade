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
import com.zach.gasTrade.dao.CustomerUserDao;
import com.zach.gasTrade.service.CustomerUserService;
import com.zach.gasTrade.vo.CustomerUserVo;


@Service("customerUserService")
public class CustomerUserServiceImpl implements CustomerUserService{
	
	@Autowired
	private CustomerUserDao customerUserDao;
	
	 /**
     * 总数
     * @param customerUserVo
     * @return
     */
    public int getCustomerUserCount(CustomerUserVo customerUserVo){
    	return customerUserDao.getCustomerUserCount(customerUserVo);
    }
    
    /**
     * 分页列表
     * @param customerUserVo
     * @return
     */
	 public List<CustomerUserVo> getCustomerUserPage(CustomerUserVo customerUserVo){
		 return customerUserDao.getCustomerUserPage(customerUserVo);
	 }

    /**
     * 列表
     * @param customerUserVo
     * @return
     */
    public List<CustomerUserVo> getCustomerUserList(CustomerUserVo customerUserVo){
    	return customerUserDao.getCustomerUserList(customerUserVo);
    }
    
    /**
     * 根据条件查询一条信息
     * @param customerUserVo
     * @return
     */
	 public CustomerUserVo getCustomerUserBySelective(CustomerUserVo customerUserVo){
		 return customerUserDao.getCustomerUserBySelective(customerUserVo);
	 }
	
    /**
	 * 保存
	 * @param customerUserVo
	 */
    public int save(CustomerUserVo customerUserVo){
    	String id = SerialGenerator.getUUID();
    	Date nowTime = new Date();
    	customerUserVo.setId(id);
    	// TODO wx_open_id、longitude、latitude
    	customerUserVo.setWxOpenId("");
    	customerUserVo.setLongitude("");
    	customerUserVo.setLatitude("");
    	customerUserVo.setCreateTime(nowTime);
    	customerUserVo.setUpdateTime(nowTime);
    	
    	return customerUserDao.save(customerUserVo);
    }
    
    /**
	 * 更新
	 * @param customerUserVo
	 */
    public int update(CustomerUserVo customerUserVo){
    	Date nowTime = new Date();
    	customerUserVo.setUpdateTime(nowTime);
    	
    	return customerUserDao.update(customerUserVo);
    }
    
    /**
	 * 删除
	 * @param customerUserVo
	 */
    public int delete(CustomerUserVo customerUserVo){
    	return customerUserDao.delete(customerUserVo);
    }
	
	
}

