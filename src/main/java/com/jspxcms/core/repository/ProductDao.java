package com.jspxcms.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.Product;

/**
 * OrderDao
 * 
 * @author liufang
 * 
 */
public interface ProductDao extends Repository<Product, Integer> , ProductDaoPlus {
	public Page<Product> findAll(Specification<Product> spec, Pageable pageable);
	public Product save(Product order);
	public Product findOne(Integer id);
	public void delete(Product bean);

}
