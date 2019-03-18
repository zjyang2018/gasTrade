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
import com.zach.gasTrade.dao.AdminUserDao;
import com.zach.gasTrade.dto.AdminUserDto;
import com.zach.gasTrade.service.AdminUserService;
import com.zach.gasTrade.vo.AdminUserVo;

@Service("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {
	@Autowired
	private AdminUserDao adminUserDao;

	/**
	 * 总数
	 * 
	 * @param adminUserVo
	 * @return
	 */
	public int getAdminUserCount(AdminUserVo adminUserVo) {
		return adminUserDao.getAdminUserCount(adminUserVo);
	}

	/**
	 * 分页列表
	 * 
	 * @param adminUserVo
	 * @return
	 */
	public List<AdminUserVo> getAdminUserPage(AdminUserVo adminUserVo) {
		return adminUserDao.getAdminUserPage(adminUserVo);
	}

	/**
	 * 列表
	 * 
	 * @param adminUserVo
	 * @return
	 */
	public List<AdminUserVo> getAdminUserList(AdminUserVo adminUserVo) {
		return adminUserDao.getAdminUserList(adminUserVo);
	}

	/**
	 * 根据条件查询一条信息
	 * 
	 * @param adminUserVo
	 * @return
	 */
	public AdminUserVo getAdminUserBySelective(AdminUserVo adminUserVo) {
		return adminUserDao.getAdminUserBySelective(adminUserVo);
	}

	/**
	 * 保存
	 * 
	 * @param adminUserDto
	 */
	public int save(AdminUserDto adminUserDto) {
		String id = SerialGenerator.getUUID();
		Date nowTime = new Date();
		adminUserDto.setId(id);
		adminUserDto.setAccountStatus("10");
		adminUserDto.setCreateTime(nowTime);
		adminUserDto.setUpdateTime(nowTime);

		return adminUserDao.save(adminUserDto);
	}

	/**
	 * 更新
	 * 
	 * @param adminUserVo
	 */
	public int update(AdminUserVo adminUserVo) {
		Date nowTime = new Date();
		adminUserVo.setUpdateTime(nowTime);

		return adminUserDao.update(adminUserVo);
	}

	/**
	 * 删除
	 * 
	 * @param adminUserVo
	 */
	public int delete(AdminUserVo adminUserVo) {
		return adminUserDao.delete(adminUserVo);
	}

}
