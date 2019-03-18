/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service;

import java.util.List;

import com.zach.gasTrade.dto.AdminUserDto;
import com.zach.gasTrade.vo.AdminUserVo;

public interface AdminUserService {
	/**
	 * 总数
	 * 
	 * @param adminUserVo
	 * @return
	 */
	public int getAdminUserCount(AdminUserVo adminUserVo);

	/**
	 * 分页列表
	 * 
	 * @param adminUserVo
	 * @return
	 */
	public List<AdminUserVo> getAdminUserPage(AdminUserVo adminUserVo);

	/**
	 * 列表
	 * 
	 * @param adminUserVo
	 * @return
	 */
	public List<AdminUserVo> getAdminUserList(AdminUserVo adminUserVo);

	/**
	 * 根据条件查询一条信息
	 * 
	 * @param adminUserVo
	 * @return
	 */
	public AdminUserVo getAdminUserBySelective(AdminUserVo adminUserVo);

	/**
	 * 保存
	 * 
	 * @param adminUserVo
	 */
	public int save(AdminUserDto adminUserDto);

	/**
	 * 更新
	 * 
	 * @param adminUserVo
	 */
	public int update(AdminUserVo adminUserVo);

	/**
	 * 删除
	 * 
	 * @param adminUserVo
	 */
	public int delete(AdminUserVo adminUserVo);

}
