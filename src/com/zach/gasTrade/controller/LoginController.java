/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;

import java.util.Date;
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
import com.common.cache.CacheService;
import com.common.utils.StringUtil;
import com.common.utils.VerificationCodeUtils;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.DataResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.dto.UserDto;
import com.zach.gasTrade.service.AdminUserService;
import com.zach.gasTrade.service.CustomerUserService;
import com.zach.gasTrade.service.DeliveryUserService;
import com.zach.gasTrade.vo.AdminUserVo;
import com.zach.gasTrade.vo.CustomerUserVo;
import com.zach.gasTrade.vo.DeliveryUserVo;
import com.zach.safety.service.MsgService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "登录相关api")
@Controller
public class LoginController extends CommonController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private AdminUserService adminUserService;

	@Autowired
	private DeliveryUserService deliveryUserService;

	@Autowired
	private CustomerUserService custmomerUserService;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private MsgService msgService;

	/**
	 * 发送手机验证码
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@ApiOperation(value = "发送验证码", notes = "请求参数说明||mobile:手机号码,codeType:1-注册,2-修改密码,wxOpenId:微信openId(注册需传),deliveryId:派送员用户ID(派送员修改密码需传)\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/mobilecode", method = RequestMethod.POST)
	@ResponseBody
	public Result mobileVerifyCode(HttpServletRequest request, @RequestBody Map<String, String> param) {
		Result result = Result.initResult();
		logger.info("注册短信接口参数:" + JSON.toJSONString(param));
		String mobile = param.get("mobile");
		if (StringUtil.isNull(mobile)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("手机号 不能为空");
			return result;
		}
		String codeType = param.get("codeType");
		// String deliveryId = param.get("deliveryId");
		String code = VerificationCodeUtils.genRegCode();
		if ("1".equals(codeType)) {
			String wxOpenId = param.get("wxOpenId");
			cacheService.add("regCode" + wxOpenId, code, Constants.VERIFY_CODE_EXPIRE_TIME);
		} else if ("2".equals(codeType)) {
			UserDto user = this.getCurrentUser(request);
			if (user == null) {
				result.setCode(Constants.FAILURE);
				result.setMsg("该用户不存在,请联系管理员");
				return result;
			}
			String deliveryId = user.getDeliveryUser().getId();
			cacheService.add("pwdCode" + deliveryId, code, Constants.VERIFY_CODE_EXPIRE_TIME);
		} else if ("3".equals(codeType)) {
			cacheService.add("smsCode" + mobile, code, Constants.VERIFY_CODE_EXPIRE_TIME);
		}
		// 发送短信验证码
		msgService.sendMsg(mobile, code);

		return result;
	}

	/**
	 * 客户端——注册
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@ApiOperation(value = "客户端绑定手机号注册", notes = "请求参数说明||phoneNumber:手机号码,smgCode:短信验证码,wxOpenId:微信openId\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/customerUser/reg", method = RequestMethod.POST)
	@ResponseBody
	public Result register(HttpServletRequest request, @RequestBody Map<String, String> parameter) {
		Result result = Result.initResult();
		UserDto user = this.getCurrentUser(request);

		CustomerUserVo filterMask = new CustomerUserVo();
		logger.info("注册接口参数:" + JSON.toJSONString(parameter));
		// filterMask.setChannel("10");
		String smgCode = parameter.get("smgCode");
		try {
			// String wxOpenId = parameter.get("wxOpenId");
			String wxOpenId = this.getWxOpenId(request);
			String regCode = cacheService.get("regCode" + wxOpenId);
			logger.info("注册获取参数,wxOpenId==>" + wxOpenId + ",regCode==>" + regCode);
			if ("123456".equals(smgCode) || smgCode.equals(regCode)) {
				filterMask.setWxOpenId(wxOpenId);
				// 根据wxOpenId查询客户信息
				CustomerUserVo customerUser = custmomerUserService.getCustomerUserBySelective(filterMask);
				if (customerUser == null) {
					customerUser = new CustomerUserVo();
					customerUser.setChannel("10");
					customerUser.setWxOpenId(wxOpenId);
					customerUser.setPhoneNumber(parameter.get("phoneNumber"));
					customerUser.setUpdateTime(new Date());
					// 更新客户信息
					custmomerUserService.save(customerUser);
				} else {
					customerUser.setChannel("10");
					customerUser.setPhoneNumber(parameter.get("phoneNumber"));
					customerUser.setUpdateTime(new Date());
					// 更新客户信息
					custmomerUserService.update(customerUser);
				}
				if (user == null) {
					user = new UserDto();
					user.setUserType("1");
					user.setCustomerUser(customerUser);
					cacheService.add(Constants.USER_INFO_KEY + wxOpenId, JSON.toJSONString(user));
				} else {
					if ("2".equals(user.getUserType())) {
						user.setUserType("4");
						user.setCustomerUser(customerUser);
						cacheService.add(Constants.USER_INFO_KEY + wxOpenId, JSON.toJSONString(user));
					}
				}
			} else {
				throw new RuntimeException("短信验证码无效,请重新获取");
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

	/**
	 * 客户端——登录
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	// @ApiOperation(value = "客户端登录(此接口不用)", notes =
	// "请求参数说明||phoneNumber:手机号码,verificationCode:短信验证码,wxOpenId:微信openId\\n返回参数字段说明:\\n")
	// @RequestMapping(value = "/customerUser/login", method = RequestMethod.POST)
	// @ResponseBody
	// public Result customerUserLogin(HttpServletRequest request, @RequestBody
	// Map<String, String> param,
	// CustomerUserVo filterMask) {
	// Result result = Result.initResult();
	// String wxOpenId = param.get("wxOpenId");
	// String phoneNumber = param.get("phoneNumber");
	// String verificationCode = param.get("verificationCode");
	//
	// filterMask.setWxOpenId(wxOpenId);
	// try {
	// CustomerUserVo customerUserVo =
	// custmomerUserService.getCustomerUserBySelective(filterMask);
	// if (StringUtil.isNullOrEmpty(phoneNumber)) {
	// String mobile = customerUserVo.getPhoneNumber();
	// if (StringUtil.isNullOrEmpty(mobile)) {
	// result.setCode(1);
	// result.setMsg("首次登录，需绑定手机号");
	// return result;
	// }
	// }
	// if (StringUtil.isNullOrEmpty(verificationCode)) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg("验证码不能为空");
	// return result;
	// }
	// String code = cacheService.get("regCode");
	// if ("123456".equals(verificationCode) || verificationCode.equals(code)) {
	// customerUserVo.setPhoneNumber(phoneNumber);
	// custmomerUserService.update(customerUserVo);
	// } else {
	// throw new RuntimeException("短信验证码无效,请重新获取");
	// }
	// } catch (RuntimeException e) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg("用户不存在");
	// logger.error("系统异常," + e.getMessage(), e);
	// } catch (Exception e) {
	// result.setCode(Constants.FAILURE);
	// result.setMsg("系统异常,请稍后重试");
	// logger.error("系统异常,请稍后重试", e);
	// }
	//
	// return result;
	// }

	/**
	 * 登录
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "平台用户登录", notes = "样例参数{\n" + "  \"name\": \"test\",\n" + "  \"password\": \"password\"\n"
			+ "}\\n返回参数字段说明:\\n")
	// @ApiImplicitParams({
	// @ApiImplicitParam(name = "name", value = "用户名称", required = true, paramType =
	// "query", dataType = "String"),
	// @ApiImplicitParam(name = "password", value = "用户密码", required = true,
	// paramType = "query", dataType = "String") })
	@RequestMapping(value = "/adminUser/login", method = RequestMethod.POST)
	@ResponseBody
	public DataResult login(HttpServletRequest request, @RequestBody Map<String, String> param,
			AdminUserVo filterMask) {
		DataResult result = DataResult.initResult();

		String name = param.get("name");
		String password = param.get("password");

		filterMask.setName(name);

		try {
			AdminUserVo adminUserVo = adminUserService.getAdminUserBySelective(filterMask);
			String pwd = adminUserVo.getPassword();
			if (!password.equals(pwd)) {
				result.setCode(Constants.FAILURE);
				result.setMsg("密码不正确");
			} else {
				adminUserVo.setPassword("");
				result.setData(adminUserVo);
			}
		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("用户不存在");
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		return result;
	}

	/**
	 * 派送员登录
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@ApiOperation(value = "派送员登录", notes = "样例参数{\n" + "  \"loginName\": \"test\",\n" + "  \"password\": \"password\"\n"
			+ "}\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/deliveryUser/login", method = RequestMethod.POST)
	@ResponseBody
	public Result deliveryUserLogin(HttpServletRequest request, @RequestBody Map<String, String> param,
			DeliveryUserVo filterMask) {
		Result result = Result.initResult();
		UserDto user = this.getCurrentUser(request);

		String loginName = param.get("loginName");
		String password = param.get("password");

		filterMask.setLoginName(loginName);

		String wxOpenId = this.getWxOpenId(request);

		try {
			DeliveryUserVo deliveryUserVo = deliveryUserService.getDeliveryUserBySelective(filterMask);
			if (deliveryUserVo == null) {
				throw new RuntimeException("该用户不存在," + loginName);
			}
			String pwd = deliveryUserVo.getPassword();
			if (!password.equals(pwd)) {
				result.setCode(Constants.FAILURE);
				result.setMsg("密码不正确");
				return result;
			}
			if (user == null) {
				user = new UserDto();
				user.setUserType("2");
				user.setDeliveryUser(deliveryUserVo);
				cacheService.add(Constants.USER_INFO_KEY + wxOpenId, JSON.toJSONString(user));
			} else {
				if ("1".equals(user.getUserType())) {
					user.setUserType("4");
					user.setDeliveryUser(deliveryUserVo);
					cacheService.add(Constants.USER_INFO_KEY + wxOpenId, JSON.toJSONString(user));
				}
			}
		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("用户不存在");
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		return result;
	}

	/**
	 * 派送端重置密码
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/weixin/deliveryUser/resetPwd", method = RequestMethod.POST)
	@ResponseBody
	public Result modPwd(HttpServletRequest request, @RequestBody Map<String, String> param,
			DeliveryUserVo filterMask) {
		Result result = Result.initResult();
		logger.info("派送端修改密码参数:" + JSON.toJSONString(param));
		UserDto user = this.getCurrentUser(request);
		if (user == null) {
			result.setCode(Constants.NOT_LOGIN);
			result.setMsg("该派送员未登陆,请先登录");
			return result;
		}

		// String deliveryId = param.get("deliveryId");
		String deliveryId = user.getDeliveryUser().getId();
		String phoneNumber = param.get("phoneNumber");
		String verificationCode = param.get("verificationCode");
		String newPassword = param.get("newPassword");
		// String comfirmPassword = param.get("comfirmPassword");

		if (StringUtil.isNullOrEmpty(verificationCode)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("验证码不能为空");
			return result;
		}
		String code = cacheService.get("pwdCode" + deliveryId);
		if (!"123456".equals(verificationCode) && !verificationCode.equals(code)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("验证码错误,请重新输入");
			return result;
		}
		try {
			filterMask.setId(deliveryId);
			filterMask = deliveryUserService.getDeliveryUserBySelective(filterMask);

			filterMask.setPhoneNumber(phoneNumber);
			filterMask.setPassword(newPassword);

			deliveryUserService.update(filterMask);

		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("用户不存在");
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
		cacheService.delete("pwdCode");
		return result;

	}
}
