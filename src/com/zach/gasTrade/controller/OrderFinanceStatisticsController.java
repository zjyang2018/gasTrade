/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.utils.StringUtil;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.ExcelUtils;
import com.zach.gasTrade.common.PageResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.service.OrderFinanceStatisticsService;
import com.zach.gasTrade.vo.OrderFinanceStatisticsVo;

import io.swagger.annotations.Api;

@Api(tags = "财务统计相关api")
@Controller
public class OrderFinanceStatisticsController extends CommonController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private OrderFinanceStatisticsService orderFinanceStatisticsService;

	/**
	 * 分页列表 + 搜索
	 * 
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@RequestMapping(value = "/orderFinanceStatistics/query_page", method = RequestMethod.POST)
	@ResponseBody
	public PageResult getPageData(HttpServletRequest request, @RequestBody Map<String, String> param,
			OrderFinanceStatisticsVo filterMask) {
		PageResult result = PageResult.initResult();

		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String searchStartDate = param.get("searchStartDate");
		String searchEndDate = request.getParameter("searchEndDate");
		if (StringUtil.isNotNullAndNotEmpty(searchStartDate)) {
			// filterMask.setDate(searchDate.substring(0, 10));
			filterMask.setStartDate(searchStartDate.substring(0, 10));
		}
		if (StringUtil.isNotNullAndNotEmpty(searchEndDate)) {
			filterMask.setEndDate(searchEndDate.substring(0, 10));
		}

		filterMask.setPage(pageNum);
		filterMask.setPageSize(pageSize);
		try {
			int total = orderFinanceStatisticsService.getOrderFinanceStatisticsCount(filterMask);
			List<OrderFinanceStatisticsVo> list = orderFinanceStatisticsService
					.getOrderFinanceStatisticsPage(filterMask);

			result.setAllCount(total);
			result.setPageNum(pageNum);
			result.setPageSize(pageSize);
			result.setData(list);
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

	@RequestMapping(value = "/orderFinanceStatistics/exportOrderStatisticsList", method = RequestMethod.GET)
	public void exportOrderStatistics(HttpServletRequest request, HttpServletResponse response,
			OrderFinanceStatisticsVo filterMask) {
		PageResult result = PageResult.initResult();

		String searchStartDate = request.getParameter("searchStartDate");
		String searchEndDate = request.getParameter("searchEndDate");
		if (StringUtil.isNotNullAndNotEmpty(searchStartDate)) {
			// filterMask.setDate(searchDate.substring(0, 10));
			filterMask.setStartDate(searchStartDate.substring(0, 10));
		}
		if (StringUtil.isNotNullAndNotEmpty(searchEndDate)) {
			filterMask.setEndDate(searchEndDate.substring(0, 10));
		}

		try {
			List<OrderFinanceStatisticsVo> list = orderFinanceStatisticsService
					.getOrderFinanceStatisticsList(filterMask);
			// 声明一个工作薄
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFRow row = ExcelUtils.constructOrderStatisticsHeader(wb);
			HSSFSheet sheet = wb.getSheet("订单统计");
			int index = 0;
			for (OrderFinanceStatisticsVo bean : list) {
				// 构造导出列表数据
				row = sheet.createRow(index + 1);
				row.createCell(0).setCellValue(index + 1);
				row.createCell(1).setCellValue(new HSSFRichTextString(bean.getDate()));
				if (bean.getBuyerCount() != null) {
					row.createCell(2).setCellValue(new HSSFRichTextString(String.valueOf(bean.getBuyerCount())));
				} else {
					row.createCell(2).setCellValue(new HSSFRichTextString("0"));
				}
				if (bean.getOrderCount() != null) {
					row.createCell(3).setCellValue(new HSSFRichTextString(String.valueOf(bean.getOrderCount())));
				} else {
					row.createCell(3).setCellValue(new HSSFRichTextString("0"));
				}
				if (bean.getPayOrderCount() != null) {
					row.createCell(4).setCellValue(new HSSFRichTextString(String.valueOf(bean.getPayOrderCount())));
				} else {
					row.createCell(4).setCellValue(new HSSFRichTextString("0"));
				}
				if (bean.getDeliveryOrderCount() != null) {
					row.createCell(5)
							.setCellValue(new HSSFRichTextString(String.valueOf(bean.getDeliveryOrderCount())));
				} else {
					row.createCell(5).setCellValue(new HSSFRichTextString("0"));
				}
				if (bean.getAvgAmount() != null) {
					row.createCell(6).setCellValue(new HSSFRichTextString(bean.getAvgAmount().toPlainString()));
				} else {
					row.createCell(6).setCellValue(new HSSFRichTextString("0.00"));
				}
				if (bean.getOrderAmount() != null) {
					row.createCell(7).setCellValue(new HSSFRichTextString(bean.getOrderAmount().toPlainString()));
				} else {
					row.createCell(7).setCellValue(new HSSFRichTextString("0.00"));
				}
			}

			OutputStream output = response.getOutputStream();
			String titleName = "订单统计";
			String excelName = URLEncoder.encode(titleName, "utf-8");
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + excelName + ".xls");
			response.flushBuffer();
			wb.write(output);
			output.close();

		} catch (RuntimeException e) {
			result.setCode(Constants.FAILURE);
			result.setMsg(e.getMessage());
			logger.error("系统异常," + e.getMessage(), e);
		} catch (Exception e) {
			result.setCode(Constants.FAILURE);
			result.setMsg("系统异常,请稍后重试");
			logger.error("系统异常,请稍后重试", e);
		}
	}

	/**
	 * 新增
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/orderFinanceStatistics/save", method = RequestMethod.GET)
	@ResponseBody
	public Result save(HttpServletRequest request) {
		Result result = Result.initResult();

		try {
			orderFinanceStatisticsService.save();

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
