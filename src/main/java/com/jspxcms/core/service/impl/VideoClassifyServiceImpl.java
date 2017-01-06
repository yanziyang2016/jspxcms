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
import com.jspxcms.core.domain.VideoClassify;
import com.jspxcms.core.repository.VideoClassifyDao;
import com.jspxcms.core.service.VideoClassifyService;

/**
 * UserServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class VideoClassifyServiceImpl implements VideoClassifyService{
	private static final int SALT_SIZE = 8;

	public Page<VideoClassify> findPage(Map<String, String[]> params,
			Pageable pageable) {
		Page<VideoClassify> VideoClassifyPage=dao.findAll(spec( params), pageable);
		
		return VideoClassifyPage;
	}
	
	private Specification<VideoClassify> spec( Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<VideoClassify> fsp = SearchFilter.spec(filters, VideoClassify.class);
		Specification<VideoClassify> sp = new Specification<VideoClassify>() {
			public Predicate toPredicate(Root<VideoClassify> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
							
				return pred;
			}
		};
		return sp;
	}

	

	

	private VideoClassifyDao dao;
	@Autowired
	public void setDao(VideoClassifyDao dao) {
		this.dao = dao;
	}
	@Transactional
	public VideoClassify save(VideoClassify VideoClassify) {
		return dao.save(VideoClassify);
	}

	@Override
	public VideoClassify get(Integer id) {
		VideoClassify VideoClassify = dao.findOne(id);
		return VideoClassify;
	}

	@Transactional
	public void update(VideoClassify bean) {
		dao.save(bean);
		
	}

	@Transactional
	public void delete(Integer id) {
		VideoClassify bean = dao.findOne(id);
		dao.delete(bean);
	}

}
