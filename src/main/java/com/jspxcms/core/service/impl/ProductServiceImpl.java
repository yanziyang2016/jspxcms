package com.jspxcms.core.service.impl;

import java.util.Collection;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.orm.SearchFilter;
import com.jspxcms.core.domain.Product;
import com.jspxcms.core.repository.ProductDao;
import com.jspxcms.core.service.ProductService;

/**
 * UserServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService{
	private static final int SALT_SIZE = 8;

	public Page<Product> findPage(Map<String, String[]> params,
			Pageable pageable) {
		Page<Product> ProductPage=dao.findAll(spec( params), pageable);
		
		return ProductPage;
	}
	
	private Specification<Product> spec( Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<Product> fsp = SearchFilter.spec(filters, Product.class);
		Specification<Product> sp = new Specification<Product>() {
			public Predicate toPredicate(Root<Product> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
							
				return pred;
			}
		};
		return sp;
	}

	

	

	private ProductDao dao;
	@Autowired
	public void setDao(ProductDao dao) {
		this.dao = dao;
	}
	@Transactional
	public Product save(Product Product) {
		return dao.save(Product);
	}

	@Override
	public Product get(Integer id) {
		Product Product = dao.findOne(id);
		return Product;
	}

	@Transactional
	public void update(Product bean) {
		dao.save(bean);
		
	}

	@Transactional
	public void delete(Integer id) {
		Product bean = dao.findOne(id);
		dao.delete(bean);
	}

	@Transactional
	public void upOrDown(Integer status, Integer id) {
		dao.upOrDown(status,id);
		
	}




}
