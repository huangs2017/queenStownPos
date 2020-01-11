package com.bean;

import com.util.annotation.AnnotationField;

public class ShiftInfo {
	
	@AnnotationField(columnName = "shiftid", type = "String")			private String shiftid;
	@AnnotationField(columnName = "shiftdate", type = "String")			private String shiftdate;
	@AnnotationField(columnName = "shifttime", type = "String")			private String shifttime;
	@AnnotationField(columnName = "cashiernum", type = "String")		private double cashiernum;
	@AnnotationField(columnName = "cash", type = "String")				private double cash;
	@AnnotationField(columnName = "shuaka", type = "String")			private String shuaka;
	@AnnotationField(columnName = "huiyuan", type = "String")			private int huiyuan;
	@AnnotationField(columnName = "amount", type = "String")			private int amount;
	@AnnotationField(columnName = "tag", type = "String")				private int tag;
	
	public String getShiftid() {
		return shiftid;
	}

	public void setShiftid(String shiftid) {
		this.shiftid = shiftid;
	}

	public String getShiftdate() {
		return shiftdate;
	}

	public void setShiftdate(String shiftdate) {
		this.shiftdate = shiftdate;
	}

	public String getShifttime() {
		return shifttime;
	}

	public void setShifttime(String shifttime) {
		this.shifttime = shifttime;
	}

	public double getCashiernum() {
		return cashiernum;
	}

	public void setCashiernum(double cashiernum) {
		this.cashiernum = cashiernum;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public String getShuaka() {
		return shuaka;
	}

	public void setShuaka(String shuaka) {
		this.shuaka = shuaka;
	}

	public int getHuiyuan() {
		return huiyuan;
	}

	public void setHuiyuan(int huiyuan) {
		this.huiyuan = huiyuan;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}
	
}
