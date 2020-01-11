package com.util;

import com.activity.LoginActivity;
import com.activity.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginOnClickListener implements OnClickListener {

	private LoginOnFocusListener focusListener;

	private EditText edit_zhanghao;
	private EditText edit_mima;

	public LoginOnClickListener(LoginActivity activity) {
		this.focusListener = activity.focusListener;
		edit_zhanghao = activity.edit_zhanghao;
		edit_mima = activity.edit_mima;
	}

	// _______________________________________________________________________________________
	@Override
	public void onClick(View v) {

		Button button = (Button) v;
		switch (v.getId()) {

		case R.id.back: // 回退
			switch (focusListener.focusflag) {
			case "edit_zhanghao":
				String temp3 = edit_zhanghao.getText().toString();
				if (temp3.length() > 0) {
					edit_zhanghao.setText(temp3.substring(0, temp3.length() - 1));
				}
				break;
			case "edit_mima":
				String temp4 = edit_mima.getText().toString();
				if (temp4.length() > 0) {
					edit_mima.setText(temp4.substring(0, temp4.length() - 1));
				}
				break;
			}
			break;

		case R.id.delete:
			switch (focusListener.focusflag) {
			case "edit_zhanghao":
				edit_zhanghao.setText("");
				break;
			case "edit_mima":
				edit_mima.setText("");
				break;
			}
			break;

		case R.id.ok:
			break;
		case R.id.close:
			break;

		default: // 数字键
			switch (focusListener.focusflag) {
			case "edit_zhanghao":
				edit_zhanghao.append(button.getText());
				break;
			case "edit_mima":
				edit_mima.append(button.getText());
				break;
			}
			break;
		}

	}
	// _______________________________________________________________________________________

}
