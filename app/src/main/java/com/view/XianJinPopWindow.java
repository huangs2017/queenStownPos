package com.view;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.activity.CollectMoney;
import com.activity.R;
import com.util.MyUtil;

public class XianJinPopWindow {
	
	//现金PopWindow
	public void xianjinPopWindow(CollectMoney activity, View v) {
		final View vPopupWindow = activity.getLayoutInflater().inflate(R.layout.collectmoney_xianjin, null);
        final PopupWindow popupWindow = new PopupWindow(vPopupWindow, 500, 400, true); // true获得焦点
        
        EditText edit_money = vPopupWindow.findViewById(R.id.edit_money);
        MyUtil.hideSoftKeyboard(edit_money); // EditText 获得焦点时隐藏软键盘
        
        Button btn_zhaoling = vPopupWindow.findViewById(R.id.btn_zhaoling);
        LinearLayout layout_gone = vPopupWindow.findViewById(R.id.layout_gone);
        
        int[] pop_buttons_id = { R.id.collectmoney_pop_zero, R.id.collectmoney_pop_one, R.id.collectmoney_pop_two,
					        	 R.id.collectmoney_pop_three, R.id.collectmoney_pop_four, R.id.collectmoney_pop_five,
					        	 R.id.collectmoney_pop_six, R.id.collectmoney_pop_seven, R.id.collectmoney_pop_eight, 
					        	 R.id.collectmoney_pop_nine, R.id.collectmoney_pop_back, R.id.collectmoney_pop_delete, 
					        	 R.id.collectmoney_pop_ok, R.id.collectmoney_pop_point, R.id.collectmoney_pop_close,
					        	 R.id.twenty, R.id.fifty, R.id.hundred};
        Button[] pop_buttons = new Button[pop_buttons_id.length];
        
        XianJinWindowClickListener xianjinWindowClickListener = new XianJinWindowClickListener 
        		(activity, popupWindow, edit_money, btn_zhaoling, layout_gone );
    	for (int i = 0; i < pop_buttons_id.length; i++) {
    		pop_buttons[i] = vPopupWindow.findViewById(pop_buttons_id[i]);
    		pop_buttons[i].setOnClickListener(xianjinWindowClickListener);
		}
    	btn_zhaoling.setOnClickListener(xianjinWindowClickListener);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
	}

}
