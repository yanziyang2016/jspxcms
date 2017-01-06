package com.jspxcms.core.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.UserStatus;

/**
 * OrderDao
 * 
 * @author liufang
 * 
 */
public interface UserStatusDao extends Repository<UserStatus, Integer> , UserStatusDaoPlus {
	public UserStatus save(UserStatus order);
	public UserStatus findOne(Integer id);
	public void delete(UserStatus bean);
	@Query(" from UserStatus bean where bean.macAddress=?1")
	public UserStatus getByMacAddress(String macAddress);
	@Query(" from UserStatus bean where bean.userId=?1")
	public List<UserStatus> getListById(Integer id);
	
//	@Modifying  
//	@Query("update Order bean set bean.logisticsNo=?2,bean.logisticsName=?3,bean.status=?4 where bean.orderNo=?1")
//	public void updateOrder(String orderNo, String logisticsNo, String logisticsName, Integer status);
}
