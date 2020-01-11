package com.bean;

import com.util.annotation.AnnotationField;

public class MixtureInfo {

	@AnnotationField(columnName = "mixtureid", type = "String")				private String mixtureid;
	@AnnotationField(columnName = "mixturename", type = "String")			private String mixturename;

	private String mixturepicurl;
	private String mixtureorderlistid;
	private String orderlistid;
	private String mixturenumber;
	private String tag;

	private int quantity;

	public String getMixtureid() {
		return mixtureid;
	}

	public void setMixtureid(String mixtureid) {
		this.mixtureid = mixtureid;
	}

	public String getMixturename() {
		return mixturename;
	}

	public void setMixturename(String mixturename) {
		this.mixturename = mixturename;
	}

	public String getMixturepicurl() {
		return mixturepicurl;
	}

	public void setMixturepicurl(String mixturepicurl) {
		this.mixturepicurl = mixturepicurl;
	}

	public String getMixtureorderlistid() {
		return mixtureorderlistid;
	}

	public void setMixtureorderlistid(String mixtureorderlistid) {
		this.mixtureorderlistid = mixtureorderlistid;
	}

	public String getOrderlistid() {
		return orderlistid;
	}

	public void setOrderlistid(String orderlistid) {
		this.orderlistid = orderlistid;
	}

	public String getMixturenumber() {
		return mixturenumber;
	}

	public void setMixturenumber(String mixturenumber) {
		this.mixturenumber = mixturenumber;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
