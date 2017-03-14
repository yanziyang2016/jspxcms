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
@Table(name = "cms_product_classify")
public class ProductClassify implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	
	


	private Integer id;
	private String classifyName;//分类名称
	private Integer beforeClassifyId;//上级分类id
	private Integer sourceId;//分类等级
	private String beforeClassifyName;//上级分类名称


	@Id
	@Column(name = "f_classify_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_product_classify", pkColumnValue = "cms_product_classify", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_product_classify")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "f_source_id", nullable = false)
	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	@Length(max = 64)
	@Column(name = "f_before_classify_name", length = 64)
	public String getBeforeClassifyName() {
		return beforeClassifyName;
	}

	public void setBeforeClassifyName(String beforeClassifyName) {
		this.beforeClassifyName = beforeClassifyName;
	}
	@Length(max = 64)
	@Column(name = "f_classify_name", length = 64)
	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}
	@Column(name = "f_before_classify_id")
	public Integer getBeforeClassifyId() {
		return beforeClassifyId;
	}

	public void setBeforeClassifyId(Integer beforeClassifyId) {
		this.beforeClassifyId = beforeClassifyId;
	}
	
	


	
}
