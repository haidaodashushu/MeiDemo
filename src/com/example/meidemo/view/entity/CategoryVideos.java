package com.example.meidemo.view.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;

public class CategoryVideos {
	List<CategoryVideo> list = null;
	public CategoryVideos(String str){
		ObjectMapper mapper = new ObjectMapper();
		 try {
			 list = new ArrayList<CategoryVideo>();
			JSONArray array = new JSONArray(str);
			for (int i = 0; i < array.length(); i++) {
				CategoryVideo categoryVideo = mapper.readValue(array.getString(i), CategoryVideo.class);
				list.add(categoryVideo);
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
	
	
	public List<CategoryVideo> getList() {
		return list;
	}


	public void setList(List<CategoryVideo> list) {
		this.list = list;
	}

}


