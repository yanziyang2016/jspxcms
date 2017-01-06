package com.jspxcms.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.core.domain.UserStatus;
import com.jspxcms.core.repository.UserStatusDao;
import com.jspxcms.core.service.UserStatusService;

/**
 * UserStatusServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class UserStatusServiceImpl implements UserStatusService{
	private static final int SALT_SIZE = 8;



	private UserStatusDao dao;
	@Autowired
	public void setDao(UserStatusDao dao) {
		this.dao = dao;
	}
	@Transactional
	public UserStatus save(UserStatus userStatus) {
		return dao.save(userStatus);
	}

	@Override
	public UserStatus get(Integer id) {
		UserStatus userStatus = dao.findOne(id);
		return userStatus;
	}

	@Transactional
	public void update(UserStatus bean) {
		dao.save(bean);
		
	}

	@Transactional
	public void delete(Integer id) {
		UserStatus bean = dao.findOne(id);
		dao.delete(bean);
	}
	@Override
	public UserStatus getByMacAddress(String macAddress) {
		return dao.getByMacAddress(macAddress);
	}
	@Override
	public List<UserStatus> getListById(Integer id) {
		return dao.getListById(id);
	}

}
