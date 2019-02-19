/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.zach.gasTrade.vo.CustomerUserVo;
import com.zach.gasTrade.service.CustomerUserService;


@Controller
public class CustomerUserController {
	@Autowired
	private CustomerUserService customerUserService;
	
	
	/**
	 * 进入主页面
	 * @param filterMask
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/customerUser/main", method = RequestMethod.GET)
	String mainPage(CustomerUserVo filterMask,Model model,HttpServletRequest request, HttpServletResponse response)
	{ 
		model.addAttribute("filterMask", filterMask);
		return "CustomerUser/main";
	}
	
	/**
	 * table 列表
	 * @param request
	 * @param response
	 * @param CustomerUserVo
	 * @throws Exception
	 */
	@RequestMapping(value = "/customerUser/query")
    public void getJsonDataGrid(HttpServletRequest request,
	    HttpServletResponse response,CustomerUserVo filterMask) throws Exception {
	List<CustomerUserVo> list = new ArrayList<CustomerUserVo>();
	int total = customerUserService.getCustomerUserCount(filterMask);
	list = customerUserService.getCustomerUserList(filterMask);
	PrintWriter write = response.getWriter();
	
	Map map=new HashMap();
	map.put("total", total);
	map.put("rows",  list);

	write.write(JSONObject.toJSONString(map));
	write.flush();
	write.close();
    }

	/**
	 * 进入新增页面
	 * @param filterMask
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/customerUser/newPage", method = RequestMethod.POST)
	String NewPage(CustomerUserVo filterMask,Model model)
	{
		model.addAttribute("filterMask", filterMask);
		return "CustomerUser/new";
	}
	
	/**
	 * 新增
	 * @param country
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/customerUser/save")
	@Transactional
	public String save(HttpServletRequest request,Model model,
			    HttpServletResponse response,CustomerUserVo filterMask) throws Exception {
			//保存
		    customerUserService.save(filterMask);
		    model.addAttribute("filterMask", new CustomerUserVo());
			return "CustomerUser/main";

	}
	
	/**
	 * 进入修改页面
	 * @param filterMask
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/customerUser/editPage", method = RequestMethod.POST)	
    public String  editPage(CustomerUserVo filterMask,Model model) throws Exception{
	model.addAttribute("filterMask", filterMask);
	return "CustomerUser/edit";
    }
	
	
	/**
	 * 修改
	 * @param country
	 * @param result
	 * @return
	 */
	 @RequestMapping(value = "/customerUser/edit")
	    @Transactional
	    public String edit(HttpServletRequest request,Model model,
		    HttpServletResponse response,CustomerUserVo filterMask) throws Exception {
		
		 customerUserService.update(filterMask);
		 model.addAttribute("filterMask", new CustomerUserVo());
		 return "CustomerUser/main";
	    }
	
	 
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/customerUser/delete")
	@Transactional
	public void delete(CustomerUserVo filterMask,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		customerUserService.delete(filterMask);
		PrintWriter write = response.getWriter();
		write.write("SUCC");
		write.flush();
		write.close();
	}
}
