package com.example.meidemo.view.entity;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;

public class TuanInfos {
	
	ArrayList<TuanInfo> tuaninfos ;
	public TuanInfos(String str) {
		// TODO Auto-generated constructor stub
		
		if (str==null||"".equals(str)) {
			return;
		}
		tuaninfos= new ArrayList<TuanInfo>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JSONArray array = new JSONArray(str);
			for (int i = 0; i < array.length(); i++) {
				TuanInfo tuanInfo = mapper.readValue(array.getString(i), TuanInfo.class);
				tuaninfos.add(tuanInfo);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public ArrayList<TuanInfo> getTuaninfos() {
		return tuaninfos;
	}
	public void setTuaninfos(ArrayList<TuanInfo> tuaninfos) {
		this.tuaninfos = tuaninfos;
	}
	
	
}
