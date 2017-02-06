package com.jspxcms.core.service.impl;

import java.util.Collection;
import java.util.List;
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
import com.jspxcms.core.domain.VideoTwo;
import com.jspxcms.core.repository.VideoTwoDao;
import com.jspxcms.core.service.VideoTwoService;

/**
 * UserServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class VideoTwoServiceImpl implements VideoTwoService{
	private static final int SALT_SIZE = 8;

	public Page<VideoTwo> findPage(Map<String, String[]> params,
			Pageable pageable) {
		Page<VideoTwo> VideoTwoPage=dao.findAll(spec( params), pageable);
		
		return VideoTwoPage;
	}
	
	private Specification<VideoTwo> spec( Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<VideoTwo> fsp = SearchFilter.spec(filters, VideoTwo.class);
		Specification<VideoTwo> sp = new Specification<VideoTwo>() {
			public Predicate toPredicate(Root<VideoTwo> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
							
				return pred;
			}
		};
		return sp;
	}

	

	

	private VideoTwoDao dao;
	@Autowired
	public void setDao(VideoTwoDao dao) {
		this.dao = dao;
	}
	@Transactional
	public VideoTwo save(VideoTwo VideoTwo) {
		return dao.save(VideoTwo);
	}

	@Override
	public VideoTwo get(Integer id) {
		VideoTwo VideoTwo = dao.findOne(id);
		return VideoTwo;
	}

	@Transactional
	public void update(VideoTwo bean) {
		dao.save(bean);
		
	}

	@Transactional
	public void delete(Integer id) {
		VideoTwo bean = dao.findOne(id);
		dao.delete(bean);
	}

	@Override
	public List<VideoTwo> getbyAid(String aid) {
		return  dao.getbyAid(aid);
	}



}
