package com.example.meidemo.activity;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meidemo.MainActivity;
import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.SharedPreferenceUtil;
import com.example.meidemo.data.GlobalData;

public class RegisterActivity extends Activity implements OnClickListener {
	EditText user, phone, password, passwordConfirm;
	Button registerBtn;
	TextView getPhone,getCode,getPassword;
	FrameLayout registerSetpFv;
	int step = 1;
	View phoneView,codeView,passwordView;
	Animation rightIn,leftout;
	String phoneStr,codeStr,passwordStr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
//		user = (EditText) findViewById(R.id.user);
//		phone = (EditText) findViewById(R.id.phone);
//		password = (EditText) findViewById(R.id.password);
//		passwordConfirm = (EditText) findViewById(R.id.password_confirm);
		registerBtn = (Button) findViewById(R.id.register_btn);
		registerSetpFv = (FrameLayout)findViewById(R.id.register_setp_fv);
		getPhone = (TextView) findViewById(R.id.get_phone);
		getCode = (TextView) findViewById(R.id.get_code);
		getPassword = (TextView) findViewById(R.id.get_password);
		phoneView = LayoutInflater.from(this).inflate(R.layout.regitster_getphone, null);
		codeView = LayoutInflater.from(this).inflate(R.layout.regitster_getcode, null);
		passwordView = LayoutInflater.from(this).inflate(R.layout.regitster_getpassword, null);
		rightIn = AnimationUtils.loadAnimation(this, R.anim.right_in);
		leftout = AnimationUtils.loadAnimation(this, R.anim.left_out);
		registerBtn.setOnClickListener(this);
		setNextStep(step,false);
	}

	private boolean validate() {
		EditText view = null;
		switch (step) {
		case 1:
			view = (EditText)findViewById(R.id.phone);
			phoneStr = view.getText().toString();
			if (phoneStr==null||"".equals(phoneStr)) {
				String error = "不能为空";
				setErrorTextColor(view, error);
				return false;
			}else {
				view.setError(null);
			}
			break;
		case 2:
			view = (EditText)findViewById(R.id.code);
			codeStr = view.getText().toString();
			if (codeStr==null||"".equals(codeStr)) {
				String error = "不能为空";
				setErrorTextColor(view, error);
				return false;
			}else {
				view.setError(null);
			}
			break;
		case 3:
			view = (EditText)findViewById(R.id.password);
			passwordStr = view.getText().toString();
			
			EditText view1 = (EditText)findViewById(R.id.password_confirm);
			String content1 = view1.getText().toString();
			if (passwordStr==null||"".equals(passwordStr)) {
				String error = "不能为空";
				setErrorTextColor(view, error);
				return false;
			}else {
				view.setError(null);
				if (content1==null||"".equals(content1)) {
					String error = "不能为空";
					setErrorTextColor(view1, error);
					return false;
				}else if(!content1.equals(passwordStr)){
					String error = "密码不一致";
					setErrorTextColor(view1, error);
					return false;
				}else {
					view1.setError(null);
				}
			}
			break;
		}
		return true;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_btn:
			if (step==1) {
				if (validate()) {
					step+=1;
					setNextStep(step,true);
				}
			}else if (step==2) {
				if (validate()) {
					step+=1;
					setNextStep(step,true);
				}
			}else if (step==3) {
				if (validate()) {
					step+=1;
					requestRegister();
				}
			}
			
			break;

		default:
			break;
		}
	}
	public void setNextStep(int step,boolean hasAnimation) {
		this.step = step;
		//处在第几布
		if (step==1) {
			registerSetpFv.removeAllViews();
			registerSetpFv.addView(phoneView);
			phoneView.requestFocus();
			if (hasAnimation) {
				phoneView.startAnimation(rightIn);
			}
			
			resetColor();
			getPhone.setSelected(true);
		}else if (step == 2) {
			registerSetpFv.removeAllViews();
			registerSetpFv.addView(codeView);
			codeView.requestFocus();
			if (hasAnimation) {
				phoneView.startAnimation(leftout);
				codeView.startAnimation(rightIn);
			}

			resetColor();
			getCode.setSelected(true);
		}else if (step == 3) {
			registerSetpFv.removeAllViews();
			registerSetpFv.addView(passwordView);
			passwordView.requestFocus();
			if (hasAnimation) {
				codeView.startAnimation(leftout);
				passwordView.startAnimation(rightIn);
			}
			
			resetColor();
			getPassword.setSelected(true);
		}else if(step==4){
			
		}
	}
	/**
	 * 注册
	 */
	private void requestRegister() {
		FinalHttp http = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("user", phoneStr);
		params.put("phone", phoneStr);
		params.put("code", "1234");
		params.put("pwd", passwordStr);
		params.put("comfirpwd", passwordStr);
		http.get(GlobalData.ip+GlobalData.REGISTER, params, new  AjaxCallBack<Object>() {
			ProgressDialog dialog  = null;
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				dialog = ProgressDialog.show(RegisterActivity.this, null, "正在注册");
			}
			@Override
			public void onSuccess(Object t) {
				Log.i("注册", t.toString());
				dialog.dismiss();
				
				try {
					JSONObject obj = new JSONObject(t.toString());
					String sid = obj.getString("sid");
					String msg = obj.getString("msg");
					String state = obj.getString("state");
					if ("1".equals(state)) {
						Toast.makeText(RegisterActivity.this,msg, Toast.LENGTH_LONG).show();
						SharedPreferenceUtil.setString(RegisterActivity.this, GlobalData.SPINFOTOKEN, sid);
						SharedPreferenceUtil.setString(RegisterActivity.this, GlobalData.SPINFOPHONE, phoneStr);
						
						Intent intent = new Intent();
//						intent.putExtra("user", phoneStr);
//						intent.putExtra("password", passwordStr);
						setResult(GlobalData.REGISTER_RESULT_OK, intent);
						RegisterActivity.this.finish();
					}else {
						Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();
						setNextStep(1, false);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				Log.e("注册", "失败");
				dialog = ProgressDialog.show(RegisterActivity.this, null, "注册失败");
			}
		});
	}
	/**
	 * 重置步骤提示文字颜色
	 */
	private void resetColor() {
		// TODO Auto-generated method stub
		getCode.setSelected(false);
		getPassword.setSelected(false);
		getPhone.setSelected(false);
	}
	
	private void setErrorTextColor(TextView view,String error) {
		ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.RED);
		SpannableStringBuilder ssbuilder = new SpannableStringBuilder(error);
		ssbuilder.setSpan(fgcspan, 0, error.length(), 0);
		view.requestFocus();
		view.setError(ssbuilder);
	}
}
