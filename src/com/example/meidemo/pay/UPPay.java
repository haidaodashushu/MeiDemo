package com.example.meidemo.pay;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.meidemo.CommonUtils.HttpHelper;
import com.example.meidemo.CommonUtils.StringUtils;
import com.example.meidemo.data.GlobalData;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

/**
 * 银联支付
 * 
 * @author wangzhengkui
 */
public class UPPay implements Callback{
	private static final String LOG_TAG = "PayDemo";
	private static final int PLUGIN_VALID = 0;
	private static final int PLUGIN_NOT_INSTALLED = -1;
	private static final int PLUGIN_NEED_UPGRADE = 2;

	/*****************************************************************
	 * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
	 *****************************************************************/
	private String mMode = "01";
	Context context;
	Handler mHandler;
	String priceStr;
	String orderId;
	private ProgressDialog mLoadingDialog = null;

	private static final String TN_URL_01 = GlobalData.ip+GlobalData.YINLIANPAY;
	
	DecimalFormat format = new DecimalFormat("#0.##");
	public UPPay(Context context, String priceStr,String orderId) {
		this.context = context;
		mHandler = new Handler(this);
		if (priceStr.equals("")) {
//			Common.ShowInfo(instance, "充值金额不能为空");
			Toast.makeText(context, "充值金额不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!isDecimal(priceStr)){
			Toast.makeText(context, "充值金额不正确", Toast.LENGTH_SHORT).show();
//			Common.ShowInfo(instance, "充值金额不正确");
			return;
		}
		//保证传进来的orderId>0
		try {
			if (Double.parseDouble(orderId)<=0) {
				Toast.makeText(context, "参数错误", Toast.LENGTH_SHORT).show();
				return;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, "参数错误", Toast.LENGTH_SHORT).show();
			return;
		}
		this.orderId = orderId;
		this.priceStr = priceStr;
		
	}
	public void startPay() {
		getTn();
	}
	/**
	 * 获取tn号
	 */
	private void getTn() {
		mLoadingDialog = ProgressDialog.show(context, // context
				"", // title
				"正在努力的获取tn中,请稍候...", // message
				true); // 进度是否是不确定的，这只和创建进度条有关

		/*************************************************
		 * 
		 * 步骤1：从网络开始,获取交易流水号即TN
		 * 
		 ************************************************/
		new Thread(mRunnable).run();

	}
	Runnable mRunnable = new Runnable() {
		
		@Override
		public void run() {

			String tn = null;
			try {

				String url = TN_URL_01;
				
				JSONObject req = new JSONObject();
				req.put("orderid", orderId);
				req.put("memberid", "0");
				double price = Double.parseDouble(priceStr);
				req.put("amount",format.format(price*100) );
				req.put("clientid", GlobalData.clientID);
				req.put("clientty", GlobalData.clientty);
				req.put("appversion", GlobalData.appVersion);
				req.put("descripe", "");
				
				
				Map<String, String> values = new HashMap<String, String>();
				values.put("callback", "1");
				values.put("sid", GlobalData.sid);
				values.put("trade", req.toString());
				
				String result = HttpHelper.getInstance(context).Post(TN_URL_01, values);
				if (result!=null&&!"".equals(result)) {
					Message msg = mHandler.obtainMessage();
//					tn = "201409181655020010972";
					tn = result;
					result = StringUtils.removeParentheses(result);
					JSONObject obj = new JSONObject(result);
					tn = obj.getString("tn");
					String respCode = obj.getString("respCode");
					
					msg.obj = tn;
					mHandler.sendMessage(msg);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			

		}
	};
	

	@Override
	public boolean handleMessage(Message msg) {
		Log.e(LOG_TAG, " " + "" + msg.obj);
		if (mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}

		String tn = "";
		if (msg.obj == null || ((String) msg.obj).length() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("错误提示");
			builder.setMessage("网络连接失败,请重试!");
			builder.setNegativeButton("确定",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create().show();
		} else {
			tn = (String) msg.obj;
			/*************************************************
			 * 
			 * 步骤2：通过银联工具类启动支付插件
			 * 
			 ************************************************/
			// mMode参数解释：
			// 0 - 启动银联正式环境
			// 1 - 连接银联测试环境
			UPPayAssistEx.startPayByJAR((Activity) context, PayActivity.class, null, null, tn, mMode);
			/*int ret = UPPayAssistEx.startPay((Activity) context, null, null,tn, mMode);
			if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
				// 需要重新安装控件
				Log.e(LOG_TAG, " plugin not found or need upgrade!!!");

				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("提示");
				builder.setMessage("完成购买需要安装银联支付控件，是否安装？");

				builder.setNegativeButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								UPPayAssistEx.installUPPayPlugin(context);
							}
						});

				builder.setPositiveButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();

			}
			Log.e(LOG_TAG, "" + ret);*/
		}

		return false;
	}
	/** 验证是否为全数字 **/
	public static boolean isDecimal(String str){  
		  return Pattern.compile("([1-9]+[0-9]*|0)(\\.[\\d]+)?").matcher(str).matches();  
	}
	class KeyAndValue{
		String key;
		String value;
	}
	
	class ComparatorUser implements Comparator{
		 public int compare(Object arg0, Object arg1) {
			 KeyAndValue kv0=(KeyAndValue)arg0;
			 KeyAndValue kv1=(KeyAndValue)arg1;
			 int flag=kv0.key.compareTo(kv1.key);
			 return flag;
		 }
	}
}
