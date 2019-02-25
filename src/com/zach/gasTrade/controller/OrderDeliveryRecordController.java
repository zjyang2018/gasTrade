/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;

import java.util.HashMap;
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
import com.zach.gasTrade.dto.OrderDeliveryCountDto;
import com.zach.gasTrade.dto.OrderDeliveryProgressDto;
import com.zach.gasTrade.service.CustomerUserService;
import com.zach.gasTrade.service.DeliveryUserService;
import com.zach.gasTrade.service.OrderDeliveryRecordService;
import com.zach.gasTrade.service.OrderInfoService;
import com.zach.gasTrade.vo.DeliveryUserVo;
import com.zach.gasTrade.vo.OrderDeliveryRecordVo;
import com.zach.gasTrade.vo.OrderInfoVo;


@Controller
public class OrderDeliveryRecordController {
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private OrderDeliveryRecordService orderDeliveryRecordService;
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	@Autowired
	private CustomerUserService customerUserService;
	
	@Autowired
	private DeliveryUserService deliveryUserService;
		
	/**
	 * 派送统计分页列表
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@RequestMapping(value = "/orderDeliveryRecord/deliveryCountList",method = RequestMethod.POST)
	@ResponseBody
    public PageResult getDeliveryCountList(HttpServletRequest request, @RequestBody Map<String,String> param) {
		PageResult result=PageResult.initResult();
		
		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		int startIndex = (pageNum - 1) * pageSize;
		String name = param.get("deliveryName");
		String deliveryName = name + "%";

		Map<String, Object> map = new HashMap<>();
		map.put("deliveryName", deliveryName);
		map.put("startIndex", startIndex);
		map.put("pageSize", pageSize);
		try{
			int total = orderDeliveryRecordService.getDeliveryUserCount(map);
			List<OrderDeliveryCountDto> list = orderDeliveryRecordService.getOrderDeliveryPage(map);
			
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
	@RequestMapping(value = "/orderDeliveryRecord/save",method = RequestMethod.POST)
	@ResponseBody
	public Result save(HttpServletRequest request,@RequestBody OrderDeliveryRecordVo filterMask) {
		Result result = Result.initResult();
		
		try{
							
			orderDeliveryRecordService.save(filterMask);
			
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
	@RequestMapping(value = "/orderDeliveryRecord/edit",method = RequestMethod.POST)
	@ResponseBody
	public Result edit(HttpServletRequest request,@RequestBody Map<String,String> param) {
		Result result = Result.initResult();
		String orderId = param.get("orderId");
		String deliveryName = param.get("deliveryName");
		
		OrderInfoVo orderInfoVo = new OrderInfoVo();
		orderInfoVo.setOrderId(orderId);
		
		DeliveryUserVo deliveryUserVo = new DeliveryUserVo();
		deliveryUserVo.setName(deliveryName);
		DeliveryUserVo deliveryUser = deliveryUserService.getDeliveryUserBySelective(deliveryUserVo);
		
		orderInfoVo.setAllotDeliveryId(deliveryUser.getId());
				
		try{
			orderInfoService.update(orderInfoVo);
			
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
	 * 进度查询
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/orderDeliveryRecord/query_delivery_progress",method = RequestMethod.POST)
	@ResponseBody
	public DataResult info(HttpServletRequest request,@RequestBody OrderInfoVo filterMask) {
		DataResult result = DataResult.initResult();
			
		try{
			OrderInfoVo orderInfo = orderInfoService.getOrderInfoBySelective(filterMask);
			DeliveryUserVo deliveryUserVo = new DeliveryUserVo();
			deliveryUserVo.setId(orderInfo.getAllotDeliveryId());
			DeliveryUserVo deliveryUser = deliveryUserService.getDeliveryUserBySelective(deliveryUserVo);
			OrderDeliveryRecordVo orderDeliveryRecord = new OrderDeliveryRecordVo();
			
			OrderDeliveryProgressDto orderDeliveryProgressDto = new OrderDeliveryProgressDto();
			orderDeliveryProgressDto.setOrderId(orderInfo.getOrderId());
			orderDeliveryProgressDto.setDeliveryName(deliveryUser.getName());
			orderDeliveryProgressDto.setDeliveryPhoneNumber(deliveryUser.getPhoneNumber());
			orderDeliveryProgressDto.setAcceptTime(orderDeliveryRecord.getAcceptTime());
			orderDeliveryProgressDto.setAllotTime(orderDeliveryRecord.getAllotTime());
			orderDeliveryProgressDto.setCompleteTime(orderDeliveryRecord.getCompleteTime());
			orderDeliveryProgressDto.setDeliveryTime(orderDeliveryRecord.getDeliveryTime());
			orderDeliveryProgressDto.setEndLocation(orderDeliveryRecord.getEndLocation());
			orderDeliveryProgressDto.setMoveLocation(orderDeliveryRecord.getMoveLocation());
			orderDeliveryProgressDto.setStartLocation(orderDeliveryRecord.getStartLocation());
			
			result.setData(orderDeliveryProgressDto);
			
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
