package com.activity;

import java.util.ArrayList;
import org.json.JSONException;
import com.adapter.CategoryAdapter;
import com.adapter.ProductAdapter;
import com.adapter.CartAdapter;
import com.bean.CategoryInfo;
import com.bean.ProductInfo;
import com.http.HttpListener;
import com.http.UDPUtil;
import com.util.db.DBUtil;
import com.util.JsonUtil;
import com.util.MyUtil;
import com.util.annotation.BindView;
import com.util.annotation.DealAnnotation;
import com.view.MaterialPopWindow;
import com.view.XianJinPopWindow;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

public class CollectMoney extends Activity implements OnClickListener, HttpListener{
	
	@BindView(R.id.gv_category)			GridView gv_category;
	@BindView(R.id.gv_product)			GridView gv_product;
	@BindView(R.id.btn_return)			Button btn_return; //返回
	@BindView(R.id.btn_jia)				Button btn_jia;
	@BindView(R.id.btn_jian)			Button btn_jian;
	@BindView(R.id.btn_shanchu)			Button btn_shanchu;
	@BindView(R.id.btn_xianjin)			public Button btn_xianjin; //现金
	@BindView(R.id.list)				ListView lv;
	@BindView(R.id.txt_total_price)		public TextView txt_total_price; //所有商品的总价
	
	public CartAdapter cartAdapter;
	public CategoryAdapter categoryAdapter;
	public ProductAdapter productAdapter;
	public ArrayList<CategoryInfo> categoryList;
	public ArrayList<ProductInfo> productList;
	public ArrayList<ProductInfo> cartList = new ArrayList<ProductInfo>();
	
	public ProductInfo productInfo;
	Context ctx;
	public int cur_pos = -1; //点击lv中item的位置
	public final int udp_port1 = 8888;
	public final int udp_port2 = 9999;
	public final int http_uploadOrder = 1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collectmoney_activity);
		DealAnnotation.bind(this);
		ctx = this;
//		JBInterface.initPrinter(); //打印初始化
	    
	    btn_jia.setOnClickListener(this);
	    btn_jian.setOnClickListener(this);
	    btn_shanchu.setOnClickListener(this);
	    btn_xianjin.setOnClickListener(this);
	    btn_return.setOnClickListener(this);

		categoryList = DBUtil.getCategoryList(ctx);
		categoryAdapter = new CategoryAdapter(categoryList);
		gv_category.setAdapter(categoryAdapter);

		productList = DBUtil.getProductList(ctx, categoryList.get(0).getCategories2id());
		productAdapter = new ProductAdapter(productList);
		gv_product.setAdapter(productAdapter);

		cartAdapter = new CartAdapter(cartList, this);
		lv.setAdapter(cartAdapter);
		/*
		ListView 、GridView的OnItemClickListener的事件无响应情况：
		原因是ListView、GridView的子元素包含了Button、ImageButton、CheckBox，
		Button的优先级高于ListView 、GridView，所以不能监听item的点击事件。
		*/
		gv_category.setOnItemClickListener((parent, view, position, id) -> {
            TextView txt_name = view.findViewById(R.id.txt_name);
            String categories2id = (String) txt_name.getTag();
			productList = DBUtil.getProductList(ctx, categories2id);
            productAdapter.setData(productList);
        });
		
		gv_product.setOnItemClickListener((parent, view, position, id) -> {
            TextView txt_name = view.findViewById(R.id.txt_name);
            productClick(txt_name);
        });
		
		//点击商品监听
		lv.setOnItemClickListener((parent, view, position, id) -> {
            cur_pos = position;
            cartAdapter.notifyDataSetChanged();
        });
		
		MyUtil.hideStatusBar();
	}
	
	
	public void productClick(View v) {
		TextView txt_it = (TextView)v;
		productInfo = (ProductInfo) txt_it.getTag();
		int i = 0;
		for (ProductInfo productInfo2 : cartList) { //再次点击商品，遍历list找到已有的商品
			i++;
	    	if(txt_it.getText().equals(productInfo2.getItname())) {
			    int itNumber = productInfo2.getItnumber() + 1;
			    productInfo2.setItnumber(itNumber);
				cur_pos = i-1;
				updateCart();
				return;
	    	}
		}
		//初次点击商品
		productInfo.setItnumber(1);
		 if( productInfo.getSalemodel() > 0 ){
			new MaterialPopWindow().materialPopWindow(this, productInfo);
		 }
		 else{
			 cartList.add(productInfo);
			 cur_pos = cartList.size() - 1;
			 updateCart();
		 }
	}
	 
	//选完配料执行此方法
	 public void mixtureResult() {
		 cartList.add(productInfo);
		 cur_pos = cartList.size() - 1;
		 updateCart();
	 }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_xianjin:
				new XianJinPopWindow().xianjinPopWindow(this, v);
				break;
			case R.id.btn_return:
				Intent intent = new Intent(this, LoginActivity.class);
				startActivity(intent);
				finish();
				break;
			case R.id.btn_jia: //加
				if (cartList.size() > 0) {
					ProductInfo productInfo = cartList.get(cur_pos);
					int itNumber = productInfo.getItnumber() + 1;
					productInfo.setItnumber(itNumber);
					updateCart();
				}
				break;
			case R.id.btn_jian: // 减
				if (cartList.size() > 0) {
					ProductInfo productInfo = cartList.get(cur_pos);
					int itNumber = productInfo.getItnumber();
					if (itNumber == 1) del_OnClick();    //删除
					else {
						itNumber--;
						productInfo.setItnumber(itNumber);
						updateCart();
					}
				}
				break;
			case R.id.btn_shanchu://删除
				del_OnClick();
				break;
		}
	}

	public void del_OnClick() {
		if (cartList.size() > 0) {
			cartList.remove(cur_pos);
			cur_pos = cartList.size() - 1;
			updateCart();
		}
	}

	private void updateCart() {
		double total_Price = 0;
		for (ProductInfo productInfo : cartList) {
			total_Price = total_Price + productInfo.getItprice() * productInfo.getItnumber();
		}
		txt_total_price.setText(total_Price + "");

		cartAdapter.setData(cartList);
		new UDPUtil( udp_port1, JsonUtil.bean2Json(cartList) ).start();
	}

	@Override
	protected void onDestroy() {
//		JBInterface.closePrinter();
//		JBInterface.closeCashBox();
		super.onDestroy();
	}

	@Override
	public void onHttpSuccess(int requestCode, Object obj, String tag) {
		String result = (String) obj;
		try {
			DBUtil.updateTableTag(ctx, result, "orders", "orderid");						// 更新orders表
			DBUtil.updateTableTag(ctx, result, "orderlist", "orderlistid");					// 更新orderlist表
			DBUtil.updateTableTag(ctx, result, "mixtureorderlist", "mixtureorderlistid");	// 更新mixtureorderlist表
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void onHttpFail(int requestCode, String errorMsg, String tag) {
		
	}
	
}
