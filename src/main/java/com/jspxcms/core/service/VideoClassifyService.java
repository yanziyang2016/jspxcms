package com.jspxcms.core.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.core.domain.Order;
import com.jspxcms.core.domain.VideoClassify;

/**
 * OrderService
 * 
 * @author liufang
 * 
 */
public interface VideoClassifyService {
	public Page<VideoClassify> findPage( Map<String, String[]> params,
			Pageable pageable);
	
//	public Page<VideoClassify> findPage(Integer id,	Pageable pageable);
//	public Page<VideoClassify> findPage2(Integer integer, Pageable pageable);

	public VideoClassify save(VideoClassify videoClassify);

	public VideoClassify get(Integer id);

	public void update(VideoClassify bean);

	public void delete(Integer id);

	
	
	
}
