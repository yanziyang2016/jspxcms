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

/**
 * cms_user_record
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_user_record")
public class UserRecord implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	


	private Integer id;
	private Integer userId;
	private Integer infoId;
	private Date recordDate;
	private String type;


	@Id
	@Column(name = "f_record_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_user_record", pkColumnValue = "cms_user_record", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_user_record")
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
	@Column(name = "f_info_id", nullable = false)
	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "f_record_date", length = 19)
	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}


	
}
