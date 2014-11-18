package com.example.meidemo.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.ScreenUtil;
import com.example.meidemo.view.adapter.TopNavigationCategoryAdapterLeft;
import com.example.meidemo.view.adapter.TopNavigationCategoryAdapterRight;
import com.example.meidemo.view.entity.CategoryInfos.CategoryInfo;
import com.example.meidemo.view.interfaces.OnListItemClickListener;

public class TopNavigationCategory extends LinearLayout {
	PopupWindow pop;
	Context context;
	ListView leftListView;// 左边的listView
	TopNavigationCategoryAdapterLeft leftAdapter;
	ListView rightListView;// 右边的listView
	TopNavigationCategoryAdapterRight rightAdapter;
	View oldView;
	OnListItemClickListener leftitemClickListener;
	OnListItemClickListener rightItemClickListener;
	List<HashMap<String, String[]>> list = new ArrayList<HashMap<String, String[]>>();
	String category;//初始化分类信息，确定是哪种团购（美食，电影，丽人。。。）
	int index= 0;//该初始信息对应下标
	List<String> leftList;//左边list的内容
	List<String> rightList;//右边list的内容
	List<String> leftListIds;
	List<String> rightListIds;
	HashMap<CategoryInfo, List<CategoryInfo>> categorys;
	public TopNavigationCategory(Context context, PopupWindow pop,String category,HashMap<CategoryInfo, List<CategoryInfo>> categorys,
			OnListItemClickListener leftitemClickListener,OnListItemClickListener rightItemClickListener) {
		// TODO Auto-generated constructor stub
		super(context);
		this.pop = pop;
		this.context = context;
		this.category = category;
		this.categorys = categorys;
		this.leftitemClickListener = leftitemClickListener;
		this.rightItemClickListener  = rightItemClickListener;
		leftList = new ArrayList<String>();
		rightList = new ArrayList<String>();
		initView();
		
		initDate();
	}

	public TopNavigationCategory(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context, attrs);
	}

	public TopNavigationCategory(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void initView() {
		View view = inflate(context, R.layout.top_navigation_category, this);

		leftListView = (ListView) view.findViewById(R.id.h_pop_list);
		leftListView.getLayoutParams().height = (int) (ScreenUtil
				.getHeight(context) * 0.6);
		leftAdapter = new TopNavigationCategoryAdapterLeft(leftList,context);
		leftListView.setAdapter(leftAdapter);
		leftListView.setOnItemClickListener(leftListViewListener);

		rightListView = (ListView) view.findViewById(R.id.h_pop_list1);
		rightListView.getLayoutParams().height = (int) (ScreenUtil
				.getHeight(context) * 0.6);
		rightAdapter = new TopNavigationCategoryAdapterRight(rightList,
				context);
		rightListView.setAdapter(rightAdapter);
		rightListView.setOnItemClickListener(new RightListViewListener());
//		this.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		view.startAnimation(AnimationUtils.loadAnimation(context,
				R.anim.in_toptobottom));
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
	}

	OnItemClickListener leftListViewListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			leftAdapter.setCurrent(position);
			HashMap<String, String[]> temphashMap= list.get(position);
			Iterator<Entry<String, String[]>> iterator = temphashMap.entrySet().iterator();
			List<String> temp  =Arrays.asList(iterator.next().getValue());
			rightList.clear();
			rightList.addAll(temp);
			rightAdapter.setrightList(rightList);
			setListViewIsShow(rightListView, rightList);
			if (leftitemClickListener!=null) {
				//回调方法
				HashMap<String, Object> data = new HashMap<String, Object>();
				leftitemClickListener.itemClickListener(data);
			}
		}
	};
	class RightListViewListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			pop.dismiss();
			Log.i("POPRent", position+"");
			if (rightItemClickListener!=null) {
				HashMap<String, Object> data = new HashMap<String, Object>();
				data.put("rightCategory", rightListIds.get(position));
				data.put("position", position);
				data.put("data", rightList.get(position));
				rightItemClickListener.itemClickListener(data);
			}
		}
	}

	//初始化数据
	private void initDate() {
		
		int index = 0;
		
		String[] item = new String[]{};
		String[] itemId = new String[]{};
		List<CategoryInfo> itemCategoryInfos = new ArrayList<CategoryInfo>();
		if (categorys==null) {
			return;
		}
		Iterator<CategoryInfo> iterator = categorys.keySet().iterator();
		leftListIds = new ArrayList<String>();
		rightListIds = new ArrayList<String>();
		while (iterator.hasNext()) {
			CategoryInfo categoryInfo = iterator.next();
			leftList.add(categoryInfo.name);
			leftListIds.add(categoryInfo.id);
			itemCategoryInfos = categorys.get(categoryInfo);
			item = new String[itemCategoryInfos.size()];
			itemId = new String[itemCategoryInfos.size()];
			if (categoryInfo.name.contains(category)) {
				this.index = index;
				rightList.clear();
				rightListIds.clear();
				for (int j = 0; j < itemCategoryInfos.size(); j++) {
					CategoryInfo categoryInfoItem =itemCategoryInfos.get(j);
					item[j] = categoryInfoItem.name;
					itemId[j] = categoryInfoItem.id;
				}
				
				rightList.addAll(Arrays.asList(item));
				rightListIds.addAll(Arrays.asList(itemId));
				rightAdapter.notifyDataSetChanged();
			}
			
			HashMap<String, String[]> hashMap = new HashMap<String, String[]>();
			hashMap.put(categoryInfo.name, item);
			list.add(hashMap);
			index++;
		}
		
		/*
		String[] string = new String[0];
		hashMap.put("全部分类", string);
		list.add(hashMap);
		
		hashMap = new HashMap<String, String[]>();
		string = new String[0] ;
		hashMap.put("电影", string);
		list.add(hashMap);
		
		hashMap = new HashMap<String, String[]>();
		string = new String[] { "全部", "火锅", "自助餐", "西餐", "日韩料理",
				"蛋糕甜点" };
		hashMap.put("美食", string);
		list.add(hashMap);
			
		hashMap = new HashMap<String, String[]>();
		string = new String[] { "全部", "经济型酒店", "豪华酒店", "主题酒店", "公寓型酒店",
				"客栈" ,"青年旅社","度假酒店"};
		hashMap.put("酒店", string);
		list.add(hashMap);
		
		hashMap = new HashMap<String, String[]>();
		string = new String[0] ;
		hashMap.put("KTV", string);
		list.add(hashMap);
		
		hashMap = new HashMap<String, String[]>();
		string = new String[0] ;
		hashMap.put("美容", string);
		list.add(hashMap);
		
		hashMap = new HashMap<String, String[]>();
		string = new String[0] ;
		hashMap.put("景点门票", string);
		list.add(hashMap);
		
		
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, String[]> temphashMap= list.get(i);
			Iterator<String> iterator = temphashMap.keySet().iterator();
			String key = iterator.next();
			leftList.add(key);
			if (category.equals(key)) {
				index = i;
				rightList.clear();
				rightList.addAll(Arrays.asList(temphashMap.get(key)));
				rightAdapter.notifyDataSetChanged();
			}
		}*/
		leftAdapter.notifyDataSetChanged();
		leftAdapter.setCurrent(this.index);
		
		setListViewIsShow(rightListView, rightList);
		Log.i("TopNav", Arrays.toString(leftList.toArray()));
		
		
	}
	/**设置listView是否显示和隐藏</p>根据list的大小*/
	public void setListViewIsShow(ListView view,List<String> rightList){
		if (rightList==null||rightList.size()==0) {
			view.setVisibility(View.INVISIBLE);
		}else {
			view.setVisibility(View.VISIBLE);
		}
	}
	
	
	
}
