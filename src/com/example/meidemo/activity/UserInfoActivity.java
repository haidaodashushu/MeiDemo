package com.example.meidemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.SharedPreferenceUtil;
import com.example.meidemo.data.GlobalData;

public class UserInfoActivity extends Activity implements OnClickListener {
	Button unLoginBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userinfolayout);
		unLoginBtn = (Button) findViewById(R.id.unlogin_btn);
		unLoginBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.unlogin_btn:
			SharedPreferenceUtil.removeString(this, GlobalData.SPINFOTOKEN);
			SharedPreferenceUtil.removeString(this, GlobalData.SPINFOPHONE);
			/*UserInfoActivity.this.finish();*/
			setResult(GlobalData.UNLOING_RESULT_OK, null);
			UserInfoActivity.this.finish();
			break;

		default:
			break;
		}
	}
}
