package com.example.meidemo.CommonUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceUtil {
	private static final String NAME = "INFOS";
	private SharedPreferenceUtil() {
		// TODO Auto-generated constructor stub
	}
	
	private static SharedPreferences getInstance(Context context){
		SharedPreferences perPreferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE );
		return perPreferences;
	}
	public static String getString(Context context,String key) {
		SharedPreferences perPreferences = getInstance(context);
		return perPreferences.getString(key, "");
		/*SharedPreferences perPreferences = getInstance(context);
		if (perPreferences.contains(key)) {
			
		}*/
	}
	public static void removeString(Context context, String key) {
		SharedPreferences perPreferences = getInstance(context);
		Editor editor = perPreferences.edit();
		editor.remove(key);
		editor.commit();
	}
	public static boolean setString(Context context,String key,String value){
		SharedPreferences perPreferences = getInstance(context);
		Editor editor = perPreferences.edit();
		editor.putString(key, value);
		return editor.commit();
	}
	public static boolean setInt(Context context,String key,int value){
		SharedPreferences perPreferences = getInstance(context);
		Editor editor = perPreferences.edit();
		editor.putInt(key, value);
		return editor.commit();
	}
	public static boolean getBoolean(Context context,String key){
		SharedPreferences perPreferences = getInstance(context);
		return perPreferences.getBoolean(key, false);
	}
	public static int getInt(Context context,String key){
		SharedPreferences perPreferences = getInstance(context);
		return perPreferences.getInt(key, Integer.MIN_VALUE);
	}
}
