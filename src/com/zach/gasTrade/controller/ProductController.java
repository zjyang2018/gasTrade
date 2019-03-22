/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.common.seq.SerialGenerator;
import com.common.utils.StringUtil;
import com.zach.gasTrade.common.Constants;
import com.zach.gasTrade.common.DataResult;
import com.zach.gasTrade.common.PageResult;
import com.zach.gasTrade.common.Result;
import com.zach.gasTrade.dto.ProductInfoDto;
import com.zach.gasTrade.dto.ProductListDto;
import com.zach.gasTrade.service.AdminUserService;
import com.zach.gasTrade.service.ProductService;
import com.zach.gasTrade.vo.AdminUserVo;
import com.zach.gasTrade.vo.ProductVo;

import io.swagger.annotations.Api;

@Api(tags = "产品相关api")
@Controller
public class ProductController extends CommonController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private ProductService productService;

	@Autowired
	private AdminUserService adminUserService;

	/**
	 * 分页列表
	 * 
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@RequestMapping(value = "/product/query_page", method = RequestMethod.POST)
	@ResponseBody
	public PageResult getPageData(HttpServletRequest request, @RequestBody Map<String, String> param,
			ProductVo filterMask) {
		PageResult result = PageResult.initResult();

		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String likeName = param.get("productName");
		if (StringUtil.isNotNullAndNotEmpty(likeName)) {
			String productName = likeName + "%";
			filterMask.setProductName(productName);
		}

		filterMask.setPage(pageNum);
		filterMask.setPageSize(pageSize);
		try {
			int total = productService.getProductCount(filterMask);
			List<ProductVo> list = productService.getProductPage(filterMask);
			for (ProductVo productVo : list) {
				String imgPath = productVo.getImagePath();
				String[] imgPathArr = {};
				if (StringUtil.isNotNullAndNotEmpty(imgPath)) {
					imgPathArr = imgPath.split(",");
				}
				String imagesPath = "";
				for (String str : imgPathArr) {
					imagesPath += Constants.BASE_PATH + str + ",";
				}
				if (imagesPath.endsWith(",")) {
					imagesPath = imagesPath.substring(0, imagesPath.length() - 1);
				}
				productVo.setImagePath(imagesPath);
			}

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

	/**
	 * 新增
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/product/save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(HttpServletRequest request) {
		DataResult result = DataResult.initResult();
		ProductVo filterMask = new ProductVo();
		// 取得用户ID
		String createUserId = request.getParameter("createUserId");
		String productName = request.getParameter("productName");
		String spec = request.getParameter("spec");
		String amount = request.getParameter("amount");
		String stockQty = request.getParameter("stockQty");
		String productDesc = request.getParameter("productDesc");

		filterMask.setCreateUserId(createUserId);
		filterMask.setProductName(productName);
		filterMask.setSpec(spec);
		filterMask.setAmount(BigDecimal.valueOf(Integer.parseInt(amount)));
		filterMask.setStockQty(Integer.parseInt(stockQty));
		filterMask.setProductDesc(productDesc);

		// 取得上传的文件
		// MultipartResolver resolver = new StandardServletMultipartResolver();
		// MultipartHttpServletRequest multipartRequest =
		// resolver.resolveMultipart(request);
		// List<MultipartFile> images = multipartRequest.getFiles("image");
		MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> files = Murequest.getFileMap();// 得到文件map对象
		if (files.size() > 3) {
			result.setCode(Constants.FAILURE);
			result.setMsg("图片不能超过3张哟");
			return result;
		}
		if (!files.isEmpty()) {
			// 保存数据库的路径
			String sqlPath = "";
			String imagesPath = "";
			// 定义文件保存的本地路径
			String realUploadPath = request.getSession().getServletContext().getRealPath("/") + "images";
			File file = new File(realUploadPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			// 定义 文件名
			String filename = null;
			try {
				for (MultipartFile multipartFile : files.values()) {
					// 生成uuid作为文件名称
					String uuid = SerialGenerator.getUUID();
					// 获得文件类型（可以判断如果不是图片，禁止上传）
					String contentType = multipartFile.getContentType();
					// 获得上传文件名
					String realFileName = multipartFile.getOriginalFilename();
					int pos = realFileName.indexOf('.');
					// 获得文件后缀名
					String suffixName = realFileName.substring(pos + 1, realFileName.length());
					// 得到新 文件名
					filename = uuid + "." + suffixName;

					File tagetFile = new File(realUploadPath + "/" + filename);// 创建文件对象
					if (!tagetFile.exists()) {// 文件名不存在 则新建文件，并将文件复制到新建文件中
						tagetFile.createNewFile();
					}
					// 文件保存路径
					multipartFile.transferTo(tagetFile);
					// 把图片的相对路径保存至数据库
					sqlPath += filename + ",";
					imagesPath += Constants.BASE_PATH + filename + ",";
				}

				sqlPath = sqlPath.substring(0, sqlPath.length() - 1);
				imagesPath = imagesPath.substring(0, imagesPath.length() - 1);

				// 把图片的相对路径保存至数据库
				// sqlPath = "/images/"+filename;
				// sqlPath = realUploadPath + "/"+filename;
				filterMask.setImagePath(sqlPath);
				Map<String, String> map = new HashMap<String, String>();
				map.put("imagesPath", imagesPath);
				result.setData(map);

				productService.save(filterMask);

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

		return result;
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/product/edit", method = RequestMethod.POST)
	@ResponseBody
	public Result edit(HttpServletRequest request) {
		DataResult result = DataResult.initResult();
		ProductVo filterMask = new ProductVo();
		String profuctId = request.getParameter("profuctId");
		if (StringUtil.isNullOrEmpty(profuctId)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("产品编号不能为空");
			return result;
		}
		String updateUserId = request.getParameter("updateUserId");
		if (StringUtil.isNullOrEmpty(updateUserId)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("用户编号不能为空");
			return result;
		}

		String productName = request.getParameter("productName");
		String spec = request.getParameter("spec");
		String amount = request.getParameter("amount");
		String stockQty = request.getParameter("stockQty");
		String productDesc = request.getParameter("productDesc");
		filterMask.setProductId(profuctId);

		ProductVo productVo = productService.getProductBySelective(filterMask);
		String imgPath = productVo.getImagePath();
		String[] imgPathArr = {};
		if (StringUtil.isNotNullAndNotEmpty(imgPath)) {
			imgPathArr = imgPath.split(",");
		}
		productVo.setUpdateUserId(updateUserId);
		if (StringUtil.isNotNullAndNotEmpty(productName)) {
			productVo.setProductName(productName);
		}
		if (StringUtil.isNotNullAndNotEmpty(spec)) {
			productVo.setSpec(spec);
		}
		if (StringUtil.isNotNullAndNotEmpty(amount)) {
			productVo.setAmount(BigDecimal.valueOf(Integer.parseInt(amount)));
		}
		if (StringUtil.isNotNullAndNotEmpty(stockQty)) {
			productVo.setStockQty(Integer.parseInt(stockQty));
		}
		if (StringUtil.isNotNullAndNotEmpty(productDesc)) {
			productVo.setProductDesc(productDesc);
		}

		MultipartHttpServletRequest Murequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> files = Murequest.getFileMap();// 得到文件map对象
		int currentImgCount = imgPathArr.length;
		if ((files.size() + currentImgCount) > 3) {
			result.setCode(Constants.FAILURE);
			result.setMsg("已上传" + currentImgCount + "张图片，只能再上传" + (3 - currentImgCount) + "张图片");
			return result;
		}
		if (!files.isEmpty()) {
			// 保存数据库的路径
			String sqlPath = "";
			String imagesPath = "";
			// 定义文件保存的本地路径
			String realUploadPath = request.getSession().getServletContext().getRealPath("/") + "images";
			File file = new File(realUploadPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			// 定义 文件名
			String filename = null;
			try {
				for (MultipartFile multipartFile : files.values()) {
					// 生成uuid作为文件名称
					String uuid = SerialGenerator.getUUID();
					// 获得文件类型（可以判断如果不是图片，禁止上传）
					String contentType = multipartFile.getContentType();
					// 获得上传文件名
					String realFileName = multipartFile.getOriginalFilename();
					int pos = realFileName.indexOf('.');
					// 获得文件后缀名
					String suffixName = realFileName.substring(pos + 1, realFileName.length());
					// 得到新 文件名
					filename = uuid + "." + suffixName;

					File tagetFile = new File(realUploadPath + "/" + filename);// 创建文件对象
					if (!tagetFile.exists()) {// 文件名不存在 则新建文件，并将文件复制到新建文件中
						tagetFile.createNewFile();
					}
					// 文件保存路径
					multipartFile.transferTo(tagetFile);
					// 把图片的相对路径保存至数据库
					sqlPath += filename + ",";
					imagesPath += Constants.BASE_PATH + filename + ",";
				}
				sqlPath += imgPath;
				sqlPath.replaceAll(" ", "");
				if (sqlPath.endsWith(",")) {
					sqlPath = sqlPath.substring(0, sqlPath.length() - 1);
				}

				for (String str : imgPathArr) {
					imagesPath += Constants.BASE_PATH + str + ",";
				}
				if (imagesPath.endsWith(",")) {
					imagesPath = imagesPath.substring(0, imagesPath.length() - 1);
				}

				// 把图片的相对路径保存至数据库
				// sqlPath = "/images/"+filename;
				// sqlPath = realUploadPath + "/"+filename;
				productVo.setImagePath(sqlPath);
				Map<String, String> map = new HashMap<String, String>();
				map.put("imagesPath", imagesPath);
				result.setData(map);

				productService.update(productVo);

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

		return result;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/product/delete", method = RequestMethod.POST)
	@ResponseBody
	public Result delete(HttpServletRequest request, @RequestBody Map<String, String> param) {
		Result result = Result.initResult();
		ProductVo filterMask = new ProductVo();
		String productIds = param.get("productIds");
		if (StringUtil.isNullOrEmpty(productIds)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("产品编号不能为空");
			return result;
		}
		String updateUserId = param.get("updateUserId");
		if (StringUtil.isNullOrEmpty(updateUserId)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("用户编号不能为空");
			return result;
		}
		String[] prods = productIds.split(",");
		try {
			for (String str : prods) {
				filterMask.setStatus("20");
				filterMask.setProductId(str);
				filterMask.setCreateUserId(updateUserId);
				productService.update(filterMask);
			}
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

	/**
	 * 删除图片
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/img/delete", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteImg(HttpServletRequest request, @RequestBody Map<String, String> param) {
		Result result = Result.initResult();
		String images = param.get("images");
		String productId = param.get("productId");
		if (StringUtil.isNullOrEmpty(productId)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("产品编号不能为空");
			return result;
		}
		String updateUserId = param.get("updateUserId");
		if (StringUtil.isNullOrEmpty(updateUserId)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("用户编号不能为空");
			return result;
		}
		ProductVo filterMask = new ProductVo();
		filterMask.setProductId(productId);

		ProductVo productVo = productService.getProductBySelective(filterMask);
		String imagePath = productVo.getImagePath();
		if (StringUtil.isNullOrEmpty(imagePath)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("没有图片可删除了");
			return result;
		}

		String[] imgArr = imagePath.split(",");

		List<String> strList = Arrays.asList(imgArr);
		List<String> arrList = new ArrayList<String>(strList);

		String realUploadPath = request.getServletContext().getRealPath("/") + "images";
		if (StringUtil.isNotNullAndNotEmpty(images)) {
			String[] imgNames = images.split(",");
			for (String str : imgNames) {
				if (arrList.contains(str)) {
					arrList.remove(str);
					File file = new File(realUploadPath + "/" + str);
					file.delete();
				}
			}
			if (!arrList.isEmpty()) {
				String sqlPath = arrList.toString();
				sqlPath = sqlPath.substring(1, sqlPath.length() - 1);
				sqlPath.replaceAll(" ", "");
				productVo.setImagePath(sqlPath);
			} else {
				productVo.setImagePath("");
			}

		}
		productVo.setUpdateUserId(updateUserId);

		try {
			productService.update(productVo);

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

	/**
	 * 公众号——产品详情
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/product/info", method = RequestMethod.POST)
	@ResponseBody
	public DataResult info(HttpServletRequest request, @RequestBody ProductVo filterMask) {
		DataResult result = DataResult.initResult();
		if (StringUtil.isNullOrEmpty(filterMask.getProductId())) {
			result.setCode(Constants.FAILURE);
			result.setMsg("产品编号不能为空");
			return result;
		}

		try {

			ProductVo product = productService.getProductBySelective(filterMask);
			AdminUserVo adminUserVo = new AdminUserVo();
			adminUserVo.setId(product.getCreateUserId());
			AdminUserVo adminUser = adminUserService.getAdminUserBySelective(adminUserVo);
			String imgPath = product.getImagePath();
			String[] imgPathArr = {};
			if (StringUtil.isNotNullAndNotEmpty(imgPath)) {
				imgPathArr = imgPath.split(",");
			}
			String imagesPath = "";
			for (String str : imgPathArr) {
				imagesPath += Constants.BASE_PATH + str + ",";
			}
			if (imagesPath.endsWith(",")) {
				imagesPath = imagesPath.substring(0, imagesPath.length() - 1);
			}

			ProductInfoDto productInfoDto = new ProductInfoDto();
			productInfoDto.setProductId(product.getProductId());
			productInfoDto.setProductName(product.getProductName());
			productInfoDto.setAmount(product.getAmount());
			productInfoDto.setProductDesc(product.getProductDesc());
			productInfoDto.setSpec(product.getSpec());
			productInfoDto.setImagePath(imagesPath);
			productInfoDto.setAddress(adminUser.getAddress());
			result.setData(productInfoDto);

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

	/**
	 * 公众号——商品列表
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/product/list", method = RequestMethod.POST)
	@ResponseBody
	public DataResult productList(HttpServletRequest request, @RequestBody Map<String, String> param,
			ProductVo filterMask) {
		PageResult result = PageResult.initResult();

		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		filterMask.setStatus("10");
		filterMask.setPage(pageNum);
		filterMask.setPageSize(pageSize);
		try {
			int total = productService.getProductCount(filterMask);
			List<ProductListDto> products = productService.getProductInfoPage(filterMask);
			for (ProductListDto product : products) {
				String imgPath = product.getImagePath();
				String[] imgPathArr = {};
				if (StringUtil.isNotNullAndNotEmpty(imgPath)) {
					imgPathArr = imgPath.split(",");
				}
				String imagesPath = "";
				for (String str : imgPathArr) {
					imagesPath += Constants.BASE_PATH + str + ",";
				}
				if (imagesPath.endsWith(",")) {
					imagesPath = imagesPath.substring(0, imagesPath.length() - 1);
				}
				product.setImagePath(imagesPath);
			}
			result.setAllCount(total);
			result.setPageNum(pageNum);
			result.setPageSize(pageSize);
			result.setData(products);

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
