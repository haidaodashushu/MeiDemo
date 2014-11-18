package com.example.meidemo.activity;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.SharedPreferenceUtil;
import com.example.meidemo.data.GlobalData;
import com.example.meidemo.pay.UPPay;
import com.example.meidemo.view.entity.PayOrderInfo;

public class PayOrderActivity extends Activity implements OnClickListener{
	Bundle bundle;
	String name;
	String price;
	String phone;
	String sid;
	int count;
	double sum;
	/** 团购ID */
	String id;
	TextView nameTv, priceTv, sumPriceTv, phoneTv, phoneNewTv,restPayTv;
	EditText countTv;
	LinearLayout payLv;
	Button commitBtn;
	PayOrderInfo payOinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_order_linearlayout);
		nameTv = (TextView) findViewById(R.id.name);
		priceTv = (TextView) findViewById(R.id.price);
		restPayTv = (TextView)findViewById(R.id.rest_pay);
		sumPriceTv = (TextView)findViewById(R.id.price_sum);
		countTv = (EditText) findViewById(R.id.count);
		payLv = (LinearLayout) findViewById(R.id.payLv);
		commitBtn = (Button)findViewById(R.id.commit_btn);
		bundle = getIntent().getExtras();
		// 订单的name和price由抢购界面传过来
		bundle = getIntent().getExtras();
		name = bundle.getString("name");
		price = bundle.getString("price");
		id = bundle.getString("id");
		count = bundle.getInt("count");
		sum = bundle.getDouble("sum");
		payOinfo = (PayOrderInfo) bundle.getSerializable("orderInfo");

		nameTv.setText(name);
		priceTv.setText(price);
		countTv.setText(count + "");
		countTv.setEnabled(false);
		restPayTv.setText(payOinfo.paymoney);
		sumPriceTv.setText(sum+"");
		
		phone = SharedPreferenceUtil.getString(this, GlobalData.SPINFOPHONE);
		sid = SharedPreferenceUtil.getString(this, GlobalData.SPINFOTOKEN);

		if (payOinfo != null && payOinfo.paymoney.equals("0")) {
			payLv.setVisibility(View.GONE);
		}
		commitBtn.setOnClickListener(this);
	}
	/**
	 * 支付订单,直接调用接口，不需要银联支付
	 */
	private void requestPay() {
		FinalHttp http = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("sid", sid);
		params.put("orderid", payOinfo.orderid);
		params.put("money", sum+"");
		http.get(GlobalData.ip+GlobalData.PAYORDER, params, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Log.i("PayOrderActivity", t.toString());
//				{"state":"1","result":"\u652f\u4ed8\u6210\u529f","orderid":"64"}
				try {
					JSONObject object = new JSONObject(t.toString());
					String orderId = object.getString("orderid");
					String priceStr = object.getString("paymoney");
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.commit_btn:
			
			if (payOinfo != null && payOinfo.paymoney.equals("0")) {
				payLv.setVisibility(View.GONE);
				requestPay();
			}else{
				UPPay pay = new UPPay(this, payOinfo.paymoney, payOinfo.orderid);
				pay.startPay();
			}
			
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		 /************************************************* 
         * 
         *  步骤3：处理银联手机支付控件返回的支付结果 
         *  
         ************************************************/
        if (data == null) {
            return;
        }

        String msg = "";
        /*
         * 支付控件返回字符串:success、fail、cancel
         *      分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            msg = "支付成功！";
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付结果通知");
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        //builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
	}

}
