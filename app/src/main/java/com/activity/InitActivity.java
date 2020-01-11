package com.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.http.HttpListener;
import com.http.HttpUtil;
import com.http.httpUrl;
import com.util.db.DBUtil;
import com.util.MyUtil;
import com.util.SPUtils;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

public class InitActivity extends Activity implements HttpListener {

	private volatile int tableCount; //需要初始化表的数量
//	volatile修饰的成员变量在每次被线程访问时，都强迫从共享内存中重读该成员变量的值。
//	而且，当成员变量发生变化时，强迫线程将变化值回写到共享内存。
//	这样在任何时刻，两个不同的线程总是看到某个成员变量的同一个值。

	private ExecutorService pool = Executors.newSingleThreadExecutor();
	private final int http_getList = 1;

	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.init_activity);
		String appVersion = MyUtil.getAppVersionName(this);

		if (appVersion.compareTo(SPUtils.getString(this, "version", "0.1")) < 0) { // 本地更新
			File file = new File("/mnt/sdcard", "queen_updata.apk");
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW); // 执行动作
			intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive"); // 执行的数据类型
			startActivity(intent);
		} else {
			String cpuSerial = MyUtil.getCPUSerial();
			SPUtils.putString(this, "cpuSerial", cpuSerial);
//			SPUtils.putString(this, "cpuSerial", "0580ba2c56534848807788541623670c");  // 固定假CPU序列号
			String dataBasePath = getApplicationContext().getDatabasePath("temp.db").getAbsolutePath();
			File file = new File(dataBasePath); //数据库文件
			Intent intent = getIntent();
			String tongbu = intent.getStringExtra("tongbu");	// 同步
			if (!file.exists() || "同步".equals(tongbu)) {		// 第一次使用或点击同步
				System.out.println("-->temp.db不存在，开始更新数据库表格");
				// 初始化以下6张表
				pool.execute(getThread("systemset"));
				pool.execute(getThread("cashier"));
				pool.execute(getThread("categories1"));
				pool.execute(getThread("categories2"));
				pool.execute(getThread("it"));
				pool.execute(getThread("mixture"));
			}
			else{
				Intent intent2 = new Intent(InitActivity.this, LoginActivity.class);
				startActivity(intent2);
				finish();
			}
		}
		MyUtil.hideStatusBar();
	}

	public Runnable getThread(String tableName) {
		ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("list", tableName));
		HttpUtil httpUtil = new HttpUtil(this, httpUrl.getlist, params, this, http_getList); // 线程
		httpUtil.setTableName(tableName);
		return httpUtil;
	}

	@Override
	public void onHttpSuccess(int requestCode, Object obj, String tableName) {
		String result = (String) obj;
		JSONArray jsonArray = null;
		try {
			jsonArray = new JSONArray(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		DBUtil.initDB(this, tableName, jsonArray);
		tableCount++;
//		tableName表更新完成
		if (tableCount == 6) { // 6张表全部加载完成
			Intent intent = new Intent(InitActivity.this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void onHttpFail(int requestCode, String errorMsg, String tableName) {
		pool.execute(getThread(tableName)); // 多个线程同时往数据库插数据可能会出问题
	}
}
