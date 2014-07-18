package com.example.meidemo.view;

import com.example.meidemo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

public class PopupWindowRentView extends LinearLayout{
	PopupWindow pop;
	Context context;
	public PopupWindowRentView(Context context,PopupWindow pop) {
		// TODO Auto-generated constructor stub
		super(context);
		this.pop = pop;
		this.context = context;
		initView();
	}
	public PopupWindowRentView(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context, attrs);
	}
	public PopupWindowRentView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void initView(){
		View view=inflate(context, R.layout.hr_popwindow_rent, null);
//		View view = LayoutInflater.from(context).inflate( R.layout.hr_popwindow_rent, null);
		String[] str = new String[]{"王一","王一","王一"};
		ListView listView = (ListView)view.findViewById(R.id.h_pop_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, str);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("POPRent", position+"");
			}
		});
		listView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.in_toptobottom));
		this.addView(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
	}

}
