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
@Table(name = "cms_product")
public class Product implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	
	


	private Integer id;
	private String title;//名称
	private Date publishDate;	
	private double price;	//供货价格
	private double marketPrice;	//销售价格
	private String smallImage;	//标题图
	private String largeImage;	//正文图
	private Integer stock;//商品库存
	private Integer productpro;	//商品性质 1:虚拟商品；2：实物商品
	private Integer periodCount;	//每期申请数
	private String introduce;	//商品介绍
	private String specification;	//规格参数
	private String packingList;	//包装清单
	private String services;	//售后服务
	private Integer oneClassifyId;//一级分类id
	private Integer twoClassifyId;//二级分类id
	private Integer status;	//商品状态 0：下架；1：上架
	private String oneClassifyName;//一级分类
	private String twoClassifyName;//二级分类
	private String statusName;//状态名
	private String productproName;//商品性质分类


	@Id
	@Column(name = "f_product_id", unique = true, nullable = false)
	@TableGenerator(name = "tg_cms_product", pkColumnValue = "cms_product", table = "t_id_table", pkColumnName = "f_table", valueColumnName = "f_id_value", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tg_cms_product")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Length(max = 64)
	@Column(name = "title", length = 64)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "publishDate", nullable = false, length = 19)
	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	@Column(name = "price", nullable = false)
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Column(name = "marketPrice", nullable = false)
	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	@Length(max = 255)
	@Column(name = "smallImage", length = 255)
	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	@Length(max = 255)
	@Column(name = "largeImage", length = 255)
	public String getLargeImage() {
		return largeImage;
	}

	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}
	@Column(name = "stock", nullable = false)
	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	@Column(name = "productpro", nullable = false)
	public Integer getProductpro() {
		return productpro;
	}

	public void setProductpro(Integer productpro) {
		this.productpro = productpro;
	}
	@Column(name = "periodCount", nullable = false)
	public Integer getPeriodCount() {
		return periodCount;
	}

	public void setPeriodCount(Integer periodCount) {
		this.periodCount = periodCount;
	}
	@Length(max = 16777216)
	@Column(name = "introduce", length = 16777216)
	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	@Length(max = 16777216)
	@Column(name = "specification", length = 16777216)
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
	@Length(max = 16777216)
	@Column(name = "packingList", length = 16777216)
	public String getPackingList() {
		return packingList;
	}

	public void setPackingList(String packingList) {
		this.packingList = packingList;
	}
	@Length(max = 16777216)
	@Column(name = "services", length = 16777216)
	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}
	@Column(name = "oneClassifyId", nullable = false)
	public Integer getOneClassifyId() {
		return oneClassifyId;
	}

	public void setOneClassifyId(Integer oneClassifyId) {
		this.oneClassifyId = oneClassifyId;
	}
	@Column(name = "twoClassifyId", nullable = false)
	public Integer getTwoClassifyId() {
		return twoClassifyId;
	}

	public void setTwoClassifyId(Integer twoClassifyId) {
		this.twoClassifyId = twoClassifyId;
	}
	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Transient
	public String getOneClassifyName() {
		return oneClassifyName;
	}
	public void setOneClassifyName(String oneClassifyName) {
		this.oneClassifyName = oneClassifyName;
	}
	@Transient
	public String getTwoClassifyName() {
		return twoClassifyName;
	}
	public void setTwoClassifyName(String twoClassifyName) {
		this.twoClassifyName = twoClassifyName;
	}
	@Transient
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	@Transient
	public String getProductproName() {
		return productproName;
	}

	public void setProductproName(String productproName) {
		this.productproName = productproName;
	}
	


	
}
