/*
 * Powered By moti technology
 * Web Site: http://moti technology
 * Since 2017 - 2019
 */

package com.zach.gasTrade.service;

import java.util.List;

import com.zach.gasTrade.dto.ProductListDto;
import com.zach.gasTrade.vo.ProductVo;

public interface ProductService{
	/**
     * 总数
     * @param productVo
     * @return
     */
	 public int getProductCount(ProductVo productVo);
	 
	 /**
     * 分页列表
     * @param productVo
     * @return
     */
	 public List<ProductVo> getProductPage(ProductVo productVo);
	 
	 /**
     * 列表
     * @param productVo
     * @return
     */
	 public List<ProductListDto> getProductList(ProductVo productVo);
	 
	 /**
     * 根据条件查询一条信息
     * @param productVo
     * @return
     */
	 public ProductVo getProductBySelective(ProductVo productVo);
	 
	 /**
	 * 保存
	 * @param productVo
	 */
    public int save(ProductVo productVo);
    
    /**
	 * 更新
	 * @param productVo
	 */
    public int update(ProductVo productVo);
    
    /**
	 * 删除
	 * @param productVo
	 */
    public int delete(ProductVo productVo);

	public List<ProductListDto> getProductInfoPage(ProductVo filterMask);
    

}

