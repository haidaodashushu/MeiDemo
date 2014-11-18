package com.example.meidemo.activity;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meidemo.MainActivity;
import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.SharedPreferenceUtil;
import com.example.meidemo.data.GlobalData;

public class LoginActivity extends Activity implements OnClickListener{
	EditText user,password;
	Button loginBtn;
	String userStr,passwordStr;
	TextView registerTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		user = (EditText)findViewById(R.id.user);
		password = (EditText)findViewById(R.id.password);
		loginBtn = (Button)findViewById(R.id.login_btn);
		registerTv = (TextView)findViewById(R.id.register_tv);
		registerTv.setClickable(true);
		registerTv.setOnClickListener(this);
		loginBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			if (validate()) {
				requesetLogin(userStr, passwordStr);
			}
			break;
		case R.id.register_tv:
			Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
			startActivityForResult(intent,1);
			break;
		default:
			break;
		}
	}
	private boolean validate() {
		userStr = user.getText().toString();
		passwordStr = password.getText().toString();
		if (userStr==null||"".equals(userStr)) {
			String error = "不能为空";
			setErrorTextColor(user, error);
			return false;
		}else{
			user.setError(null);
			if (passwordStr==null||"".equals(passwordStr)) {
				
				String error = "不能为空";
				setErrorTextColor(password, error);
				return false;
			}else {
				password.setError(null);
			}
		} 
		return true;

	}
	private void requesetLogin(final String user,String password) {
		FinalHttp http = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("user", user);
		params.put("pwd", password);
		http.get(GlobalData.ip+GlobalData.LOGIN, params, new  AjaxCallBack<Object>() {
			ProgressDialog dialog  = null;
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				dialog = ProgressDialog.show(LoginActivity.this, null, "正在登录");
			}
			@Override
			public void onSuccess(Object t) {
				Log.i("登录", t.toString());
				dialog.dismiss();
				
				try {
					JSONObject obj = new JSONObject(t.toString());
					String sid = obj.getString("sid");
					String msg = obj.getString("msg");
					String state = obj.getString("state");
					if ("1".equals(state)) {
						Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
						SharedPreferenceUtil.setString(LoginActivity.this, GlobalData.SPINFOTOKEN, sid);
						SharedPreferenceUtil.setString(LoginActivity.this, GlobalData.SPINFOPHONE, user);
						/*Intent intent = new Intent();
						intent.setClass(LoginActivity.this, MainActivity.class);
						intent.putExtra("from", "Login");
						intent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
						startActivity(intent);*/
						
						setResult(GlobalData.LOING_RESULT_OK, null);
						LoginActivity.this.finish();
						
					}else {
						Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				Log.e("登录", "失败");
				dialog = ProgressDialog.show(LoginActivity.this, null, "登录失败");
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==1) {
			//从注册界面跳转
//			if (data!=null) {
//				String user = data.getStringExtra("user");
//				String password = data.getStringExtra("password");
//				requesetLogin(user, password);
//			}
			if (resultCode==GlobalData.REGISTER_RESULT_OK) {
				setResult(GlobalData.LOING_RESULT_OK, null);
				LoginActivity.this.finish();
			}else {
				//donothing
			}
			
			
		}
	}
	private void setErrorTextColor(TextView view,String error) {
		ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
		SpannableStringBuilder ssbuilder = new SpannableStringBuilder(error);
		ssbuilder.setSpan(fgcspan, 0, error.length(), 0);
		view.requestFocus();
		view.setError(ssbuilder);
	}
}
