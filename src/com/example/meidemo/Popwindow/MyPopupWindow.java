package com.example.meidemo.Popwindow;

import com.example.meidemo.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class MyPopupWindow{
	Context context;
	PopupWindow pop;
	ViewGroup anchor;
	public MotionEvent event1;
	public MyPopupWindow(Context context,ViewGroup anchor) {
		this.context = context;
		this.anchor  = anchor;
		pop = createPopupWindow();
	}
	public PopupWindow getPop() {
		return pop;
	}
	private PopupWindow createPopupWindow() {
		PopupWindow pop = new PopupWindow(context);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.MATCH_PARENT);

		ColorDrawable dw = new ColorDrawable(0x80000000);
		pop.setBackgroundDrawable(dw);
		pop.setFocusable(true);
		pop.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				event1 = event;
				return false;
			}
		});
		pop.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// 在这里借用popwindow的点击事件来判断点击是否落在一下4个按钮上，触发他们的点击
				if (event1==null) {
					return;
				}
				int[] location = new int[2];
				ViewGroup group = (ViewGroup) anchor.getChildAt(0);
				for (int i = 0; i < group.getChildCount(); i++) {
					group.getChildAt(i).getLocationOnScreen(location);
					if (event1.getRawX() < location[0] +group.getChildAt(i).getWidth()
							&& event1.getRawX() > location[0]
							&& event1.getRawY() < location[1]
									+ group.getChildAt(i).getHeight()
							&& event1.getRawY() > location[1]) {
						group.getChildAt(i).performClick();
						break;
					}
				}
				event1 = null;
			}
		});

		return pop;
	}
}
