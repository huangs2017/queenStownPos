package com.bean;

import java.util.ArrayList;

import com.util.annotation.AnnotationField;

public class ProductInfo {
	
	@AnnotationField(columnName = "itid", type = "String")			private String itid;
	@AnnotationField(columnName = "belongto", type = "String")		private String belongto;
	@AnnotationField(columnName = "itname", type = "String")		private String itname;
	@AnnotationField(columnName = "itprice", type = "double")		private double itprice;
	@AnnotationField(columnName = "itprice2", type = "double")		private double itprice2;
	@AnnotationField(columnName = "picurl", type = "String")		private String picurl;
	@AnnotationField(columnName = "salemodel", type = "int")		private int salemodel; // 可选配料数
//	@AnnotationField(columnName = "itnumber", type = "int")			private int itnumber;
	private int itnumber;
	
	private ArrayList<MixtureInfo> mixtureList;

	
	public String getItid() {
		return itid;
	}

	public void setItid(String itid) {
		this.itid = itid;
	}

	public String getBelongto() {
		return belongto;
	}

	public void setBelongto(String belongto) {
		this.belongto = belongto;
	}

	public String getItname() {
		return itname;
	}

	public void setItname(String itname) {
		this.itname = itname;
	}

	public double getItprice() {
		return itprice;
	}

	public void setItprice(double itprice) {
		this.itprice = itprice;
	}

	public double getItprice2() {
		return itprice2;
	}

	public void setItprice2(double itprice2) {
		this.itprice2 = itprice2;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public int getSalemodel() {
		return salemodel;
	}

	public void setSalemodel(int salemodel) {
		this.salemodel = salemodel;
	}

	public int getItnumber() {
		return itnumber;
	}

	public void setItnumber(int itnumber) {
		this.itnumber = itnumber;
	}

	public ArrayList<MixtureInfo> getMixtureList() {
		return mixtureList;
	}

	public void setMixtureList(ArrayList<MixtureInfo> mixtureList) {
		this.mixtureList = mixtureList;
	}

	
}
