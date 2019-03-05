/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.zach.gasTrade.service.DeliveryLocationHistoryService;
import com.zach.gasTrade.vo.DeliveryLocationHistoryVo;

import io.swagger.annotations.Api;

@Api(tags = "派送员历史位置信息相关api")
@Controller
public class DeliveryLocationHistoryController {
	@Autowired
	private DeliveryLocationHistoryService deliveryLocationHistoryService;

	/**
	 * 进入主页面
	 * 
	 * @param filterMask
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deliveryLocationHistory/main", method = RequestMethod.GET)
	String mainPage(DeliveryLocationHistoryVo filterMask, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		model.addAttribute("filterMask", filterMask);
		return "DeliveryLocationHistory/main";
	}

	/**
	 * table 列表
	 * 
	 * @param request
	 * @param response
	 * @param DeliveryLocationHistoryVo
	 * @throws Exception
	 */
	@RequestMapping(value = "/deliveryLocationHistory/query")
	public void getJsonDataGrid(HttpServletRequest request, HttpServletResponse response,
			DeliveryLocationHistoryVo filterMask) throws Exception {
		List<DeliveryLocationHistoryVo> list = new ArrayList<DeliveryLocationHistoryVo>();
		int total = deliveryLocationHistoryService.getDeliveryLocationHistoryCount(filterMask);
		list = deliveryLocationHistoryService.getDeliveryLocationHistoryList(filterMask);
		PrintWriter write = response.getWriter();

		Map map = new HashMap();
		map.put("total", total);
		map.put("rows", list);

		write.write(JSONObject.toJSONString(map));
		write.flush();
		write.close();
	}

	/**
	 * 进入新增页面
	 * 
	 * @param filterMask
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deliveryLocationHistory/newPage", method = RequestMethod.POST)
	String NewPage(DeliveryLocationHistoryVo filterMask, Model model) {
		model.addAttribute("filterMask", filterMask);
		return "DeliveryLocationHistory/new";
	}

	/**
	 * 新增
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/deliveryLocationHistory/save")
	@Transactional
	public String save(HttpServletRequest request, Model model, HttpServletResponse response,
			DeliveryLocationHistoryVo filterMask) throws Exception {
		// 保存
		deliveryLocationHistoryService.save(filterMask);
		model.addAttribute("filterMask", new DeliveryLocationHistoryVo());
		return "DeliveryLocationHistory/main";

	}

	/**
	 * 进入修改页面
	 * 
	 * @param filterMask
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deliveryLocationHistory/editPage", method = RequestMethod.POST)
	public String editPage(DeliveryLocationHistoryVo filterMask, Model model) throws Exception {
		model.addAttribute("filterMask", filterMask);
		return "DeliveryLocationHistory/edit";
	}

	/**
	 * 修改
	 * 
	 * @param country
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/deliveryLocationHistory/edit")
	@Transactional
	public String edit(HttpServletRequest request, Model model, HttpServletResponse response,
			DeliveryLocationHistoryVo filterMask) throws Exception {

		deliveryLocationHistoryService.update(filterMask);
		model.addAttribute("filterMask", new DeliveryLocationHistoryVo());
		return "DeliveryLocationHistory/main";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deliveryLocationHistory/delete")
	@Transactional
	public void delete(DeliveryLocationHistoryVo filterMask, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		deliveryLocationHistoryService.delete(filterMask);
		PrintWriter write = response.getWriter();
		write.write("SUCC");
		write.flush();
		write.close();
	}
}
