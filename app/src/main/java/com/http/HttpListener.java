package com.http;

/** 网络请求回调 */

public interface HttpListener {
	void onHttpSuccess(int requestCode, Object obj, String tag); // 请求时传入tag，请求完成返回tag
	void onHttpFail(int requestCode, String errorMsg, String tag);
}
