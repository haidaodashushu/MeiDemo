package com.example.meidemo.CommonUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class HttpHelper {
	protected ConnectivityManager connectivityManager = null;
	protected Context mContext = null;
	protected static HttpHelper instance;

	public static HttpHelper getInstance(Context context) {
		if (instance == null) {
			instance = new HttpHelper(context);
		}
		return instance;
	}

	private HttpHelper(Context context) {
		mContext = context;
		connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	protected int checkNetWordState() {
		NetworkInfo mobNetInfo = connectivityManager.getActiveNetworkInfo();
		if (mobNetInfo != null) {
			if (mobNetInfo.isAvailable()) {
				int netType = mobNetInfo.getType();
				if (netType == ConnectivityManager.TYPE_WIFI) {
					return 2;
				} else if (netType == ConnectivityManager.TYPE_MOBILE) {
					return 1;
				}
			}
		}
		return -1;
	}

	protected HttpURLConnection URLConnection(String url) {
		HttpURLConnection urlCon = null;

		int networkType = checkNetWordState();
		if (networkType > 0) {
			try {
				urlCon = (HttpURLConnection) new URL(url).openConnection();
			} catch (Exception e) {
				e.printStackTrace();
				urlCon = null;
			}
			if (urlCon != null) {
				urlCon.setConnectTimeout(10000);
				urlCon.setReadTimeout(10000);
			}
		}
		return urlCon;
	}
	public String get(String url,Map<String, String> value){
		HttpClient client = new DefaultHttpClient();
		
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 8000);
		
		Iterator<Entry<String, String>> iterator= value.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		while (iterator.hasNext()) {
			Entry<String, String> v = iterator.next();
			try {
				sb.append(URLEncoder.encode(v.getKey(), "UTF-8"));
				sb.append("=");
				sb.append(URLEncoder.encode(v.getValue(), "UTF-8"));
				sb.append("&");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		
		url = url.endsWith("?")?url+sb.toString():url+"&"+sb.toString();
		
		Log.i("HttpHelper", url);
		
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			int httpCode = response.getStatusLine().getStatusCode();
			if (httpCode==200) {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
	public String Post(String url, Map<String, String> value) {
		String result = "";
		HttpURLConnection conn = URLConnection(url);
		if (conn != null) {
			try {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setDoInput(true);
				if (value != null) {
					StringBuilder params = new StringBuilder();
					Iterator<Entry<String, String>> iterator = value.entrySet()
							.iterator();
					while (iterator.hasNext()) {
						Entry<String, String> v = iterator.next();
//						params.append(URLEncoder.encode(v.getKey(), "UTF-8"));
						params.append(v.getKey());
						params.append("=");
						params.append(v.getValue());
						params.append("&");
					}
					if (params.length() > 0)
						params.deleteCharAt(params.length() - 1);
					DataOutputStream dos = new DataOutputStream(
							conn.getOutputStream());// 这里建立tcp连接，如果timeout会出现connectTimeOutException
					dos.write(params.toString().getBytes("utf-8"));
					dos.flush();
					dos.close();
				}
				
				@SuppressWarnings("unused")
				int code = conn.getResponseCode();
//				Log.v("tn", "code = " + code);
				
				InputStreamReader isr = new InputStreamReader(
						conn.getInputStream(), "utf-8");
				BufferedReader br = new BufferedReader(isr);
				String temp = null;
				result = "";
				while ((temp = br.readLine()) != null) {
					result += temp;
				}
				isr.close();
				conn.disconnect();
			} catch (ConnectException e) {
				e.printStackTrace();
				result = "";
			} catch (IOException e) {
				e.printStackTrace();
				result = "";
			}
		}
		return result;
	}
}
