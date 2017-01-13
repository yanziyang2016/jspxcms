package com.jspxcms.core.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.core.domain.Order;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.domain.UserRecord;

/**
 * OrderService
 * 
 * @author liufang
 * 
 */
public interface UserRecordService {
	
	public UserRecord save(UserRecord userRecord);

	public int findRecordByUserAndInfo(Integer userId, Integer infoId);

	public int findRecordByUserAndVideo(Integer userId, Integer infoId, String type);

	
	
	
}
