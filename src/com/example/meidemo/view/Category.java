package com.example.meidemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.meidemo.R;

public class Category extends LinearLayout{
	Context context;
	public Category(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		initView();
	}
	
	private void initView() {
		View view = inflate(context, R.layout.category, this);
	}
}
