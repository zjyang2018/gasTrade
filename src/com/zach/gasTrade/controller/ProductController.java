/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
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
import io.swagger.annotations.ApiOperation;
import sun.misc.BASE64Decoder;

@Api(tags = "产品相关api")
@Controller
public class ProductController extends CommonController {
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	private ProductService productService;

	@Autowired
	private AdminUserService adminUserService;

	/**
	 * 分页列表 + 搜索
	 * 
	 * @param request
	 * @param filterMask
	 * @return PageResult
	 */
	@ApiOperation(value = "产品管理列表", notes = "请求参数说明||pageNum:页码,pageSize:每页条数,productName:搜索参数（产品名称）\\n返回参数字段说明:\\n")
	@RequestMapping(value = "/product/query_page", method = RequestMethod.POST)
	@ResponseBody
	public PageResult getPageData(HttpServletRequest request, @RequestBody Map<String, String> param,
			ProductVo filterMask) {
		PageResult result = PageResult.initResult();
		logger.info("产品管理列表接口参数:" + JSON.toJSONString(param));
		int pageNum = Integer.valueOf(param.get(Constants.PAGE_NUM));
		int pageSize = Integer.valueOf(param.get(Constants.PAGE_SIZE));
		String likeName = param.get("productName");
		if (StringUtil.isNotNullAndNotEmpty(likeName)) {
			String productName = likeName.trim();
			filterMask.setProductName(productName);
		}

		filterMask.setPage(pageNum);
		filterMask.setPageSize(pageSize);
		try {
			int total = productService.getProductCount(filterMask);
			List<ProductVo> list = productService.getProductPage(filterMask);

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
	public Result save(HttpServletRequest request, @RequestBody ProductVo filterMask) {
		Result result = Result.initResult();
		try {
			if (StringUtil.isNull(filterMask.getProductName())) {
				throw new RuntimeException("产品名称不能为空");
			}
			if (StringUtil.isNull(filterMask.getSpec())) {
				throw new RuntimeException("产品规格不能为空");
			}
			if (filterMask.getAmount() == null) {
				throw new RuntimeException("产品金额不能为空");
			}
			String imagesPath = filterMask.getImagePath();
			if (StringUtil.isNotNullAndNotEmpty(imagesPath)) {
				String[] images = imagesPath.split(",");
				if (images.length > 3) {
					// 去掉空,取最后的3张图片
					List<String> list = new ArrayList<String>();
					for (int index = 0; index < images.length; index++) {
						if (StringUtil.isNotNullAndNotEmpty(images[index])) {
							list.add(images[index]);
						}
					}
					if (list.size() > 3) {
						imagesPath = list.get(1) + "," + list.get(2) + "," + list.get(2);
					}
				}
				filterMask.setImagePath(imagesPath);
			}

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
	public Result edit(HttpServletRequest request, @RequestBody ProductVo filterMask) {
		DataResult result = DataResult.initResult();
		if (StringUtil.isNullOrEmpty(filterMask.getProductId())) {
			result.setCode(Constants.FAILURE);
			result.setMsg("产品编号不能为空");
			return result;
		}
		try {

			ProductVo param = new ProductVo();
			param.setProductId(filterMask.getProductId());
			ProductVo productVo = productService.getProductBySelective(param);
			if (productVo == null) {
				result.setCode(Constants.FAILURE);
				result.setMsg("该产品不存在," + filterMask.getProductId());
				return result;
			}
			productVo.setUpdateUserId(filterMask.getUpdateUserId());
			if (StringUtil.isNotNullAndNotEmpty(filterMask.getProductName())) {
				productVo.setProductName(filterMask.getProductName());
			}
			if (StringUtil.isNotNullAndNotEmpty(filterMask.getSpec())) {
				productVo.setSpec(filterMask.getSpec());
			}
			productVo.setAmount(filterMask.getAmount());
			productVo.setStockQty(filterMask.getStockQty());
			if (StringUtil.isNotNullAndNotEmpty(filterMask.getProductDesc())) {
				productVo.setProductDesc(filterMask.getProductDesc());
			}

			String imagesPath = filterMask.getImagePath();
			if (StringUtil.isNotNullAndNotEmpty(imagesPath)) {
				String[] images = imagesPath.split(",");
				if (images.length > 3) {
					// 去掉空,取最后的3张图片
					List<String> list = new ArrayList<String>();
					for (int index = 0; index < images.length; index++) {
						if (StringUtil.isNotNullAndNotEmpty(images[index])) {
							list.add(images[index]);
						}
					}
					if (list.size() > 3) {
						imagesPath = list.get(1) + "," + list.get(2) + "," + list.get(2);
					}
				}
				productVo.setImagePath(imagesPath);
			}

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
		String productIds = param.get("productIds");
		if (StringUtil.isNullOrEmpty(productIds)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("产品编号不能为空");
			return result;
		}
		String updateUserId = param.get("updateUserId");
		String[] prods = productIds.split(";");
		try {
			for (String productId : prods) {
				if (StringUtil.isNull(productId)) {
					continue;
				}
				ProductVo productParam = new ProductVo();
				productParam.setProductId(productId);
				ProductVo product = productService.getProductBySelective(productParam);
				if (product == null) {
					continue;
				}
				// 处理删除图片
				// String imagePath = product.getImagePath();
				// if (StringUtil.isNotNullAndNotEmpty(imagePath)) {
				// String realBasePath = request.getServletContext().getRealPath("/") +
				// "images/";
				// String[] images = imagePath.split(";");
				// for (String image : images) {
				// if (StringUtil.isNull(image)) {
				// continue;
				// }
				// try {
				// int index = image.lastIndexOf("/");
				// String imageName = image.substring(index + 1, image.length());
				// File imageFile = new File(realBasePath + imageName);
				// if (imageFile.exists()) {
				// imageFile.delete();
				// }
				// } catch (Exception e) {
				// logger.info("删除图片文件失败," + image);
				// }
				// }
				// }

				product.setStatus("20");
				if (StringUtil.isNotNullAndNotEmpty(updateUserId)) {
					product.setUpdateUserId(updateUserId);
				}
				productService.update(product);
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
	 * 产品图片上传
	 * 
	 * @param request
	 * @param filterMask
	 * @return Result
	 */
	@RequestMapping(value = "/product/upload/image", method = RequestMethod.POST)
	@ResponseBody
	public DataResult uploadProductImage(HttpServletRequest request, @RequestBody Map<String, String> filterMask) {
		DataResult result = DataResult.initResult();
		String image = filterMask.get("image");
		String imageSuffixName = filterMask.get("imageSuffixName");
		if (StringUtil.isNull(image)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("图片为空");
			return result;
		}
		if (StringUtil.isNull(imageSuffixName)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("图片后缀名为空");
			return result;
		}
		try {
			String realBasePath = request.getServletContext().getRealPath("/") + "images/";
			logger.info("上传图片获取当前图片路径:" + realBasePath);

			String uuid = SerialGenerator.getUUID();
			// 得到新 文件名
			String fileName = uuid + "." + imageSuffixName;

			File imageFile = new File(realBasePath + fileName);// 创建文件对象
			if (!imageFile.exists()) {// 文件名不存在 则新建文件，并将文件复制到新建文件中
				imageFile.createNewFile();
			}
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				// Base64解码
				byte[] b = decoder.decodeBuffer(image);
				for (int i = 0; i < b.length; ++i) {
					// 调整异常数据
					if (b[i] < 0) {
						b[i] += 256;
					}
				}
				OutputStream out = new FileOutputStream(imageFile);
				out.write(b);
				out.flush();
				out.close();
			} catch (Exception e) {
				logger.error("写入图片文件异常", e);
			}
			result.setData(Constants.BASE_PATH + fileName);
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
	@RequestMapping(value = "/weixin/product/info", method = RequestMethod.GET)
	@ResponseBody
	public DataResult info(HttpServletRequest request, ProductVo filterMask) {
		DataResult result = DataResult.initResult();
		String productId = request.getParameter("productId");
		if (StringUtil.isNullOrEmpty(productId)) {
			result.setCode(Constants.FAILURE);
			result.setMsg("产品编号不能为空");
			return result;
		}

		try {
			filterMask.setProductId(productId);
			ProductVo product = productService.getProductBySelective(filterMask);
			if (product == null) {
				throw new RuntimeException("该产品不存在," + productId);
			}
			ProductInfoDto productInfoDto = new ProductInfoDto();
			productInfoDto.setProductId(product.getProductId());
			productInfoDto.setProductName(product.getProductName());
			productInfoDto.setAmount(product.getAmount());
			productInfoDto.setProductDesc(product.getProductDesc());
			productInfoDto.setSpec(product.getSpec());
			productInfoDto.setImagePath(product.getImagePath());
			AdminUserVo adminUserVo = new AdminUserVo();
			adminUserVo.setId(product.getCreateUserId());
			productInfoDto.setAddress(product.getBusinessAddress());
			// AdminUserVo adminUser =
			// adminUserService.getAdminUserBySelective(adminUserVo);
			// if (adminUser != null) {
			// productInfoDto.setAddress(adminUser.getAddress());
			// }
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
	@RequestMapping(value = "/weixin/product/list", method = RequestMethod.POST)
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
