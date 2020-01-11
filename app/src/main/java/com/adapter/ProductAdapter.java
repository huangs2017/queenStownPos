package com.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.activity.R;
import com.bean.ProductInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter{
	
	ArrayList<ProductInfo> productList;


	public ProductAdapter(ArrayList<ProductInfo> productList) {
		this.productList = productList;
	}

	public int getCount() {
		return productList.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_adapter, parent, false);
		TextView txt_name = convertView.findViewById(R.id.txt_name);
		TextView txt_price = convertView.findViewById(R.id.txt_price);
		ProductInfo product =  productList.get(position);
		txt_name.setText(product.getItname());
		txt_price.setText(product.getItprice() + "");
		Map<String, Object> it_map = new HashMap<String, Object>();
		txt_name.setTag(product);
		return convertView;
	}
	
	public void setData(ArrayList<ProductInfo> productList) {
		this.productList = productList;
		notifyDataSetChanged();
	}


}