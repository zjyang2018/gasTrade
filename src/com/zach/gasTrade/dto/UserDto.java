package com.zach.gasTrade.dto;

import com.common.utils.StringUtil;
import com.zach.gasTrade.vo.AdminUserVo;
import com.zach.gasTrade.vo.CustomerUserVo;
import com.zach.gasTrade.vo.DeliveryUserVo;

public class UserDto {

	// wxOpenId
	private String wxOpenId;

	// 用户类型:1-客户端用户,2-派送端用户,3-管理端用户,4-即是客户端用户也是派送端用户
	private String userType;

	// 客户端用户信息
	private CustomerUserVo customerUser;

	// 派送端用户信息
	private DeliveryUserVo deliveryUser;

	// 管理端用户信息
	private AdminUserVo adminUser;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public CustomerUserVo getCustomerUser() {
		return customerUser;
	}

	public void setCustomerUser(CustomerUserVo customerUser) {
		this.customerUser = customerUser;
	}

	public DeliveryUserVo getDeliveryUser() {
		return deliveryUser;
	}

	public void setDeliveryUser(DeliveryUserVo deliveryUser) {
		this.deliveryUser = deliveryUser;
	}

	public AdminUserVo getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(AdminUserVo adminUser) {
		this.adminUser = adminUser;
	}

	public String getWxOpenId() {
		if (StringUtil.isNull(wxOpenId)) {
			if (deliveryUser != null) {
				return deliveryUser.getWxOpenId();
			} else if (customerUser != null) {
				return customerUser.getWxOpenId();
			}
		}
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

}
