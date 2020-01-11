package com.bean;

import com.util.annotation.AnnotationField;

public class CashierInfo {
	
	@AnnotationField(columnName = "cashiernum", type = "String")		private String cashiernum;
	@AnnotationField(columnName = "cashierid", type = "String")			private String cashierid;
	@AnnotationField(columnName = "cashierpwd", type = "String")		private String cashierpwd;
	@AnnotationField(columnName = "cashiername", type = "String")		private String cashiername;
	@AnnotationField(columnName = "cashiersex", type = "String")		private String cashiersex;
	@AnnotationField(columnName = "cashierpicurl", type = "String")		private String cashierpicurl;

	public String getCashiernum() {
		return cashiernum;
	}

	public void setCashiernum(String cashiernum) {
		this.cashiernum = cashiernum;
	}

	public String getCashierid() {
		return cashierid;
	}

	public void setCashierid(String cashierid) {
		this.cashierid = cashierid;
	}

	public String getCashierpwd() {
		return cashierpwd;
	}

	public void setCashierpwd(String cashierpwd) {
		this.cashierpwd = cashierpwd;
	}

	public String getCashiername() {
		return cashiername;
	}

	public void setCashiername(String cashiername) {
		this.cashiername = cashiername;
	}

	public String getCashiersex() {
		return cashiersex;
	}

	public void setCashiersex(String cashiersex) {
		this.cashiersex = cashiersex;
	}

	public String getCashierpicurl() {
		return cashierpicurl;
	}

	public void setCashierpicurl(String cashierpicurl) {
		this.cashierpicurl = cashierpicurl;
	}
	
}
