package com.example.meidemo.view.adapter;

import java.util.List;

import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.ScreenUtil;
import com.example.meidemo.view.entity.GroupHeadViewGrid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class GroupHeadViewAdapter extends BaseAdapter {
	List<GroupHeadViewGrid> list;
	Context context;
	LayoutParams params;
	GridView gridView;
	public GroupHeadViewAdapter(Context context, List<GroupHeadViewGrid> list,GridView gridView) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.gridView = gridView;
		setLayoutParams();
	}
	private void setLayoutParams() {
		
		params =new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		int width = ScreenUtil.getWidth(context)/4;
		Log.i("TGA", width+"");
		params.height = width;
		params.width = width;
	}

	public void setList(List<GroupHeadViewGrid> list) {
		if (list != null) {
			this.list = list;
			notifyDataSetChanged();
		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (list!=null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		Log.i("TGA", position+"");
//		convertView = LayoutInflater.from(context).inflate(R.layout.group_fragment_headview_grid_item, null);
//		convertView.setLayoutParams(params);
//		TextView text = (TextView) convertView
//				.findViewById(R.id.group_headview_grid_item_text);
//		text.setText(list.get(position).getTitle());
		
//		TextView textView = new TextView(context);
//		textView.setText(list.get(position).getTitle());
		return convertView;
	}
}
