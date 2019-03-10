/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.DataResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.service.DeliveryLocationHistoryService;
import com.zach.gasTrade.vo.DeliveryLocationHistoryVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "派送员历史位置信息相关api")
@Controller
public class DeliveryLocationHistoryController {
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private DeliveryLocationHistoryService deliveryLocationHistoryService;

	/**
	 * 查询
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "查询派送员地址", notes = "样例参数{\n" + "  \"deliveryUserId\": \"6666666\"\n" + "}\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/deliveryLocationHistory/query")
	@ResponseBody
	public Result query(HttpServletRequest request,@RequestBody DeliveryLocationHistoryVo filterMask) throws Exception {
		DataResult result = DataResult.initResult();

		try {
			// 查询
			DeliveryLocationHistoryVo deliveryLocationHistory = deliveryLocationHistoryService.getDeliveryLocationHistoryBySelective(filterMask);	
			result.setData(deliveryLocationHistory);
			
		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		return result;

	}
	
	/**
	 * 新增
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "保存派送员地址", notes = "样例参数{\n" + "  \"deliveryUserId\": \"6666666\",\n"
			+ "  \"location\": \"长沙市中心\",\n" + "  \"longitude\": \"112.59\",\n"
			+ "  \"latitude\": \"28.12\"\n" + "}\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/deliveryLocationHistory/save")
	@ResponseBody
	public Result save(HttpServletRequest request,@RequestBody DeliveryLocationHistoryVo filterMask) {
		Result result = Result.initResult();
		
		try {
			// 保存
			deliveryLocationHistoryService.save(filterMask);
			
		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		return result;

	}
	
	/**
	 * 修改
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "更新派送员地址", notes = "样例参数{\n" + "  \"deliveryUserId\": \"6666666\",\n"
			+ "  \"location\": \"长沙市中心\",\n" + "  \"longitude\": \"112.59\",\n"
			+ "  \"latitude\": \"28.12\"\n" + "}\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/deliveryLocationHistory/edit")
	@ResponseBody
	public Result edit(HttpServletRequest request,@RequestBody DeliveryLocationHistoryVo filterMask) {
		Result result = Result.initResult();
		
		try {
			// 更新
			deliveryLocationHistoryService.update(filterMask);
			
		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@ApiOperation(value = "删除派送员地址", notes = "样例参数{\n" + "  \"deliveryUserId\": \"6666666\"\n" + "}\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/deliveryLocationHistory/delete")
	@ResponseBody
	public Result delete(HttpServletRequest request, DeliveryLocationHistoryVo filterMask) {
		Result result = Result.initResult();
		
		try {
			// 删除
			deliveryLocationHistoryService.delete(filterMask);
			
		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		return result;
	}
}
