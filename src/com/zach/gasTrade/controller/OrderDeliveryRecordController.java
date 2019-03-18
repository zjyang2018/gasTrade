/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.common.utils.CalendarUtils;
import com.common.utils.StringUtil;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.DataResult;
import com.zach.gasTrade.common.ExcelUtils;
import com.zach.gasTrade.common.PageResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.dto.OrderDeliveryCountDto;
import com.zach.gasTrade.service.CustomerUserService;
import com.zach.gasTrade.service.DeliveryUserService;
import com.zach.gasTrade.service.OrderDeliveryRecordService;
import com.zach.gasTrade.service.OrderInfoService;
import com.zach.gasTrade.vo.DeliveryUserVo;
import com.zach.gasTrade.vo.OrderDeliveryRecordVo;
import com.zach.gasTrade.vo.OrderInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "订单派送记录相关api")
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
	 * 
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@RequestMapping(value = "/orderDeliveryRecord/deliveryCountList", method = RequestMethod.POST)
	@ResponseBody
	public PageResult getDeliveryCountList(HttpServletRequest request, @RequestBody Map<String, String> param) {
		PageResult result = PageResult.initResult();
		Map<String, Object> map = new HashMap<String, Object>();

		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		int startIndex = (pageNum - 1) * pageSize;
		String name = param.get("deliveryName");
		if (StringUtil.isNotNullAndNotEmpty(name)) {
			String deliveryName = name + "%";
			map.put("deliveryName", deliveryName);
		}
		map.put("startIndex", startIndex);
		map.put("pageSize", pageSize);
		try {
			int total = orderDeliveryRecordService.getOrderDeliveryStatisticsCount(map);
			List<OrderDeliveryCountDto> list = orderDeliveryRecordService.getOrderDeliveryStatisticsPage(map);

			result.setAllCount(total);
			result.setPageNum(pageNum);
			result.setPageSize(pageSize);
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
	 * 派送统计列表导出
	 * 
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@RequestMapping(value = "/orderDeliveryRecord/exportDeliveryCountList", method = RequestMethod.GET)
	public void exportDeliveryCountList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, String> param) {
		PageResult result = PageResult.initResult();
		Map<String, Object> map = new HashMap<String, Object>();

		String name = param.get("deliveryName");
		if (StringUtil.isNotNullAndNotEmpty(name)) {
			String deliveryName = name + "%";
			map.put("deliveryName", deliveryName);
		}
		try {
			List<OrderDeliveryCountDto> list = orderDeliveryRecordService.getOrderDeliveryStatisticsList(map);
			// 声明一个工作薄
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFRow row = ExcelUtils.constructDeliverStatisticsHeader(wb);
			HSSFSheet sheet = wb.getSheet("派送统计");
			int index = 0;

			for (OrderDeliveryCountDto bean : list) {
				// 构造导出列表数据
				row = sheet.createRow(index + 1);
				row.createCell(0).setCellValue(index + 1);
				row.createCell(1).setCellValue(new HSSFRichTextString(bean.getDeliveryName()));

				row.createCell(2).setCellValue(new HSSFRichTextString(String.valueOf(bean.getDayCompleteTime())));

				row.createCell(3).setCellValue(new HSSFRichTextString(String.valueOf(bean.getDayAcceptTime())));

				row.createCell(4).setCellValue(new HSSFRichTextString(String.valueOf(bean.getMonthCompleteTime())));

				row.createCell(5)
						.setCellValue(new HSSFRichTextString(String.valueOf(bean.getAccumulatedCompleteTime())));
			}

			OutputStream output = response.getOutputStream();
			String titleName = "派送统计";
			String excelName = URLEncoder.encode(titleName, "utf-8");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + excelName + ".xls");
			response.flushBuffer();
			wb.write(output);
			output.close();

		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
	}

	/**
	 * 新增
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/orderDeliveryRecord/save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(HttpServletRequest request, @RequestBody OrderDeliveryRecordVo filterMask) {
		Result result = Result.initResult();

		try {

			orderDeliveryRecordService.save(filterMask);

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
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/orderDeliveryRecord/edit", method = RequestMethod.POST)
	@ResponseBody
	public Result edit(HttpServletRequest request, @RequestBody Map<String, String> param) {
		Result result = Result.initResult();
		String orderId = param.get("orderId");
		if (StringUtil.isNullOrEmpty(orderId)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("订单编号不能为空");
			return result;
		}
		String deliveryName = param.get("deliveryName");

		OrderInfoVo orderInfoVo = new OrderInfoVo();
		orderInfoVo.setOrderId(orderId);

		DeliveryUserVo deliveryUserVo = new DeliveryUserVo();
		deliveryUserVo.setName(deliveryName);
		DeliveryUserVo deliveryUser = deliveryUserService.getDeliveryUserBySelective(deliveryUserVo);

		orderInfoVo.setAllotDeliveryId(deliveryUser.getId());

		try {
			orderInfoService.update(orderInfoVo);

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
	 * 进度查询
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@ApiOperation(value = "订单派送进度详情", notes = "请求参数说明||orderId:订单编号\\n返回参数字段说明:"
			+ "状态码:1为订单未分配,startLocation:商家位置,endLocation:客户地址,订单派送进度\\n")
	@RequestMapping(value = "/orderDeliveryRecord/query_delivery_progress", method = RequestMethod.POST)
	@ResponseBody
	public DataResult info(HttpServletRequest request, @RequestBody OrderDeliveryRecordVo filterMask) {
		logger.info("订单派送进度详情参数:" + JSON.toJSONString(filterMask));
		DataResult result = DataResult.initResult();
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> orderDeliveryProgress = new ArrayList<String>();
		try {
			if (StringUtil.isNullOrEmpty(filterMask.getOrderId())) {
				throw new RuntimeException("订单编号不能为空");
			}
			OrderDeliveryRecordVo orderDeliveryRecord = orderDeliveryRecordService
					.getOrderDeliveryRecordBySelective(filterMask);
			if (orderDeliveryRecord == null) {
				result.setCode(1);
				result.setMsg("订单暂未分配,请稍等");
				return result;
			}
			OrderInfoVo orderInfoVo = new OrderInfoVo();
			orderInfoVo.setOrderId(filterMask.getOrderId());
			OrderInfoVo orderInfo = orderInfoService.getOrderInfoBySelective(orderInfoVo);
			String order_status = orderInfo.getOrderStatus();
			// 获取派送员信息
			DeliveryUserVo deliveryUserVo = new DeliveryUserVo();
			deliveryUserVo.setId(orderInfo.getAllotDeliveryId());
			DeliveryUserVo deliveryUser = deliveryUserService.getDeliveryUserBySelective(deliveryUserVo);
			String deliveryUserName = deliveryUser.getName();
			String deliveryUserPhoneNumber = deliveryUser.getPhoneNumber();
			String allotTime = CalendarUtils.formatDateTime(orderDeliveryRecord.getAllotTime());

			String msg1 = "订单已自动分配给派送员 (" + deliveryUserName + ": " + deliveryUserPhoneNumber + ") " + allotTime;
			orderDeliveryProgress.add(msg1);

			// 判断派送员是否接单
			if ("40".equals(order_status)) {
				String acceptTime = CalendarUtils.formatDateTime(orderDeliveryRecord.getAcceptTime());
				String msg2 = "派送员 (" + deliveryUserName + ": " + deliveryUserPhoneNumber + ") 已接单 " + acceptTime;
				orderDeliveryProgress.add(msg2);
			}
			// 订单派送中
			if ("50".equals(order_status)) {
				String deliveryTime = CalendarUtils.formatDateTime(orderDeliveryRecord.getDeliveryTime());
				String msg3 = "派送员 (" + deliveryUserName + ": " + deliveryUserPhoneNumber + ") 已取货物, 正在派送  "
						+ deliveryTime;
				orderDeliveryProgress.add(msg3);
			}
			// 订单派送完成
			if ("60".equals(order_status)) {
				String completeTime = CalendarUtils.formatDateTime(orderDeliveryRecord.getCompleteTime());
				String msg4 = "派送员 (" + deliveryUserName + ": " + deliveryUserPhoneNumber + ") 已到达目的地, 且客户签收！  "
						+ completeTime;
				orderDeliveryProgress.add(msg4);
			}
			// 判断是移动端还是PC端访问
			String userAgent = request.getHeader("user-agent");
			// 移动端
			if (userAgent.indexOf("Android") != -1 || userAgent.indexOf("iPhone") != -1
					|| userAgent.indexOf("iPad") != -1) {
				map.put("startLocation", orderDeliveryRecord.getStartLocation());
				map.put("endLocation", orderDeliveryRecord.getEndLocation());
			} else {
				// PC端
				map.put("startLocation", orderDeliveryRecord.getStartLocation());
				map.put("endLocation", orderDeliveryRecord.getEndLocation());
				map.put("moveLocation", orderDeliveryRecord.getMoveLocation());
			}
			map.put("orderDeliveryProgress", orderDeliveryProgress);

			result.setData(map);

		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("订单派送进度详情," + JSON.toJSONString(filterMask), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		return result;
	}
}
