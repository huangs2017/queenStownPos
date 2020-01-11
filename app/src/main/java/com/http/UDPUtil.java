package com.http;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import android.util.Log;

public class UDPUtil {

	private int port; // 端口
	private String params;
	private String tag_in = "HSHttp_in";

	public UDPUtil(int port, String params) {
		this.port = port;
		this.params = params;
	}

	public void start() {
		new Thread() {
			public void run() {
				requestStart();
			}
		}.start();
	}

	/** 向广告机-发送UDP数据报 */
	private void requestStart() {
		Log.i(tag_in, "发送UDP数据报: " + port + "\n入参-->" + "\n" + params);
		byte[] b = params.getBytes();
		try {
			DatagramSocket datagramSocket = new DatagramSocket();
			DatagramPacket datagramPacket = new DatagramPacket(b, b.length, InetAddress.getByName("255.255.255.255"), port); // b不能为null, 但可以是""
			datagramSocket.send(datagramPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
