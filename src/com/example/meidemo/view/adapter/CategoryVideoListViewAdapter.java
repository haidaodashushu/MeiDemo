package com.example.meidemo.view.adapter;

import java.text.DecimalFormat;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meidemo.R;
import com.example.meidemo.activity.DetailFoodCategoryActivity;
import com.example.meidemo.activity.DetailVideoCategoryActivity;
import com.example.meidemo.view.entity.CategoryFood;
import com.example.meidemo.view.entity.CategoryVideo;

public class CategoryVideoListViewAdapter extends BaseAdapter implements OnClickListener{
	List<CategoryVideo> list;
	Context context;
	FinalBitmap fb;
	DecimalFormat format ;
	public CategoryVideoListViewAdapter(Context context,List<CategoryVideo>  list){
		super();
		this.context = context;
		this.list = list;
		fb = FinalBitmap.create(context);
		format = new DecimalFormat("##.#");
	}
	
	public List<CategoryVideo>  getList() {
		return list;
	}

	public void setList(List<CategoryVideo>  list) {
		if (list==null) {
			return;
		}
		this.list = list;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		if (list==null) {
			return 0;
		}
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
	public View getView(int position, View agr1, ViewGroup parent) {
		Holder holder = null;
		ViewGroup convertView = (ViewGroup) agr1;
		CategoryVideo video = null;
		if (convertView==null) {
			holder = new Holder();
			convertView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.category_video_item, null);
			holder.movie = (TextView)convertView.findViewById(R.id.movie);
			holder.tuanImage = (TextView)convertView.findViewById(R.id.tuanImage);
			holder.ratingBar1 = (RatingBar)convertView.findViewById(R.id.ratingBar1);
			holder.score = (TextView)convertView.findViewById(R.id.score);
			holder.opinionCount = (TextView)convertView.findViewById(R.id.opinionCount);
			holder.money = (TextView)convertView.findViewById(R.id.money);
			holder.distance = (TextView)convertView.findViewById(R.id.distance);
			holder.zone = (TextView)convertView.findViewById(R.id.zone);
			
			convertView.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		video = list.get(position);
		holder.position = position;
		holder.movie.setText(video.getTitle());
		holder.distance.setText(new DecimalFormat("#.##").format(Float.parseFloat(video.distance)/1000)+"km");
		holder.money.setText("￥"+video.market_price+"元起");
		holder.ratingBar1.setRating(Float.parseFloat(video.score));
		holder.score.setText(video.score);
//		holder.opinionCount.setText(video.opinionCount);
		holder.zone.setText(video.zone);
		convertView.setOnClickListener(this);
		return convertView;
	}
	
	class Holder{
		TextView movie;
		TextView tuanImage;
		RatingBar ratingBar1;
		TextView score;
		TextView opinionCount;
		TextView money;
		TextView distance;
		TextView zone;
		int position;
	}
	@Override
	public void onClick(View v) {
		Toast.makeText(context, list.get(((Holder)v.getTag()).position).title+list.get(((Holder)v.getTag()).position).id, Toast.LENGTH_SHORT).show();
		//进入美食详情界面
		Intent intent = new Intent();
		intent.setClass(context, DetailVideoCategoryActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("id", list.get(((Holder)v.getTag()).position).id);
		bundle.putSerializable("category",list.get(((Holder)v.getTag()).position));
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
}
