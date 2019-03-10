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

@Api(tags = "娲鹃�佸憳鍘嗗彶浣嶇疆淇℃伅鐩稿叧api")
@Controller
public class DeliveryLocationHistoryController {
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private DeliveryLocationHistoryService deliveryLocationHistoryService;

	/**
	 * 鏌ヨ
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "鏌ヨ娲鹃�佸憳鍦板潃", notes = "鏍蜂緥鍙傛暟{\n" + "  \"deliveryUserId\": \"6666666\"\n" + "}\\n杩斿洖鍙傛暟瀛楁璇存槑:\\n")
	@RequestMapping(value = "/deliveryLocationHistory/query")
	@ResponseBody
	public Result query(HttpServletRequest request,@RequestBody DeliveryLocationHistoryVo filterMask) throws Exception {
		DataResult result = DataResult.initResult();

		try {
			// 鏌ヨ
			DeliveryLocationHistoryVo deliveryLocationHistory = deliveryLocationHistoryService.getDeliveryLocationHistoryBySelective(filterMask);	
			result.setData(deliveryLocationHistory);
			
		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("绯荤粺寮傚父," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("绯荤粺寮傚父,璇风◢鍚庨噸璇�");
			logger.error("绯荤粺寮傚父,璇风◢鍚庨噸璇�", e);
		}
		return result;

	}
	
	/**
	 * 鏂板
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "淇濆瓨娲鹃�佸憳鍦板潃", notes = "")
	@RequestMapping(value = "/deliveryLocationHistory/save")
	@ResponseBody
	public Result save(HttpServletRequest request,@RequestBody DeliveryLocationHistoryVo filterMask) {
		Result result = Result.initResult();
		
		try {
			// 淇濆瓨
			deliveryLocationHistoryService.save(filterMask);
			
		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("绯荤粺寮傚父," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("绯荤粺寮傚父,璇风◢鍚庨噸璇�");
			logger.error("绯荤粺寮傚父,璇风◢鍚庨噸璇�", e);
		}
		return result;

	}
	
	/**
	 * 淇敼
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@ApiOperation(value = "鏇存柊娲鹃�佸憳鍦板潃", notes = "鏍蜂緥鍙傛暟")
	@RequestMapping(value = "/deliveryLocationHistory/edit")
	@ResponseBody
	public Result edit(HttpServletRequest request,@RequestBody DeliveryLocationHistoryVo filterMask) {
		Result result = Result.initResult();
		
		try {
			// 鏇存柊
			deliveryLocationHistoryService.update(filterMask);
			
		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("绯荤粺寮傚父," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("绯荤粺寮傚父,璇风◢鍚庨噸璇�");
			logger.error("绯荤粺寮傚父,璇风◢鍚庨噸璇�", e);
		}
		return result;
	}

	/**
	 * 鍒犻櫎
	 * 
	 * @return
	 */
	@ApiOperation(value = "鍒犻櫎娲鹃�佸憳鍦板潃", notes = "鏍蜂緥鍙傛暟{\n" + "  \"deliveryUserId\": \"6666666\"\n" + "}\\n杩斿洖鍙傛暟瀛楁璇存槑:\\n")
	@RequestMapping(value = "/deliveryLocationHistory/delete")
	@ResponseBody
	public Result delete(HttpServletRequest request, DeliveryLocationHistoryVo filterMask) {
		Result result = Result.initResult();
		
		try {
			// 鍒犻櫎
			deliveryLocationHistoryService.delete(filterMask);
			
		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("绯荤粺寮傚父," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("绯荤粺寮傚父,璇风◢鍚庨噸璇�");
			logger.error("绯荤粺寮傚父,璇风◢鍚庨噸璇�", e);
		}
		return result;
	}
}
