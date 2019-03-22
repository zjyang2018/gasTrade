package com.zach.gasTrade.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.common.wx.TokenUtil;
import com.common.wx.WeiXinConstant;
import com.common.wx.WeiXinSignUtil;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.DataResult;
import com.zach.gasTrade.dto.UserDto;
import com.zach.gasTrade.service.CustomerUserService;
import com.zach.gasTrade.vo.CustomerUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "微信相关api")
@Controller
public class WeiXinController extends CommonController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private CustomerUserService customerUserService;

	@ApiOperation(value = "加载微信公众号js配置 接口", notes = "请求方式:GET\\n返回参数字段说明||appId:公众号appId,timestamp:随机时间戳 ,nonceStr:随机字符串,signature:签名字符串\\n")
	@RequestMapping(value = "/weixin/loadConfig", method = RequestMethod.POST)
	@ResponseBody
	public DataResult wxsdk_config(HttpServletRequest request, Map<String, String> param) {
		DataResult result = DataResult.initResult();
		// 1、获取请求url
		// String url = request.getRequestURI();
		String url = param.get("url");
		// 2、获取Ticket
		String jsapi_ticket = TokenUtil.getWXTicket();
		// 3、时间戳和随机字符串
		String noncestr = WeiXinSignUtil.getNonceStr();
		String timestamp = WeiXinSignUtil.getTimeStamp();
		// System.out.println("accessToken:"+accessToken+"\njsapi_ticket:"+jsapi_ticket+"\n时间戳："+timestamp+"\n随机字符串："+noncestr);
		// 5、将参数排序并拼接字符串
		String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
				+ url;
		// 6、将字符串进行sha1加密
		logger.info("sha1加密==前==字符串==>" + str);
		String signature = WeiXinSignUtil.sha1Hex(str);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appid", WeiXinConstant.appId);
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("signature", signature);
		result.setData(map);

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
	public DataResult getWeiXinUserBaseInfo(HttpServletRequest request) {
		DataResult result = DataResult.initResult();
		String code = request.getParameter("code");
		logger.info("获取到微信授权code:" + code);
		// Map<String, String> paramer = new HashMap<String, String>();
		// paramer.put("code", "code");
		// paramer.put("isRegister", "false");
		try {
			Map<String, Object> returnData = customerUserService.getWeiXinUserBaseInfo(code);
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

	/**
	 * 个人中心查询客户信息
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/weixin/getUserInfo", method = RequestMethod.GET)
	@ResponseBody
	public DataResult getCustomerUserInfo(HttpServletRequest request, CustomerUserVo filterMask) {
		DataResult result = DataResult.initResult();
		String userType = request.getParameter("userType");
		logger.info("查询个人信息参数,userType:" + userType);
		try {
			UserDto user = this.getCurrentUser(request);
			if (user == null) {
				result.setCode(Constants.USER_NOT_EXIST);
				result.setMsg("用户信息不存在");
			} else if ("1".equals(user.getUserType())) {
				result.setData(user.getCustomerUser());
			} else if ("2".equals(user.getUserType())) {
				result.setData(user.getDeliveryUser());
			} else if ("4".equals(user.getUserType())) {
				if ("1".equals(userType)) {
					result.setData(user.getCustomerUser());
				} else if ("2".equals(userType)) {
					result.setData(user.getDeliveryUser());
				} else {
					result.setCode(Constants.PARAM_NOT_NULL);
					result.setMsg("参数类型不能为空");
				}
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
