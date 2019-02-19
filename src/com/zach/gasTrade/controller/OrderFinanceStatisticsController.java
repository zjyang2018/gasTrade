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
import com.zach.gasTrade.vo.OrderFinanceStatisticsVo;
import com.zach.gasTrade.service.OrderFinanceStatisticsService;


@Controller
public class OrderFinanceStatisticsController {
	@Autowired
	private OrderFinanceStatisticsService orderFinanceStatisticsService;
	
	
	/**
	 * 进入主页面
	 * @param filterMask
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/orderFinanceStatistics/main", method = RequestMethod.GET)
	String mainPage(OrderFinanceStatisticsVo filterMask,Model model,HttpServletRequest request, HttpServletResponse response)
	{ 
		model.addAttribute("filterMask", filterMask);
		return "OrderFinanceStatistics/main";
	}
	
	/**
	 * table 列表
	 * @param request
	 * @param response
	 * @param OrderFinanceStatisticsVo
	 * @throws Exception
	 */
	@RequestMapping(value = "/orderFinanceStatistics/query")
    public void getJsonDataGrid(HttpServletRequest request,
	    HttpServletResponse response,OrderFinanceStatisticsVo filterMask) throws Exception {
	List<OrderFinanceStatisticsVo> list = new ArrayList<OrderFinanceStatisticsVo>();
	int total = orderFinanceStatisticsService.getOrderFinanceStatisticsCount(filterMask);
	list = orderFinanceStatisticsService.getOrderFinanceStatisticsList(filterMask);
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
	@RequestMapping(value = "/orderFinanceStatistics/newPage", method = RequestMethod.POST)
	String NewPage(OrderFinanceStatisticsVo filterMask,Model model)
	{
		model.addAttribute("filterMask", filterMask);
		return "OrderFinanceStatistics/new";
	}
	
	/**
	 * 新增
	 * @param country
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/orderFinanceStatistics/save")
	@Transactional
	public String save(HttpServletRequest request,Model model,
			    HttpServletResponse response,OrderFinanceStatisticsVo filterMask) throws Exception {
			//保存
		    orderFinanceStatisticsService.save(filterMask);
		    model.addAttribute("filterMask", new OrderFinanceStatisticsVo());
			return "OrderFinanceStatistics/main";

	}
	
	/**
	 * 进入修改页面
	 * @param filterMask
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/orderFinanceStatistics/editPage", method = RequestMethod.POST)	
    public String  editPage(OrderFinanceStatisticsVo filterMask,Model model) throws Exception{
	model.addAttribute("filterMask", filterMask);
	return "OrderFinanceStatistics/edit";
    }
	
	
	/**
	 * 修改
	 * @param country
	 * @param result
	 * @return
	 */
	 @RequestMapping(value = "/orderFinanceStatistics/edit")
	    @Transactional
	    public String edit(HttpServletRequest request,Model model,
		    HttpServletResponse response,OrderFinanceStatisticsVo filterMask) throws Exception {
		
		 orderFinanceStatisticsService.update(filterMask);
		 model.addAttribute("filterMask", new OrderFinanceStatisticsVo());
		 return "OrderFinanceStatistics/main";
	    }
	
	 
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/orderFinanceStatistics/delete")
	@Transactional
	public void delete(OrderFinanceStatisticsVo filterMask,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		orderFinanceStatisticsService.delete(filterMask);
		PrintWriter write = response.getWriter();
		write.write("SUCC");
		write.flush();
		write.close();
	}
}
