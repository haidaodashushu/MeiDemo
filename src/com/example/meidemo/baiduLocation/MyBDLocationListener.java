package com.example.meidemo.baiduLocation;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

public class MyBDLocationListener implements BDLocationListener{
	public MyBDLocationListener() {
		// TODO Auto-generated constructor stub
	}
	OnLocationListener listener;
	@Override
	public void onReceiveLocation(BDLocation location) {
		LocationDate locationDate = LocationDate.getInstance();
		
		locationDate.time = location.getTime();
		locationDate.errorCode = location.getLocType();
		locationDate.latitude = location.getLatitude();
		locationDate.lontitude = location.getLongitude();
		locationDate.radius = location.getRadius();
		locationDate.addr = location.getAddrStr();
		locationDate.city = location.getCity();
		locationDate.province = location.getProvince();
		locationDate.district = location.getDistrict();
		listener.onListener(location);
		/*if (locationDate.errorCode==61||locationDate.errorCode==66|| 
				locationDate.errorCode==161||locationDate.errorCode ==65) {
			//定位成功
			listener.onSuccess((args.equals(locationDate.time)?locationDate.time:(
					args.equals(locationDate.errorCode)?locationDate.errorCode:(args.equals(locationDate.latitude)?locationDate.time:
						args.equals(locationDate.time)?locationDate.time:(args.equals(locationDate.time)?locationDate.time:
					(args.equals(locationDate.time)?locationDate.time:""))))));
		}*/
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nerror code : ");
		sb.append(location.getLocType());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		sb.append("\nradius : ");
		sb.append(location.getRadius());
		if (location.getLocType() == BDLocation.TypeGpsLocation){
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
			sb.append("\ndirection : ");
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			sb.append(location.getDirection());
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			sb.append("\noperationers : ");
			sb.append(location.getOperators());
		}
		Log.i("BaiduLocationApiDem", sb.toString());
	}
	public interface OnLocationListener{
		public void onListener(BDLocation data);
	}
	public OnLocationListener getListener() {
		return listener;
	}
	public void setListener(OnLocationListener listener) {
		this.listener = listener;
	}
	
	
}
