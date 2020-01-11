package com.bean;

import com.util.annotation.AnnotationField;

public class OrderInfo {

	@AnnotationField(columnName = "orderid", type = "String")		private String orderid;
	@AnnotationField(columnName = "orderdate", type = "String")		private String orderdate;
	@AnnotationField(columnName = "ordertime", type = "String")		private String ordertime;
	@AnnotationField(columnName = "amount", type = "double")		private double amount;
	@AnnotationField(columnName = "tag", type = "String")			private String tag;
	@AnnotationField(columnName = "tag2", type = "String")			private String tag2;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTag2() {
		return tag2;
	}

	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}

}
