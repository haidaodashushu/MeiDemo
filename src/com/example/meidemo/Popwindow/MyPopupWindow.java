package com.example.meidemo.Popwindow;

import com.example.meidemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

public class MyPopupWindow extends PopupWindow{
	Context context;
	MyPopupWindow pop;
	View contentView;
	public MyPopupWindow(Context context,View contentView) {
		this.context = context;
		pop = (MyPopupWindow) new PopupWindow(context);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.MATCH_PARENT);
//		if (contentView==null) {
//			View view = LayoutInflater.from(context).inflate(R.layout.hr_popwindow_rent, null);
//			pop.setContentView(view);
//		}else {
//			pop.setContentView(contentView);
//		}
//		pop.setContentView(contentView);
	}
	public void setContentView(View contentView) {
		this.contentView = contentView;
		if (contentView==null) {
			return;
		}
		pop.setContentView(contentView);
	}
	@Override
	public void showAsDropDown(View anchor, int xoff, int yoff) {
		// TODO Auto-generated method stub
		super.showAsDropDown(anchor, xoff, yoff);
		
	}
//	public void showPopWindow(View v,int xoff,int yoff){
//		if (contentView!=null) {
//			pop.setContentView(contentView);
//		}
//		pop.showAsDropDown(v, 0, 0);
//		String[] str = new String[]{"王一","王一","王一"};
//		ViewGroup tempGroup = (ViewGroup) pop.getContentView();
//		ListView listView = (ListView) tempGroup.getChildAt(0);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, android.R.id.text1, str);
//		listView.setAdapter(adapter);
//		listView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.in_toptobottom));
//	}
//	
}
