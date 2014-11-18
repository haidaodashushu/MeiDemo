package com.example.meidemo.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.ScreenUtil;
import com.example.meidemo.view.interfaces.OnListItemClickListener;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

public class TopNavigationSort extends LinearLayout{
	PopupWindow pop;
	Context context;
	OnListItemClickListener itemClickListener;
	String[] str = null;
	public TopNavigationSort(Context context,PopupWindow pop,OnListItemClickListener itemClickListener) {
		// TODO Auto-generated constructor stub
		super(context);
		this.pop = pop;
		this.context = context;
		this.itemClickListener = itemClickListener;
		initView();
	}
	public TopNavigationSort(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context, attrs);
	}
	public TopNavigationSort(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void initView(){
		View view=inflate(context, R.layout.top_navigation_sort, this);
//		View view = LayoutInflater.from(context).inflate( R.layout.hr_popwindow_rent, null);
		ListView listView = (ListView)view.findViewById(R.id.h_pop_list);
		listView.getLayoutParams().width = (int) (ScreenUtil.getWidth(context)*0.5);
		
		str = new String[]{"销量最多","价格最低","价格最高","最新发布"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, str);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				pop.dismiss();
				Log.i("POPRent", position+"");
				if (itemClickListener!=null) {
					HashMap<String, Object> data = new HashMap<String, Object>();
					data.put("sort", position);
					data.put("position", position);
					data.put("data", str[position]);
					itemClickListener.itemClickListener(data);
				}
			}
		});
		listView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.in_toptobottom));
//		this.addView(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
	}
}
