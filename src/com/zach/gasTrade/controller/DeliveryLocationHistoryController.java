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
import org.springframework.web.bind.annotation.ResponseBody;

import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.DataResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.service.DeliveryLocationHistoryService;
import com.zach.gasTrade.vo.DeliveryLocationHistoryVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "派送员历史位置相关api")
@Controller
public class DeliveryLocationHistoryController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private DeliveryLocationHistoryService deliveryLocationHistoryService;

	/**
	 * 根据条件查询
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "历史位置查询", notes = "请求参数说明{\n" + "  \"deliveryUserId\": \"6666666\"\n" + "}\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/deliveryLocationHistory/query")
	@ResponseBody
	public Result query(HttpServletRequest request, @RequestBody DeliveryLocationHistoryVo filterMask)
			throws Exception {
		DataResult result = DataResult.initResult();

		try {
			// 查询
			DeliveryLocationHistoryVo deliveryLocationHistory = deliveryLocationHistoryService
					.getDeliveryLocationHistoryBySelective(filterMask);
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
	 * 获取所有派送员最新记录列表
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "获取所有派送员最新记录列表", notes = "请求参数说明:\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/deliveryLocationHistory/allDeliveryLocationList")
	@ResponseBody
	public DataResult queryAllDeliveryLocationList(HttpServletRequest request, Map<String, Object> filterMask)
			throws Exception {
		DataResult result = DataResult.initResult();

		try {
			// 查询
			List<DeliveryLocationHistoryVo> list = deliveryLocationHistoryService
					.queryAllDeliveryLocationList(filterMask);

			result.setData(list);
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
	 * 保存
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "位置信息添加接口", notes = "")
	@RequestMapping(value = "/deliveryLocationHistory/save")
	@ResponseBody
	public Result save(HttpServletRequest request, @RequestBody DeliveryLocationHistoryVo filterMask) {
		Result result = Result.initResult();

		try {
			// 淇濆瓨
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
	@ApiOperation(value = "位置更新接口", notes = "请求参数说明")
	@RequestMapping(value = "/deliveryLocationHistory/edit")
	@ResponseBody
	public Result edit(HttpServletRequest request, @RequestBody DeliveryLocationHistoryVo filterMask) {
		Result result = Result.initResult();

		try {
			// 位置更新
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
	@ApiOperation(value = "位置信息删除接口", notes = "请求参数说明{\n" + "  \"deliveryUserId\": \"6666666\"\n" + "}\\n返回参数字段说明:\\n")
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
