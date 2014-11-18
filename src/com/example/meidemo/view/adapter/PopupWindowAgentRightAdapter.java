package com.example.meidemo.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meidemo.R;

public class PopupWindowAgentRightAdapter extends BaseAdapter{
	List<String> rightList ; 
	Context context;
	int current = -1;
	public PopupWindowAgentRightAdapter(List<String> list,Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.rightList = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rightList.size();
	}
	
	public void setrightList(List<String> rightList) {
		this.rightList = rightList;
		this.notifyDataSetChanged();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return rightList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView==null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.h_popwindow_agent_left_item, null);
			holder = new Holder();
			holder.img = (ImageView) convertView.findViewById(R.id.h_popwindow_agent_left_image);
			holder.text = (TextView)convertView.findViewById(R.id.h_popwindow_agent_left_text);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		holder.text.setText(rightList.get(position));
		holder.text.setBackgroundColor(context.getResources().getColor(R.color.h_popwindow_agent_right_text_unselect));
		holder.position = position;
		if (current==position) {
			holder.img.setVisibility(View.VISIBLE);
		}else {
			holder.img.setVisibility(View.GONE);
		}
		return convertView;
	}
	public static class Holder{
		int position;
		ImageView img;
		TextView text;
	}
}
