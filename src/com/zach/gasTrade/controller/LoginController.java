/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;


import java.util.Map;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.utils.StringUtil;
import com.common.utils.VerificationCodeUtils;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.service.AdminUserService;
import com.zach.gasTrade.service.CustomerUserService;
import com.zach.gasTrade.service.DeliveryUserService;
import com.zach.gasTrade.vo.AdminUserVo;
import com.zach.gasTrade.vo.CustomerUserVo;
import com.zach.gasTrade.vo.DeliveryUserVo;
import com.zach.safety.service.MsgService;

import io.swagger.annotations.ApiOperation;

@Controller
public class LoginController {	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private AdminUserService adminUserService;
	
	@Autowired
	private DeliveryUserService deliveryUserService;
	
	@Autowired
	private CustomerUserService custmomerUserService;
	
	@Autowired
	private MsgService msgService;
	
	/**
	 * 发送手机验证码
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/mobilecode", method = RequestMethod.POST)
	@ResponseBody
    public Result mobileVerifyCode(HttpServletRequest request,@RequestBody Map<String, String> param) {
		Result result=Result.initResult();
		
        String mobile = param.get("mobile");
        String codeType = param.get("String codeType");
        String deliveryId = param.get("deliveryId");
        String code = VerificationCodeUtils.genRegCode();
        HttpSession httpSession = request.getSession();
        if("1".equals(codeType)) {
        	httpSession.setAttribute("regCode", code);
        }else if("2".equals(codeType)) {
        	if(StringUtil.isNullOrEmpty(deliveryId)) {
        		result.setCode(Constants.FAILURE);
    			result.setMsg("派送员编号 不能为空");
    			return result;
        	}
        	DeliveryUserVo filterMask = new DeliveryUserVo();
        	filterMask.setId(deliveryId);
    		filterMask.setPhoneNumber(mobile);
    		DeliveryUserVo deliveryUserVo = deliveryUserService.getDeliveryUserBySelective(filterMask);
    		if(deliveryUserVo == null) {
    			result.setCode(Constants.FAILURE);
    			result.setMsg("手机号不正确,请重新输入");
    			return result;
    		}
        	httpSession.setAttribute("pwdCode", code);
        }
        msgService.sendMsg(mobile, code);
        // TimeTask实现1分钟后从session中删除code
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String regCode = (String) httpSession.getAttribute("regCode");
				String pwdCode = (String) httpSession.getAttribute("pwdCode");
				if(StringUtil.isNotNullAndNotEmpty(regCode)) {
					httpSession.removeAttribute("regCode");
				}
				if(StringUtil.isNotNullAndNotEmpty(pwdCode)) {
					httpSession.removeAttribute("pwdCode");
				}
				
				timer.cancel();
			}
		}, 1*60*1000);
        
        return result;
    }
	
	/**
	 * 客户端——注册
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/customerUser/reg", method = RequestMethod.POST)
	@ResponseBody
    public Result register(HttpServletRequest request, @RequestBody CustomerUserVo filterMask) {
		Result result=Result.initResult();
		filterMask.setChannel("10");
		
        try {		
			custmomerUserService.save(filterMask);
			
		}catch (RuntimeException e){
			result.setCode(Constants.FAILURE);
			result.setMsg("用户不存在");
			logger.error("系统异常,"+e.getMessage(),e);
		}catch (Exception e){
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试",e);
		}
		return result;	
    }
	
	/**
	 * 客户端——登录
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/customerUser/login", method = RequestMethod.POST)
	@ResponseBody
    public Result customerUserLogin(HttpServletRequest request, @RequestBody Map<String, String> param,CustomerUserVo filterMask) {
		Result result=Result.initResult();
		String wxOpenId = param.get("wxOpenId");
		String phoneNumber = param.get("phoneNumber");
		String verificationCode = param.get("verificationCode");
		
		filterMask.setWxOpenId(wxOpenId);		
        try {		
        	CustomerUserVo customerUserVo = custmomerUserService.getCustomerUserBySelective(filterMask);
        	if(StringUtil.isNullOrEmpty(phoneNumber)) {
            	String mobile = customerUserVo.getPhoneNumber();
            	if(StringUtil.isNullOrEmpty(mobile)) {
            		result.setCode(1);
        			result.setMsg("首次登录，需绑定手机号");
        			return result;
            	}
        	}
        	if(StringUtil.isNullOrEmpty(verificationCode)) {    	
            		result.setCode(Constants.FAILURE);
        			result.setMsg("验证码不能为空");
        			return result;
        	}
        	HttpSession httpSession = request.getSession();
        	String code = (String) httpSession.getAttribute("regCode");
        	if(!code.equals(verificationCode)) {
        		result.setCode(Constants.FAILURE);
    			result.setMsg("验证码错误,请重新输入");
    			return result;
        	}
        	customerUserVo.setPhoneNumber(phoneNumber);
        	custmomerUserService.update(customerUserVo);
        	
        	httpSession.removeAttribute("regCode");
			
		}catch (RuntimeException e){
			result.setCode(Constants.FAILURE);
			result.setMsg("用户不存在");
			logger.error("系统异常,"+e.getMessage(),e);
		}catch (Exception e){
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试",e);
		}
		return result;	
    }
	
	/**
	 * 登录
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@ApiOperation(value = "平台用户登录", notes = "样例参数{\n" + "  \"name\": \"test\",\n" + "  \"password\": \"password\"\n"
			+ "}\\n返回参数字段说明:\\n")
	// @ApiImplicitParams({
	// @ApiImplicitParam(name = "name", value = "用户名称", required = true, paramType =
	// "query", dataType = "String"),
	// @ApiImplicitParam(name = "password", value = "用户密码", required = true,
	// paramType = "query", dataType = "String") })
	@RequestMapping(value = "/adminUser/login", method = RequestMethod.POST)
	@ResponseBody
	public Result login(HttpServletRequest request, @RequestBody Map<String,String> param, AdminUserVo filterMask) {
		Result result=Result.initResult();
		
		String name = param.get("name");
		String password = param.get("password");
		
		filterMask.setName(name);
		
		try {		
			AdminUserVo adminUserVo = adminUserService.getAdminUserBySelective(filterMask);
			String pwd = adminUserVo.getPassword();
			if(!password.equals(pwd)) {
				result.setCode(Constants.FAILURE);
				result.setMsg("密码不正确");
			}
		}catch (RuntimeException e){
			result.setCode(Constants.FAILURE);
			result.setMsg("用户不存在");
			logger.error("系统异常,"+e.getMessage(),e);
		}catch (Exception e){
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试",e);
		}
		return result;	
	}
	
	/**
	 * 派送员登录
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/deliveryUser/login", method = RequestMethod.POST)
	@ResponseBody
	public Result deliveryUserLogin(HttpServletRequest request, @RequestBody Map<String,String> param, DeliveryUserVo filterMask) {
		Result result=Result.initResult();
		
		String loginName = param.get("loginName");
		String password = param.get("password");
		
		filterMask.setLoginName(loginName);
		
		try {		
			DeliveryUserVo deliveryUserVo = deliveryUserService.getDeliveryUserBySelective(filterMask);
			String pwd = deliveryUserVo.getPassword();
			if(!password.equals(pwd)) {
				result.setCode(Constants.FAILURE);
				result.setMsg("密码不正确");
				return result;
			}
		}catch (RuntimeException e){
			result.setCode(Constants.FAILURE);
			result.setMsg("用户不存在");
			logger.error("系统异常,"+e.getMessage(),e);
		}catch (Exception e){
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试",e);
		}
		return result;	
	}
			
	/**
	 * 派送端重置密码
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	public Result modPwd(HttpServletRequest request, @RequestBody Map<String,String> param, DeliveryUserVo filterMask) {
		Result result=Result.initResult();
		
		String deliveryId = param.get("deliveryId");
		String phoneNumber = param.get("phoneNumber");
		String verificationCode = param.get("verificationCode");
		String newPassword = param.get("newPassword");
		String comfirmPassword = param.get("comfirmPassword");
		
		if(StringUtil.isNullOrEmpty(verificationCode)) {    	
    		result.setCode(Constants.FAILURE);
			result.setMsg("验证码不能为空");
			return result;
	}
	HttpSession httpSession = request.getSession();
	String code = (String) httpSession.getAttribute("pwdCode");
	if(!code.equals(verificationCode)) {
		result.setCode(Constants.FAILURE);
		result.setMsg("验证码错误,请重新输入");
		return result;
	}
		
		if(!newPassword.equals(comfirmPassword)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("两次密码输入不一样");
			return result;
		}
		filterMask.setId(deliveryId);
		filterMask.setPhoneNumber(phoneNumber);
		filterMask.setPassword(newPassword);
		
		try {		
			deliveryUserService.update(filterMask);
			
		}catch (RuntimeException e){
			result.setCode(Constants.FAILURE);
			result.setMsg("用户不存在");
			logger.error("系统异常,"+e.getMessage(),e);
		}catch (Exception e){
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试",e);
		}
		return result;	

	}
}
