package com.jspxcms.core.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.core.domain.VideoClassify;
import com.jspxcms.core.domain.VideoResult;

/**
 * OrderService
 * 
 * @author liufang
 * 
 */
public interface VideoResultService {
	public Page<VideoResult> findPage( Map<String, String[]> params,
			Pageable pageable);
	
	public VideoResult save(VideoResult videoResult);

	public VideoResult get(Integer id);

	public void update(VideoResult bean);

	public void delete(Integer id);

	
	
	
}
