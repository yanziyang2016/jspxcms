package com.jspxcms.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.UserRecord;
import com.jspxcms.core.repository.UserRecordDao;
import com.jspxcms.core.service.UserRecordService;

/**
 * UserServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class UserRecordServiceImpl implements UserRecordService{
	private static final int SALT_SIZE = 8;
	private UserRecordDao dao;
	@Autowired
	public void setDao(UserRecordDao dao) {
		this.dao = dao;
	}

	@Transactional
	public UserRecord save(UserRecord userRecord) {
		return dao.save(userRecord);
	}

	@Override
	public int findRecordByUserAndInfo(Integer userId, Integer infoId) {
		return dao.findRecordByUserAndInfo(userId,infoId);
	}

	@Override
	public int findRecordByUserAndVideo(Integer userId, Integer infoId, String type) {
		return dao.findRecordByUserAndVideo(userId,infoId,type);
	}

	

}
