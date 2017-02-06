package com.jspxcms.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.VideoTwo;

/**
 * OrderDao
 * 
 * @author liufang
 * 
 */
public interface VideoTwoDao extends Repository<VideoTwo, Integer> , VideoTwoDaoPlus {
	public Page<VideoTwo> findAll(Specification<VideoTwo> spec, Pageable pageable);
	public VideoTwo save(VideoTwo order);
	public VideoTwo findOne(Integer id);
	public void delete(VideoTwo bean);
	@Query(" from VideoTwo bean where bean.aid=?1")
	public List<VideoTwo> getbyAid(String aid);
	@Query(" from VideoTwo bean where bean.title=?1")
	public List<VideoTwo> getbyTitle(String title);

}
