package com.jspxcms.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.core.domain.Product;

/**
 * OrderService
 * 
 * @author liufang
 * 
 */
public interface ProductService {
	public Page<Product> findPage( Map<String, String[]> params,
			Pageable pageable);
	

	public Product save(Product Product);

	public Product get(Integer id);

	public void update(Product bean);

	public void delete(Integer id);





	
	
	
}
