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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.utils.MapHelper;
import com.common.utils.StringUtil;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.DataResult;
import com.zach.gasTrade.common.PageResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.service.CustomerUserService;
import com.zach.gasTrade.vo.CustomerUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "客户端用户相关api")
@Controller
public class CustomerUserController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private CustomerUserService customerUserService;

	/**
	 * 分页列表 + 搜索
	 * 
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@RequestMapping(value = "/customerUser/query_page", method = RequestMethod.POST)
	@ResponseBody
	public PageResult getPageData(HttpServletRequest request, @RequestBody Map<String, String> param,
			CustomerUserVo filterMask) {
		PageResult result = PageResult.initResult();

		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String searchParam = param.get("searchParam");
		String channel = param.get("channel");

		if (StringUtil.isNotNullAndNotEmpty(searchParam)) {
			String selectParam = searchParam.trim() + "%";
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
			if(jsonObject==null||jsonObject.getInteger("confidence")<16) {
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
			if(jsonObject==null||jsonObject.getInteger("confidence")<16) {
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
	 * 微信回调授权获取openId
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/weixin/getWeiXinUserInfo", method = RequestMethod.GET)
	@ResponseBody
	public DataResult getWeiXinUserInfo(HttpServletRequest request) {
		DataResult result = DataResult.initResult();
		String code = request.getParameter("code");
		logger.info("获取到微信授权code:" + code);
		// Map<String, String> paramer = new HashMap<String, String>();
		// paramer.put("code", "code");
		// paramer.put("isRegister", "false");
		try {
			Map<String, Object> returnData = customerUserService.getWeiXinUserInfo(code);
			logger.info("获取到微信授权wxOpenId结果为:" + JSON.toJSONString(returnData));
			result.setData(returnData);
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
