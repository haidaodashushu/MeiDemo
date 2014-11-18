package com.example.meidemo.view.adapter;

import java.util.List;

import com.example.meidemo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TopNavigationCategoryAdapterLeft extends BaseAdapter{
	List<String> leftList ; 
	Context context;
	public int current = 0;
	public TopNavigationCategoryAdapterLeft(List<String> list,Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.leftList = list;
	}
	
	public List<String> getLeftList() {
		return leftList;
	}

	public void setLeftList(List<String> leftList) {
		if (leftList==null) {
			return;
		}
		this.leftList = leftList;
		this.notifyDataSetChanged();
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return leftList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return leftList.get(position);
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
		holder.text.setText(leftList.get(position));
		holder.position = position;
		if (current==position) {
			holder.img.setVisibility(View.VISIBLE);
			holder.text.setBackgroundColor(context.getResources().getColor(R.color.h_popwindow_agent_left_text_select));
		}else {
			holder.img.setVisibility(View.GONE);
			holder.text.setBackgroundColor(context.getResources().getColor(R.color.h_popwindow_agent_left_text_unselect));
		}
		
		return convertView;
	}
	public static class Holder{
		public int position;
		public ImageView img;
		public TextView text;
	}
}
