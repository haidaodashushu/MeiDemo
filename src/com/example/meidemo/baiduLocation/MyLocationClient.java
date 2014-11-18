package com.example.meidemo.baiduLocation;

import android.app.Activity;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.meidemo.MainApplication;
import com.example.meidemo.baiduLocation.MyBDLocationListener.OnLocationListener;

public class MyLocationClient {
	private LocationClient mLocationClient;
	private MyBDLocationListener mBdLocationListener;
	private static MyLocationClient myLocationClient;
	private MyLocationClient(){
		
	}
	public static synchronized MyLocationClient getInstance(){
		if (myLocationClient==null) {
			myLocationClient = new MyLocationClient();
		}
		return myLocationClient;		
	}
	public  void initLocation(Activity activity,OnLocationListener listener){
		mLocationClient = ((MainApplication)activity.getApplication()).locationClient;
		mBdLocationListener = new MyBDLocationListener();
		mBdLocationListener.setListener(listener);
		mLocationClient.registerLocationListener(mBdLocationListener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		
	}
	
	
}
