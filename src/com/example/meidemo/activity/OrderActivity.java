package com.example.meidemo.activity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.SharedPreferenceUtil;
import com.example.meidemo.R.id;
import com.example.meidemo.R.layout;
import com.example.meidemo.data.GlobalData;
import com.example.meidemo.view.entity.PayOrderInfo;

public class OrderActivity extends Activity implements OnClickListener{
	Button addBtn,subBtn,commitBtn;
	TextView nameTv,priceTv,sumPriceTv,phoneTv,phoneNewTv;
	EditText countTv;
	String name;
	String price;
	String phone;
	String sid ;
	/**团购ID*/
	String id;
	int count = 1;
	double sum;
	Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commit_order_linearlayot);
		addBtn = (Button)findViewById(R.id.add_btn);
		subBtn = (Button)findViewById(R.id.sub_btn);
		commitBtn = (Button)findViewById(R.id.commit_btn);
		
		nameTv = (TextView)findViewById(R.id.name);
		priceTv = (TextView)findViewById(R.id.price);
		sumPriceTv = (TextView)findViewById(R.id.price_sum);
		countTv = (EditText)findViewById(R.id.count);
		phoneTv = (TextView)findViewById(R.id.phone);
		phoneNewTv = (TextView)findViewById(R.id.phone_new);
		
		//订单的name和price由抢购界面传过来
		bundle = getIntent().getExtras();
		name = bundle.getString("name");
		price = bundle.getString("price");
		id = bundle.getString("id");
		nameTv.setText(name);
		priceTv.setText(price);
		
		phone  = SharedPreferenceUtil.getString(this, GlobalData.SPINFOPHONE);
		sid = SharedPreferenceUtil.getString(this, GlobalData.SPINFOTOKEN);
		
		if (phone==null||"".equals(phone)) {
			phoneTv.setText("");
		}
		else {
			phoneTv.setText(phone.replaceAll("(\\d{3})(\\d+)(\\d{4})", "$1****$3"));
		}
		addBtn.setOnClickListener(this);
		subBtn.setOnClickListener(this);
		commitBtn.setOnClickListener(this);
		phoneNewTv.setOnClickListener(this);
		
		countTv.setSelection(countTv.getText().length());
		//添加数字输入监听
		countTv.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				if (arg0.toString().equals("")) {
					return;
				}
				count = Integer.parseInt(arg0.toString());
				if (count==1) {
					subBtn.setEnabled(false);
				}else {
					subBtn.setEnabled(true);
				}
				sum = count* Double.parseDouble(price);
				sumPriceTv.setText(sum+"元");
			}
		});
		setCount(count);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add_btn:
			count+=1;
			setCount(count);
			break;
		case R.id.sub_btn:
			if (count!=1) {
				count-=1;
				setCount(count);
			}
			
			break;
		case R.id.commit_btn:
			commitOrder();
			
			break;
		case R.id.phone_new:
			
			break;
		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0==1 &&( arg1 == GlobalData.LOING_RESULT_OK||arg1==GlobalData.REGISTER_RESULT_OK)) {
			phone  = SharedPreferenceUtil.getString(this, GlobalData.SPINFOPHONE);
			sid = SharedPreferenceUtil.getString(this, GlobalData.SPINFOTOKEN);
			phoneTv.setText(phone.replaceAll("(\\d{4})(\\d+)(\\d{4})", "$1****$3"));
		}
	}
	private void commitOrder() {
		
		if (phone==null||"".equals(phone)||sid==null||"".equals(sid)) {
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivityForResult(intent, 1);
			return;
		}
		JSONObject object = new JSONObject();
		try {
			JSONArray array = new JSONArray();
			
			JSONObject object2 = new JSONObject();
			object2.put("teamid", id);
			object2.put("quantity", count+"");
			object2.put("price", price);
			object2.put("money", sum);
			array.put(object2);
			
			
			//总单
			object.put("service", "");
			object.put("money", sum);
			
			object.put("mobile",phone);
			object.put("cityid", GlobalData.cityId);
			object.put("realname", "");
			object.put("zipcode", "");
			object.put("address", "");
			object.put("clientid", GlobalData.clientID);
			object.put("clientty", "12500");//渠道号
			object.put("version", GlobalData.appVersion);
			object.put("order", array);
			
			String msg = Base64.encodeToString(object.toString().getBytes(), Base64.DEFAULT);
			FinalHttp http = new FinalHttp();
			AjaxParams params = new AjaxParams();
			params.put("msg", msg);
			params.put("sid", sid);
			
			http.get(GlobalData.ip+GlobalData.ORDER, params, new AjaxCallBack<Object>() {
				@Override
				public void onSuccess(Object t) {
					// TODO Auto-generated method stub
					super.onSuccess(t);
					
					Log.i("OrderActivity", t.toString());
					try {
						PayOrderInfo orderInfo  = new ObjectMapper().readValue(t.toString(), PayOrderInfo.class);
						//返回信息是否为1
						if ("1".equals(orderInfo.getState())) {
							//写入本地缓存
							FileOutputStream fout =openFileOutput("log.txt", MODE_PRIVATE);
							byte [] bytes = t.toString().getBytes(); 

					        fout.write(bytes); 
					        
					        fout.close();
					        Intent intent = new Intent(OrderActivity.this,PayOrderActivity.class);
							if (bundle==null) {
								bundle = new Bundle();
							}
							bundle.putSerializable("orderInfo", orderInfo);
							bundle.putInt("count", count);
							bundle.putDouble("sum", sum);
							intent.putExtras(bundle);
							startActivity(intent);
						}else {
							Toast.makeText(OrderActivity.this, "获取订单错误", Toast.LENGTH_SHORT).show();
						}
					} catch (JsonParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						Toast.makeText(OrderActivity.this, "订单解析错误", Toast.LENGTH_SHORT).show();
					} catch (JsonMappingException e1) {
						// TODO Auto-generated catch block
						Toast.makeText(OrderActivity.this, "订单解析错误", Toast.LENGTH_SHORT).show();
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 设置团购数量
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
		if (count==1) {
			subBtn.setEnabled(false);
		}else {
			subBtn.setEnabled(true);
		}
		sum = count* Double.parseDouble(price);
		sumPriceTv.setText(sum+"元");
		countTv.setText(count+"");
		countTv.setSelection(countTv.getText().length());
	}
}
