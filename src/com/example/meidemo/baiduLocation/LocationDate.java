package com.example.meidemo.baiduLocation;

public class LocationDate {
	
	private static LocationDate locationDate;
	private LocationDate(){
		
	}
	public static synchronized LocationDate getInstance(){
		if (locationDate==null) {
			locationDate = new LocationDate();
		}
		return locationDate;		
	}
	public String time;
	public float errorCode;
	public  double latitude;
	public  double lontitude;
	public  double radius;
	public  String addr;
	public  int operationers;
	public  String province;
	public  String city;	
	public  String district;
}
