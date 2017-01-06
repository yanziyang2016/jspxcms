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
 * User
 * 
 * @author liufang
 * 
 */
@Entity
@Table(name = "cms_video_result")
public class VideoResult implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	
	


	private Integer id;
	private Date importDate;//导入日期
	private Integer result;//结果
	private Integer count;//数目



	@Id
	@Column(name = "f_video_result_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_video_result", pkColumnValue = "cms_video_result", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_video_result")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "import_date", length = 19)
	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	


	
}
