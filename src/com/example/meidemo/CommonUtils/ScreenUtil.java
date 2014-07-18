package com.example.meidemo.CommonUtils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author wangzhengkui
 *	屏幕相关工具类
 */
public class ScreenUtil {
	public static DisplayMetrics getDisplayMetrics(Context context){
		DisplayMetrics metric = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric;
	}
	public static int getWidth(Context context){
		return getDisplayMetrics(context).widthPixels;
	}
	public static int getHeight(Context context){
		return getDisplayMetrics(context).heightPixels;
	}
	public static int getDensityDpi(Context context){
		return getDisplayMetrics(context).densityDpi;
	}
}
