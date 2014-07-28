package com.example.meidemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditorView extends EditText{
	
	public EditorView(Context context,AttributeSet attrs){
		super(context, attrs);
		initView();
	}
	
	public EditorView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}
	private void initView() {
		
	}
}
