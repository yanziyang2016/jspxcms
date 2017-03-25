package com.jspxcms.core.domain;

import java.util.List;

public class ProductOrderList {
	private int currpage;
	private int isfirst;//1:是;0:否
	private int islast;//1:是;0:否
	private int totalpage;
	private long totalcount;
	private List<Order> productOrderList;
	public int getCurrpage() {
		return currpage;
	}
	public void setCurrpage(int currpage) {
		this.currpage = currpage;
	}
	public int getIsfirst() {
		return isfirst;
	}
	public void setIsfirst(int isfirst) {
		this.isfirst = isfirst;
	}
	public int getIslast() {
		return islast;
	}
	public void setIslast(int islast) {
		this.islast = islast;
	}
	public int getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
	
	public long getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(long totalcount) {
		this.totalcount = totalcount;
	}
	public List<Order> getProductOrderList() {
		return productOrderList;
	}
	public void setProductOrderList(List<Order> productOrderList) {
		this.productOrderList = productOrderList;
	}


	
	

}
