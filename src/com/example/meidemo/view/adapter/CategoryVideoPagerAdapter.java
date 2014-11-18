package com.example.meidemo.view.adapter;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meidemo.R;
import com.example.meidemo.view.entity.CategoryVideoPager;

/***
 * 废弃不用
 * @deprecated
 * @author Administrator
 *
 */
public class CategoryVideoPagerAdapter extends PagerAdapter{
	List<CategoryVideoPager> list;
	List<View> listViews;
	Context context;
	FinalBitmap fb = null;
	Bitmap loadingBitmap;
	Bitmap laodfailBitmap;
	public CategoryVideoPagerAdapter(Context context,List<CategoryVideoPager> list){
		this.context = context;
		this.list = list;
		fb= FinalBitmap.create(context);
		listViews = new ArrayList<View>();
		loadingBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		laodfailBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		initView();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = listViews.get(position);
		ImageView image = (ImageView) view.findViewById(R.id.videoImage);
		fb.display(image, list.get(position).image, loadingBitmap, laodfailBitmap);
		TextView videoName = (TextView)view.findViewById(R.id.videoName);
		videoName.setText(list.get(position).title);
		TextView videoScore = (TextView)view.findViewById(R.id.videoScore);
		videoScore.setText(list.get(position).score);
		container.addView(view);
		return view;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(listViews.get(position));
	}
	
	private void initView() {
		listViews = new ArrayList<View>();
		if (list!=null) {
			for (int i = 0; i < list.size(); i++) {
				View view = LayoutInflater.from(context).inflate(R.layout.category_video_pager_item, null);
				
				listViews.add(view);
			}
		}
		
	}
}
