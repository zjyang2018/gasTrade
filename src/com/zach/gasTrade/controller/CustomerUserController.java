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

import com.common.utils.StringUtil;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.PageResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.service.CustomerUserService;
import com.zach.gasTrade.vo.CustomerUserVo;


@Controller
public class CustomerUserController {
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private CustomerUserService customerUserService;
	
	
	/**
	 * 分页列表 + 搜索
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@RequestMapping(value = "/customerUser/query_page",method = RequestMethod.POST)
	@ResponseBody
    public PageResult getPageData(HttpServletRequest request, @RequestBody Map<String,String> param, CustomerUserVo filterMask) {
		PageResult result=PageResult.initResult();
		
		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String searchParam = param.get("searchParam");
		String channel = param.get("channel");
		
		if(StringUtil.isNotNullAndNotEmpty(searchParam)) {
			String selectParam = searchParam.trim() + "%";
			// searchParam以"1"开头则按手机号搜索
			if(selectParam.startsWith("1")) {
				filterMask.setPhoneNumber(selectParam);
			}else {
				filterMask.setName(selectParam);
			}
		}
		
		if(StringUtil.isNotNullAndNotEmpty(channel)) {
			filterMask.setChannel(channel);
		}
		filterMask.setPage(pageNum);
		filterMask.setPageSize(pageSize);
		try{
			int total = customerUserService.getCustomerUserCount(filterMask);
			List<CustomerUserVo> list = customerUserService.getCustomerUserPage(filterMask);
			
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
	@RequestMapping(value = "/customerUser/save",method = RequestMethod.POST)
	@ResponseBody
	public Result save(HttpServletRequest request,@RequestBody CustomerUserVo filterMask) {
		Result result = Result.initResult();
				
		try{
			customerUserService.save(filterMask);
			
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
	@RequestMapping(value = "/customerUser/edit",method = RequestMethod.POST)
	@ResponseBody
	public Result edit(HttpServletRequest request,@RequestBody CustomerUserVo filterMask) {
		Result result = Result.initResult();
		if(StringUtil.isNullOrEmpty(filterMask.getId())) {
			result.setCode(Constants.FAILURE);
			result.setMsg("客户编号不能为空");
			return result;
		}
				
		try{
			customerUserService.update(filterMask);
			
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
