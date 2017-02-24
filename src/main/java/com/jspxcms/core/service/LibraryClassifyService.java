package com.jspxcms.core.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.core.domain.LibraryClassify;

/**
 * OrderService
 * 
 * @author liufang
 * 
 */
public interface LibraryClassifyService {
	public Page<LibraryClassify> findPage( Map<String, String[]> params,
			Pageable pageable);

	public LibraryClassify save(LibraryClassify LibraryClassify);

	public LibraryClassify get(Integer id);

	public void update(LibraryClassify bean);

	public void delete(Integer id);

	
	
	
}
