package com.jspxcms.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.ProductClassify;

/**
 * OrderDao
 * 
 * @author liufang
 * 
 */
public interface ProductClassifyDao extends Repository<ProductClassify, Integer> , ProductClassifyDaoPlus {
	public Page<ProductClassify> findAll(Specification<ProductClassify> spec, Pageable pageable);
	public ProductClassify save(ProductClassify order);
	public ProductClassify findOne(Integer id);
	public void delete(ProductClassify bean);
}
