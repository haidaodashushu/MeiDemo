package com.example.meidemo.view.adapter;

import java.text.DecimalFormat;
import java.util.HashMap;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meidemo.R;
import com.example.meidemo.activity.DetailFoodCategoryActivity;
import com.example.meidemo.view.entity.CategoryFood;

public class CategoryFoodListViewAdapter extends BaseAdapter implements OnClickListener{
	List<HashMap<List<CategoryFood>, Boolean>> list;
	boolean[] clicks ;
	Context context;
	FinalBitmap fb;
	DecimalFormat format ;
	public static final int TYPE1 = 1;
	public static final int TYPE2 = 2;
	public static final int TYPE3 = 3;
	public static final int TYPE4 = 4;
	public CategoryFoodListViewAdapter(Context context,List<HashMap<List<CategoryFood>, Boolean>> list){
		super();
		this.context = context;
		this.list = list;
		if (list!=null) {
			clicks = new boolean[list.size()+1];
		}
		fb = FinalBitmap.create(context);
		format = new DecimalFormat("##.#");
	}
	

	public void setList(List<HashMap<List<CategoryFood>, Boolean>> list) {
		if (list==null) {
			return;
		}
		this.list = list;
		clicks = new boolean[list.size()+1];
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
	/**首先全部加载数据*/
	public View getView(int position, View agr1, ViewGroup parent){
		Holder holder = null;
//		List<CategoryFood> fCategaries = list.get(position);
		HashMap<List<CategoryFood>, Boolean> maps = list.get(position);
		List<CategoryFood> fCategaries= maps.keySet().iterator().hasNext()?maps.keySet().iterator().next():null;
		Boolean isClick = maps.values().iterator().hasNext()?maps.values().iterator().next():null;
		if (fCategaries==null||isClick == null) {
			return agr1;
		}
		int count = fCategaries.size();
		View categoryInfo= null;
		ViewGroup convertView = (ViewGroup) agr1;
		if (convertView==null) {
			holder = new Holder();
			convertView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.category_food_item, null);
			holder.categoryInfos = (LinearLayout)convertView.findViewById(R.id.category_infos);
			holder.title = (TextView)convertView.findViewById(R.id.title);
			holder.distance = (TextView)convertView.findViewById(R.id.distance);
			holder.restCategory = (LinearLayout)convertView.findViewById(R.id.restCategory);
			holder.zone = (TextView)convertView.findViewById(R.id.zone);

			convertView.setTag(holder);
			holder.restCategory.setTag(maps);
			
			
			
		}else {
			holder = (Holder) convertView.getTag();
		}
		holder.categoryInfos.removeAllViews();
		//团购数量为1-2个
		if (count==1||count==2) {
			holder.restCategory.setVisibility(View.GONE);
			for (int i = 0; i < count; i++) {
				categoryInfo = LayoutInflater.from(context).inflate(R.layout.category_food_item_item, null);
				CategoryFood fCategary = fCategaries.get(i);
				setDataForItem(categoryInfo, fCategary);
				holder.categoryInfos.addView(categoryInfo);
			}
		}else if (count>2) {
			if (isClick) {
				holder.restCategory.setVisibility(View.GONE);
			}else {
				int restCount = count-2;
				count = 2;
				holder.restCount = restCount;
				holder.restCategory.setVisibility(View.VISIBLE);
				holder.restCategory.setTag(maps);
				((TextView)holder.restCategory.getChildAt(0)).setText("查看本店其他"+restCount+"个团购");
				holder.restCategory.setOnClickListener(this);
			}
			
			for ( int i = 0; i < count; i++) {
				categoryInfo = LayoutInflater.from(context).inflate(R.layout.category_food_item_item, null);
				CategoryFood fCategary = fCategaries.get(i);
				setDataForItem(categoryInfo, fCategary);
				holder.categoryInfos.addView(categoryInfo);
			}
			
			
		}
		
		holder.title.setText(fCategaries.get(0).title);//
		holder.zone.setText(fCategaries.get(0).zone);//区域
		holder.distance.setText(format.format(fCategaries.get(0).distance/1000.0)+"km");
		holder.position = position;
		return convertView;
	}
	/**给每个View设置值*/
	public View setDataForItem(View categoryInfo,CategoryFood fCategary){
		//初始化
		HolderItem holderItem = new HolderItem();
		holderItem.imageView = (ImageView)categoryInfo.findViewById(R.id.image);
		holderItem.isYuyue = (ImageView)categoryInfo.findViewById(R.id.isYuyue);
		holderItem.content = (TextView)categoryInfo.findViewById(R.id.content);
		holderItem.teamPrice = (TextView)categoryInfo.findViewById(R.id.newMoney);
		holderItem.marketPrice = (TextView)categoryInfo.findViewById(R.id.oldMoney);
		holderItem.saleCount = (TextView)categoryInfo.findViewById(R.id.saleCount);
		
		//赋值
		holderItem.content.setText(fCategary.product);
		holderItem.teamPrice.setText(fCategary.team_price);
		holderItem.marketPrice.setText(fCategary.market_price+"元");
		holderItem.marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		holderItem.saleCount.setText(fCategary.now_number);
		fb.display(holderItem.imageView, fCategary.image);
		
		categoryInfo.setTag(fCategary);
		categoryInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, ((CategoryFood)v.getTag()).title+((CategoryFood)v.getTag()).id, Toast.LENGTH_SHORT).show();
				//进入美食详情界面
				Intent intent = new Intent();
				intent.setClass(context, DetailFoodCategoryActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("id", ((CategoryFood)v.getTag()).id);
				bundle.putSerializable("category", (CategoryFood)v.getTag());
				intent.putExtras(bundle);
				context.startActivity(intent);
				
			}
		});
		
		return categoryInfo;
		
	}
	/*public View getView1(int position, View agr1, ViewGroup parent) {
		Holder holder = null;
		final List<CategoryFood> fCategaries = list.get(position);
		int count = fCategaries.size();
		View categoryInfo= null;
		ViewGroup convertView = (ViewGroup) agr1;
		if (convertView==null) {
			holder = new Holder();
			convertView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.category_food_item, null);
			holder.categoryInfos = (LinearLayout)convertView.findViewById(R.id.category_infos);
			holder.title = (TextView)convertView.findViewById(R.id.title);
			holder.distance = (TextView)convertView.findViewById(R.id.distance);
			holder.restCategory = (LinearLayout)convertView.findViewById(R.id.restCategory);
			holder.zone = (TextView)convertView.findViewById(R.id.zone);
			convertView.setTag(holder);
			
			//团购数量为1-2个
			if (count==1||count==2) {
				holder.restCategory.setVisibility(View.GONE);
				for (int i = 0; i < count; i++) {
					categoryInfo = LayoutInflater.from(context).inflate(R.layout.category_food_item_item, null);
					HolderItem holderItem = new HolderItem();
					holderItem.imageView = (ImageView)categoryInfo.findViewById(R.id.image);
					holderItem.isYuyue = (ImageView)categoryInfo.findViewById(R.id.isYuyue);
					holderItem.content = (TextView)categoryInfo.findViewById(R.id.content);
					holderItem.teamPrice = (TextView)categoryInfo.findViewById(R.id.newMoney);
					holderItem.marketPrice = (TextView)categoryInfo.findViewById(R.id.oldMoney);
					holderItem.saleCount = (TextView)categoryInfo.findViewById(R.id.saleCount);
					CategoryFood fCategary = fCategaries.get(i);
					
					holderItem.content.setText(fCategary.product);
					holderItem.teamPrice.setText(fCategary.team_price);
					holderItem.marketPrice.setText(fCategary.market_price+"元");
					holderItem.marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
					holderItem.saleCount.setText(fCategary.now_number);
					fb.display(holderItem.imageView, fCategary.image);
					holder.categoryInfos.addView(categoryInfo,i);
					
					categoryInfo.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Toast.makeText(context, fCategaries.get(0).title+fCategaries.get(0).id, Toast.LENGTH_SHORT).show();
						}
					});
				}
			}else  if (count>2) {
				int restCount = count-2;
				holder.restCount = restCount;
				((TextView)holder.restCategory.getChildAt(0)).setText("查看本店其他"+restCount+"个团购");
				holder.restCategory.setOnClickListener(this);
				
				holder.holderItems= new ArrayList<CategoryFoodListViewAdapter.HolderItem>();
				holder.holderItemViews = new ArrayList<View>();
				for ( int i = 0; i < count; i++) {
					categoryInfo = LayoutInflater.from(context).inflate(R.layout.category_food_item_item, null);
					holder.holderItemViews.add(categoryInfo);//保存每一个view
					
					//初始化
					HolderItem holderItem = new HolderItem();
					holderItem.imageView = (ImageView)categoryInfo.findViewById(R.id.image);
					holderItem.isYuyue = (ImageView)categoryInfo.findViewById(R.id.isYuyue);
					holderItem.content = (TextView)categoryInfo.findViewById(R.id.content);
					holderItem.teamPrice = (TextView)categoryInfo.findViewById(R.id.newMoney);
					holderItem.marketPrice = (TextView)categoryInfo.findViewById(R.id.oldMoney);
					holderItem.saleCount = (TextView)categoryInfo.findViewById(R.id.saleCount);
					CategoryFood fCategary = fCategaries.get(i);
					//赋值
					holderItem.content.setText(fCategary.product);
					holderItem.teamPrice.setText(fCategary.team_price);
					holderItem.marketPrice.setText(fCategary.market_price+"元");
					holderItem.marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
					holderItem.saleCount.setText(fCategary.now_number);
					fb.display(holderItem.imageView, fCategary.image);
					
					categoryInfo.setTag(i);
					categoryInfo.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Toast.makeText(context, fCategaries.get((Integer)v.getTag()).title+fCategaries.get((Integer)v.getTag()).id, Toast.LENGTH_SHORT).show();
						}
					});
					holder.holderItems.add(holderItem);
					if ((i==0||i==1)&&!fCategary.click) {
						holder.categoryInfos.addView(categoryInfo);
					}
				}
				
				
			}
			holder.restCategory.setTag(holder);
		}else {
			holder = (Holder) convertView.getTag();
		}
		
		holder.title.setText(fCategaries.get(0).title);//
		holder.zone.setText(fCategaries.get(0).zone);//区域
		holder.distance.setText(format.format(fCategaries.get(0).distance/1000.0)+"km");
		holder.position = position;
		return convertView;
	}*/
	
	class Holder{
		LinearLayout categoryInfos;
		TextView zone;
		TextView title;
		TextView distance;
		LinearLayout restCategory;
		int restCount;
		int position;
	}
	class HolderItem{
		ImageView imageView;
		ImageView isYuyue;
		TextView content;
		TextView saleCount;
		TextView teamPrice;
		TextView marketPrice;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.restCategory:
			@SuppressWarnings("unchecked")
			HashMap<List<CategoryFood>, Boolean> maps = (HashMap<List<CategoryFood>, Boolean>) v.getTag();
			Boolean isClick = maps.values().iterator().hasNext()?maps.values().iterator().next():null;
			List<CategoryFood> fCategaries = maps.keySet().iterator().hasNext()?maps.keySet().iterator().next():null;
			if (isClick!=null) {
				isClick = true;
				maps.put(fCategaries, isClick);
				this.notifyDataSetChanged();
			}
			break;
		default:
			break;
		}
	}

	
}
