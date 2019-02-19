/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.seq.SerialGenerator;
import com.zach.gasTrade.dao.ProductDao;
import com.zach.gasTrade.service.ProductService;
import com.zach.gasTrade.vo.ProductVo;


@Service("productService")
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductDao productDao;
	
	 /**
     * 总数
     * @param productVo
     * @return
     */
    public int getProductCount(ProductVo productVo){
    	return productDao.getProductCount(productVo);
    }
    
    /**
     * 分页列表
     * @param productVo
     * @return
     */
	 public List<ProductVo> getProductPage(ProductVo productVo){
		 return productDao.getProductPage(productVo);
	 }

    /**
     * 列表
     * @param productVo
     * @return
     */
    public List<ProductVo> getProductList(ProductVo productVo){
    	return productDao.getProductList(productVo);
    }
    
    /**
     * 根据条件查询一条信息
     * @param productVo
     * @return
     */
	 public ProductVo getProductBySelective(ProductVo productVo){
		 return productDao.getProductBySelective(productVo);
	 }
	
    /**
	 * 保存
	 * @param productVo
	 */
    public int save(ProductVo productVo){
    	String product_id = SerialGenerator.getSerialNum();
    	Date nowTime = new Date();
    	productVo.setProductId(product_id);
    	productVo.setStatus("10");
    	productVo.setCreateTime(nowTime);
    	productVo.setUpdateTime(nowTime);
    	productVo.setUpdateUserId(productVo.getCreateUserId());
	
    	return productDao.save(productVo);
    }
    
    /**
	 * 更新
	 * @param productVo
	 */
    public int update(ProductVo productVo){
    	Date nowTime = new Date();
    	productVo.setUpdateTime(nowTime);
    	
    	return productDao.update(productVo);
    }
    
    /**
	 * 删除
	 * @param productVo
	 */
    public int delete(ProductVo productVo){
    	return productDao.delete(productVo);
    }
	
	
}

