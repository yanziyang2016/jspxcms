package com.jspxcms.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.VideoClassify;

/**
 * OrderDao
 * 
 * @author liufang
 * 
 */
public interface VideoClassifyDao extends Repository<VideoClassify, Integer> , VideoClassifyDaoPlus {
	public Page<VideoClassify> findAll(Specification<VideoClassify> spec, Pageable pageable);
	public VideoClassify save(VideoClassify order);
	public VideoClassify findOne(Integer id);
	public void delete(VideoClassify bean);
}
