package com.jspxcms.core.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.core.domain.ProductClassify;

/**
 * OrderService
 * 
 * @author liufang
 * 
 */
public interface ProductClassifyService {
	public Page<ProductClassify> findPage( Map<String, String[]> params,
			Pageable pageable);

	public ProductClassify save(ProductClassify ProductClassify);

	public ProductClassify get(Integer id);

	public void update(ProductClassify bean);

	public void delete(Integer id);

	
	
	
}
