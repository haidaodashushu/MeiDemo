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

public class GroupListViewAdapter extends BaseAdapter{
	List<String> leftList ; 
	Context context;
	public int current = 0;
	public GroupListViewAdapter(List<String> list,Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.leftList = list;
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
			
			convertView = LayoutInflater.from(context).inflate(R.layout.group_listview_item, null);
			holder = new Holder();
			holder.img = (ImageView) convertView.findViewById(R.id.gropu_listview_img);
			holder.title = (TextView)convertView.findViewById(R.id.group_listview_title);
			holder.content = (TextView)convertView.findViewById(R.id.group_listview_content);
			holder.distance = (TextView)convertView.findViewById(R.id.group_listview_distance);
			holder.newMoney = (TextView)convertView.findViewById(R.id.group_listview_newMoney);
			holder.oldMoney = (TextView)convertView.findViewById(R.id.group_listview_oldMoney);
			
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	public static class Holder{
		public int position;
		public ImageView img;
		public TextView content;
		public TextView title;
		public TextView distance;
		public TextView oldMoney;
		public TextView newMoney;
	}
}
