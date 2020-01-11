package com.bean;

import com.util.annotation.AnnotationField;

public class SystemSet {
	
	@AnnotationField(columnName = "deviceid", type = "String")			private String deviceid;
	@AnnotationField(columnName = "shopid", type = "String")			private String shopid;
	@AnnotationField(columnName = "shopname", type = "String")			private String shopname;
	@AnnotationField(columnName = "shopkeeperid", type = "String")		private String shopkeeperid;
	@AnnotationField(columnName = "shopkeeperpwd", type = "String")		private String shopkeeperpwd;
	@AnnotationField(columnName = "shopnature", type = "String")		private String shopnature;
	@AnnotationField(columnName = "powerontimes", type = "String")		private String powerontimes;

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getShopkeeperid() {
		return shopkeeperid;
	}

	public void setShopkeeperid(String shopkeeperid) {
		this.shopkeeperid = shopkeeperid;
	}

	public String getShopkeeperpwd() {
		return shopkeeperpwd;
	}

	public void setShopkeeperpwd(String shopkeeperpwd) {
		this.shopkeeperpwd = shopkeeperpwd;
	}

	public String getShopnature() {
		return shopnature;
	}

	public void setShopnature(String shopnature) {
		this.shopnature = shopnature;
	}

	public String getPowerontimes() {
		return powerontimes;
	}

	public void setPowerontimes(String powerontimes) {
		this.powerontimes = powerontimes;
	}
	
}
