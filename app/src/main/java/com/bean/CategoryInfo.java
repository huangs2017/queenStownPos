package com.bean;

import com.util.annotation.AnnotationField;

public class CategoryInfo {

	@AnnotationField(columnName = "categories2name", type = "String")	private String categories2name;
	@AnnotationField(columnName = "categories2id", type = "String")		private String categories2id;

	public String getCategories2name() {
		return categories2name;
	}

	public void setCategories2name(String categories2name) {
		this.categories2name = categories2name;
	}

	public String getCategories2id() {
		return categories2id;
	}

	public void setCategories2id(String categories2id) {
		this.categories2id = categories2id;
	}
}
