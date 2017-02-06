package com.jspxcms.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.jspxcms.core.domain.VideoTwo;

/**
 * OrderService
 * 
 * @author liufang
 * 
 */
public interface VideoTwoService {
	public Page<VideoTwo> findPage( Map<String, String[]> params,
			Pageable pageable);
	

	public VideoTwo save(VideoTwo VideoTwo);

	public VideoTwo get(Integer id);

	public void update(VideoTwo bean);

	public void delete(Integer id);


	public List<VideoTwo> getbyAid(String aid);


	public List<VideoTwo> getbyTitle(String title);




	
	
	
}
