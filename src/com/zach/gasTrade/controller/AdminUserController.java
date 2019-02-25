/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.DataResult;
import com.zach.gasTrade.common.PageResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.dto.AdminUserDto;
import com.zach.gasTrade.service.AdminUserService;
import com.zach.gasTrade.vo.AdminUserVo;


@Controller
public class AdminUserController {	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private AdminUserService adminUserService;
	
	/**
	 * 登录
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/adminUser/login", method = RequestMethod.POST)
	@ResponseBody
	public Result login(HttpServletRequest request, @RequestBody Map<String,String> param, AdminUserVo filterMask) {
		Result result=Result.initResult();
		
		String name = param.get("name");
		String password = param.get("password");
		
		filterMask.setName(name);
		
		try {		
			AdminUserVo adminUserVo = adminUserService.getAdminUserBySelective(filterMask);
			String pwd = adminUserVo.getPassword();
			if(!password.equals(pwd)) {
				result.setCode(Constants.FAILURE);
				result.setMsg("密码不正确");
			}
		}catch (RuntimeException e){
			result.setCode(Constants.FAILURE);
			result.setMsg("用户不存在");
			logger.error("系统异常,"+e.getMessage(),e);
		}catch (Exception e){
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试",e);
		}
		return result;	
	}
	
	/**
	 * 分页列表  + 搜索
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@RequestMapping(value = "/adminUser/query_page",method = RequestMethod.POST)
	@ResponseBody
    public PageResult getPageData(HttpServletRequest request, @RequestBody Map<String,String> param, AdminUserVo filterMask) {
		PageResult result=PageResult.initResult();
		
		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String searchParam = param.get("searchParam");
		String workStatus = param.get("workStatus");
		String accountStatus = param.get("accountStatus");
		String selectParam = searchParam.trim() + "%";
		// searchParam以"1"开头则按手机号搜索
		if(selectParam.startsWith("1")) {
			filterMask.setPhoneNumber(selectParam);
		}else {
			filterMask.setName(selectParam);
		}
		filterMask.setWorkStatus(workStatus);
		filterMask.setAccountStatus(accountStatus);
		filterMask.setPage(pageNum);
		filterMask.setPageSize(pageSize);
		try{
			int total = adminUserService.getAdminUserCount(filterMask);
			List<AdminUserVo> list = adminUserService.getAdminUserPage(filterMask);
			
			result.setAllCount(total);
			result.setPageNum(pageNum);
			result.setPageSize(pageSize);
			result.setData(list);
		}catch (RuntimeException e){
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常,"+e.getMessage(),e);
		}catch (Exception e){
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试",e);
		}
		return result;
    }


	/**
	 * 新增
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/adminUser/save",method = RequestMethod.POST)
	@ResponseBody
	public Result save(HttpServletRequest request,@RequestBody AdminUserDto filterMask) {
		Result result = Result.initResult();
		
		String initialPassword = filterMask.getInitialPassword();
		String confirmPassword = filterMask.getComfirmPassword();
		if(!initialPassword.equals(confirmPassword)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("确认密码输入不正确");
			return result;
		}
		
		filterMask.setPassword(initialPassword);
				
		try{
			adminUserService.save(filterMask);
			
		}catch (RuntimeException e){
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常,"+e.getMessage(),e);
		}catch (Exception e){
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试",e);
		}
		return result;
	}
	
	/**
	 * 修改
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/adminUser/edit",method = RequestMethod.POST)
	@ResponseBody
	public Result edit(HttpServletRequest request,@RequestBody AdminUserVo filterMask) {
		Result result = Result.initResult();
				
		try{
			adminUserService.update(filterMask);
			
		}catch (RuntimeException e){
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常,"+e.getMessage(),e);
		}catch (Exception e){
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试",e);
		}
		return result;
	}
	
	/**
	 * 删除
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/adminUser/delete",method = RequestMethod.POST)
	@ResponseBody
	public Result delete(HttpServletRequest request,@RequestBody AdminUserVo filterMask) {
		Result result = Result.initResult();
		
		filterMask.setAccountStatus("20");
				
		try{
			adminUserService.update(filterMask);
			
		}catch (RuntimeException e){
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常,"+e.getMessage(),e);
		}catch (Exception e){
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试",e);
		}
		return result;
	}
	
	/**
	 * 详情
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/adminUser/info",method = RequestMethod.POST)
	@ResponseBody
	public DataResult info(HttpServletRequest request,@RequestBody AdminUserVo filterMask) {
		DataResult result = DataResult.initResult();
			
		try{
			AdminUserVo adminUser = adminUserService.getAdminUserBySelective(filterMask);
			result.setData(adminUser);
			
		}catch (RuntimeException e){
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常,"+e.getMessage(),e);
		}catch (Exception e){
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试",e);
		}
		return result;
	}
}
