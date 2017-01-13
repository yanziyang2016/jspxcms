package com.jspxcms.core.domain;

import com.jspxcms.common.web.Anchor;

public class CommentTemp implements Anchor{
	
	public String title;

	public String url;

	public Boolean newWindow;

	public String color;

	public Boolean strong;

	public Boolean em;

	public String description;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getNewWindow() {
		return newWindow;
	}

	public void setNewWindow(Boolean newWindow) {
		this.newWindow = newWindow;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Boolean getStrong() {
		return strong;
	}

	public void setStrong(Boolean strong) {
		this.strong = strong;
	}

	public Boolean getEm() {
		return em;
	}

	public void setEm(Boolean em) {
		this.em = em;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
