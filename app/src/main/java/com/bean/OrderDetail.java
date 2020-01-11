package com.bean;

import com.util.annotation.AnnotationField;

public class OrderDetail {

	@AnnotationField(columnName = "orderlistid", type = "String")	private String orderlistid;
	@AnnotationField(columnName = "orderid", type = "String")		private String orderid;
	@AnnotationField(columnName = "itid", type = "String")			private String itid;
	@AnnotationField(columnName = "itname", type = "String")		private String itname;
	@AnnotationField(columnName = "itnumber", type = "String")		private String itnumber;
	@AnnotationField(columnName = "cashiernum", type = "String")	private String cashiernum;
	@AnnotationField(columnName = "amount", type = "String")		private String amount;
	@AnnotationField(columnName = "tag", type = "String")			private String tag;

	public String getOrderlistid() {
		return orderlistid;
	}

	public void setOrderlistid(String orderlistid) {
		this.orderlistid = orderlistid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getItid() {
		return itid;
	}

	public void setItid(String itid) {
		this.itid = itid;
	}

	public String getItname() {
		return itname;
	}

	public void setItname(String itname) {
		this.itname = itname;
	}

	public String getItnumber() {
		return itnumber;
	}

	public void setItnumber(String itnumber) {
		this.itnumber = itnumber;
	}

	public String getCashiernum() {
		return cashiernum;
	}

	public void setCashiernum(String cashiernum) {
		this.cashiernum = cashiernum;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
