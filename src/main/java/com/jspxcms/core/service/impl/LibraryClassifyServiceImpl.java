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
import com.jspxcms.core.domain.LibraryClassify;
import com.jspxcms.core.repository.LibraryClassifyDao;
import com.jspxcms.core.service.LibraryClassifyService;

/**
 * UserServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class LibraryClassifyServiceImpl implements LibraryClassifyService{
	private static final int SALT_SIZE = 8;

	public Page<LibraryClassify> findPage(Map<String, String[]> params,
			Pageable pageable) {
		Page<LibraryClassify> LibraryClassifyPage=dao.findAll(spec( params), pageable);
		
		return LibraryClassifyPage;
	}
	
	private Specification<LibraryClassify> spec( Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<LibraryClassify> fsp = SearchFilter.spec(filters, LibraryClassify.class);
		Specification<LibraryClassify> sp = new Specification<LibraryClassify>() {
			public Predicate toPredicate(Root<LibraryClassify> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
							
				return pred;
			}
		};
		return sp;
	}

	

	

	private LibraryClassifyDao dao;
	@Autowired
	public void setDao(LibraryClassifyDao dao) {
		this.dao = dao;
	}
	@Transactional
	public LibraryClassify save(LibraryClassify LibraryClassify) {
		return dao.save(LibraryClassify);
	}

	@Override
	public LibraryClassify get(Integer id) {
		LibraryClassify LibraryClassify = dao.findOne(id);
		return LibraryClassify;
	}

	@Transactional
	public void update(LibraryClassify bean) {
		dao.save(bean);
		
	}

	@Transactional
	public void delete(Integer id) {
		LibraryClassify bean = dao.findOne(id);
		dao.delete(bean);
	}

}
