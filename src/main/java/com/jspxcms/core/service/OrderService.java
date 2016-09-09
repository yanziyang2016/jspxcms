package com.jspxcms.core.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.core.domain.Order;
import com.jspxcms.core.domain.User;

/**
 * OrderService
 * 
 * @author liufang
 * 
 */
public interface OrderService {
	public Page<Order> findPage( Map<String, String[]> params,
			Pageable pageable);
	
	public Page<Order> findPage(Integer id,	Pageable pageable);
	public Page<Order> findPage2(Integer integer, Pageable pageable);

	public Order save(Order order);

	public Order get(Integer id);

	public void update(Order bean);

	public void delete(Integer id);

	
	
	
}
