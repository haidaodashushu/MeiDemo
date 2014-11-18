package com.example.meidemo.myInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.meidemo.BaseFragment;
import com.example.meidemo.R;
import com.example.meidemo.activity.UserInfoActivity;

public class MyinfoFragment extends BaseFragment implements OnClickListener{
	Activity mActivity;
	View contentView;
	LinearLayout usercenterLv;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		contentView = inflater.inflate(R.layout.myinfo_fragment, container, false);
		return contentView;
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = activity;
	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		usercenterLv = (LinearLayout)contentView.findViewById(R.id.usercenterLv);
		usercenterLv.setClickable(true);
		usercenterLv.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.usercenterLv:
			Intent intent = new Intent();
			intent.setClass(mActivity, UserInfoActivity.class);
			startActivityForResult(intent,1);
			break;
		default:
			break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
}
