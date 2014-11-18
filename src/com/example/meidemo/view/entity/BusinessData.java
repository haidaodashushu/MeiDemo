package com.example.meidemo.view.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BusinessData extends BaseInfo{
	public List<BusinessViewGrid> topInfos = new ArrayList<BusinessViewGrid>();
	public BusinessData(String data) {
		try {
			JSONArray array = new JSONArray(data);
			int count = array.length();
			for (int i = 0; i < count; i++) {
				JSONObject o = array.getJSONObject(i);
				BusinessViewGrid topInfo = getSelectInfo(o.toString());
				topInfos.add(topInfo);
			}
		} catch (Exception e) {

		}
	}
	
	public BusinessData(){
		
	}
	
	public BusinessViewGrid getSelectInfo(String s){
		try {
			BusinessViewGrid partnerInfo = new BusinessViewGrid();
			JSONObject o = new JSONObject(s);
			partnerInfo.setBusTitle(getString("title", o));
			partnerInfo.setBusImg(getString("image", o));
			partnerInfo.setBusScore((float)getDouble("score", o));
			partnerInfo.setPingLun(getint("pingnum", o));
//			partnerInfo.setBusTuanFlag(getBoolean("tuanflag", o));
			partnerInfo.setBusZone(getString("zone", o));
			 
			partnerInfo.setBusCategory(getString("name",o));
			return partnerInfo;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
