/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;

import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.common.utils.CalendarUtils;
import com.common.utils.StringUtil;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.PageResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.dto.DeliveryUserDto;
import com.zach.gasTrade.service.DeliveryUserService;
import com.zach.gasTrade.vo.DeliveryUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "派送员用户相关api")
@Controller
public class DeliveryUserController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private DeliveryUserService deliveryUserService;

	/**
	 * 分页列表 + 搜索
	 * 
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@ApiOperation(value = "派送员创建账号列表", notes = "请求参数说明||pageNum:起始页码,pageSize:记录数,searchParam:搜索参数\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/deliveryUser/query_page", method = RequestMethod.POST)
	@ResponseBody
	public PageResult getPageData(HttpServletRequest request, @RequestBody Map<String, String> param,
			DeliveryUserVo filterMask) {
		PageResult result = PageResult.initResult();
		try {
			logger.info("派送员创建账号列表接口参数:" + JSON.toJSONString(param));
			int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
			int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
			String searchParam = param.get("searchParam");
			
			Map<String, Object> map = new HashMap<>();
			
			if (StringUtil.isNotNullAndNotEmpty(searchParam)) {
				String selectParam = searchParam.trim() + "%";
				// searchParam以"1"开头则按手机号搜索
				if (selectParam.startsWith("1")) {
					map.put("phoneNumber", selectParam);
				} else {
					map.put("name", selectParam);
				}
			}
			
			int startIndex = (pageNum - 1) * pageSize;
			map.put("startIndex", startIndex);
			map.put("pageSize", pageSize);
			
			int total = deliveryUserService.getDeliveryUserCount(map);
			List<DeliveryUserVo> list = deliveryUserService.getDeliveryUserPage(map);

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
	 * 分页列表 + 搜索(人员管理)
	 * 
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@ApiOperation(value = "派送员管理", notes = "请求参数说明||pageNum:起始页码,pageSize:记录数,dateParam:日期,workStatus:工作状态,searchParam:搜索参数\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/deliveryUser/select_page", method = RequestMethod.POST)
	@ResponseBody
	public PageResult selectPageData(HttpServletRequest request, @RequestBody Map<String, String> param,
			DeliveryUserVo filterMask) {
		PageResult result = PageResult.initResult();
		logger.info("派送员管理接口参数:" + JSON.toJSONString(param));
		try {
			int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
			int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
			String time = param.get("dateParam");
			String workStatus = param.get("workStatus");
			String searchParam = param.get("searchParam");

			Map<String, Object> map = new HashMap<>();

			if (StringUtil.isNotNullAndNotEmpty(searchParam)) {
				String selectParam = searchParam.trim() + "%";
				// searchParam以"1"开头则按手机号搜索
				if (selectParam.startsWith("1")) {
					map.put("phoneNumber", selectParam);
				} else {
					// 按性名搜索
					map.put("name", selectParam);
				}
			}
			if (StringUtil.isNotNullAndNotEmpty(workStatus)) {
				map.put("workStatus", workStatus);
			}
			if (StringUtil.isNotNullAndNotEmpty(time)) {
				// 将字符串转换为日期格式的Date类型(yyyy-MM-dd)
				if (time.indexOf(" ") != -1) {
					time = time.substring(0, time.indexOf(" "));
				}
				map.put("createTime", time);
			}
			
			int startIndex = (pageNum - 1) * pageSize;
			map.put("startIndex", startIndex);
			map.put("pageSize", pageSize);

			int total = deliveryUserService.getDeliveryUserCount(map);
			List<DeliveryUserVo> list = deliveryUserService.getDeliveryUserPage(map);

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
	@RequestMapping(value = "/deliveryUser/save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(HttpServletRequest request, @RequestBody DeliveryUserDto filterMask) {
		Result result = Result.initResult();

		String initialPassword = filterMask.getInitialPassword();
		String confirmPassword = filterMask.getComfirmPassword();
		if (!initialPassword.equals(confirmPassword)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("确认密码输入不正确");
			return result;
		}

		filterMask.setPassword(initialPassword);

		try {
			deliveryUserService.save(filterMask);

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
	@RequestMapping(value = "/deliveryUser/edit", method = RequestMethod.POST)
	@ResponseBody
	public Result edit(HttpServletRequest request, @RequestBody DeliveryUserVo filterMask) {
		Result result = Result.initResult();
		if (StringUtil.isNullOrEmpty(filterMask.getId())) {
			result.setCode(Constants.FAILURE);
			result.setMsg("派送员编号不能为空");
			return result;
		}

		try {
			deliveryUserService.update(filterMask);

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
	 * 切换账号状态 账号状态:10-正常，20-冻结
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@ApiOperation(value = "切换账号状态", notes = "请求参数说明||id:派送员编号,frozenReason:冻结原因,accountStatus:账号状态:10-正常，20-冻结\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/deliveryUser/change_account_status", method = RequestMethod.POST)
	@ResponseBody
	public Result delete(HttpServletRequest request, @RequestBody DeliveryUserVo filterMask) {
		Result result = Result.initResult();
		logger.info("切换账号状态接口参数:" + JSON.toJSONString(filterMask));
		try {
			// 参数非空判断
			if (StringUtil.isNullOrEmpty(filterMask.getId())) {
				throw new RuntimeException("派送员编号不能为空");
			}
			if (StringUtil.isNullOrEmpty(filterMask.getFrozenReason())) {
				throw new RuntimeException("冻结原因不能为空");
			}
			if (StringUtil.isNullOrEmpty(filterMask.getAccountStatus())) {
				throw new RuntimeException("账号状态不能为空");
			}
			if ("20".equals(filterMask.getAccountStatus())) {
				filterMask.setWorkStatus("20");
			}
			// 更新数据库
			deliveryUserService.update(filterMask);

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
