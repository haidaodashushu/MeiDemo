package com.example.meidemo.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;

public class NetWorkManager {
	/** 无网络 */
	public static final byte NO_NET = -1;
	/** 代理 */
	public static final byte Connenction_cmwap = 1;

	/** 直连 */
	public static final byte Connenction_cmnet = 0;

	/** wifi直连 */
	public static final byte Connenction_wifi = 2;
	
	public static String wapurl = "10.0.0.200";
	
	/**
	 * 获得当前的网络类型
	 * 
	 * @return
	 */
	public static int getNetworkType(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivity.getActiveNetworkInfo();

		if (info == null
				|| (info.getState() != NetworkInfo.State.CONNECTING && info
						.getState() != NetworkInfo.State.CONNECTED)) {
			return NO_NET;
		}

		if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			return Connenction_wifi;
		} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
			if (Proxy.getDefaultHost() != null
					|| Proxy.getHost(context) != null) {
				wapurl = Proxy.getDefaultHost();
				return Connenction_cmwap;

			} else {
				return Connenction_cmnet;
			}
		}
		// Log.v("网络连接类型", "OK");
		return NO_NET;
	}
}
