package com.jspxcms.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "cms_v_u")
public class VideoFour implements java.io.Serializable  {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String aid;
	private String length;
	private String name;
	private String no;// 集数序号
	private String pubtime;		//发布时间	
	private String vid;//视频ID
	private String videoPic;
	private String url;//播放地址
	
	@Id
	@Column(name = "f_v_u_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_v_u", pkColumnValue = "cms_v_u", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_v_u")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	@Column(name = "length", length = 50)
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	@Length(max = 50)
	@Column(name = "name", length = 50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Length(max = 50)
	@Column(name = "no", length = 50)
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Length(max = 50)
	@Column(name = "pubtime", length = 50)
	public String getPubtime() {
		return pubtime;
	}
	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}
	@Length(max = 50)
	@Column(name = "vid", length = 50)
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	@Length(max = 500)
	@Column(name = "videoPic", length = 500)
	public String getVideoPic() {
		return videoPic;
	}
	public void setVideoPic(String videoPic) {
		this.videoPic = videoPic;
	}
	@Length(max = 500)
	@Column(name = "url", length = 500)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}


	

}
