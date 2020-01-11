package com.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartupReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent2 = new Intent(context, InitActivity.class);
        //在从非Activity启动到Activity时需要加该标记，用来创建一个栈空间
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent2); 
    }
	
}

