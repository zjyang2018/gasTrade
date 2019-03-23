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
import com.common.utils.StringUtil;
import com.zach.gasTrade.dao.DeliveryUserDao;
import com.zach.gasTrade.dao.OrderInfoDao;
import com.zach.gasTrade.dto.DeliveryUserDto;
import com.zach.gasTrade.service.DeliveryUserService;
import com.zach.gasTrade.vo.DeliveryUserVo;
import com.zach.gasTrade.vo.OrderInfoVo;

@Service("deliveryUserService")
public class DeliveryUserServiceImpl implements DeliveryUserService {

	@Autowired
	private DeliveryUserDao deliveryUserDao;

	@Autowired
	private OrderInfoDao orderInfoDao;

	/**
	 * 总数
	 * 
	 * @param map
	 * @return
	 */
	public int getDeliveryUserCount(Map<String, Object> map) {
		return deliveryUserDao.getDeliveryUserCount(map);
	}

	/**
	 * 分页列表
	 * 
	 * @param map
	 * @return
	 */
	public List<DeliveryUserVo> getDeliveryUserPage(Map<String, Object> map) {
		return deliveryUserDao.getDeliveryUserPage(map);
	}

	/**
	 * 列表
	 * 
	 * @param deliveryUserVo
	 * @return
	 */
	public List<DeliveryUserVo> getDeliveryUserList(DeliveryUserVo deliveryUserVo) {
		return deliveryUserDao.getDeliveryUserList(deliveryUserVo);
	}

	/**
	 * 根据条件查询一条信息
	 * 
	 * @param deliveryUserVo
	 * @return
	 */
	public DeliveryUserVo getDeliveryUserBySelective(DeliveryUserVo deliveryUserVo) {
		return deliveryUserDao.getDeliveryUserBySelective(deliveryUserVo);
	}

	/**
	 * 保存
	 * 
	 * @param deliveryUserVo
	 */
	public int save(DeliveryUserDto deliveryUserDto) {
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
	 * 
	 * @param deliveryUserVo
	 */
	public int update(DeliveryUserVo deliveryUserVo) {
		Date nowTime = new Date();
		deliveryUserVo.setUpdateTime(nowTime);

		return deliveryUserDao.update(deliveryUserVo);
	}

	/**
	 * 删除
	 * 
	 * @param deliveryUserVo
	 */
	public int delete(DeliveryUserVo deliveryUserVo) {
		return deliveryUserDao.delete(deliveryUserVo);
	}

	@Override
	public List<DeliveryUserVo> getDeliveryUserList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return deliveryUserDao.selectDeliveryUserList(map);
	}

	@Override
	public void checkWorkStatus(String deliveryUserId) {
		Date now = new Date();
		if (StringUtil.isNull(deliveryUserId)) {
			DeliveryUserVo deliveryUserVo = new DeliveryUserVo();
			deliveryUserVo.setAccountStatus("10");
			List<DeliveryUserVo> list = deliveryUserDao.getDeliveryUserList(deliveryUserVo);
			for (DeliveryUserVo bean : list) {
				OrderInfoVo orderInfoVo = new OrderInfoVo();
				orderInfoVo.setAllotDeliveryId(bean.getId());
				orderInfoVo.setAllotStatus("20");
				// 派送中
				orderInfoVo.setOrderStatus("50");
				List<OrderInfoVo> orders = orderInfoDao.getOrderInfoList(orderInfoVo);
				if (orders == null || orders.isEmpty()) {
					// 已接单
					orderInfoVo.setOrderStatus("40");
					orders = orderInfoDao.getOrderInfoList(orderInfoVo);
					if (orders == null || orders.isEmpty()) {
						bean.setWorkStatus("20");
						bean.setUpdateTime(now);
					} else {
						bean.setWorkStatus("10");
						bean.setUpdateTime(now);
					}
				} else {
					bean.setWorkStatus("10");
					bean.setUpdateTime(now);
				}
				deliveryUserDao.update(bean);
			}
		} else {
			DeliveryUserVo deliveryUserVo = new DeliveryUserVo();
			deliveryUserVo.setAccountStatus("10");
			deliveryUserVo.setId(deliveryUserId);
			DeliveryUserVo deliveryUser = deliveryUserDao.getDeliveryUserBySelective(deliveryUserVo);
			if (deliveryUser == null) {
				return;
			}
			OrderInfoVo orderInfoVo = new OrderInfoVo();
			orderInfoVo.setAllotDeliveryId(deliveryUser.getId());
			orderInfoVo.setAllotStatus("20");
			// 派送中
			orderInfoVo.setOrderStatus("50");
			List<OrderInfoVo> orders = orderInfoDao.getOrderInfoList(orderInfoVo);
			if (orders == null || orders.isEmpty()) {
				// 已接单
				orderInfoVo.setOrderStatus("40");
				orders = orderInfoDao.getOrderInfoList(orderInfoVo);
				if (orders == null || orders.isEmpty()) {
					deliveryUser.setWorkStatus("10");
					deliveryUser.setUpdateTime(now);
				} else {
					deliveryUser.setWorkStatus("20");
					deliveryUser.setUpdateTime(now);
				}
			} else {
				deliveryUser.setWorkStatus("20");
				deliveryUser.setUpdateTime(now);
			}
			deliveryUserDao.update(deliveryUser);
		}

	}

}
