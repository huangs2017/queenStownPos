package com.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import com.util.SHAEncrypt;
import com.util.SPUtils;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class HttpUtil extends Thread {

	private Context ctx;
	private String frontUrl;
	private ArrayList<BasicNameValuePair> arrayList;
	private HttpListener listener;
	private int requestCode;
	private String tableName; // tag标记字段

	public HttpUtil(Context ctx, String frontUrl, ArrayList<BasicNameValuePair> arrayList, HttpListener listener, int requestCode) {
		this.ctx = ctx;
		this.frontUrl = frontUrl;
		this.arrayList = arrayList;
		this.listener = listener;
		this.requestCode = requestCode;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public void run() {
		String shaToken = new SHAEncrypt().getEncryption(); //验证信息
		String url = frontUrl + shaToken + "&deviceid=" + SPUtils.getString(ctx, "cpuSerial");
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);	// 请求超时
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000); 			// 读取超时
		HttpPost httpPost = new HttpPost(url);
		System.out.println("url:   " + url);
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(arrayList, "utf-8");
		} // 上传arrayList到服务器
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		httpPost.setEntity(entity);

		try {
			HttpResponse response = client.execute(httpPost); // 执行post请求
			if (response.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(response.getEntity(), "UTF-8");
				Message msg = handler.obtainMessage();
				msg.obj = result;
				handler.sendMessage(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			listener.onHttpSuccess(requestCode, msg.obj, tableName);
		}
	};

}
