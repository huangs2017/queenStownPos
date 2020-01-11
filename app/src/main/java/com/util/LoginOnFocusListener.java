package com.util;

import com.activity.R;
import android.view.View;
import android.view.View.OnFocusChangeListener;

public class LoginOnFocusListener implements OnFocusChangeListener {

    public String focusflag;

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.edit_zhanghao:
                if (hasFocus) {
                    focusflag = "edit_zhanghao";
                }
                break;
            case R.id.edit_mima:
                if (hasFocus) {
                    focusflag = "edit_mima";
                }
                break;
        }
    }

}
