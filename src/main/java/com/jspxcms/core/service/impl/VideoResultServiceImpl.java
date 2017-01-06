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
import com.jspxcms.core.domain.VideoResult;
import com.jspxcms.core.repository.VideoResultDao;
import com.jspxcms.core.service.VideoResultService;

/**
 * UserServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class VideoResultServiceImpl implements VideoResultService{
	private static final int SALT_SIZE = 8;

	public Page<VideoResult> findPage(Map<String, String[]> params,
			Pageable pageable) {
		Page<VideoResult> VideoResultPage=dao.findAll(spec( params), pageable);
		
		return VideoResultPage;
	}
	
	private Specification<VideoResult> spec( Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<VideoResult> fsp = SearchFilter.spec(filters, VideoResult.class);
		Specification<VideoResult> sp = new Specification<VideoResult>() {
			public Predicate toPredicate(Root<VideoResult> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
							
				return pred;
			}
		};
		return sp;
	}

	

	

	private VideoResultDao dao;
	@Autowired
	public void setDao(VideoResultDao dao) {
		this.dao = dao;
	}
	@Transactional
	public VideoResult save(VideoResult VideoResult) {
		return dao.save(VideoResult);
	}

	@Override
	public VideoResult get(Integer id) {
		VideoResult VideoResult = dao.findOne(id);
		return VideoResult;
	}

	@Transactional
	public void update(VideoResult bean) {
		dao.save(bean);
		
	}

	@Transactional
	public void delete(Integer id) {
		VideoResult bean = dao.findOne(id);
		dao.delete(bean);
	}

}
