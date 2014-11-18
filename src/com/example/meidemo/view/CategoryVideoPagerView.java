package com.example.meidemo.view;

import com.example.meidemo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class CategoryVideoPagerView extends LinearLayout{
	Context context;
	LinearLayout videoPager;
	public CategoryVideoPagerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public CategoryVideoPagerView(Context context){
		super(context);
		this.context =context;
		initView();
	}
	private void initView() {
		inflate(context, R.layout.category_video_pager, this);
		videoPager= (LinearLayout)findViewById(R.id.videoPager);
	}
	public LinearLayout getVideoPager() {
		return videoPager;
	}
}
