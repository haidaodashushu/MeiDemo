package com.example.meidemo.view.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;

public class Comments {
	public List<Comment> list = new ArrayList<Comment>();
	
	public Comments(String str){
		try {
			JSONArray array = new JSONArray(str);
			ObjectMapper mapper = new ObjectMapper();
			for (int i = 0; i < array.length(); i++) {
				Comment comment = mapper.readValue(array.getString(i), Comment.class);
				list.add(comment);
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
