package com.example.meidemo.view;

import java.util.ArrayList;
import java.util.List;

import com.example.meidemo.R;
import com.example.meidemo.view.adapter.GroupHeadViewAdapter;
import com.example.meidemo.view.entity.GroupHeadViewGrid;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

public class GroupHeadView extends LinearLayout{
	Context context;
	GridView gridView;
	GroupHeadViewAdapter adapter;
	List<GroupHeadViewGrid> list;
	public GroupHeadView(Context context) {
		super(context);
		this.context = context;
		initView();
		// TODO Auto-generated constructor stub
	}
	public GroupHeadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context =  context;
		initView();
		// TODO Auto-generated constructor stub
	}
	private void initView() {
		View layout = inflate(context, R.layout.group_fragment_headview, null);
		this.addView(layout);
		gridView = (GridView)layout.findViewById(R.id.group_headview_grid);
		adapter = new GroupHeadViewAdapter(context, list,gridView);
		gridView.setAdapter(adapter);
		defaultData();
		
	}
	private void defaultData() {
		list = new ArrayList<GroupHeadViewGrid>();
		for (int i = 0; i < 8; i++) {
			GroupHeadViewGrid grid = new GroupHeadViewGrid();
			grid.setTitle("商户");
			grid.setIconUrl("http://baidu.com");
			list.add(grid);
		}
		Log.i("GroupHeadView", "list.size"+list.size());
		adapter.setList(list);
	}
	private void requestData() {
		
	}
}
