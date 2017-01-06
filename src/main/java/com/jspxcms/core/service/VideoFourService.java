package com.jspxcms.core.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.core.domain.VideoFour;

/**
 * OrderService
 * 
 * @author liufang
 * 
 */
public interface VideoFourService {
	public Page<VideoFour> findPage( Map<String, String[]> params,
			Pageable pageable);
	

	public VideoFour save(VideoFour VideoFour);

	public VideoFour get(Integer id);

	public void update(VideoFour bean);

	public void delete(Integer id);


	public VideoFour getbyvid(String vid);

	
	
	
}
