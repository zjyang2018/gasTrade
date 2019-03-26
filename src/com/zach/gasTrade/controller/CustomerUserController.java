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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.utils.MapHelper;
import com.common.utils.StringUtil;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.PageResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.dto.UserDto;
import com.zach.gasTrade.service.CustomerUserService;
import com.zach.gasTrade.service.DeliveryUserService;
import com.zach.gasTrade.vo.CustomerUserVo;
import com.zach.gasTrade.vo.DeliveryUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "客户端用户相关api")
@Controller
public class CustomerUserController extends CommonController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private CustomerUserService customerUserService;

	@Autowired
	private DeliveryUserService deliveryUserService;

	/**
	 * 分页列表 + 搜索
	 * 
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@ApiOperation(value = "客户端用户列表", notes = "请求参数说明||pageNum:页码 ,pageSize:每页条数 ,searchParam:搜索参数（姓名或手机号）,"
			+ "channel:渠道来源:10-微信公众号,20-客服录入,30-其它\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/customerUser/query_page", method = RequestMethod.POST)
	@ResponseBody
	public PageResult getPageData(HttpServletRequest request, @RequestBody Map<String, String> param,
			CustomerUserVo filterMask) {
		PageResult result = PageResult.initResult();
		logger.info("客户端用户列表接口参数:" + JSON.toJSONString(param));
		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String searchParam = param.get("searchParam");
		String channel = param.get("channel");

		if (StringUtil.isNotNullAndNotEmpty(searchParam)) {
			String selectParam = searchParam.trim();
			// searchParam以"1"开头则按手机号搜索
			if (selectParam.startsWith("1")) {
				filterMask.setPhoneNumber(selectParam);
			} else {
				filterMask.setName(selectParam);
			}
		}

		if (StringUtil.isNotNullAndNotEmpty(channel)) {
			filterMask.setChannel(channel);
		}
		filterMask.setPage(pageNum);
		filterMask.setPageSize(pageSize);
		try {
			int total = customerUserService.getCustomerUserCount(filterMask);
			List<CustomerUserVo> list = customerUserService.getCustomerUserPage(filterMask);

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
	 * 新增
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@ApiOperation(value = "用户添加", notes = "请求参数说明||name:姓名 ,wxName:微信昵称,sex:性别,phoneNumber:手机号码,address:联系地址,channel:渠道来源:10-微信公众号,20-客服录入,30-其它,remark:备注\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/customerUser/save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(HttpServletRequest request, @RequestBody CustomerUserVo filterMask) {
		Result result = Result.initResult();
		logger.info("用户添加接口参数:" + JSON.toJSONString(filterMask));
		try {
			String address = filterMask.getAddress();
			JSONObject jsonObject = MapHelper.addressToPoint(address);
			if (jsonObject == null || jsonObject.getInteger("confidence") < 16) {
				throw new RuntimeException("地址输入详细，请重新输入");
			}

			customerUserService.save(filterMask);

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
	@ApiOperation(value = "用户编辑", notes = "请求参数说明||id:用户编号:必需参数 ,name:姓名 ,wxName:微信昵称,sex:性别,phoneNumber:手机号码,address:联系地址,channel:渠道来源:10-微信公众号,20-客服录入,30-其它,remark:备注\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/customerUser/edit", method = RequestMethod.POST)
	@ResponseBody
	public Result edit(HttpServletRequest request, @RequestBody CustomerUserVo filterMask) {
		Result result = Result.initResult();
		logger.info("用户添加接口参数:" + JSON.toJSONString(filterMask));
		try {

			if (StringUtil.isNullOrEmpty(filterMask.getId())) {
				throw new RuntimeException("用户编号不能为空");
			}

			String address = filterMask.getAddress();
			JSONObject jsonObject = MapHelper.addressToPoint(address);
			if (jsonObject == null || jsonObject.getInteger("confidence") < 16) {
				throw new RuntimeException("地址输入详细，请重新输入");
			}

			customerUserService.update(filterMask);

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
	 * 修改客户收货信息
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@ApiOperation(value = "修改客户收货信息", notes = "请求参数说明||name:姓名 ,,phoneNumber:手机号码,address:联系地址\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/weixin/customerUser/updateInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result updateAddress(HttpServletRequest request, @RequestBody DeliveryUserVo filterMask) {
		Result result = Result.initResult();
		logger.info("修改客户收货信息接口参数:" + JSON.toJSONString(filterMask));
		try {
			String pageType = request.getParameter("pageType");

			UserDto user = this.getCurrentUser(request);
			if (user == null || user.getCustomerUser() == null) {
				throw new RuntimeException("该用户信息已过期,请重新进入");
			}

			String address = filterMask.getAddress();
			JSONObject jsonObject = MapHelper.addressToPoint(address);
			if (jsonObject == null || jsonObject.getInteger("confidence") < 16) {
				throw new RuntimeException("地址输入详细，请重新输入");
			}

			// pageType:1-客户端,2-派送端
			if ("2".equals(pageType)) {
				if (user.getDeliveryUser() == null) {
					throw new RuntimeException("该用户非派送员," + this.getWxOpenId(request));
				}

				DeliveryUserVo deliveryUserVo = new DeliveryUserVo();
				deliveryUserVo.setId(user.getDeliveryUser().getId());
				deliveryUserVo.setName(filterMask.getName());
				deliveryUserVo.setAddress(filterMask.getAddress());
				deliveryUserVo.setPhoneNumber(filterMask.getPhoneNumber());
				deliveryUserVo.setStockQty(filterMask.getStockQty());
				deliveryUserService.update(deliveryUserVo);
			} else {
				if (user.getCustomerUser() == null) {
					throw new RuntimeException("该用户非客户端用户," + this.getWxOpenId(request));
				}
				String customerId = user.getCustomerUser().getId();
				CustomerUserVo customerUser = new CustomerUserVo();
				// param.setId(customerId);
				// CustomerUserVo customerUser =
				// customerUserService.getCustomerUserBySelective(param);
				customerUser.setId(customerId);
				customerUser.setAddress(address);
				customerUser.setPhoneNumber(filterMask.getPhoneNumber());
				customerUser.setName(filterMask.getName());
				customerUser.setLongitude(jsonObject.getJSONObject("location").getString("lng"));
				customerUser.setLatitude(jsonObject.getJSONObject("location").getString("lat"));
				customerUser.setUpdateTime(new Date());
				customerUserService.update(customerUser);
			}
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
