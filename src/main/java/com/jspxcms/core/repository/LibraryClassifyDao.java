package com.jspxcms.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.LibraryClassify;

/**
 * OrderDao
 * 
 * @author liufang
 * 
 */
public interface LibraryClassifyDao extends Repository<LibraryClassify, Integer> , LibraryClassifyDaoPlus {
	public Page<LibraryClassify> findAll(Specification<LibraryClassify> spec, Pageable pageable);
	public LibraryClassify save(LibraryClassify order);
	public LibraryClassify findOne(Integer id);
	public void delete(LibraryClassify bean);
}
