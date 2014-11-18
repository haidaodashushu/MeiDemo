package com.example.meidemo;

import com.baidu.location.LocationClient;
import com.example.meidemo.CommonUtils.SharedPreferenceUtil;
import com.example.meidemo.data.GlobalData;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class MainApplication extends Application{
	public LocationClient locationClient;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		locationClient = new LocationClient(getApplicationContext());
		//获取版本信息
		PackageManager manager = getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			GlobalData.appVersion = info.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GlobalData.sid = SharedPreferenceUtil.getString(this, GlobalData.SPINFOTOKEN);
	}
}
