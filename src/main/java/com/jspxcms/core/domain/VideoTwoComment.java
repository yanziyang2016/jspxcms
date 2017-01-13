package com.jspxcms.core.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.jspxcms.common.web.Anchor;

/**
 * InfoComment
 * 
 * @author liufang
 * 
 */
public class VideoTwoComment extends Comment {
	private static final long serialVersionUID = 1L;
	@Override
	public Anchor getAnchor() {
		return info;
	}
	private CommentTemp info;
	public CommentTemp getInfo() {
		return info;
	}
	public void setInfo(CommentTemp info) {
		this.info = info;
	}
	
	
	
	
	

	
}
