package com.activity;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.bean.CashierInfo;
import com.bean.SystemSet;
import com.update.UpdateManager;
import com.util.db.DBUtil;
import com.util.LoginOnClickListener;
import com.util.LoginOnFocusListener;
import com.util.MyUtil;
import com.util.SPUtils;
import com.util.annotation.BindView;
import com.util.annotation.DealAnnotation;
import com.view.JiaoBanBiaoPopWindow;
import com.view.KaiBanPopWindow;

public class LoginActivity extends Activity implements OnClickListener{

	@BindView(R.id.text_dianpu) 	public TextView text_dianpu; 	//店铺
	@BindView(R.id.edit_zhanghao) 	public EditText edit_zhanghao; 	//账号
	@BindView(R.id.edit_mima) 		public EditText edit_mima; 		//密码
	
	@BindView(R.id.btn_login)		public Button denglu; 	//登录
	@BindView(R.id.tongbu) 			Button tongbu; 			//同步
	@BindView(R.id.jiaoban) 		Button jiaoban;			//交班
	@BindView(R.id.shezhi) 			Button shezhi; 			//设置
	
	public OnClickListener clickListener; 		//自定义键盘点击监听
	public LoginOnFocusListener focusListener; 	//EditText获得焦点监听
	public String cashiernum; 					//收银员编号
	public String cashiername;	 				//收银员姓名
	public String beiyongjin; 					//备用金
	private int[] ids = { R.id.zero, R.id.one, R.id.two, R.id.three, R.id.four,
			              R.id.five,R.id.six, R.id.seven, R.id.eight, R.id.nine,
			              R.id.back, R.id.delete, R.id.ok, R.id.point, R.id.close };
	private Button[] buttons = new Button[ids.length];


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		DealAnnotation.bind(this);
//		JBInterface.initPrinter(); //打印初始化
		focusListener = new LoginOnFocusListener();
		MyUtil.hideSoftKeyboard(edit_zhanghao);
		MyUtil.hideSoftKeyboard(edit_mima);
		
		edit_zhanghao.setOnFocusChangeListener(focusListener);
		edit_mima.setOnFocusChangeListener(focusListener);
		clickListener = new LoginOnClickListener(this);
		
		for (int i = 0; i < ids.length; i++) {
			buttons[i] = this.findViewById(ids[i]);
			buttons[i].setOnClickListener(clickListener);
		}
			
		denglu.setOnClickListener(this);
		tongbu.setOnClickListener(this);
		jiaoban.setOnClickListener(this);
		text_dianpu.setText(DBUtil.getShopName(this));
		if(MyUtil.isNetConnected(this)) {
			new UpdateManager(this).start(); // 向服务器检查更新
		}
		
		MyUtil.hideStatusBar();
	} 
	
	String cashierName; //“交班”对话框中选中的收银员姓名
	String cashierNum;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:

			ArrayList<SystemSet> systemList = DBUtil.getSystemSet(this);
			for (SystemSet systemSet : systemList) {
				String shopid = systemSet.getShopid();
				SPUtils.putString(this, "shopid", shopid);
				
				String shopkeeperid= systemSet.getShopkeeperid();
				String shopkeeperpwd = systemSet.getShopkeeperpwd();
				String zhanghao = edit_zhanghao.getText().toString();
				String mima = edit_mima.getText().toString();

				if (shopkeeperid.equals(zhanghao) && shopkeeperpwd.equals(mima)) {
					tongbu.setVisibility(View.VISIBLE);
					jiaoban.setVisibility(View.VISIBLE);
					shezhi.setVisibility(View.VISIBLE);
					return;
				}
			}

			ArrayList<CashierInfo> cashierList = DBUtil.getCashierList(this);
			for (CashierInfo cashier : cashierList) {
				String cashierid = cashier.getCashierid();
				String cashierpwd = cashier.getCashierpwd();
				cashiernum = cashier.getCashiernum();
				String zhanghao = edit_zhanghao.getText().toString();
				String mima = edit_mima.getText().toString();
				if(cashierid.equals(zhanghao) && cashierpwd.equals(mima)) {
					SPUtils.putString(this, "thisCashiernum", cashiernum);
					if(SPUtils.getString(this, cashiernum+"jiaoban", "1").equals("1")){ //收银员未开班(第一次登录)
						//未交班是0，交班是1    （开班是0）
						cashiername = cashier.getCashiername();
						new KaiBanPopWindow().showPopWindow(this);
					}
					else{
						Intent intent = new Intent(this, CollectMoney.class);
						startActivity(intent);
						finish();
					}
					return;
				}
			}
			
			break;
		case R.id.tongbu:	//重新从服务器初始化表数据
			Intent intent = new Intent(this, InitActivity.class);
			intent.putExtra("tongbu", "同步");
			startActivity(intent);
			finish();
			break;
		case R.id.jiaoban:
			ArrayList<CashierInfo> cashierList2 = DBUtil.getCashierList(this);


			ArrayList<String> cashiernameList = new ArrayList<String>(); //未交班收银员的名字
			ArrayList<String> cashiernumList = new ArrayList<String>();
			for (CashierInfo cashier : cashierList2) {
				String cashiernum_temp = cashier.getCashiernum();
				String temp = SPUtils.getString(this, cashiernum_temp + "jiaoban", "1");
				if(temp.equals("0")){ //未交班
					cashiernameList.add(cashier.getCashiername());
					cashiernumList.add(cashier.getCashiernum());
				}
			}
			
			final String[] items = cashiernameList.toArray(new String[cashiernameList.size()]); //未交班收银员的名字
			final String[] numbers = cashiernumList.toArray(new String[cashiernumList.size()]);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("交班")
                    .setCancelable(false)
                    .setSingleChoiceItems(items, -1, (dialog, item) -> {
						cashierName = items[item];
						cashierNum = numbers[item];
					})
                    .setPositiveButton("确定", (dialog, id) ->
							new JiaoBanBiaoPopWindow().jiaobanbiaoPopWindow(LoginActivity.this, cashierName, cashierNum))
                    .setNegativeButton("取消", (dialog, id) -> {

					});
            AlertDialog alert = builder.create();
            alert.show();
			break;
		}
		
	}
	
	@Override
	protected void onDestroy() {
//		JBInterface.closePrinter();
//		JBInterface.closeCashBox();
		super.onDestroy();
	}
	
}
