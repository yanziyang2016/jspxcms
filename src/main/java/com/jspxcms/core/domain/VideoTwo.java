package com.jspxcms.core.domain;

import java.util.List;

import javax.persistence.*;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "cms_v_m")
public class VideoTwo implements java.io.Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private Integer vmid;
	private String aid;//专辑ID
	private String albumPC;	//专辑播放数
	private String albumUrl;//专辑地址
	private String cid;//专辑分类
	private String clarity;	//视频质量1：高清21：超清
	private List<VideoThree> videos;
	private String year;
	private String 	albumTitle;//专辑标题
	private String 	area;//专辑所属区域
	private String 	bigPic;//专辑封面图
	private String 	cname;//分类名称
	private String 	desc;//简介
	private String 	dt;//导演
	private String 	fee;
	private String 	hidden;
	private String 	horBigPic;//横大图
	private String 	horSmallPic;//横小图
	private String 	id;//视频ID
	private String 	isover;// 完结标识 1：完结
	private String 	mA;//主演
	private String 	newcid;
	private String 	nowSet;//当前集数
	private String 	pid;
	private String 	playcount;
	private String 	sc;
	private String 	scategory;
	private String 	score;//打分
	private String 	showtime;//更新时间
	private String 	tip;
	private String 	title;//标题
	private String 	totalSet;//总集数
	private String 	url;
	private String 	verBigPic;//封面图
	private String 	verSmallPic;
	private String 	videoTvType;
	private String 	videoUrl;
	
	@Id
	@Column(name = "f_v_m_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_v_m", pkColumnValue = "cms_v_m", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_v_m")
	public Integer getVmid() {
		return vmid;
	}
	public void setVmid(Integer vmid) {
		this.vmid = vmid;
	}

	@Length(max = 50)
	@Column(name = "aid", length = 50)
	public String getAid() {
		return aid;
	}
	
	public void setAid(String aid) {
		this.aid = aid;
	}
	@Length(max = 50)
	@Column(name = "albumPC", length = 50)
	public String getAlbumPC() {
		return albumPC;
	}
	public void setAlbumPC(String albumPC) {
		this.albumPC = albumPC;
	}
	@Length(max = 640)
	@Column(name = "albumUrl", length = 640)
	public String getAlbumUrl() {
		return albumUrl;
	}
	public void setAlbumUrl(String albumUrl) {
		this.albumUrl = albumUrl;
	}
	@Length(max = 50)
	@Column(name = "cid", length = 50)
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	@Length(max = 50)
	@Column(name = "clarity", length = 50)
	public String getClarity() {
		return clarity;
	}
	public void setClarity(String clarity) {
		this.clarity = clarity;
	}
	
	@Transient
	public List<VideoThree> getVideos() {
		return videos;
	}
	public void setVideos(List<VideoThree> videos) {
		this.videos = videos;
	}
	@Length(max = 50)
	@Column(name = "year", length = 50)
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	@Length(max = 200)
	@Column(name = "albumTitle", length = 200)
	public String getAlbumTitle() {
		return albumTitle;
	}
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	@Length(max = 50)
	@Column(name = "area", length = 50)
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	@Length(max = 500)
	@Column(name = "bigPic", length = 500)
	public String getBigPic() {
		return bigPic;
	}
	public void setBigPic(String bigPic) {
		this.bigPic = bigPic;
	}
	@Length(max = 50)
	@Column(name = "cname", length = 50)
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	@Length(max = 1000)
	@Column(name = "desc1", length = 1000)
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Length(max = 50)
	@Column(name = "dt", length = 50)
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	@Length(max = 50)
	@Column(name = "fee", length = 50)
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	@Length(max = 50)
	@Column(name = "hidden", length = 50)
	public String getHidden() {
		return hidden;
	}
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}
	@Length(max = 500)
	@Column(name = "horBigPic", length = 500)
	public String getHorBigPic() {
		return horBigPic;
	}
	public void setHorBigPic(String horBigPic) {
		this.horBigPic = horBigPic;
	}
	@Length(max = 500)
	@Column(name = "horSmallPic", length = 500)
	public String getHorSmallPic() {
		return horSmallPic;
	}
	public void setHorSmallPic(String horSmallPic) {
		this.horSmallPic = horSmallPic;
	}
	@Length(max = 50)
	@Column(name = "id", length = 50)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Length(max = 50)
	@Column(name = "isover", length = 50)
	public String getIsover() {
		return isover;
	}
	public void setIsover(String isover) {
		this.isover = isover;
	}
	@Length(max = 50)
	@Column(name = "mA", length = 50)
	public String getmA() {
		return mA;
	}
	public void setmA(String mA) {
		this.mA = mA;
	}
	@Length(max = 50)
	@Column(name = "newcid", length = 50)
	public String getNewcid() {
		return newcid;
	}
	public void setNewcid(String newcid) {
		this.newcid = newcid;
	}
	@Length(max = 50)
	@Column(name = "nowSet", length = 50)
	public String getNowSet() {
		return nowSet;
	}
	public void setNowSet(String nowSet) {
		this.nowSet = nowSet;
	}
	@Length(max = 50)
	@Column(name = "pid", length = 50)
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	@Length(max = 50)
	@Column(name = "playcount", length = 50)
	public String getPlaycount() {
		return playcount;
	}
	public void setPlaycount(String playcount) {
		this.playcount = playcount;
	}
	@Length(max = 50)
	@Column(name = "sc", length = 50)
	public String getSc() {
		return sc;
	}
	public void setSc(String sc) {
		this.sc = sc;
	}
	@Length(max = 50)
	@Column(name = "scategory", length = 50)
	public String getScategory() {
		return scategory;
	}
	public void setScategory(String scategory) {
		this.scategory = scategory;
	}
	@Length(max = 50)
	@Column(name = "score", length = 50)
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	@Length(max = 50)
	@Column(name = "showtime", length = 50)
	public String getShowtime() {
		return showtime;
	}
	public void setShowtime(String showtime) {
		this.showtime = showtime;
	}
	@Length(max = 50)
	@Column(name = "tip", length = 50)
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	@Length(max = 200)
	@Column(name = "title", length = 200)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Length(max = 50)
	@Column(name = "totalSet", length = 50)
	public String getTotalSet() {
		return totalSet;
	}
	public void setTotalSet(String totalSet) {
		this.totalSet = totalSet;
	}
	@Length(max = 500)
	@Column(name = "url", length = 500)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Length(max = 500)
	@Column(name = "verBigPic", length = 500)
	public String getVerBigPic() {
		return verBigPic;
	}
	public void setVerBigPic(String verBigPic) {
		this.verBigPic = verBigPic;
	}
	@Length(max = 500)
	@Column(name = "verSmallPic", length = 500)
	public String getVerSmallPic() {
		return verSmallPic;
	}
	public void setVerSmallPic(String verSmallPic) {
		this.verSmallPic = verSmallPic;
	}
	@Length(max = 50)
	@Column(name = "videoTvType", length = 50)
	public String getVideoTvType() {
		return videoTvType;
	}
	public void setVideoTvType(String videoTvType) {
		this.videoTvType = videoTvType;
	}
	@Length(max = 500)
	@Column(name = "videoUrl", length = 500)
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}



}
