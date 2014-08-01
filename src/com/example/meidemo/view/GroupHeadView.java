package com.example.meidemo.view;

import java.util.ArrayList;
import java.util.List;

import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.ScreenUtil;
import com.example.meidemo.view.adapter.GroupHeadViewAdapter;
import com.example.meidemo.view.entity.GroupHeadViewGrid;

import android.content.Context;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GroupHeadView extends LinearLayout {
	Context context;
	LinearLayout gridView;
	GroupHeadViewAdapter adapter;
	List<GroupHeadViewGrid> list;
	private int columnsNum = 4;
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
		View layout = inflate(context, R.layout.group_fragment_headview, this);
		gridView = (LinearLayout)layout.findViewById(R.id.group_gridView);
//		adapter = new GroupHeadViewAdapter(context, list,gridView);
//		gridView.setAdapter(adapter);
		defaultData();
		createView();
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
//		adapter.setList(list);
		
		
	}
	/**
	 * 创建网格布局的内容
	 */
	private void createView() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		android.view.ViewGroup.LayoutParams lineParams_v = new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		lineParams_v.width = 1;
		lineParams_v.height = getItemWidth();
		android.view.ViewGroup.LayoutParams lineParams_h = new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		lineParams_h.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
		lineParams_h.height = 1;
		android.view.ViewGroup.LayoutParams itemParams = new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		itemParams.width = getItemWidth();
		itemParams.height = getItemWidth();
		//行数
		int rowNumber = (list.size()%columnsNum==0)?list.size()/columnsNum:list.size()/columnsNum+1;
		
		for (int i = 0; i < rowNumber; i++) {
			//添加横向分割线
			View view = LayoutInflater.from(context).inflate(R.layout.line_v, null);
			gridView.addView(view, lineParams_h);
			LinearLayout rowLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.group_headview_line, null);
			//添加每一行
			for (int j = 0; j < columnsNum; j++) {		
				LinearLayout itemLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.group_headview_item, null);
//				ImageView itemImage = (ImageView)itemLayout.findViewById(R.id.group_headview_item_img);
//				TextView itemText = (TextView)itemLayout.findViewById(R.id.group_headview_item_text);
				rowLayout.addView(itemLayout,itemParams);
				itemLayout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Toast.makeText(context, "点击事件", Toast.LENGTH_SHORT).show();
					}
				});
//				添加竖向分割线
				View view_item_v = LayoutInflater.from(context).inflate(R.layout.line_v, null);
				rowLayout.addView(view_item_v,lineParams_v);
			}
			gridView.addView(rowLayout,params);
		}
		//添加横向分割线
		View view = LayoutInflater.from(context).inflate(R.layout.line_v, null);
		gridView.addView(view, lineParams_h);
//		this.addView(linearLayout,params);
	}
	/**
	 * 计算每个item宽度
	 * @return
	 */
	private int getItemWidth() {
		int width = ScreenUtil.getWidth(context)/4;
		return width;
	}
	private void requestData() {
		
	}
}
