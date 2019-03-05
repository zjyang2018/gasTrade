/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.zach.gasTrade.common.DataResult;
import com.zach.gasTrade.common.PageResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.dto.DeliveryMonitorDto;
import com.zach.gasTrade.dto.OrderInfoDto;
import com.zach.gasTrade.dto.OrderListDto;
import com.zach.gasTrade.netpay.UnoinPayUtil;
import com.zach.gasTrade.service.CustomerUserService;
import com.zach.gasTrade.service.DeliveryUserService;
import com.zach.gasTrade.service.OrderInfoService;
import com.zach.gasTrade.vo.CustomerUserVo;
import com.zach.gasTrade.vo.DeliveryUserVo;
import com.zach.gasTrade.vo.OrderInfoVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "订单相关api")
@Controller
public class OrderInfoController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private OrderInfoService orderInfoService;

	@Autowired
	private CustomerUserService customerUserService;

	@Autowired
	private DeliveryUserService deliveryUserService;

	/**
	 * 分页列表 + 搜索 + 高级搜索
	 * 
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@RequestMapping(value = "/orderInfo/query_page", method = RequestMethod.POST)
	@ResponseBody
	public PageResult getPageData(HttpServletRequest request, @RequestBody Map<String, String> param,
			OrderListDto filterMask) {
		PageResult result = PageResult.initResult();

		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String orderId = param.get("orderId");
		if (StringUtil.isNotNullAndNotEmpty(orderId)) {
			String id = orderId.trim() + "%";
			filterMask.setOrderId(id);
		}

		String payStatus = param.get("payStatus");
		if (StringUtil.isNotNullAndNotEmpty(payStatus)) {
			filterMask.setPayStatus(payStatus);
		}
		String searchCustomerParam = param.get("searchCustomerParam");
		if (StringUtil.isNotNullAndNotEmpty(searchCustomerParam)) {
			String selectCustomerParam = searchCustomerParam.trim() + "%";
			// selectCustomerParam以"1"开头则按手机号搜索
			if (selectCustomerParam.startsWith("1")) {
				filterMask.setCustomerPhoneNumber(selectCustomerParam);
			} else {
				filterMask.setCustomerName(selectCustomerParam);
			}
		}

		String searchDeliveryParam = param.get("searchDeliveryParam");
		if (StringUtil.isNotNullAndNotEmpty(searchDeliveryParam)) {
			String selectDeliveryParam = searchDeliveryParam.trim() + "%";
			// selectDeliveryParam以"1"开头则按手机号搜索
			if (selectDeliveryParam.startsWith("1")) {
				filterMask.setDeliveryPhoneNumber(selectDeliveryParam);
			} else {
				filterMask.setDeliveryName(selectDeliveryParam);
			}
		}

		String allotStatus = param.get("allotStatus");
		if (StringUtil.isNotNullAndNotEmpty(allotStatus)) {
			filterMask.setAllotStatus(allotStatus);
		}
		String orderStatus = param.get("orderStatus");
		if (StringUtil.isNotNullAndNotEmpty(orderStatus)) {
			filterMask.setOrderStatus(orderStatus);
		}
		String payTime = param.get("payTime");
		if (StringUtil.isNotNullAndNotEmpty(payTime)) {
			Date payTimeToDate = DateTimeUtils.stringToDate(payTime, new Date().toString());
			filterMask.setPayTime(payTimeToDate);
		}

		filterMask.setPage(pageNum);
		filterMask.setPageSize(pageSize);
		try {
			int total = orderInfoService.getOrderInfoCount(filterMask);
			List<OrderListDto> list = orderInfoService.getOrderInfoPage(filterMask);

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
	 * 派送监控——分页列表 + 搜索
	 * 
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@RequestMapping(value = "/orderInfo/orderMonitorList", method = RequestMethod.POST)
	@ResponseBody
	public PageResult getOrderMonitorList(HttpServletRequest request, @RequestBody Map<String, String> param) {
		PageResult result = PageResult.initResult();
		Map<String, Object> map = new HashMap<String, Object>();

		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String id = param.get("orderId");
		if (StringUtil.isNotNullAndNotEmpty(id)) {
			String orderId = id.trim() + "%";
			map.put("orderId", orderId);
		}

		String startTime = param.get("startTime");
		if (StringUtil.isNotNullAndNotEmpty(startTime)) {
			Date startDate = DateTimeUtils.stringToDate(startTime, new Date().toString());
			map.put("startDate", startDate);
		}
		String endTime = param.get("endTime");
		if (StringUtil.isNotNullAndNotEmpty(endTime)) {
			Date endDate = DateTimeUtils.stringToDate(endTime, new Date().toString());
			map.put("endDate", endDate);
		}

		int startIndex = (pageNum - 1) * pageSize;
		map.put("startIndex", startIndex);
		map.put("pageSize", pageSize);

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
			+ "  \"customerUserId\": \"6888888\"\n" + "}\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/orderInfo/buy", method = RequestMethod.POST)
	@ResponseBody
	public DataResult buy(HttpServletRequest request, HttpServletResponse response,
			@RequestBody OrderInfoVo filterMask) {
		DataResult result = DataResult.initResult();

		try {
			String payUrl = orderInfoService.buy(filterMask);
			logger.info("支付请求payUrl==>" + payUrl);
			response.sendRedirect(payUrl);
			// result.setData(orderInfoService.save(filterMask));
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
	@RequestMapping(value = "/orderInfo/payNotify", method = RequestMethod.POST)
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
			writer.print("SUCCESS");
		} else {
			writer.print("FAILED");
		}
		writer.flush();
	}

	/**
	 * 接收通知的方法
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
	 * 修改
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/orderInfo/edit", method = RequestMethod.POST)
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
		String editReason = param.get("editReason");

		OrderInfoVo orderInfoVo = new OrderInfoVo();
		orderInfoVo.setOrderId(orderId);
		orderInfoVo.setEditReason(editReason);

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
	 * 详情
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/orderInfo/info", method = RequestMethod.POST)
	@ResponseBody
	public DataResult info(HttpServletRequest request, @RequestBody OrderInfoVo filterMask) {
		DataResult result = DataResult.initResult();
		if (StringUtil.isNullOrEmpty(filterMask.getOrderId())) {
			result.setCode(Constants.FAILURE);
			result.setMsg("订单编号不能为空");
			return result;
		}

		try {
			OrderInfoVo orderInfo = orderInfoService.getOrderInfoBySelective(filterMask);
			CustomerUserVo customerUserVo = new CustomerUserVo();
			customerUserVo.setId(orderInfo.getCustomerUserId());
			CustomerUserVo customerUser = customerUserService.getCustomerUserBySelective(customerUserVo);
			DeliveryUserVo deliveryUserVo = new DeliveryUserVo();
			deliveryUserVo.setId(orderInfo.getAllotDeliveryId());
			DeliveryUserVo deliveryUser = deliveryUserService.getDeliveryUserBySelective(deliveryUserVo);

			OrderInfoDto orderInfoDto = new OrderInfoDto();
			orderInfoDto.setOrderId(orderInfo.getOrderId());
			orderInfoDto.setAmount(orderInfo.getAmount());
			orderInfoDto.setPayStatus(orderInfo.getPayStatus());
			orderInfoDto.setPayTime(orderInfo.getPayTime());
			orderInfoDto.setAllotStatus(orderInfo.getAllotStatus());
			orderInfoDto.setAllotTime(orderInfo.getAllotTime());
			orderInfoDto.setAllotDeliveryName(deliveryUser.getName());
			orderInfoDto.setAllotDeliveryPhoneNumber(deliveryUser.getPhoneNumber());
			orderInfoDto.setCustomerUserName(customerUser.getName());
			orderInfoDto.setCustomerAddress(customerUser.getAddress());
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
}
