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

import com.common.utils.StringUtil;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.PageResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.service.OrderFinanceStatisticsService;
import com.zach.gasTrade.vo.OrderFinanceStatisticsVo;


@Controller
public class OrderFinanceStatisticsController {
private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private OrderFinanceStatisticsService orderFinanceStatisticsService;
		
	/**
	 * 分页列表  + 搜索
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@RequestMapping(value = "/orderFinanceStatistics/query_page",method = RequestMethod.POST)
	@ResponseBody
    public PageResult getPageData(HttpServletRequest request, @RequestBody Map<String,String> param, OrderFinanceStatisticsVo filterMask) {
		PageResult result=PageResult.initResult();
		
		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String searchDate = param.get("searchDate");
		if(StringUtil.isNotNullAndNotEmpty(searchDate)) {
			String date = searchDate + "%";
			filterMask.setDate(date);
		}
				
		filterMask.setPage(pageNum);
		filterMask.setPageSize(pageSize);
		try{
			int total = orderFinanceStatisticsService.getOrderFinanceStatisticsCount(filterMask);
			List<OrderFinanceStatisticsVo> list = orderFinanceStatisticsService.getOrderFinanceStatisticsPage(filterMask);
			
			result.setAllCount(total);
			result.setPageNum(pageNum);
			result.setPageSize(pageSize);
			result.setData(list);
		}catch (RuntimeException e){
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常,"+e.getMessage(),e);
		}catch (Exception e){
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试",e);
		}
		return result;
    }
	
	/**
	 * 新增
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/orderFinanceStatistics/save",method = RequestMethod.GET)
	@ResponseBody
	public Result save(HttpServletRequest request) {
		Result result = Result.initResult();
				
		try{			
			orderFinanceStatisticsService.save();
			
		}catch (RuntimeException e){
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常,"+e.getMessage(),e);
		}catch (Exception e){
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试",e);
		}
		return result;
	}

}
