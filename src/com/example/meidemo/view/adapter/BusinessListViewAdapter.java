package com.example.meidemo.view.adapter;

import java.util.List;

import net.tsz.afinal.FinalBitmap;

import zrc.widget.ZrcListView;
import zrc.widget.ZrcListView.OnScrollListener;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;
import com.example.meidemo.CommonUtils.SyncImageLoader;
import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.ScreenUtil;
import com.example.meidemo.view.entity.BusinessViewGrid;

public class BusinessListViewAdapter  extends BaseAdapter{
	private List<BusinessViewGrid> leftList ; 
	private ZrcListView listView; 
	Context context;
	public int current = 0;
	private SyncImageLoader syncImageLoader;
	FinalBitmap fb;

	public BusinessListViewAdapter(List<BusinessViewGrid> list,ZrcListView zrcview,Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.leftList = list;
		this.listView = zrcview;
		fb = FinalBitmap.create(context);
		syncImageLoader = new SyncImageLoader();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (leftList==null) {
			return 0;
		}
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
		BusinessGridHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.business_listview_item, null);
			holder = new BusinessGridHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.business_listview_img);
			holder.title = (TextView)convertView.findViewById(R.id.busniess_title);
			holder.pinglun = (TextView)convertView.findViewById(R.id.busniess_ping);
			holder.distance = (TextView)convertView.findViewById(R.id.busniess_juli);
			holder.score = (ImageView)convertView.findViewById(R.id.busniess_ping_img);
			holder.category = (TextView)convertView.findViewById(R.id.busniess_category);
			holder.zone = (TextView)convertView.findViewById(R.id.busniess_zone);
			convertView.setTag(holder);
		}else {
			holder = (BusinessGridHolder) convertView.getTag();
		}
		
		
		//holder.img.setBackgroundResource((String)leftList.get(position).getBusImg());
		holder.title.setText((String)leftList.get(position).getBusTitle());
		float pingscore = leftList.get(position).getBusScore();
		//if(pingscore>=5)
		holder.score.setBackgroundResource(R.drawable.start);
		holder.pinglun.setText(leftList.get(position).getPingLun()+"评价");
		holder.category.setText(leftList.get(position).getBusCategory());
		holder.zone.setText(leftList.get(position).getBusZone());
		//holder.img.setBackgroundResource(R.drawable.rc_item_bg);
//		syncImageLoader.loadImage(position,leftList.get(position).getBusImg(),imageLoadListener);
		fb.display(holder.img, leftList.get(position).getBusImg());
		return convertView;
	}
	SyncImageLoader.OnImageLoadListener imageLoadListener = new SyncImageLoader.OnImageLoadListener(){

		@Override
		public void onImageLoad(Integer t, Drawable drawable) {
	
			View view = listView.findViewWithTag(t);
			 
		 
			
			if(view != null){
				Log.v("onImageLoad","下载图片成功"+t);
				ImageView iv = (ImageView) view.findViewById(R.id.business_listview_img);
				iv.setBackgroundDrawable(drawable);
			}
		}
		@Override
		public void onError(Integer t) {
			Log.v("onImageLoad","下载图片不成功"+t);
			View view = listView.findViewWithTag(t);
			if(view != null){
				ImageView iv = (ImageView) view.findViewById(R.id.business_listview_img);
				iv.setBackgroundResource(R.drawable.rc_item_bg);
			}
		}
		
	};
	
	public void loadImage(){
		int start = listView.getFirstVisiblePosition();
		int end =listView.getLastVisiblePosition();
		if(end >= getCount()){
			end = getCount() -1;
		}
		syncImageLoader.setLoadLimit(start, end);
		syncImageLoader.unlock();
	}
	
	 
	public List<BusinessViewGrid> getLeftList() {
		return leftList;
	}
	public void setLeftList(List<BusinessViewGrid> leftList) {
		if (leftList==null||leftList.size()==0) {
			return;
		}
		this.leftList = leftList;
		this.notifyDataSetChanged();
	}

	public static class BusinessGridHolder{
		public int position;
		public ImageView img;
		public TextView title;
		public TextView pinglun;
		public ImageView score;
		public TextView distance;
		public TextView category;
		public TextView zone;
	}
}