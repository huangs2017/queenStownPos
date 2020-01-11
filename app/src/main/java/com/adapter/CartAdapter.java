package com.adapter;

import java.util.ArrayList;
import com.activity.CollectMoney;
import com.activity.R;
import com.bean.ProductInfo;
import com.util.MyUtil;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CartAdapter extends BaseAdapter {
	
	ArrayList<ProductInfo> cartList;
	private CollectMoney activity;
	
	public CartAdapter(ArrayList<ProductInfo> cartList, CollectMoney activity) {
		super();
		this.cartList = cartList;
		this.activity = activity;
	}

	public int getCount() {
		return cartList.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_adapter, parent, false);
		TextView txt_name = convertView.findViewById(R.id.txt_name);
		TextView txt_number = convertView.findViewById(R.id.txt_number);
		TextView txt_price = convertView.findViewById(R.id.txt_price);
		TextView txt_amount = convertView.findViewById(R.id.txt_amount);

		ProductInfo product = cartList.get(position);
		txt_name.setText(MyUtil.getItNameShow(product));
		txt_number.setText( product.getItnumber() + "");
		txt_price.setText( product.getItprice() +"");
		txt_amount.setText(product.getItnumber() * product.getItprice() + "");
		txt_number.setTag(position + "");

		convertView.setBackgroundColor(Color.WHITE);
		if (position == activity.cur_pos) { // 点击item的位置
			convertView.setBackgroundColor(Color.MAGENTA); // ////此一直有背景色 116
		}
		return convertView;
	}
	
	public void setData(ArrayList<ProductInfo> cartList) {
		this.cartList = cartList;
		notifyDataSetChanged();
	}

}