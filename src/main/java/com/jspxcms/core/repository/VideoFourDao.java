package com.jspxcms.core.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.VideoFour;

/**
 * OrderDao
 * 
 * @author liufang
 * 
 */
public interface VideoFourDao extends Repository<VideoFour, Integer> , VideoFourDaoPlus {
	public Page<VideoFour> findAll(Specification<VideoFour> spec, Pageable pageable);
	public VideoFour save(VideoFour order);
	public VideoFour findOne(Integer id);
	public void delete(VideoFour bean);
	@Query(" from VideoFour bean where bean.vid=?1")
	public VideoFour getbyvid(String vid);
	@Query(" from VideoFour bean where bean.aid=?1")
	public List<VideoFour> findAllByAid(String aid);
}
