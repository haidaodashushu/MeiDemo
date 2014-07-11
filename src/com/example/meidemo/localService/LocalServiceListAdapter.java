package com.example.meidemo.localService;

import java.util.List;

import com.example.meidemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author wangzhengkui
 *
 */
public class LocalServiceListAdapter extends BaseAdapter{
	Context context ;
	List<LocalServiceEntity> list;
	public LocalServiceListAdapter(Context context,List<LocalServiceEntity> list) {
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
		Holder holder;
		if (convertView==null) {
			holder = new Holder();
			convertView = LayoutInflater.from(context).inflate(R.layout.house_rent_item, null);
		}
		return null;
	}
	
	static class Holder{
		ImageView image;
		TextView title;
		TextView content;
		TextView price;
		TextView publish;
	}
	
}
