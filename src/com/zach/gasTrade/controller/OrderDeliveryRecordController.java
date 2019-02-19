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
import com.zach.gasTrade.vo.OrderDeliveryRecordVo;
import com.zach.gasTrade.service.OrderDeliveryRecordService;


@Controller
public class OrderDeliveryRecordController {
	@Autowired
	private OrderDeliveryRecordService orderDeliveryRecordService;
	
	
	/**
	 * 进入主页面
	 * @param filterMask
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/orderDeliveryRecord/main", method = RequestMethod.GET)
	String mainPage(OrderDeliveryRecordVo filterMask,Model model,HttpServletRequest request, HttpServletResponse response)
	{ 
		model.addAttribute("filterMask", filterMask);
		return "OrderDeliveryRecord/main";
	}
	
	/**
	 * table 列表
	 * @param request
	 * @param response
	 * @param OrderDeliveryRecordVo
	 * @throws Exception
	 */
	@RequestMapping(value = "/orderDeliveryRecord/query")
    public void getJsonDataGrid(HttpServletRequest request,
	    HttpServletResponse response,OrderDeliveryRecordVo filterMask) throws Exception {
	List<OrderDeliveryRecordVo> list = new ArrayList<OrderDeliveryRecordVo>();
	int total = orderDeliveryRecordService.getOrderDeliveryRecordCount(filterMask);
	list = orderDeliveryRecordService.getOrderDeliveryRecordList(filterMask);
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
	@RequestMapping(value = "/orderDeliveryRecord/newPage", method = RequestMethod.POST)
	String NewPage(OrderDeliveryRecordVo filterMask,Model model)
	{
		model.addAttribute("filterMask", filterMask);
		return "OrderDeliveryRecord/new";
	}
	
	/**
	 * 新增
	 * @param country
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/orderDeliveryRecord/save")
	@Transactional
	public String save(HttpServletRequest request,Model model,
			    HttpServletResponse response,OrderDeliveryRecordVo filterMask) throws Exception {
			//保存
		    orderDeliveryRecordService.save(filterMask);
		    model.addAttribute("filterMask", new OrderDeliveryRecordVo());
			return "OrderDeliveryRecord/main";

	}
	
	/**
	 * 进入修改页面
	 * @param filterMask
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/orderDeliveryRecord/editPage", method = RequestMethod.POST)	
    public String  editPage(OrderDeliveryRecordVo filterMask,Model model) throws Exception{
	model.addAttribute("filterMask", filterMask);
	return "OrderDeliveryRecord/edit";
    }
	
	
	/**
	 * 修改
	 * @param country
	 * @param result
	 * @return
	 */
	 @RequestMapping(value = "/orderDeliveryRecord/edit")
	    @Transactional
	    public String edit(HttpServletRequest request,Model model,
		    HttpServletResponse response,OrderDeliveryRecordVo filterMask) throws Exception {
		
		 orderDeliveryRecordService.update(filterMask);
		 model.addAttribute("filterMask", new OrderDeliveryRecordVo());
		 return "OrderDeliveryRecord/main";
	    }
	
	 
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/orderDeliveryRecord/delete")
	@Transactional
	public void delete(OrderDeliveryRecordVo filterMask,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		orderDeliveryRecordService.delete(filterMask);
		PrintWriter write = response.getWriter();
		write.write("SUCC");
		write.flush();
		write.close();
	}
}
