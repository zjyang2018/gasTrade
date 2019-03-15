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

import com.common.seq.SerialGenerator;
import com.zach.gasTrade.dao.DeliveryUserDao;
import com.zach.gasTrade.dto.DeliveryUserDto;
import com.zach.gasTrade.service.DeliveryUserService;
import com.zach.gasTrade.vo.DeliveryUserVo;


@Service("deliveryUserService")
public class DeliveryUserServiceImpl implements DeliveryUserService{
	
	@Autowired
	private DeliveryUserDao deliveryUserDao;
	
	 /**
     * 总数
     * @param map
     * @return
     */
    public int getDeliveryUserCount(Map<String,Object> map){
    	return deliveryUserDao.getDeliveryUserCount(map);
    }
    
    /**
     * 分页列表
     * @param map
     * @return
     */
	 public List<DeliveryUserVo> getDeliveryUserPage(Map<String,Object> map){
		 return deliveryUserDao.getDeliveryUserPage(map);
	 }

    /**
     * 列表
     * @param deliveryUserVo
     * @return
     */
    public List<DeliveryUserVo> getDeliveryUserList(DeliveryUserVo deliveryUserVo){
    	return deliveryUserDao.getDeliveryUserList(deliveryUserVo);
    }
    
    /**
     * 根据条件查询一条信息
     * @param deliveryUserVo
     * @return
     */
	 public DeliveryUserVo getDeliveryUserBySelective(DeliveryUserVo deliveryUserVo){
		 return deliveryUserDao.getDeliveryUserBySelective(deliveryUserVo);
	 }
	
    /**
	 * 保存
	 * @param deliveryUserVo
	 */
    public int save(DeliveryUserDto deliveryUserDto){
    	String id = SerialGenerator.getUUID();
    	Date nowTime = new Date();
    	deliveryUserDto.setId(id);
    	deliveryUserDto.setEquipNo("");
    	deliveryUserDto.setAccountStatus("10");
    	deliveryUserDto.setWorkStatus("20");
    	deliveryUserDto.setFrozenReason("");
    	deliveryUserDto.setCreateTime(nowTime);
    	deliveryUserDto.setUpdateTime(nowTime);
    	
    	return deliveryUserDao.save(deliveryUserDto);
    }
    
    /**
	 * 更新
	 * @param deliveryUserVo
	 */
    public int update(DeliveryUserVo deliveryUserVo){
    	Date nowTime = new Date();
    	deliveryUserVo.setUpdateTime(nowTime);
    	
    	return deliveryUserDao.update(deliveryUserVo);
    }
    
    /**
	 * 删除
	 * @param deliveryUserVo
	 */
    public int delete(DeliveryUserVo deliveryUserVo){
    	return deliveryUserDao.delete(deliveryUserVo);
    }
	
	
}

