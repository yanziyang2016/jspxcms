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
@Table(name = "cms_order")
public class Order implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	


	private Integer id;
	private String orderNo;//订单号
	private Integer userId;//用户ID
	private Integer infoId;//商品id
	private Integer infoPeriod;//商品期数
	private Integer status;//订单状态
	private Integer periodNo;//商品当期幸运序列
	private String logisticsNo;//物流单号
	private String logisticsName;//物流公司
	private Date orderDate;
	private String userName;//用户名
	private String mobile;//用户电话
	private String userAddress;//用户地址
	private String infoName;//商品名
	private String productPro;//商品性质（实物或虚拟）


	@Id
	@Column(name = "f_order_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_order", pkColumnValue = "cms_order", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_order")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Length(max = 64)
	@Column(name = "f_order_no", length = 64)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	@Length(max = 64)
	@Column(name = "f_logistics_no", length = 64)
	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}
	@Length(max = 64)
	@Column(name = "f_logistics_name", length = 64)
	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}
	@Transient
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Transient
	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	@Transient
	public String getInfoName() {
		return infoName;
	}
	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}
	@Transient
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Transient
	public String getProductPro() {
		return productPro;
	}

	public void setProductPro(String productPro) {
		this.productPro = productPro;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "f_order_date", length = 19)
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Column(name = "f_status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
	


	
}
