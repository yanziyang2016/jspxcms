package com.jspxcms.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;

/**
 * User
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_user_status")
public class UserStatus implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	


	private Integer id;
	private Integer userId;//用户ID
	private Integer status;//状态
	private String macAddress;//MAC地址
	private Date lastDate;
	private String userName;//用户名


	@Id
	@Column(name = "f_user_status_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_user_status", pkColumnValue = "cms_user_status", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_user_status")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	@Column(name = "f_user_id", nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	

	@Length(max = 640)
	@Column(name = "f_mac_address", length = 640)
	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	@Length(max = 64)
	@Column(name = "f_user_name", length = 64)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "f_last_date", length = 19)
	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	@Column(name = "f_status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
	


	
}
