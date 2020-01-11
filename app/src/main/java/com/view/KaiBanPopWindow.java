package com.view;

import android.content.Intent;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.activity.CollectMoney;
import com.activity.LoginActivity;
import com.activity.R;
import com.util.SPUtils;

//收银员PopWindow
public class KaiBanPopWindow implements OnClickListener{
    
	LoginActivity activity;
	String cashiernum; //收银员编号
	String beiyongjin; //备用金
	
	PopupWindow popupWindow;
	EditText login_pop_edit;

	public void showPopWindow(LoginActivity activity) {
		this.activity = activity;
		this.cashiernum = activity.cashiernum;
		this.beiyongjin = activity.beiyongjin;
		final View vPopupWindow = activity.getLayoutInflater().inflate(R.layout.login_beiyongjin, null);
        popupWindow = new PopupWindow(vPopupWindow, 400, 400, true); //宽400 高400   true获得焦点
        
        login_pop_edit = (EditText) vPopupWindow.findViewById(R.id.login_pop_edit);
        TextView login_pop_cashiername= (TextView) vPopupWindow.findViewById(R.id.login_pop_cashiername);
        login_pop_cashiername.setText("开班收银员姓名："+ activity.cashiername);
        
        int[] pop_buttons_id = { R.id.login_pop_zero, R.id.login_pop_one, R.id.login_pop_two, R.id.login_pop_three, R.id.login_pop_four,
        					     R.id.login_pop_five,R.id.login_pop_six, R.id.login_pop_seven, R.id.login_pop_eight, R.id.login_pop_nine,
        					     R.id.login_pop_back, R.id.login_pop_delete, R.id.login_pop_ok, R.id.login_pop_point, R.id.login_pop_close };
        Button[] pop_buttons = new Button[pop_buttons_id.length];
    	for (int i = 0; i < pop_buttons_id.length; i++) {
    		pop_buttons[i] = (Button) vPopupWindow.findViewById(pop_buttons_id[i]);
    		pop_buttons[i].setOnClickListener(this);
		}
        popupWindow.showAtLocation(activity.denglu, Gravity.BOTTOM, 300, 220);
	}


	@Override
	public void onClick(View v) {
		Button button = (Button) v;
		switch (v.getId()) {
		case R.id.login_pop_back:  //回退
			String temp1 = login_pop_edit.getText().toString();
			if(temp1.length()>0)  { login_pop_edit.setText(temp1.substring(0, temp1.length() - 1)); }
			break;
		case R.id.login_pop_delete:
			login_pop_edit.setText("");
			break;
		case R.id.login_pop_ok:
			beiyongjin=login_pop_edit.getText().toString();
			if(TextUtils.isEmpty(beiyongjin)){
				beiyongjin="0";
			}
			System.out.println("收银员"+activity.cashiername+"备用金额"+activity.beiyongjin);
			Time time = new Time();
			time.setToNow();
			SPUtils.putString(activity, cashiernum+"jiaoban", "0"); //开班了
			SPUtils.putString(activity, cashiernum+"beiyongjin", beiyongjin); //备用金
			SPUtils.putString(activity, cashiernum+"starttime", time.format("%Y-%m-%d %H:%M:%S")); //开班时间
			//钱箱
//			JBInterface.QX_CTL(true);//打开钱箱
			try {
				Thread.sleep(100);
//				JBInterface.QX_CTL(false); //关闭钱箱
			} 	
			catch (InterruptedException e) { e.printStackTrace(); }
        	Intent intent = new Intent(activity, CollectMoney.class);
        	activity.startActivity(intent);
			popupWindow.dismiss();
			activity.finish();
			break;
		case R.id.login_pop_close:
			popupWindow.dismiss();
			break;
		default:  //数字键
			login_pop_edit.append(button.getText());
			break;
		}
	}
	

}
