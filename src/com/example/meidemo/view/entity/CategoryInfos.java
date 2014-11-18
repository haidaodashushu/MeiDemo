package com.example.meidemo.view.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 顶部的分类信息
 * 
 * @author Administrator
 * 
 */
public class CategoryInfos {
	// HashMap<String, String[]> categorys = new HashMap<String, String[]>();
	public HashMap<CategoryInfo, List<CategoryInfo>> categorys = new LinkedHashMap<CategoryInfos.CategoryInfo, List<CategoryInfo>>();
	public String[] zones = new String[] {};

	public CategoryInfos(String str) {
		if (str == null || "".equals(str)) {
			return;
		}
		try {
			ObjectMapper mapper = new ObjectMapper();
			JSONObject object = new JSONObject(str);
			JSONArray array = object.getJSONArray("Category");
			//读取分类信息
			for (int i = 0; i < array.length(); i++) {
				JSONObject object2 = array.getJSONObject(i);
//				CategoryInfo infos = mapper.readValue(
//						object2.getJSONObject("pdata").toString(), CategoryInfo.class);
				CategoryInfo infos = new CategoryInfo();
				infos.fid = object2.getJSONObject("pdata").getString("fid");
				infos.relate_data = object2.getJSONObject("pdata").getString("relate_data");
				infos.name = object2.getJSONObject("pdata").getString("name");
				infos.id = object2.getJSONObject("pdata").getString("id");
				
				List<CategoryInfo> list = new ArrayList<CategoryInfos.CategoryInfo>();
				//如果没有子分类,则添加空的list
				if (!object2.has("subdata")) {
					categorys.put(infos, list);
					continue;
				}
				JSONArray array2 = object2.getJSONArray("subdata");
				
				for (int j = 0; j < array2.length(); j++) {
//					CategoryInfo info2 = mapper.readValue(array.getString(j),
//							CategoryInfo.class);
					
					CategoryInfo info2 = new CategoryInfo();
					info2.fid = array2.getJSONObject(j).getString("fid");
					info2.relate_data = array2.getJSONObject(j).getString("relate_data");
					info2.name = array2.getJSONObject(j).getString("name");
					info2.id = array2.getJSONObject(j).getString("id");
					list.add(info2);
				}
				categorys.put(infos, list);
			}
			
			//读取地理信息
			JSONArray zoneArray = object.getJSONArray("zone");
			zones = new String[zoneArray.length()] ;
			for (int i = 0; i < zoneArray.length(); i++) {
				zones[i] = zoneArray.getString(i);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	public class CategoryInfo {
		public String id;
		public String name;
		public String relate_data;
		public String fid;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getRelate_data() {
			return relate_data;
		}

		public void setRelate_data(String relate_data) {
			this.relate_data = relate_data;
		}

		public String getFid() {
			return fid;
		}

		public void setFid(String fid) {
			this.fid = fid;
		}

	}
}
