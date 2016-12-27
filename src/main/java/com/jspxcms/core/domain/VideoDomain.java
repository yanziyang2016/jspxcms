package com.jspxcms.core.domain;

import java.util.List;

public class VideoDomain {
	
	private String domain;
	private List<VideoUrl> urls;
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public List<VideoUrl> getUrls() {
		return urls;
	}
	public void setUrls(List<VideoUrl> urls) {
		this.urls = urls;
	}
	

}
