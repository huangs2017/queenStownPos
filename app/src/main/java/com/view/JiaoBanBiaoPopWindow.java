package com.view;

import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import android.text.format.Time;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.activity.LoginActivity;
import com.activity.R;
import com.bean.OrderInfo;
import com.bean.ShiftInfo;
import com.http.HttpListener;
import com.http.HttpUtil;
import com.http.httpUrl;
import com.util.db.DBUtil;
import com.util.JsonUtil;
import com.util.SPUtils;

//交班表PopWindow
public class JiaoBanBiaoPopWindow implements OnClickListener, HttpListener{
    
	ArrayList<String> orderid = new ArrayList<>(); //未交班收银员销售的订单id
	double total_cashier = 0; //某收银员从开班到交班销售所收入的总金额	
	LoginActivity activity;
	PopupWindow popupWindow;
	private String cashierName;
	private String cashierNum;
	private final int http_uploadData = 1;
	
	public void jiaobanbiaoPopWindow(final LoginActivity activity, String cashierName, String cashierNum) {
		this.activity = activity;
		this.cashierName = cashierName;
		this.cashierNum = cashierNum;
		final View vPopupWindow = activity.getLayoutInflater().inflate(R.layout.login_jiaobanbiao, null);
        popupWindow = new PopupWindow(vPopupWindow, 350, 600, true); // true获得焦点
        Button btn_queren = vPopupWindow.findViewById(R.id.btn_queren);
        Button btn_quxiao = vPopupWindow.findViewById(R.id.btn_quxiao);
        TextView cashiernum_text = vPopupWindow.findViewById(R.id.textview_eighteen_cashiernum);
        TextView cashiername_text = vPopupWindow.findViewById(R.id.textview_eighteen_cashiername);
        TextView total_text = vPopupWindow.findViewById(R.id.textview_eighteencash);
        
		ArrayList<OrderInfo> orderList = DBUtil.getOrderList(activity, cashierNum);
		total_cashier = 0;
		orderid.clear();
		
		for (OrderInfo order : orderList) {
			total_cashier = total_cashier + order.getAmount();
			orderid.add(order.getOrderid());
		}
		
        cashiername_text.setText(cashierName);
        cashiernum_text.setText(cashierNum);
		total_text.setText(String.valueOf(total_cashier));
        popupWindow.showAtLocation(activity.denglu, Gravity.BOTTOM, 300, 100);
        btn_quxiao.setOnClickListener(this);
        btn_queren.setOnClickListener(this);
	}
	
	private void uploadShiftData(){
		System.out.println("uploadShiftData  更新shift表");
		//SQLiteDatabase db = openOrCreateDatabase("temp.db", SQLiteDatabase.OPEN_READONLY, null);
		//数据库没用单例时，有时会莫名其妙的把temp.db中的所有表都删除掉(而不是清空表)，导致shifttable表不存在，从而程序在此处死掉
		ArrayList<ShiftInfo> shiftList = DBUtil.getShiftList(activity);
		ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
		arrayList.add(new BasicNameValuePair("shifttable", JsonUtil.bean2Json(shiftList)));
		new HttpUtil(activity, httpUrl.update, arrayList, this, http_uploadData).start();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_queren:
			DBUtil.updateOrderTableTag2(activity, orderid);
	    	Time time = new Time();
			time.setToNow();
			
			//从SharedPreferences获取日期，并与当前日期比较
			if(SPUtils.getString(activity, "shift_date")==null){
				SPUtils.putString(activity, "shift_date", time.format("%Y-%m-%d"));
			}
			String shift_date = SPUtils.getString(activity, "shift_date");
			if(!(time.format("%Y-%m-%d").equals(shift_date))){
				SPUtils.putString(activity, "shift_date", time.format("%Y-%m-%d"));
				SPUtils.putInt(activity, "shiftidtail", 1);
			}
			

	        String shopid = SPUtils.getString(activity, "shopid");
			int shiftidtail = SPUtils.getInt(activity, "shiftidtail", 1);
			String shiftid = shopid + time.format("%Y%m%d") + String.format("%05d", shiftidtail);
			DBUtil.insertShiftTable(activity, total_cashier, shiftid, time, cashierNum);
		    SPUtils.putInt(activity, "shiftidtail", ++shiftidtail);
		    SPUtils.putString(activity, cashierNum+"jiaoban", "1");  //收银员交班,对应425行 
		    
		    String biaotou="交班表"+"\n";
			String danhao="交班单号："+shiftid+"\n";
			String fenge="-------------------------------\n";
			String biaozhong=String.format("%-32s", "交班时间："+ time.format("%Y-%m-%d") + "," + time.format("%H:%M:%S"))+"\n";
			String biaozhong2=String.format("%-32s","收银员编号："+cashierNum)+"\n";
			String biaozhong3="收银员姓名："+cashierName;
			biaozhong3=String.format("%-32s","收银员姓名："+cashierName)+"\n";
			String biaowei="店长签字：        收银员签字：";
			String biaozhong4=String.format("%-32s","备用金："+SPUtils.getString(activity, cashierNum+"beiyongjin", "0"))+"\n";
			String biaozhong5=String.format("%-32s","收入现金："+total_cashier)+"\n";
			String biaozhong6=String.format("%-32s","应交现金："+String.valueOf(total_cashier +
					Integer.valueOf(SPUtils.getString(activity, cashierNum+"beiyongjin", "0"))))+"\n";

//			JBInterface.printText(biaotou+danhao+fenge+biaozhong+biaozhong2+biaozhong3+biaozhong4+biaozhong5+biaozhong6+biaowei);
//			JBInterface.printText(biaotou+danhao+fenge+biaozhong+biaozhong2+biaozhong3+biaozhong4+biaozhong5+biaozhong6+biaowei);
//			JBInterface.QX_CTL(true);//打开钱箱
			try {
				Thread.sleep(100);
//				JBInterface.QX_CTL(false); // 关闭钱箱
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		   popupWindow.dismiss();
		   uploadShiftData();
			break;
		case R.id.btn_quxiao:
			 popupWindow.dismiss();
			break;
	
		}
	}
	
	@Override
	public void onHttpSuccess(int requestCode, Object obj, String tag) {
		String result = (String) obj;
		try {
			DBUtil.updateTableTag(activity, result, "shifttable", "shiftid");	// 更新shifttable表
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onHttpFail(int requestCode, String errorMsg, String tag) {
		
	}

}
