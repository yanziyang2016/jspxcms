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
import com.jspxcms.core.domain.ProductClassify;
import com.jspxcms.core.repository.ProductClassifyDao;
import com.jspxcms.core.service.ProductClassifyService;

/**
 * UserServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class ProductClassifyServiceImpl implements ProductClassifyService{
	private static final int SALT_SIZE = 8;

	public Page<ProductClassify> findPage(Map<String, String[]> params,
			Pageable pageable) {
		Page<ProductClassify> ProductClassifyPage=dao.findAll(spec( params), pageable);
		
		return ProductClassifyPage;
	}
	
	private Specification<ProductClassify> spec( Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<ProductClassify> fsp = SearchFilter.spec(filters, ProductClassify.class);
		Specification<ProductClassify> sp = new Specification<ProductClassify>() {
			public Predicate toPredicate(Root<ProductClassify> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
							
				return pred;
			}
		};
		return sp;
	}

	

	

	private ProductClassifyDao dao;
	@Autowired
	public void setDao(ProductClassifyDao dao) {
		this.dao = dao;
	}
	@Transactional
	public ProductClassify save(ProductClassify ProductClassify) {
		return dao.save(ProductClassify);
	}

	@Override
	public ProductClassify get(Integer id) {
		ProductClassify ProductClassify = dao.findOne(id);
		return ProductClassify;
	}

	@Transactional
	public void update(ProductClassify bean) {
		dao.save(bean);
		
	}

	@Transactional
	public void delete(Integer id) {
		ProductClassify bean = dao.findOne(id);
		dao.delete(bean);
	}

}
