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

/**
 * User
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_product_record")
public class ProductRecord implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	


	private Integer id;
	private Integer userId;//用户ID
	private Integer infoId;//商品id
	private Integer infoPeriod;//商品期数
	private Integer periodNo;//申请序列
	private Date addDate;
	private String userName;//用户名

	@Id
	@Column(name = "f_record_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_product_record", pkColumnValue = "cms_product_record", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_product_record")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	@Column(name = "f_user_id", nullable = false)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Column(name = "f_info_id", nullable = false)
	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}
	@Column(name = "f_info_period", nullable = false)
	public Integer getInfoPeriod() {
		return infoPeriod;
	}

	public void setInfoPeriod(Integer infoPeriod) {
		this.infoPeriod = infoPeriod;
	}
	@Column(name = "f_period_no", nullable = false)
	public Integer getPeriodNo() {
		return periodNo;
	}

	public void setPeriodNo(Integer periodNo) {
		this.periodNo = periodNo;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "f_add_date", length = 19)
	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	
	

	
	

	
	
	
	
	
	


	
}
