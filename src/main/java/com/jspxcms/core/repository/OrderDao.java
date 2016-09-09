package com.jspxcms.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.Order;
import com.jspxcms.core.domain.User;

/**
 * OrderDao
 * 
 * @author liufang
 * 
 */
public interface OrderDao extends Repository<Order, Integer> , OrderDaoPlus {
	public Page<Order> findAll(Specification<Order> spec, Pageable pageable);
	public Order save(Order order);
	public Order findOne(Integer id);
	public void delete(Order bean);
	
	@Modifying  
	@Query("update Order bean set bean.logisticsNo=?2,bean.logisticsName=?3,bean.status=?4 where bean.orderNo=?1")
	public void updateOrder(String orderNo, String logisticsNo, String logisticsName, Integer status);
}
