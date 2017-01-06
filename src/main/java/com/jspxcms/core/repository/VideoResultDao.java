package com.jspxcms.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.VideoResult;

/**
 * OrderDao
 * 
 * @author liufang
 * 
 */
public interface VideoResultDao extends Repository<VideoResult, Integer> , VideoResultDaoPlus {
	public Page<VideoResult> findAll(Specification<VideoResult> spec, Pageable pageable);
	public VideoResult save(VideoResult videoResult);
	public VideoResult findOne(Integer id);
	public void delete(VideoResult bean);
}
