package com.jspxcms.core.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.UserRecord;

/**
 * UserRecordDao
 * 
 * 
 */
public interface UserRecordDao extends Repository<UserRecord, Integer> , UserRecordDaoPlus {
	public UserRecord save(UserRecord userRecord);
	

	
	@Query("select count(*) from UserRecord where userId=?1 and infoId=?2 and recordDate > CURDATE()")
	public int findRecordByUserAndInfo(Integer userId, Integer infoId);


	@Query("select count(*) from UserRecord where userId=?1 and infoId=?2 and type=?3")
	public int findRecordByUserAndVideo(Integer userId, Integer infoId, String type);
	

}
