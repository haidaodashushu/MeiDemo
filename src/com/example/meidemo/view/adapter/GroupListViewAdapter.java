package com.example.meidemo.view.adapter;

import java.text.DecimalFormat;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.SyncImageLoader;
import com.example.meidemo.activity.DetailCategoryActivity;
import com.example.meidemo.activity.DetailFoodCategoryActivity;
import com.example.meidemo.activity.DetailVideoCategoryActivity;
import com.example.meidemo.view.adapter.CategoryVideoListViewAdapter.Holder;
import com.example.meidemo.view.entity.CategoryFood;
import com.example.meidemo.view.entity.TuanInfo;

public class GroupListViewAdapter extends BaseAdapter implements OnClickListener{
	List<TuanInfo> list ; 
	Context context;
	public int current = 0;
	DecimalFormat format;
	private SyncImageLoader syncImageLoader;
	private FinalBitmap fb;
	public GroupListViewAdapter(List<TuanInfo> list,Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		format = new DecimalFormat("##.#");
		syncImageLoader = new SyncImageLoader();
		fb = FinalBitmap.create(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public void setLeftList(List<TuanInfo> list) {
		this.list = list;
		if (list.size()>0) {
			this.notifyDataSetChanged();
		}
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
			holder.now_number = (TextView)convertView.findViewById(R.id.now_number);
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		TuanInfo tuanInfo = list.get(position);
		holder.title.setText(tuanInfo.title);
		holder.content.setText(tuanInfo.product);
		holder.distance.setText(format.format(Float.parseFloat(tuanInfo.distance)/1000.0)+"km");
		holder.newMoney.setText(tuanInfo.team_price);
		holder.oldMoney.setText(tuanInfo.market_price+"元");
		holder.oldMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		holder.now_number.setText("已售"+tuanInfo.now_number);
		fb.display(holder.img, tuanInfo.image);
		holder.position = position;
		convertView.setOnClickListener(this);
//		syncImageLoader.loadImage(position,tuanInfo.image,new SyncImageLoader.OnImageLoadListener() {
//			
//			@Override
//			public void onImageLoad(Integer t, Drawable drawable) {
//				holder.img.setImageDrawable(drawable);
//			}
//			
//			@Override
//			public void onError(Integer t) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
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
		public TextView now_number;
	}
	@Override
	public void onClick(View v) {
		Toast.makeText(context, list.get(((Holder)v.getTag()).position).title+list.get(((Holder)v.getTag()).position).id, Toast.LENGTH_SHORT).show();
		//进入美食详情界面
		Intent intent = new Intent();
		intent.setClass(context, DetailCategoryActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("id", list.get(((Holder)v.getTag()).position).id);
		
		bundle.putSerializable("category",list.get(((Holder)v.getTag()).position));
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
}
