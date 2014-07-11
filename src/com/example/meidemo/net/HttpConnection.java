package com.example.meidemo.net;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.example.meidemo.CommonUtils.StringUtils;

import android.content.Context;
import android.util.Log;

public class HttpConnection {
	private final static String USER_AGENT_VALUE = "Mozilla/5.0 (Linux; U; Android 2.2; zh-cn; Desire_A8181 Build/FRF91) "
			+ "AppleWebKit/533.1 (KHTML, likeGecko) "
			+ "Version/4.0 Mobile Safari/533.1 ";
	private static String _Content_Type = "application/xml";
	private static final int overTime = 20000;// 15000 20000
	DefaultHttpClient httpclient = null;
	HttpPost httppost;
	HttpGet httpget;
	
	private final static String ClientProtocolException = "1";
	private final static String SocketTimeoutException = "2";
	private final static String Exception = "3";
	private final static String URL_NULL = "4";
	private final static String RESPONSE_NULL = "5";
	
	public DataInputStream dis;
	
	public interface HttpConnectionListener{
		public String onSuccess(String message);
		public String onFailure(String message);
	}
	public String handleConnection(String url, Context context,
			List<NameValuePair> data,HttpConnectionListener listener) throws Exception {

		long time = System.currentTimeMillis();
		// Log.v("Mytime", "1 ----"+System.currentTimeMillis());
		if (url == null) {
			listener.onFailure(HttpConnection.URL_NULL);
			return HttpConnection.URL_NULL;
		}
		HttpResponse response = null;
		try {

			int netWorkType = NetWorkManager.getNetworkType(context);

			httpclient = new DefaultHttpClient();
			HttpParams httpParams = httpclient.getParams();
			if (NetWorkManager.Connenction_cmwap == netWorkType) {
				HttpHost proxy = new HttpHost(NetWorkManager.wapurl, 80);
				httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
			}
			httpclient.getParams().setBooleanParameter(
					CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
			httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, overTime); // 从socket读数据时发生阻塞的超时时间
			httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
					overTime); // 连接的超时时间
			

			if (null != data) {
				httppost = new HttpPost(url);
				httppost.setEntity(new UrlEncodedFormEntity(data));
				response = httpclient.execute(httppost);
			} else {
				// 目标地址
				httpget = new HttpGet(url);
				httpget.addHeader("Content-Type", _Content_Type);
				// 执行
				response = httpclient.execute(httpget);
			}
		} catch (ClientProtocolException e) {

			httpclient.getConnectionManager().shutdown();
			listener.onFailure(HttpConnection.ClientProtocolException);
			throw e;
		} catch (SocketTimeoutException e) {

			httpclient.getConnectionManager().shutdown();
			listener.onFailure(HttpConnection.SocketTimeoutException);
			throw e;
		} catch (Exception e) {

			httpclient.getConnectionManager().shutdown();
			listener.onFailure(HttpConnection.Exception);
			throw e;
		}

		int reponseCode = 0;
		try {
			reponseCode = response.getStatusLine().getStatusCode();

		} catch (Exception e) {
			httpclient.getConnectionManager().shutdown();
			throw e;
		}

		if (reponseCode != HttpStatus.SC_OK
				&& reponseCode != HttpStatus.SC_PARTIAL_CONTENT) {
			httpclient.getConnectionManager().shutdown();
		} else {
			HttpEntity entity = response.getEntity();
			
			// TODO：10.17做处理 ，如果返回的数据的entry是空的，就不执行业务了，不然会挂
			if (null != entity) {// 保护
				
				if (null != entity.getContentType()) {
					StringBuffer sb = new StringBuffer();
					String temp = EntityUtils.toString(response.getEntity());
//					Log.i("HTTP", temp);
					temp = StringUtils.decodeUnicode(temp);
//					Log.i("HTTP1", temp);
					sb.append(temp);
					temp=sb.toString();
					sb = null;
					listener.onSuccess(temp); 
					
					return temp;
					
				}
			} else {// 保护
				listener.onFailure(HttpConnection.RESPONSE_NULL);
				return "";
			}

			/*try {
				dis = new DataInputStream(entity.getContent());
				
				StringBuffer sb = new StringBuffer();
				byte [] b2=new byte[1024];
				  int len;
				while ((len=dis.read(b2))!=-1) {
//					string = StringUtils.decodeUnicode(string);
					Log.i("HttpConnection", string);
					string = StringUtils.fromUnicode(string.toCharArray(), 0,
							 string.length(), new char[string.length()]);
					sb.append(string);
				}
				return sb.toString();
				// Log.v("Mytime", "耗时 --"+(System.currentTimeMillis() - time) +
				// "---" + request.getUrl());
			} catch (IllegalStateException e) {
				// Log.v("Mytime", "出错 --"+(System.currentTimeMillis() - time) +
				// "---" + request.getUrl());
				throw e;
			} catch (IOException e) {
				// Log.v("Mytime", "出错 --"+(System.currentTimeMillis() - time) +
				// "---" + request.getUrl());
				throw e;
			} finally {
			}*/
		}
		return ""; 

	}
}
