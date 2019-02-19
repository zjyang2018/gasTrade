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

import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.DataResult;
import com.zach.gasTrade.common.PageResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.service.ProductService;
import com.zach.gasTrade.vo.ProductVo;


@Controller
public class ProductController {
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 分页列表
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@RequestMapping(value = "/product/query_page",method = RequestMethod.POST)
	@ResponseBody
    public PageResult getPageData(HttpServletRequest request, @RequestBody Map<String,String> param, ProductVo filterMask) {
		PageResult result=PageResult.initResult();
		
		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String likeName = param.get("productName");
		String productName = likeName + "%";
		filterMask.setPage(pageNum);
		filterMask.setPageSize(pageSize);
		filterMask.setProductName(productName);
		try{
			int total = productService.getProductCount(filterMask);
			List<ProductVo> list = productService.getProductPage(filterMask);
			
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
	@RequestMapping(value = "/product/save",method = RequestMethod.POST)
	@ResponseBody
	public Result save(HttpServletRequest request,@RequestBody ProductVo filterMask) {
		Result result = Result.initResult();
				
		try{
			productService.save(filterMask);
			
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
	 * 修改
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/product/edit",method = RequestMethod.POST)
	@ResponseBody
	public Result edit(HttpServletRequest request,@RequestBody ProductVo filterMask) {
		Result result = Result.initResult();
				
		try{
			productService.update(filterMask);
			
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
	 * 删除
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/product/delete",method = RequestMethod.POST)
	@ResponseBody
	public Result delete(HttpServletRequest request,@RequestBody ProductVo filterMask) {
		Result result = Result.initResult();
		
		filterMask.setStatus("20");
				
		try{
			productService.update(filterMask);
			
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
	 * 详情
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/product/info",method = RequestMethod.POST)
	@ResponseBody
	public DataResult info(HttpServletRequest request,@RequestBody ProductVo filterMask) {
		DataResult result = DataResult.initResult();
			
		try{
			ProductVo product = productService.getProductBySelective(filterMask);
			result.setData(product);
			
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
