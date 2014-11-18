package com.example.meidemo.dialog;

import java.util.HashMap;

import org.codehaus.jackson.map.ser.ScalarSerializerBase;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.ScreenUtil;
import com.example.meidemo.view.interfaces.OnListItemClickListener;

public class PopWindowCitys extends PopupWindow{
	String[]  citys = null;
	Context context;
	PopupWindow pop ;
	
	OnListItemClickListener itemListener;
	public PopWindowCitys(Context context,String[]  city,OnListItemClickListener itemClickListener) {
		this.context = context;
		this.citys = city;
		this.itemListener = itemClickListener;
		View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_city_list, null);
		pop = new PopupWindow(context);
		pop.setWidth((int) (ScreenUtil.getWidth(context)*0.4));
		pop.setHeight((int) (ScreenUtil.getHeight(context)*0.5));

		ColorDrawable dw = new ColorDrawable(0x80000000);
		pop.setBackgroundDrawable(dw);
		pop.setFocusable(true);
		ListView list = (ListView)contentView.findViewById(R.id.citys);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
				android.R.id.text1,citys);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if ((itemListener)!=null) {
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put("city", citys[position]);
					itemListener.itemClickListener(hashMap);
					pop.dismiss();
				}
			}
		});
		pop.setContentView(contentView);
	}
	public PopupWindow getPop() {
		return pop;
	}
	public void setPop(PopupWindow pop) {
		this.pop = pop;
	}
	
	
}
