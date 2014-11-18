package com.example.meidemo.view.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailCategory {
	Team teams ;
	List<Service> services;
	List<OtherTeam> otherteams;
	public DetailCategory(String str){
		services = new ArrayList<Service>();
		otherteams = new ArrayList<OtherTeam>();
		try {
			JSONObject object = new JSONObject(str);
//			object.getJSONArray("team");
			ObjectMapper mapper = new ObjectMapper();
			
			JSONObject teamObject = object.getJSONObject("team");
			teams = mapper.readValue(teamObject.toString(), Team.class);
			
			JSONArray serviceArray = object.getJSONArray("service");
			for (int i = 0; i < serviceArray.length(); i++) {
				Service service = mapper.readValue(serviceArray.get(i).toString(), Service.class);
				services.add(service);
			}
			JSONArray otherTeamArray = object.getJSONArray("otherteams");
			for (int i = 0; i < otherTeamArray.length(); i++) {
				OtherTeam otherTeam = mapper.readValue(otherTeamArray.get(i).toString(), OtherTeam.class);
				otherteams.add(otherTeam);
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
	public Team getTeams() {
		return teams;
	}
	public void setTeams(Team teams) {
		this.teams = teams;
	}
	public List<Service> getServices() {
		return services;
	}
	public void setServices(List<Service> services) {
		this.services = services;
	}
	public List<OtherTeam> getOtherteams() {
		return otherteams;
	}
	public void setOtherteams(List<OtherTeam> otherteams) {
		this.otherteams = otherteams;
	}
}
