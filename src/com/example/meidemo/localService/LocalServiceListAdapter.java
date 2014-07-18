package com.example.meidemo.localService;

import java.util.ArrayList;
import java.util.List;

import com.example.meidemo.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author wangzhengkui
 * 
 */
public class LocalServiceListAdapter extends BaseAdapter implements OnClickListener{
	Context context;
	private List<LocalServiceEntity> list = new ArrayList<LocalServiceEntity>();
	LayoutInflater inflater;

	public LocalServiceListAdapter(Context context) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
	}

	public void setList(List<LocalServiceEntity> list) {
		this.list = list;
		if (list != null) {
			this.notifyDataSetChanged();
		}
	}
	public List<LocalServiceEntity> getList() {
		return list;
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView==null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.house_rent_item,null);
			holder.image = (ImageView)convertView.findViewById(R.id.hr_image);
			holder.title = (TextView)convertView.findViewById(R.id.hr_title);
			holder.content = (TextView)convertView.findViewById(R.id.hr_content);
			holder.publish = (TextView)convertView.findViewById(R.id.hr_publish);
			holder.price = (TextView)convertView.findViewById(R.id.hr_price);
			holder.position = position;
			convertView.setTag(holder);
//			convertView.setOnClickListener(this);
		}else {
			holder = (Holder) convertView.getTag();
		}
		holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
		holder.title.setText(list.get(position).title);
		if (list.get(position).click) {
			holder.title.setTextColor(context.getResources().getColor(R.color.hr_title_select));
		}else {
			holder.title.setTextColor(context.getResources().getColor(R.color.hr_title_unselect));
		}
		
		holder.publish.setText(list.get(position).publish);
		holder.price.setText(list.get(position).price);
		holder.content.setText(list.get(position).title);
		return convertView;
	}

	static class Holder {
		ImageView image;
		TextView title;
		TextView content;
		TextView price;
		TextView publish;
		int position;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		default:
			
			break;
		}
	}

}
