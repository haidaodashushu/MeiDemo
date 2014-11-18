package com.example.meidemo.view.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;

public class CategoryVideoPagers{
	List<CategoryVideoPager> list = null;
	
	public CategoryVideoPagers(String str){
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			list = new ArrayList<CategoryVideoPager>();
			JSONArray array = new JSONArray(str);
			for (int i = 0; i < array.length(); i++) {
				CategoryVideoPager pager = mapper.readValue(array.getString(i), CategoryVideoPager.class);
				list.add(pager);
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
	
	
	public List<CategoryVideoPager> getList() {
		return list;
	}


	public void setList(List<CategoryVideoPager> list) {
		this.list = list;
	}


}

	
	