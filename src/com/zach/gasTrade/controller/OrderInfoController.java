/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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
import com.common.utils.DateTimeUtils;
import com.common.utils.StringUtil;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.DataResult;
import com.zach.gasTrade.common.ExcelUtils;
import com.zach.gasTrade.common.PageResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.dto.CustomerOrderInfoDto;
import com.zach.gasTrade.dto.DeliveryMonitorDto;
import com.zach.gasTrade.dto.DeliveryOrderInfoDto;
import com.zach.gasTrade.dto.OrderInfoDetailDto;
import com.zach.gasTrade.dto.OrderInfoDto;
import com.zach.gasTrade.dto.OrderListDto;
import com.zach.gasTrade.dto.UserDto;
import com.zach.gasTrade.netpay.UnoinPayUtil;
import com.zach.gasTrade.service.CustomerUserService;
import com.zach.gasTrade.service.DeliveryUserService;
import com.zach.gasTrade.service.OrderInfoService;
import com.zach.gasTrade.vo.CustomerUserVo;
import com.zach.gasTrade.vo.DeliveryUserVo;
import com.zach.gasTrade.vo.OrderInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "订单相关api")
@Controller
public class OrderInfoController extends CommonController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	private CustomerUserService customerUserService;

	@Autowired
	private DeliveryUserService deliveryUserService;

	// @Autowired
	// private ProductService productService;
	//
	// @Autowired
	// private AdminUserService adminUserService;

	/**
	 * 分页列表 + 搜索 + 高级搜索
	 * 
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@ApiOperation(value = "pc端订单列表", notes = "请求参数说明||pageNum:页码 ,pageSize:每页条数 ,orderId:订单编号,payStatus:支付状态:10-未支付,20-已支付,"
			+ "searchCustomerParam:按客户姓名或手机号搜索,searchDeliveryParam:按派送员姓名或手机号搜索,"
			+ "allotStatus:分配状态:10-未分配，20-已分配,orderStatus:订单状态:10-未支付,20-已支付,30-已分配,40-已接单,50-派送中,60-派送完成,payTime:支付时间\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/orderInfo/query_page", method = RequestMethod.POST)
	@ResponseBody
	public PageResult getPageData(HttpServletRequest request, @RequestBody Map<String, String> param,
			OrderListDto filterMask) {
		PageResult result = PageResult.initResult();
		logger.info("pc端订单列表接口参数:" + JSON.toJSONString(param));
		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String orderId = param.get("orderId");
		String payStatus = param.get("payStatus");
		String searchCustomerParam = param.get("searchCustomerParam");
		String searchDeliveryParam = param.get("searchDeliveryParam");
		String allotStatus = param.get("allotStatus");
		String orderStatus = param.get("orderStatus");
		// String payTime = param.get("payTime");
		String startCreateTime = param.get("startCreateTime");
		String endCreateTime = param.get("endCreateTime");

		OrderInfoVo queryParam = new OrderInfoVo();

		if (StringUtil.isNotNullAndNotEmpty(orderId)) {
			queryParam.setOrderId(orderId.trim());
		}

		if (StringUtil.isNotNullAndNotEmpty(payStatus)) {
			queryParam.setPayStatus(payStatus);
		}

		if (StringUtil.isNotNullAndNotEmpty(searchCustomerParam)) {
			queryParam.setSelectCustomerParam(searchCustomerParam.trim());
		}

		if (StringUtil.isNotNullAndNotEmpty(searchDeliveryParam)) {
			queryParam.setSelectDeliveryParam(searchDeliveryParam.trim());
		}

		if (StringUtil.isNotNullAndNotEmpty(allotStatus)) {
			queryParam.setAllotStatus(allotStatus);
		}

		if (StringUtil.isNotNullAndNotEmpty(orderStatus)) {
			queryParam.setOrderStatus(orderStatus);
		}

		if (StringUtil.isNotNullAndNotEmpty(startCreateTime)) {
			// 将字符串转换为日期格式的Date类型(yyyy-MM-dd)
			startCreateTime = startCreateTime.substring(0, 10);
			queryParam.setStartCreateTime(startCreateTime);
		}

		if (StringUtil.isNotNullAndNotEmpty(endCreateTime)) {
			// 将字符串转换为日期格式的Date类型(yyyy-MM-dd)
			endCreateTime = endCreateTime.substring(0, 10);
			queryParam.setEndCreateTime(endCreateTime);
		}

		// int startIndex = (pageNum - 1) * pageSize;
		// map.put("startIndex", startIndex);
		// map.put("pageSize", pageSize);
		queryParam.setPageSize(pageSize);
		queryParam.setPage(pageNum);
		try {
			int total = orderInfoService.getOrderInfoCount(queryParam);
			List<OrderListDto> list = orderInfoService.getOrderInfoPage(queryParam);
			BigDecimal orderTotalAmount = orderInfoService.getOrderTotalAmount(queryParam);
			if (orderTotalAmount == null) {
				orderTotalAmount = BigDecimal.ZERO.setScale(2);
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("orderTotalAmount", orderTotalAmount);
			resultMap.put("list", list);

			result.setAllCount(total);
			result.setPageNum(pageNum);
			result.setPageSize(pageSize);
			result.setData(resultMap);
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

	@RequestMapping(value = "/orderInfo/exportOrderList", method = RequestMethod.GET)
	public void exportOrderList(HttpServletRequest request, HttpServletResponse response) {
		PageResult result = PageResult.initResult();

		String orderId = request.getParameter("orderId");
		String payStatus = request.getParameter("payStatus");
		String searchCustomerParam = request.getParameter("searchCustomerParam");
		String searchDeliveryParam = request.getParameter("searchDeliveryParam");
		String allotStatus = request.getParameter("allotStatus");
		String orderStatus = request.getParameter("orderStatus");
		String startCreateTime = request.getParameter("startCreateTime");
		String endCreateTime = request.getParameter("endCreateTime");

		OrderInfoVo filterMask = new OrderInfoVo();
		try {
			if (StringUtil.isNotNullAndNotEmpty(orderId)) {
				filterMask.setOrderId(orderId.trim());
			}

			if (StringUtil.isNotNullAndNotEmpty(payStatus)) {
				filterMask.setPayStatus(payStatus);
			}

			if (StringUtil.isNotNullAndNotEmpty(searchCustomerParam)) {
				filterMask.setSelectCustomerParam(searchCustomerParam.trim());
			}

			if (StringUtil.isNotNullAndNotEmpty(searchDeliveryParam)) {
				filterMask.setSelectDeliveryParam(searchDeliveryParam.trim());
			}

			if (StringUtil.isNotNullAndNotEmpty(allotStatus)) {
				filterMask.setAllotStatus(allotStatus);
			}

			if (StringUtil.isNotNullAndNotEmpty(orderStatus)) {
				filterMask.setOrderStatus(orderStatus);
			}

			if (StringUtil.isNotNullAndNotEmpty(startCreateTime)) {
				// 将字符串转换为日期格式的Date类型(yyyy-MM-dd)
				startCreateTime = startCreateTime.substring(0, 10);
				filterMask.setStartCreateTime(startCreateTime);
			}

			if (StringUtil.isNotNullAndNotEmpty(endCreateTime)) {
				// 将字符串转换为日期格式的Date类型(yyyy-MM-dd)
				endCreateTime = endCreateTime.substring(0, 10);
				filterMask.setEndCreateTime(endCreateTime);
			}
			List<OrderInfoVo> list = orderInfoService.getOrderInfoList(filterMask);
			// 声明一个工作薄
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			HSSFRow row = ExcelUtils.constructOrderListHeader(wb);
			HSSFSheet sheet = wb.getSheet("订单列表");
			int index = 0;
			for (OrderInfoVo bean : list) {
				// 构造导出列表数据
				row = sheet.createRow(index + 1);
				row.createCell(0).setCellValue(index + 1);
				row.createCell(1).setCellValue(new HSSFRichTextString(bean.getOrderId()));
				if (bean.getAmount() != null) {
					row.createCell(2).setCellValue(new HSSFRichTextString(
							bean.getAmount().setScale(2, BigDecimal.ROUND_CEILING).toPlainString()));
				} else {
					row.createCell(2).setCellValue(new HSSFRichTextString("0.00"));
				}
				if ("10".equals(bean.getOrderStatus())) {
					row.createCell(3).setCellValue(new HSSFRichTextString(String.valueOf("未支付")));
				} else if ("20".equals(bean.getOrderStatus())) {
					row.createCell(3).setCellValue(new HSSFRichTextString("已支付"));
				} else if ("30".equals(bean.getOrderStatus())) {
					row.createCell(3).setCellValue(new HSSFRichTextString("已分配"));
				} else if ("50".equals(bean.getOrderStatus())) {
					row.createCell(3).setCellValue(new HSSFRichTextString("派送中"));
				} else if ("60".equals(bean.getOrderStatus())) {
					row.createCell(3).setCellValue(new HSSFRichTextString("派送完成"));
				} else if ("70".equals(bean.getOrderStatus())) {
					row.createCell(3).setCellValue(new HSSFRichTextString("已关闭"));
				}
				row.createCell(4).setCellValue(new HSSFRichTextString(bean.getCustomerName()));
				row.createCell(5).setCellValue(new HSSFRichTextString(bean.getCustomerPhoneNumber()));
				if ("10".equals(bean.getAllotStatus())) {
					row.createCell(6).setCellValue(new HSSFRichTextString("未分配"));
				} else if ("20".equals(bean.getAllotStatus())) {
					row.createCell(6).setCellValue(new HSSFRichTextString("已分配"));
				} else {
					row.createCell(6).setCellValue(new HSSFRichTextString(""));
				}
				row.createCell(7).setCellValue(new HSSFRichTextString(bean.getDeliveryName()));
				row.createCell(8).setCellValue(new HSSFRichTextString(bean.getDeliveryPhoneNumber()));
				if ("50".equals(bean.getOrderStatus())) {
					row.createCell(9).setCellValue(new HSSFRichTextString("派送中"));
				} else if ("60".equals(bean.getOrderStatus())) {
					row.createCell(9).setCellValue(new HSSFRichTextString("派送完成"));
				} else {
					row.createCell(9).setCellValue(new HSSFRichTextString("--"));
				}
				row.createCell(10).setCellValue(new HSSFRichTextString(
						DateTimeUtils.dateToString(bean.getCreateTime(), "yyyy-MM-dd mm:hh:ss")));

				index++;
			}

			OutputStream output = response.getOutputStream();
			String titleName = "订单列表";
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
	 * 派送监控——分页列表 + 搜索
	 * 
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@ApiOperation(value = "派送监控", notes = "请求参数说明||pageNum:页码 ,pageSize:每页条数 ,orderId:订单编号,startTime:开始日期,endTime:结束日期\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/orderInfo/orderMonitorList", method = RequestMethod.POST)
	@ResponseBody
	public PageResult getOrderMonitorList(HttpServletRequest request, @RequestBody Map<String, String> param) {
		PageResult result = PageResult.initResult();
		logger.info("派送监控接口参数:" + JSON.toJSONString(param));
		Map<String, Object> map = new HashMap<String, Object>();

		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String id = param.get("orderId");
		String startTime = param.get("startTime");
		String endTime = param.get("endTime");

		if (StringUtil.isNotNullAndNotEmpty(id)) {
			String orderId = id.trim();
			map.put("orderId", orderId);
		}

		if (StringUtil.isNotNullAndNotEmpty(startTime)) {
			// 将字符串转换为日期格式的Date类型(yyyy-MM-dd)
			startTime = startTime.substring(0, 10);
			map.put("startDate", startTime);
		}

		if (StringUtil.isNotNullAndNotEmpty(endTime)) {
			// 将字符串转换为日期格式的Date类型(yyyy-MM-dd)
			endTime = endTime.substring(0, 10);
			map.put("endDate", endTime);
		}

		int startIndex = (pageNum - 1) * pageSize;
		map.put("startIndex", startIndex);
		map.put("pageSize", pageSize);

		logger.info("派送监控接口处理参数:" + JSON.toJSONString(map));

		try {
			int total = orderInfoService.getDeliveryMonitorCount(map);
			List<DeliveryMonitorDto> list = orderInfoService.getDeliveryMonitorPage(map);

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
	 * 订单下单
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@ApiOperation(value = "订单去支付", notes = "样例参数{\n" + "  \"productId\": \"6666666\",\n"
			+ "  \"orderId\": \"传订单ID则代表未支付订单\",\n" + "  \"customerUserId\": \"6888888\"\n" + "}\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/weixin/orderInfo/buy", method = RequestMethod.POST)
	@ResponseBody
	public DataResult buy(HttpServletRequest request, HttpServletResponse response,
			@RequestBody OrderInfoVo filterMask) {
		DataResult result = DataResult.initResult();
		UserDto user = this.getCurrentUser(request);
		logger.info("支付请求参数==>" + JSON.toJSONString(filterMask));
		try {
			if (user == null) {
				throw new RuntimeException("该用户未注册,不能购买");
			}
			filterMask.setCustomerUserId(user.getCustomerUser().getId());
			String payUrl = orderInfoService.buy(filterMask);
			logger.info("支付请求payUrl==>" + payUrl);
			// response.sendRedirect(payUrl);
			result.setData(payUrl);
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
	 * 接收通知的方法
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ApiOperation(value = "接收通知的方法", notes = "银联支付通知回调")
	@RequestMapping(value = "/weixin/orderInfo/payNotify", method = RequestMethod.POST)
	public void notifyHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 收到通知后记得返回SUCCESS
		PrintWriter writer = response.getWriter();

		Map<String, String> params = UnoinPayUtil.getRequestParams(request);
		logger.info("支付通知,params：" + params);

		// 通知处理
		boolean checkRet = false;
		try {
			checkRet = orderInfoService.notifyHandle(params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常,请稍后重试", e);
		}

		if (checkRet) {
			// writer.print("SUCCESS");
			writer.print("success");
		} else {
			writer.print("FAILED");
		}
		writer.flush();
	}

	/**
	 * 根据订单号退款
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ApiOperation(value = "根据订单号退款", notes = "接口参数为:orderId,系统暂时不支持退款,该接口仅用于测试支付功能,退款支付费用;同时以方便支持下迭代需求支持")
	@RequestMapping(value = "/orderInfo/refund", method = RequestMethod.GET)
	@ResponseBody
	public Result refund(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Result result = Result.initResult();
		try {
			String orderId = request.getParameter("orderId");
			if (orderId == null || orderId.isEmpty()) {
				throw new RuntimeException("退款时,订单号不能为空");
			}
			orderInfoService.refundAmount(orderId);
			// result.setData(orderInfoService.save(filterMask));
			result.setMsg("退款成功");
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
	 * 变更派送员
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/orderInfo/edit", method = RequestMethod.POST)
	@ResponseBody
	public Result edit(HttpServletRequest request, @RequestBody Map<String, String> param) {
		logger.info("订单修改参数:" + JSON.toJSONString(param));
		Result result = Result.initResult();
		String orderId = param.get("orderId");
		if (StringUtil.isNullOrEmpty(orderId)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("订单编号不能为空");
			return result;
		}
		String deliveryName = param.get("deliveryName");
		String editReason = param.get("editReason");
		try {

			OrderInfoVo orderInfoVo = new OrderInfoVo();
			orderInfoVo.setOrderId(orderId);
			orderInfoVo.setEditReason(editReason);

			DeliveryUserVo deliveryUserVo = new DeliveryUserVo();
			deliveryUserVo.setName(deliveryName);
			DeliveryUserVo deliveryUser = deliveryUserService.getDeliveryUserBySelective(deliveryUserVo);
			if (deliveryUser == null) {
				throw new RuntimeException("该用户不存在," + deliveryName);
			}
			orderInfoVo.setAllotDeliveryId(deliveryUser.getId());
			orderInfoVo.setAllotTime(new Date());

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
	 * 订单详情
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@ApiOperation(value = "订单详情", notes = "请求参数说明||orderId:订单编号\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/orderInfo/info", method = RequestMethod.POST)
	@ResponseBody
	public DataResult info(HttpServletRequest request, @RequestBody OrderInfoVo filterMask) {
		DataResult result = DataResult.initResult();
		logger.info("订单详情接口参数==>" + JSON.toJSONString(filterMask));
		try {
			if (StringUtil.isNullOrEmpty(filterMask.getOrderId())) {
				throw new RuntimeException("订单编号不能为空");
			}
			OrderInfoVo param = new OrderInfoVo();
			param.setOrderId(filterMask.getOrderId());
			OrderInfoVo orderInfo = orderInfoService.getOrderInfoBySelective(param);
			if (orderInfo == null) {
				throw new RuntimeException("该订单不存在," + filterMask.getOrderId());
			}

			OrderInfoDto orderInfoDto = new OrderInfoDto();
			orderInfoDto.setOrderId(orderInfo.getOrderId());
			orderInfoDto.setAmount(orderInfo.getAmount());
			orderInfoDto.setPayStatus(orderInfo.getPayStatus());
			orderInfoDto.setPayTime(orderInfo.getPayTime());
			orderInfoDto.setAllotStatus(orderInfo.getAllotStatus());
			orderInfoDto.setAllotTime(orderInfo.getAllotTime());

			if (StringUtil.isNotNullAndNotEmpty(orderInfo.getAllotDeliveryId())) {
				DeliveryUserVo deliveryUserVo = new DeliveryUserVo();
				deliveryUserVo.setId(orderInfo.getAllotDeliveryId());
				DeliveryUserVo deliveryUser = deliveryUserService.getDeliveryUserBySelective(deliveryUserVo);
				if (deliveryUser != null) {
					orderInfoDto.setAllotDeliveryName(deliveryUser.getName());
					orderInfoDto.setAllotDeliveryPhoneNumber(deliveryUser.getPhoneNumber());
				}
			}

			if (StringUtil.isNotNullAndNotEmpty(orderInfo.getCustomerUserId())) {
				CustomerUserVo customerUserVo = new CustomerUserVo();
				customerUserVo.setId(orderInfo.getCustomerUserId());
				CustomerUserVo customerUser = customerUserService.getCustomerUserBySelective(customerUserVo);
				if (customerUser != null) {
					orderInfoDto.setCustomerUserName(customerUser.getName());
					orderInfoDto.setCustomerAddress(customerUser.getAddress());
					orderInfoDto.setCustomerPhoneNumber(customerUser.getPhoneNumber());
				}
			}

			orderInfoDto.setOrderStatus(orderInfo.getOrderStatus());
			orderInfoDto.setRemark(orderInfo.getRemark());
			orderInfoDto.setEditReason(orderInfo.getEditReason());
			orderInfoDto.setUpdateTime(orderInfo.getUpdateTime());

			result.setData(orderInfoDto);

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
	 * 签收详情
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	// @RequestMapping(value = "/weixin/orderInfo/receiveInfo", method =
	// RequestMethod.GET)
	// @ResponseBody
	// public DataResult receiveInfo(HttpServletRequest request, OrderInfoVo
	// filterMask) {
	// DataResult result = DataResult.initResult();
	// String orderId = request.getParameter("orderId");
	// if (StringUtil.isNullOrEmpty(orderId)) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg("订单编号不能为空");
	// return result;
	// }
	//
	// try {
	// filterMask.setOrderId(orderId);
	// OrderInfoVo orderInfo = orderInfoService.getOrderInfoBySelective(filterMask);
	// if (orderInfo == null) {
	// throw new RuntimeException("该订单不存在," + filterMask.getOrderId());
	// }
	//
	// CustomerOrderInfoDto customerOrderInfoDto = new CustomerOrderInfoDto();
	// customerOrderInfoDto.setOrderId(orderInfo.getOrderId());
	// customerOrderInfoDto.setProductName(orderInfo.getProductName());
	// customerOrderInfoDto.setProductAmount(orderInfo.getAmount());
	//
	// CustomerUserVo customerUserVo = new CustomerUserVo();
	// customerUserVo.setId(orderInfo.getCustomerUserId());
	// CustomerUserVo customerUser =
	// customerUserService.getCustomerUserBySelective(customerUserVo);
	// if (customerUser != null) {
	// customerOrderInfoDto.setCustomerName(customerUser.getName());
	// }
	//
	// customerOrderInfoDto.setDeliveryCompleteTime(orderInfo.getDeliveryCompleteTime());
	// customerOrderInfoDto.setRemark(orderInfo.getRemark());
	//
	// result.setData(customerOrderInfoDto);
	//
	// } catch (RuntimeException e) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg(e.getMessage());
	// logger.error("系统异常," + e.getMessage(), e);
	// } catch (Exception e) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg("系统异常,请稍后重试");
	// logger.error("系统异常,请稍后重试", e);
	// }
	// return result;
	// }

	/**
	 * 公众号——客户端——订单详情
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	// @RequestMapping(value = "/weixin/orderInfo/orderDetail", method =
	// RequestMethod.GET)
	// @ResponseBody
	// public DataResult orderDetail(HttpServletRequest request) {
	// DataResult result = DataResult.initResult();
	// String orderId = request.getParameter("orderId");
	// if (StringUtil.isNullOrEmpty(orderId)) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg("订单编号不能为空");
	// return result;
	// }
	//
	// try {
	// OrderInfoVo filterMask = new OrderInfoVo();
	// filterMask.setOrderId(orderId);
	// OrderInfoVo orderInfo = orderInfoService.getOrderInfoBySelective(filterMask);
	// if (orderInfo == null) {
	// throw new RuntimeException("该订单不存在," + orderId);
	// }
	// ProductVo productVo = new ProductVo();
	// productVo.setProductId(orderInfo.getProductId());
	// ProductVo product = productService.getProductBySelective(productVo);
	// if (product == null) {
	// throw new RuntimeException("该订单产品已过期或不存在," + orderInfo.getProductId());
	// }
	//
	// OrderDetailDto orderDetailDto = new OrderDetailDto();
	// orderDetailDto.setOrderId(orderInfo.getOrderId());
	// orderDetailDto.setProductName(orderInfo.getProductName());
	// orderDetailDto.setAmount(orderInfo.getAmount());
	// orderDetailDto.setSpec(product.getProductDesc());
	// orderDetailDto.setPayAmount(orderInfo.getAmount());
	// orderDetailDto.setPayTime(orderInfo.getPayTime());
	//
	// result.setData(orderDetailDto);
	//
	// } catch (RuntimeException e) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg(e.getMessage());
	// logger.error("系统异常," + e.getMessage(), e);
	// } catch (Exception e) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg("系统异常,请稍后重试");
	// logger.error("系统异常,请稍后重试", e);
	// }
	// return result;
	// }

	/**
	 * 公众号——客户端——派送详情
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	// @RequestMapping(value = "/weixin/orderInfo/deliveryDetail", method =
	// RequestMethod.GET)
	// @ResponseBody
	// public DataResult deliveryDetail(HttpServletRequest request, OrderInfoVo
	// filterMask) {
	// DataResult result = DataResult.initResult();
	// String orderId = request.getParameter("orderId");
	// if (StringUtil.isNullOrEmpty(orderId)) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg("订单编号不能为空");
	// return result;
	// }
	//
	// try {
	// filterMask.setOrderId(orderId);
	// OrderInfoVo orderInfo = orderInfoService.getOrderInfoBySelective(filterMask);
	// ProductVo productVo = new ProductVo();
	// productVo.setProductId(orderInfo.getProductId());
	// ProductVo product = productService.getProductBySelective(productVo);
	// if (product == null) {
	// throw new RuntimeException("该产品不存在," + filterMask.getOrderId());
	// }
	//
	// DeliveryDetailDto deliveryDetailDto = new DeliveryDetailDto();
	// deliveryDetailDto.setOrderId(orderInfo.getOrderId());
	// deliveryDetailDto.setProductName(orderInfo.getProductName());
	// deliveryDetailDto.setAmount(orderInfo.getAmount());
	// deliveryDetailDto.setPayTime(orderInfo.getPayTime());
	// deliveryDetailDto.setDeliveryOrderTime(orderInfo.getDeliveryOrderTime());
	//
	// DeliveryUserVo deliveryUserVo = new DeliveryUserVo();
	// deliveryUserVo.setId(orderInfo.getAllotDeliveryId());
	// DeliveryUserVo deliveryUser =
	// deliveryUserService.getDeliveryUserBySelective(deliveryUserVo);
	// if (deliveryUser != null) {
	// deliveryDetailDto.setDeliveryName(deliveryUser.getName());
	// deliveryDetailDto.setDeliveryPhoneNumber(deliveryUser.getPhoneNumber());
	// }
	//
	// AdminUserVo adminUserVo = new AdminUserVo();
	// adminUserVo.setId(product.getCreateUserId());
	// AdminUserVo adminUser =
	// adminUserService.getAdminUserBySelective(adminUserVo);
	// if (adminUser != null) {
	// deliveryDetailDto.setProductAddress(adminUser.getAddress());
	// }
	// CustomerUserVo customerUserVo = new CustomerUserVo();
	// customerUserVo.setId(orderInfo.getCustomerUserId());
	// CustomerUserVo customerUser =
	// customerUserService.getCustomerUserBySelective(customerUserVo);
	// if (customerUser != null) {
	// deliveryDetailDto.setCustomerAddress(customerUser.getAddress());
	// }
	//
	// result.setData(deliveryDetailDto);
	//
	// } catch (RuntimeException e) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg(e.getMessage());
	// logger.error("系统异常," + e.getMessage(), e);
	// } catch (Exception e) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg("系统异常,请稍后重试");
	// logger.error("系统异常,请稍后重试", e);
	// }
	// return result;
	// }

	/**
	 * 测试微信消息推送
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	// @RequestMapping(value = "/orderInfo/wxMsgSend", method = RequestMethod.POST)
	// @ResponseBody
	// public Result wxMsgSend(HttpServletRequest request, @RequestBody Map<String,
	// String> param) {
	// Result result = Result.initResult();
	//
	// try {
	// AccessToken access = TokenUtil.getWXToken();
	// TemplateMessage templateMessage = TemplateMessage.New();
	// templateMessage.setOpenId("oBD9n6PMmJ9znS2AX6AsP-pR_Tzo");
	// templateMessage.setTemplateId("ykujHmHTnJTSEK0iJWVQKNq_TooXRdcaOKBsYQMAZLo");
	// templateMessage.setUrl("");
	// templateMessage.setTopcolor("#696969");
	//
	// Map<String, TemplateData> msgData = new HashMap<String, TemplateData>();
	// msgData.put("first", new TemplateData("尊敬的客户，您好，您的订单已支付完成。", "#696969"));
	// msgData.put("keyword1", new TemplateData("3194TK201903041213026613246939",
	// "#696969"));
	// msgData.put("keyword2", new TemplateData("2019/03/12", "#696969"));
	// msgData.put("keyword3", new TemplateData("微信支付", "#696969"));
	// msgData.put("keyword4", new TemplateData("100.00", "#696969"));
	// msgData.put("remark", new TemplateData("查看详情", "#696969"));
	//
	// templateMessage.setTemplateData(msgData);
	// // 推送消息
	// WeiXinUtils.pushWeiXinMsg(access.getAccessToken(), templateMessage);
	//
	// } catch (RuntimeException e) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg(e.getMessage());
	// logger.error("系统异常," + e.getMessage(), e);
	// } catch (Exception e) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg("系统异常,请稍后重试");
	// logger.error("系统异常,请稍后重试", e);
	// }
	// return result;
	// }

	/**
	 * 公众号——客户端——我的订单
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/weixin/customerOrderInfo", method = RequestMethod.POST)
	@ResponseBody
	public DataResult customerOrderInfoList(HttpServletRequest request, @RequestBody Map<String, String> param,
			OrderInfoVo filterMask) {
		PageResult result = PageResult.initResult();
		logger.info("公众号——客户端——我的订单,请求参数为:" + JSON.toJSONString(param));
		UserDto user = this.getCurrentUser(request);
		if (user == null) {
			result.setCode(Constants.NOT_LOGIN);
			result.setMsg("该派送员未登陆,请先登录");
			return result;
		}

		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		// String customerUserId = param.get("customerUserId");
		String customerUserId = user.getCustomerUser().getId();

		String status = param.get("status");
		if (StringUtil.isNullOrEmpty(customerUserId)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("客户编号不能为空");
			return result;
		}
		filterMask.setCustomerUserId(customerUserId);
		filterMask.setPage(pageNum);
		filterMask.setPageSize(pageSize);
		int total = 0;
		List<CustomerOrderInfoDto> list = null;
		try {
			// 待支付
			if ("1".equals(status)) {
				filterMask.setOrderStatus("10");
			} else if ("2".equals(status)) {
				// 派送中
				filterMask.setOrderStatus("50");
			} else if ("3".equals(status)) {
				// 已完成
				filterMask.setOrderStatus("60");
			}
			logger.info("公众号——客户端——我的订单,处理参数为:" + JSON.toJSONString(filterMask));
			total = orderInfoService.getOrderInfoNumber(filterMask);
			list = orderInfoService.getCustomerOrderInfoPage(filterMask);
		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		result.setAllCount(total);
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setData(list);
		return result;
	}

	/**
	 * 公众号——客户端——确认签收
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/weixin/orderInfo/confirmReceive", method = RequestMethod.GET)
	@ResponseBody
	public Result confirmReceive(HttpServletRequest request, OrderInfoVo filterMask) {
		Result result = Result.initResult();
		String orderId = request.getParameter("orderId");
		if (StringUtil.isNullOrEmpty(orderId)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("订单编号不能为空");
			return result;
		}

		Date nowTime = new Date();
		try {
			filterMask.setOrderId(orderId);
			OrderInfoVo orderInfoVo = orderInfoService.getOrderInfoBySelective(filterMask);
			if (orderInfoVo == null) {
				throw new RuntimeException("该订单不存在," + orderId);
			}
			orderInfoVo.setDeliveryCompleteTime(nowTime);
			orderInfoVo.setOrderStatus("60");
			orderInfoVo.setUpdateTime(nowTime);

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
	 * 公众号——派送员端——我的订单
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/weixin/deliveryOrderInfo", method = RequestMethod.POST)
	@ResponseBody
	public DataResult deliveryOrderInfoList(HttpServletRequest request, @RequestBody Map<String, String> param,
			OrderInfoVo filterMask) {
		PageResult result = PageResult.initResult();
		UserDto user = this.getCurrentUser(request);
		if (user == null) {
			result.setCode(Constants.NOT_LOGIN);
			result.setMsg("该派送员未登陆,请先登录");
			return result;
		}

		logger.info("派送员端——我的订单,请求参数:" + JSON.toJSONString(param));
		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		// String allotDeliveryId = param.get("allotDeliveryId");
		String allotDeliveryId = user.getDeliveryUser().getId();
		String status = param.get("status");
		if (StringUtil.isNullOrEmpty(allotDeliveryId)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("派送员编号不能为空");
			return result;
		}
		filterMask.setAllotDeliveryId(allotDeliveryId);
		filterMask.setPage(pageNum);
		filterMask.setPageSize(pageSize);
		int total = 0;
		List<DeliveryOrderInfoDto> list = null;
		try {
			// 待接单
			if ("1".equals(status)) {
				filterMask.setOrderStatus("30");
			} else if ("2".equals(status)) {
				// 派送中
				filterMask.setOrderStatus("50");
			} else if ("3".equals(status)) {
				// 已完成
				filterMask.setOrderStatus("60");
			}
			total = orderInfoService.getOrderInfoNumber(filterMask);
			list = orderInfoService.getDeliveryOrderInfoPage(filterMask);
		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		result.setAllCount(total);
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setData(list);
		return result;
	}

	/**
	 * 公众号——派送端——派单详情
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	// @RequestMapping(value = "/weixin/orderInfo/deliveryOrderDetail", method =
	// RequestMethod.GET)
	// @ResponseBody
	// public DataResult deliveryOrderDetail(HttpServletRequest request) {
	// DataResult result = DataResult.initResult();
	//
	// String orderId = request.getParameter("orderId");
	//
	// if (StringUtil.isNullOrEmpty(orderId)) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg("订单编号不能为空");
	// return result;
	// }
	//
	// OrderInfoVo filterMask = new OrderInfoVo();
	// filterMask.setOrderId(orderId);
	// try {
	// OrderInfoVo orderInfo = orderInfoService.getOrderInfoBySelective(filterMask);
	// if (orderInfo == null) {
	// throw new RuntimeException("该订单不存在," + orderId);
	// }
	// ProductVo productVo = new ProductVo();
	// productVo.setProductId(orderInfo.getProductId());
	// ProductVo product = productService.getProductBySelective(productVo);
	//
	// DeliveryOrderDetailDto deliveryOrderDetailDto = new DeliveryOrderDetailDto();
	//
	// CustomerUserVo customerUserVo = new CustomerUserVo();
	// customerUserVo.setId(orderInfo.getCustomerUserId());
	// CustomerUserVo customerUser =
	// customerUserService.getCustomerUserBySelective(customerUserVo);
	// if (customerUser != null) {
	// deliveryOrderDetailDto.setCustomerName(customerUser.getName());
	// deliveryOrderDetailDto.setCustomerPhoneNumber(customerUser.getPhoneNumber());
	// deliveryOrderDetailDto.setCustomerAddress(customerUser.getAddress());
	// }
	//
	// deliveryOrderDetailDto.setDeliveryCompleteTime(orderInfo.getDeliveryCompleteTime());
	// deliveryOrderDetailDto.setOrderId(orderInfo.getOrderId());
	// deliveryOrderDetailDto.setProductName(orderInfo.getProductName());
	// deliveryOrderDetailDto.setStockQty(product.getStockQty());
	// deliveryOrderDetailDto.setRemark(orderInfo.getRemark());
	//
	// AdminUserVo adminUserVo = new AdminUserVo();
	// adminUserVo.setId(product.getCreateUserId());
	// AdminUserVo adminUser =
	// adminUserService.getAdminUserBySelective(adminUserVo);
	// if (adminUser != null) {
	// deliveryOrderDetailDto.setProductAddress(adminUser.getAddress());
	// }
	//
	// result.setData(deliveryOrderDetailDto);
	//
	// } catch (RuntimeException e) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg(e.getMessage());
	// logger.error("系统异常," + e.getMessage(), e);
	// } catch (Exception e) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg("系统异常,请稍后重试");
	// logger.error("系统异常,请稍后重试", e);
	// }
	// return result;
	// }

	/**
	 * 公众号——派送端——去接单
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@ApiOperation(value = "派送端——去接单", notes = "派送端——去接单")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderId", value = "订单Id", required = true, paramType = "query", dataType = "String") })
	@RequestMapping(value = "/weixin/orderInfo/deliveryReceiveOrder", method = RequestMethod.GET)
	@ResponseBody
	public Result receiveOrder(HttpServletRequest request, OrderInfoVo filterMask) {
		Result result = Result.initResult();
		String orderId = request.getParameter("orderId");
		if (StringUtil.isNullOrEmpty(orderId)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("订单编号不能为空");
			return result;
		}

		Date nowTime = new Date();
		try {
			filterMask.setOrderId(orderId);
			OrderInfoVo orderInfoVo = orderInfoService.getOrderInfoBySelective(filterMask);
			if (orderInfoVo == null) {
				throw new RuntimeException("该订单不存在," + orderId);
			}
			orderInfoVo.setOrderStatus("50");
			orderInfoVo.setDeliveryOrderTime(nowTime);
			orderInfoVo.setUpdateTime(nowTime);

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
	 * 公众号——派送端——确认签收,减去派送库存
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@ApiOperation(value = "派送端——确认签收", notes = "确认签收,减去派送库存")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderId", value = "订单Id", required = true, paramType = "query", dataType = "String") })
	@RequestMapping(value = "/weixin/orderInfo/delivery/confirmOrderReceived", method = RequestMethod.GET)
	@ResponseBody
	public Result confirmOrderReceived(HttpServletRequest request, OrderInfoVo filterMask) {
		Result result = Result.initResult();
		String orderId = request.getParameter("orderId");
		if (StringUtil.isNullOrEmpty(orderId)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("订单编号不能为空");
			return result;
		}

		Date nowTime = new Date();
		try {
			filterMask.setOrderId(orderId);
			OrderInfoVo orderInfoVo = orderInfoService.getOrderInfoBySelective(filterMask);
			if (orderInfoVo == null) {
				throw new RuntimeException("该订单不存在," + orderId);
			}
			DeliveryUserVo deliveryUserVo = new DeliveryUserVo();
			deliveryUserVo.setId(orderInfoVo.getAllotDeliveryId());
			DeliveryUserVo deliveryUser = deliveryUserService.getDeliveryUserBySelective(deliveryUserVo);
			if (deliveryUser == null) {
				throw new RuntimeException("派送库存修改失败");
			}
			int stockQty = deliveryUser.getStockQty() == null ? 0 : deliveryUser.getStockQty();
			deliveryUser.setStockQty(stockQty - 1);
			deliveryUser.setUpdateTime(nowTime);
			deliveryUserService.update(deliveryUser);

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
	 * 公众号——订单详情
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@ApiOperation(value = "公众号——订单所有详情", notes = "订单所有详情信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orderId", value = "订单Id", required = true, paramType = "query", dataType = "String") })
	@RequestMapping(value = "/weixin/orderInfo/queryOrderDetailInfo", method = RequestMethod.GET)
	@ResponseBody
	public DataResult queryOrderDetailInfo(HttpServletRequest request, OrderInfoVo filterMask) {
		DataResult result = DataResult.initResult();
		String orderId = request.getParameter("orderId");
		if (StringUtil.isNullOrEmpty(orderId)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("订单编号不能为空");
			return result;
		}

		try {
			filterMask.setOrderId(orderId);
			OrderInfoDetailDto orderInfoVo = orderInfoService.queryOrderDetailInfo(orderId);
			if (orderInfoVo == null) {
				throw new RuntimeException("该订单不存在," + orderId);
			}
			result.setData(orderInfoVo);
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
