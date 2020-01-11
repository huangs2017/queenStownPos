package com.update;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.bean.UpdateInfo;
import com.http.httpUrl;
import com.util.SPUtils;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class UpdateManager extends Thread {

	private Context ctx;
	UpdateInfo info;
	URL ApkUrl;

	public UpdateManager(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public void run() {
		try {
			URL url = new URL(httpUrl.version);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream(); // 从服务器获得一个输入流
				info = UpdateInfoParser.getUpdateInfo(is);
			}
			if (info != null) {
				if (SPUtils.getString(ctx, "version", "0.1").compareTo(info.getVersion()) < 0) {
					ApkUrl = new URL(info.getUrl());
					new FiledownThread().start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	class FiledownThread extends Thread {
		@Override
		public void run() {
			HttpURLConnection httpURLConnection = null;
			int responseCode = 0;
			try {
				httpURLConnection = (HttpURLConnection) ApkUrl.openConnection();
				httpURLConnection.setConnectTimeout(5000);
				responseCode = httpURLConnection.getResponseCode();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (responseCode == 200) {
				try {
					InputStream is = httpURLConnection.getInputStream();
					BufferedInputStream bis = new BufferedInputStream(is);
					File file = new File("/mnt/sdcard", "queen_updata.apk");
					FileOutputStream fos = new FileOutputStream(file);
					byte[] buffer = new byte[1024];
					int len;
					while ((len = bis.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
					}
					fos.close();
					bis.close();
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Message msg = handler.obtainMessage();
				handler.sendMessage(msg);
			}
		}
	}
	
	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			SPUtils.putString(ctx, "version", info.getVersion());
		}
	};

}