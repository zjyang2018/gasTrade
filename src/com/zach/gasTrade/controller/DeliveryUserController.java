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
import com.zach.gasTrade.vo.DeliveryUserVo;
import com.zach.gasTrade.service.DeliveryUserService;


@Controller
public class DeliveryUserController {
	@Autowired
	private DeliveryUserService deliveryUserService;
	
	
	/**
	 * 进入主页面
	 * @param filterMask
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deliveryUser/main", method = RequestMethod.GET)
	String mainPage(DeliveryUserVo filterMask,Model model,HttpServletRequest request, HttpServletResponse response)
	{ 
		model.addAttribute("filterMask", filterMask);
		return "DeliveryUser/main";
	}
	
	/**
	 * table 列表
	 * @param request
	 * @param response
	 * @param DeliveryUserVo
	 * @throws Exception
	 */
	@RequestMapping(value = "/deliveryUser/query")
    public void getJsonDataGrid(HttpServletRequest request,
	    HttpServletResponse response,DeliveryUserVo filterMask) throws Exception {
	List<DeliveryUserVo> list = new ArrayList<DeliveryUserVo>();
	int total = deliveryUserService.getDeliveryUserCount(filterMask);
	list = deliveryUserService.getDeliveryUserList(filterMask);
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
	@RequestMapping(value = "/deliveryUser/newPage", method = RequestMethod.POST)
	String NewPage(DeliveryUserVo filterMask,Model model)
	{
		model.addAttribute("filterMask", filterMask);
		return "DeliveryUser/new";
	}
	
	/**
	 * 新增
	 * @param country
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/deliveryUser/save")
	@Transactional
	public String save(HttpServletRequest request,Model model,
			    HttpServletResponse response,DeliveryUserVo filterMask) throws Exception {
			//保存
		    deliveryUserService.save(filterMask);
		    model.addAttribute("filterMask", new DeliveryUserVo());
			return "DeliveryUser/main";

	}
	
	/**
	 * 进入修改页面
	 * @param filterMask
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deliveryUser/editPage", method = RequestMethod.POST)	
    public String  editPage(DeliveryUserVo filterMask,Model model) throws Exception{
	model.addAttribute("filterMask", filterMask);
	return "DeliveryUser/edit";
    }
	
	
	/**
	 * 修改
	 * @param country
	 * @param result
	 * @return
	 */
	 @RequestMapping(value = "/deliveryUser/edit")
	    @Transactional
	    public String edit(HttpServletRequest request,Model model,
		    HttpServletResponse response,DeliveryUserVo filterMask) throws Exception {
		
		 deliveryUserService.update(filterMask);
		 model.addAttribute("filterMask", new DeliveryUserVo());
		 return "DeliveryUser/main";
	    }
	
	 
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/deliveryUser/delete")
	@Transactional
	public void delete(DeliveryUserVo filterMask,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		deliveryUserService.delete(filterMask);
		PrintWriter write = response.getWriter();
		write.write("SUCC");
		write.flush();
		write.close();
	}
}
