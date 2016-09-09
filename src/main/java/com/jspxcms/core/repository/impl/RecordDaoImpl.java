package com.jspxcms.core.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.jspxcms.core.repository.RecordDaoPlus;

/**
 * RecordDaoImpl
 * 
 * @author liufang
 * 
 */
public class RecordDaoImpl implements RecordDaoPlus {
	

	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	
}
