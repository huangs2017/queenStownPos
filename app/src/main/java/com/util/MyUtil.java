package com.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import com.bean.MixtureInfo;
import com.bean.ProductInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.EditText;

public class MyUtil {
	
	// 检查是否连网
	public static boolean isNetConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null) {
			return ni.isAvailable();
		}
		return false;
	}


	/** 获取APP版本信息(版本号、版本名) */
	public static String getAppVersionName(Context context) {
		String versionName = null ;
		PackageInfo packInfo;
		try {
			// getPackageName()是你当前类的包名，0代表是获取版本信息
			packInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			versionName = packInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}
	
	
	//获取CPU序列号
	public static String getCPUSerial() {
		String cpuAddress = "";
		try {
			// 读取CPU信息
			Process process = Runtime.getRuntime().exec("cat /proc/cpuinfo");
			InputStreamReader ir = new InputStreamReader(process.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			// 查找CPU序列号
			for (int i = 1; i < 100; i++) {
				String result = input.readLine();
				if (result != null) {
					// 查找到序列号所在行
					if (result.indexOf("Serial") > -1) {
						// 提取序列号
						String strCPU = result.substring(result.indexOf(":") + 1, result.length());
						cpuAddress = strCPU.trim(); // 去空格
						break;
					}
				} else {
					break;
				} // 文件结尾
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return cpuAddress;
	}
	

	//隐藏平板屏幕下方的状态栏
	public static void hideStatusBar() {
		try {
			String ProcID = "79";
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
				ProcID = "42";
			Process proc = Runtime.getRuntime().exec(
					new String[] { "su", "-c", "service call activity " + ProcID + " s16 com.android.systemui" });
			proc.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//隐藏软键盘
	public static void hideSoftKeyboard(EditText editText) {
		Class<EditText> cls = EditText.class;
		Method method;
		try {
			method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
			method.setAccessible(true);
			method.invoke(editText, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static String getItNameShow(ProductInfo product) {
		String itName = product.getItname();
		ArrayList<MixtureInfo> mixtureList = product.getMixtureList();
		if (mixtureList != null) {
			for(MixtureInfo mixture : mixtureList){
				itName =  itName + "\n" + mixture.getMixturename() + " x" + mixture.getQuantity();
			}
		}
		return itName;
	}



	public static String print1(String itname, String itname_show, int itnumber, double itprice, String itamount){  //这是想干啥??
		int long1 = itname.length();
		long1 = 18-long1;
		String echoitname = String.format("%"+"-"+long1+"s", itname);
		String echoitnumber = String.format("%4s", itnumber);
		String echoitprice = String.format("%5s", itprice);
		String echoitamount = String.format("%5s", itamount);
		String[] sp = itname_show.split("\n",2); //字符串的拆分split("\n",2)，以"\n"为标志进行拆分，拆分为2个字符串
		String peiliao = "";
		if(sp.length==2) peiliao="\n"+"配料"+"\n"+sp[1];
		return echoitname + echoitnumber + echoitprice + echoitamount + peiliao + "\n";
	}

}
