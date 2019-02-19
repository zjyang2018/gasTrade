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
import com.zach.gasTrade.vo.OrderInfoVo;
import com.zach.gasTrade.service.OrderInfoService;


@Controller
public class OrderInfoController {
	@Autowired
	private OrderInfoService orderInfoService;
	
	
	/**
	 * 进入主页面
	 * @param filterMask
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/orderInfo/main", method = RequestMethod.GET)
	String mainPage(OrderInfoVo filterMask,Model model,HttpServletRequest request, HttpServletResponse response)
	{ 
		model.addAttribute("filterMask", filterMask);
		return "OrderInfo/main";
	}
	
	/**
	 * table 列表
	 * @param request
	 * @param response
	 * @param OrderInfoVo
	 * @throws Exception
	 */
	@RequestMapping(value = "/orderInfo/query")
    public void getJsonDataGrid(HttpServletRequest request,
	    HttpServletResponse response,OrderInfoVo filterMask) throws Exception {
	List<OrderInfoVo> list = new ArrayList<OrderInfoVo>();
	int total = orderInfoService.getOrderInfoCount(filterMask);
	list = orderInfoService.getOrderInfoList(filterMask);
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
	@RequestMapping(value = "/orderInfo/newPage", method = RequestMethod.POST)
	String NewPage(OrderInfoVo filterMask,Model model)
	{
		model.addAttribute("filterMask", filterMask);
		return "OrderInfo/new";
	}
	
	/**
	 * 新增
	 * @param country
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/orderInfo/save")
	@Transactional
	public String save(HttpServletRequest request,Model model,
			    HttpServletResponse response,OrderInfoVo filterMask) throws Exception {
			//保存
		    orderInfoService.save(filterMask);
		    model.addAttribute("filterMask", new OrderInfoVo());
			return "OrderInfo/main";

	}
	
	/**
	 * 进入修改页面
	 * @param filterMask
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/orderInfo/editPage", method = RequestMethod.POST)	
    public String  editPage(OrderInfoVo filterMask,Model model) throws Exception{
	model.addAttribute("filterMask", filterMask);
	return "OrderInfo/edit";
    }
	
	
	/**
	 * 修改
	 * @param country
	 * @param result
	 * @return
	 */
	 @RequestMapping(value = "/orderInfo/edit")
	    @Transactional
	    public String edit(HttpServletRequest request,Model model,
		    HttpServletResponse response,OrderInfoVo filterMask) throws Exception {
		
		 orderInfoService.update(filterMask);
		 model.addAttribute("filterMask", new OrderInfoVo());
		 return "OrderInfo/main";
	    }
	
	 
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/orderInfo/delete")
	@Transactional
	public void delete(OrderInfoVo filterMask,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		orderInfoService.delete(filterMask);
		PrintWriter write = response.getWriter();
		write.write("SUCC");
		write.flush();
		write.close();
	}
}
