package com.jspxcms.core.domain;

import java.util.List;

public class VideoInfo {
	
	private String aid;
	private String albumPC;	
	private String albumUrl;
	private String cid;
	private String clarity;	
	private List<VideoDomain> videos;
	private String year;
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getAlbumPC() {
		return albumPC;
	}
	public void setAlbumPC(String albumPC) {
		this.albumPC = albumPC;
	}
	public String getAlbumUrl() {
		return albumUrl;
	}
	public void setAlbumUrl(String albumUrl) {
		this.albumUrl = albumUrl;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getClarity() {
		return clarity;
	}
	public void setClarity(String clarity) {
		this.clarity = clarity;
	}
	public List<VideoDomain> getVideos() {
		return videos;
	}
	public void setVideos(List<VideoDomain> videos) {
		this.videos = videos;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	
	

}
