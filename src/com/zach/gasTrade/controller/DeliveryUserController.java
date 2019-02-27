/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;

import java.util.Date;
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

import com.common.utils.DateTimeUtils;
import com.common.utils.StringUtil;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.PageResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.dto.DeliveryUserDto;
import com.zach.gasTrade.service.DeliveryUserService;
import com.zach.gasTrade.vo.DeliveryUserVo;


@Controller
public class DeliveryUserController {
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private DeliveryUserService deliveryUserService;
	
	
	/**
	 * 分页列表 + 搜索
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@RequestMapping(value = "/deliveryUser/query_page",method = RequestMethod.POST)
	@ResponseBody
    public PageResult getPageData(HttpServletRequest request, @RequestBody Map<String,String> param, DeliveryUserVo filterMask) {
		PageResult result=PageResult.initResult();
		
		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String searchParam = param.get("searchParam");
		if(StringUtil.isNotNullAndNotEmpty(searchParam)) {
			String selectParam = searchParam.trim() + "%";
			// searchParam以"1"开头则按手机号搜索
			if(selectParam.startsWith("1")) {
				filterMask.setPhoneNumber(selectParam);
			} else {
				filterMask.setName(selectParam);
			}
		}
		
		filterMask.setPage(pageNum);
		filterMask.setPageSize(pageSize);
		try{
			int total = deliveryUserService.getDeliveryUserCount(filterMask);
			List<DeliveryUserVo> list = deliveryUserService.getDeliveryUserPage(filterMask);
			
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
	 * 分页列表 + 搜索(人员管理)
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@RequestMapping(value = "/deliveryUser/select_page",method = RequestMethod.POST)
	@ResponseBody
    public PageResult selectPageData(HttpServletRequest request, @RequestBody Map<String,String> param, DeliveryUserVo filterMask) {
		PageResult result=PageResult.initResult();
		
		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String  dateParam = param.get("dateParam");
		String  workStatus = param.get("workStatus");
		
		String searchParam = param.get("searchParam");
		if(StringUtil.isNotNullAndNotEmpty(searchParam)) {
			String selectParam = searchParam.trim() + "%";
			// searchParam以"1"开头则按手机号搜索
			if(selectParam.startsWith("1")) {
				filterMask.setPhoneNumber(selectParam);
			} else {
				filterMask.setName(selectParam);
			}
		}
		if(StringUtil.isNotNullAndNotEmpty(workStatus)) {
			filterMask.setWorkStatus(workStatus);
		}
		if(StringUtil.isNotNullAndNotEmpty(dateParam)) {
			Date date = DateTimeUtils.stringToDate(dateParam, new Date().toString());
			filterMask.setUpdateTime(date);
		}
		filterMask.setAccountStatus("10");
		filterMask.setPage(pageNum);
		filterMask.setPageSize(pageSize);
		try{
			int total = deliveryUserService.getDeliveryUserCount(filterMask);
			List<DeliveryUserVo> list = deliveryUserService.getDeliveryUserPage(filterMask);
			
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
	@RequestMapping(value = "/deliveryUser/save",method = RequestMethod.POST)
	@ResponseBody
	public Result save(HttpServletRequest request,@RequestBody DeliveryUserDto filterMask) {
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
			deliveryUserService.save(filterMask);
			
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
	@RequestMapping(value = "/deliveryUser/edit",method = RequestMethod.POST)
	@ResponseBody
	public Result edit(HttpServletRequest request,@RequestBody DeliveryUserVo filterMask) {
		Result result = Result.initResult();
		if(StringUtil.isNullOrEmpty(filterMask.getId())) {
			result.setCode(Constants.FAILURE);
			result.setMsg("派送员编号不能为空");
			return result;
		}
				
		try{
			deliveryUserService.update(filterMask);
			
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
	@RequestMapping(value = "/deliveryUser/delete",method = RequestMethod.POST)
	@ResponseBody
	public Result delete(HttpServletRequest request,@RequestBody DeliveryUserVo filterMask) {
		Result result = Result.initResult();
		if(StringUtil.isNullOrEmpty(filterMask.getId())) {
			result.setCode(Constants.FAILURE);
			result.setMsg("派送员编号不能为空");
			return result;
		}
		
		filterMask.setAccountStatus("20");
				
		try{
			deliveryUserService.update(filterMask);
			
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
