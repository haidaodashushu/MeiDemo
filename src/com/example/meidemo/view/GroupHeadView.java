package com.example.meidemo.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.ScreenUtil;
import com.example.meidemo.activity.CategaryActivity;
import com.example.meidemo.view.entity.GroupHeadViewGrid;
/**
 * 团购的头部View,分为8个，包括美食，电影，酒店等
 * @author wangzk
 *
 */
public class GroupHeadView extends LinearLayout implements OnClickListener {
	Context context;
	LinearLayout gridView;
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
		this.context = context;
		initView();
		// TODO Auto-generated constructor stub
	}

	private void initView() {
		View layout = inflate(context, R.layout.group_fragment_headview, this);
		gridView = (LinearLayout) layout.findViewById(R.id.group_gridView);
		// adapter = new GroupHeadViewAdapter(context, list,gridView);
		// gridView.setAdapter(adapter);
		defaultData();
		createView();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return false;
	}

	private void defaultData() {
		list = new ArrayList<GroupHeadViewGrid>();
		String[] titles = new String[]{"美食","电影","酒店","KTV","丽人","景点门票","本地服务","更多分类"};
		String[] ids = new String[]{"536","538","538","538","538","538","538","538"};
//		String[] iconUrls = new String[]{"美食","电影","酒店","KTV","丽人","景点门票","本地服务","更多分类"};
		int[] icons = new int[]{R.drawable.meishi,R.drawable.dianying,R.drawable.jiudian,R.drawable.jiudian,
				R.drawable.liren,R.drawable.jingdianmenpiao,R.drawable.bendifuwu,R.drawable.gengduo};
		for (int i = 0; i < icons.length; i++) {
			GroupHeadViewGrid grid = new GroupHeadViewGrid();
			grid.setTitle(titles[i]);
			grid.setIconUrl("");
			grid.setId(ids[i]);
			grid.setIcon(context.getResources().getDrawable(icons[i]));
			list.add(grid);
		}
//		GroupHeadViewGrid grid = new GroupHeadViewGrid();
//		grid.setTitle("美食");
//		grid.setIconUrl("http://baidu.com");
//		grid.setIcon(context.getResources().getDrawable(R.drawable.meishi));
//		list.add(grid);
//		
//		grid = new GroupHeadViewGrid();
//		grid.setTitle("电影");
//		grid.setIconUrl("http://baidu.com");
//		grid.setIcon(context.getResources().getDrawable(R.drawable.dianying));
//		list.add(grid);
//		
//		grid = new GroupHeadViewGrid();
//		grid.setTitle("酒店");
//		grid.setIconUrl("http://baidu.com");
//		grid.setIcon(context.getResources().getDrawable(R.drawable.jiudian));
//		list.add(grid);
//		
//		grid = new GroupHeadViewGrid();
//		grid.setTitle("KTV");
//		grid.setIconUrl("http://baidu.com");
//		grid.setIcon(context.getResources().getDrawable(R.drawable.ktv));
//		list.add(grid);
//		
//		grid = new GroupHeadViewGrid();
//		grid.setTitle("丽人");
//		grid.setIconUrl("http://baidu.com");
//		grid.setIcon(context.getResources().getDrawable(R.drawable.liren));
//		list.add(grid);
//		
//		grid = new GroupHeadViewGrid();
//		grid.setTitle("景点门票");
//		grid.setIconUrl("http://baidu.com");
//		grid.setIcon(context.getResources().getDrawable(R.drawable.jingdianmenpiao));
//		list.add(grid);
//		
//		grid = new GroupHeadViewGrid();
//		grid.setTitle("本地服务");
//		grid.setIconUrl("http://baidu.com");
//		grid.setIcon(context.getResources().getDrawable(R.drawable.bendifuwu));
//		list.add(grid);
//		
//		grid = new GroupHeadViewGrid();
//		grid.setTitle("更多分类");
//		grid.setIconUrl("http://baidu.com");
//		grid.setIcon(context.getResources().getDrawable(R.drawable.gengduo));
//		list.add(grid);
		Log.i("GroupHeadView", "list.size" + list.size());
		// adapter.setList(list);

	}

	/**
	 * 创建网格布局的内容
	 */
	private void createView() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		android.view.ViewGroup.LayoutParams lineParams_v = new android.view.ViewGroup.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		lineParams_v.width = 1;
		lineParams_v.height = getItemWidth();
		android.view.ViewGroup.LayoutParams lineParams_h = new android.view.ViewGroup.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		lineParams_h.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
		lineParams_h.height = 1;
		android.view.ViewGroup.LayoutParams itemParams = new android.view.ViewGroup.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		itemParams.width = getItemWidth();
		itemParams.height = getItemWidth();
		// 行数
		int rowNumber = (list.size() % columnsNum == 0) ? list.size()
				/ columnsNum : list.size() / columnsNum + 1;

		for (int i = 0; i < rowNumber; i++) {
			// 添加横向分割线
			View view = LayoutInflater.from(context).inflate(R.layout.line_v,
					null);
			gridView.addView(view, lineParams_h);
			LinearLayout rowLayout = (LinearLayout) LayoutInflater
					.from(context).inflate(R.layout.group_headview_line, null);
			// 添加每一行
			for (int j = 0; j < columnsNum; j++) {
				LinearLayout itemLayout = (LinearLayout) LayoutInflater.from(
						context).inflate(R.layout.group_headview_item, null);
				itemLayout.setId(i * columnsNum + j);
				rowLayout.addView(itemLayout, itemParams);
				// 添加数据
				
				ImageView imageView = (ImageView)itemLayout.findViewById(R.id.group_headview_item_img);
				TextView textView = (TextView)itemLayout.findViewById(R.id.group_headview_item_text);
				imageView.setImageDrawable(list.get(i*columnsNum+j).getIcon());
				textView.setText(list.get(i*columnsNum+j).getTitle());
				itemLayout.setOnClickListener(this);
				// 添加竖向分割线
				View view_item_v = LayoutInflater.from(context).inflate(
						R.layout.line_v, null);
				rowLayout.addView(view_item_v, lineParams_v);
			}
			gridView.addView(rowLayout, params);
		}
		// 添加横向分割线
		View view = LayoutInflater.from(context).inflate(R.layout.line_v, null);
		gridView.addView(view, lineParams_h);
	}

	/**
	 * 计算每个item宽度
	 * 
	 * @return
	 */
	private int getItemWidth() {
		int width = ScreenUtil.getWidth(context) / columnsNum;
		return width;
	}

	private void requestData() {

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.setClass(context, CategaryActivity.class);
		switch (v.getId()) {
		case 0:
			//美食   536
			break;
		case 1:
			//电影  538
			break;
		case 2:
			
			break;
		case 3:

			break;
		case 4:

			break;

		case 5:

			break;
		case 6:

			break;

		case 7:

			break;

		default:
			break;
		}
		
		intent.putExtra("category", list.get(v.getId()).getTitle());
		intent.putExtra("index",list.get(v.getId()).getId());
		context.startActivity(intent);
	}
}
