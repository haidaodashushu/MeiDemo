package com.example.meidemo.view.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;

public class CategaryFoods {
//	List<List<CategoryFood>> list;
	public List<HashMap<List<CategoryFood>, Boolean>> list;
	public CategaryFoods(String str) {
		try {
			ObjectMapper mapper = new ObjectMapper();
//			list =new ArrayList<List<CategoryFood>>();
			list = new ArrayList<HashMap<List<CategoryFood>,Boolean>>();
			JSONArray array = new JSONArray(str);
			for (int i = 0; i < array.length(); i++) {
				JSONArray array2 = array.getJSONArray(i);
				List<CategoryFood> list2 = new ArrayList<CategoryFood>();
				HashMap<List<CategoryFood>, Boolean> map = new LinkedHashMap<List<CategoryFood>, Boolean>();
				for (int j = 0; j < array2.length(); j++) {
					CategoryFood categary = mapper.readValue(array2.getString(j), CategoryFood.class);
					list2.add(categary);
				}
				map.put(list2, false);
				list.add(map);
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
	
}
