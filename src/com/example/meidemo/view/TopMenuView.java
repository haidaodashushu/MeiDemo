package com.example.meidemo.view;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.example.meidemo.R;
import com.example.meidemo.baiduLocation.LocationDate;
import com.example.meidemo.baiduLocation.MyLocationClient;
import com.example.meidemo.baiduLocation.MyBDLocationListener.OnLocationListener;
import com.example.meidemo.data.GlobalData;
import com.example.meidemo.dialog.PopWindowCitys;
import com.example.meidemo.view.interfaces.OnListItemClickListener;

public class TopMenuView extends LinearLayout{
	Context context;
	LinearLayout top_city;
	TextView top_city_text;
	ImageView top_city_arrow;
	public TopMenuView(Context context) {
		super(context);
		this.context = context;
		initView();
	}
	public TopMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	private void initView() {
		View view = inflate(context, R.layout.top_menu, this);
		top_city_text = (TextView) view.findViewById(R.id.top_city_text);
		top_city_arrow = (ImageView)view.findViewById(R.id.top_city_arrow);
		top_city = (LinearLayout) view.findViewById(R.id.top_city);
		final String[] districts = new String[]{"全城","梅江","兴宁","梅县","大埔","丰顺","五华","平远","蕉岭","梅州周边"};
		OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final PopWindowCitys pop = new PopWindowCitys(context, districts, new OnListItemClickListener() {
					
					@Override
					public void itemClickListener(HashMap<String, Object> data) {
						top_city_text.setText(data.get("city").toString()); 
						GlobalData.city = data.get("city").toString();
					}
				});
				pop.getPop().showAsDropDown(top_city_text);
			}
		};
		top_city.setOnClickListener(clickListener);
		top_city_arrow.setOnClickListener(clickListener);
	}
	
	public void setcityData() {
		if (GlobalData.city != null
				&& !"".equals(GlobalData.city)) {
			top_city_text.setText(GlobalData.city);
		} 
	}
}
