package com.example.meidemo.view.entity;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseInfo {

	public BaseInfo() {

	}

	protected int getint(String key, JSONObject jsonObj) throws JSONException {
		if (!jsonObj.isNull(key)) {
			return jsonObj.getInt(key);
		}
		return 0;
	}

	protected double getDouble(String key, JSONObject jsonObj)
			throws JSONException {
		if (!jsonObj.isNull(key)) {
			return jsonObj.getDouble(key);
		}
		return 0;
	}

	protected String getString(String key, JSONObject jsonObj)
			throws JSONException {
		if (!jsonObj.isNull(key)) {
			return jsonObj.getString(key);
		}
		return "";
	}

	protected boolean getBoolean(String key, JSONObject jsonObj)
			throws JSONException {
		if (!jsonObj.isNull(key)) {
			return jsonObj.getBoolean(key);
		}
		return false;
	}

}
