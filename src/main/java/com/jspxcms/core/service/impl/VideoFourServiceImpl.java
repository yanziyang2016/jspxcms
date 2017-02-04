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
import com.jspxcms.core.domain.VideoFour;
import com.jspxcms.core.repository.VideoFourDao;
import com.jspxcms.core.service.VideoFourService;

/**
 * UserServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class VideoFourServiceImpl implements VideoFourService{
	private static final int SALT_SIZE = 8;

	public Page<VideoFour> findPage(Map<String, String[]> params,
			Pageable pageable) {
		Page<VideoFour> VideoFourPage=dao.findAll(spec( params), pageable);
		
		return VideoFourPage;
	}
	
	private Specification<VideoFour> spec( Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<VideoFour> fsp = SearchFilter.spec(filters, VideoFour.class);
		Specification<VideoFour> sp = new Specification<VideoFour>() {
			public Predicate toPredicate(Root<VideoFour> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
							
				return pred;
			}
		};
		return sp;
	}

	

	

	private VideoFourDao dao;
	@Autowired
	public void setDao(VideoFourDao dao) {
		this.dao = dao;
	}
	@Transactional
	public VideoFour save(VideoFour VideoFour) {
		return dao.save(VideoFour);
	}

	@Override
	public VideoFour get(Integer id) {
		VideoFour VideoFour = dao.findOne(id);
		return VideoFour;
	}

	@Transactional
	public void update(VideoFour bean) {
		dao.save(bean);
		
	}

	@Transactional
	public void delete(Integer id) {
		VideoFour bean = dao.findOne(id);
		dao.delete(bean);
	}

	@Override
	public List<VideoFour> getbyvid(String vid) {
		return dao.getbyvid(vid);
	}

	@Override
	public List<VideoFour> findAllByAid(String aid) {
		return dao.findAllByAid(aid);
	}

}
