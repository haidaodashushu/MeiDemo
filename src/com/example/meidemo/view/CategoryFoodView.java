package com.example.meidemo.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;
import zrc.widget.ZrcListView.OnStartListener;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.ListUtils;
import com.example.meidemo.data.GlobalData;
import com.example.meidemo.view.adapter.CategoryFoodListViewAdapter;
import com.example.meidemo.view.entity.CategaryFoods;
import com.example.meidemo.view.entity.CategoryFood;
import com.example.meidemo.view.interfaces.ICategory;
/**
 * 实现ICategory接口，方便排序
 * @author Administrator
 *
 */
public class CategoryFoodView extends LinearLayout implements ICategory{
	public static final String TAG = "FoodCategary";
	/** 分类*/
	String category= "";
	/**该分类编号*/
	String index;
	ProgressBar progressBar;
	Context context;
	ZrcListView listView;
	CategoryFoodListViewAdapter adapter;
	List<HashMap<List<CategoryFood>, Boolean>>  list;
	int pageIndex = 1;//页数
	HashMap<String, Object> data;//由onUpdateLoadDate()方法传入的参数，目前用于排序
	
	public boolean isFirstRefresh = true;//判断是否第一次刷新,默认为true
	public CategoryFoodView(Context context){
		super(context);
		this.context = context;
		initView();
	}
	public CategoryFoodView(Context context,String category,String index) {
		super(context);
		this.context = context;
		this.category = category;
		this.index = index;
		data = new HashMap<String, Object>();
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		View view = inflate(context, R.layout.category_food, this);
		progressBar = (ProgressBar)findViewById(R.id.progressbar);
		listView = (ZrcListView) findViewById(R.id.pull_refresh_list);
		// 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
		SimpleHeader header = new SimpleHeader(context);
		header.setTextColor(0xff0066aa);
		header.setCircleColor(0xff33bbee);
		listView.setHeadable(header);

		// 设置加载更多的样式（可选）
		SimpleFooter footer = new SimpleFooter(context);
		footer.setCircleColor(0xff33bbee);
		listView.setFootable(footer);
		
		listView.setOnRefreshStartListener(new OnStartListener() {
			
			@Override
			public void onStart() {
				refreshData(data);
			}
		});
		listView.setOnLoadMoreStartListener(new OnStartListener() {
			
			@Override
			public void onStart() {
				loadMore(data);
			}
		});
		list = new ArrayList<HashMap<List<CategoryFood>, Boolean>>();
		adapter = new CategoryFoodListViewAdapter(context, list);
		listView.setAdapter(adapter);
		requestdate(data);
	}
	private void requestdate(HashMap<String, Object> data) {
		FinalHttp finalHttp = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("group", index);
		params.put("pg", "1");
		params.put("ps", "20");
		String position = "0";
		if (data!=null&&data.containsKey("sort")) {
//			0是销量，1是价格升，2 价格降，3 发布时间
			this.data.putAll(data);
			position = data.get("sort").toString();
			params.put("sort", position);
		}
		if (data!=null&&data.containsKey("zones")) {
//			城市ID，有TopNavigationZones传过来
			this.data.putAll(data);
			position = data.get("zones").toString();
			params.put("city", position);
		}
		if (data!=null&&data.containsKey("rightCategory")) {
			this.data.putAll(data);
			position = data.get("rightCategory").toString();
			params.put("group", position);
		}
		
		finalHttp.get(GlobalData.ip+GlobalData.FOODCATEGORY, params, new AjaxCallBack<Object>() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				if (isFirstRefresh) {
					progressBar.setVisibility(View.VISIBLE);
				}
				isFirstRefresh = false;
			}
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				CategaryFoods categarys = new CategaryFoods(t.toString());
				list.clear();
				list.addAll(categarys.list);
				adapter.setList(list);
				listView.setSelection(0);
				listView.setRefreshSuccess();
				progressBar.setVisibility(View.GONE);
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				listView.setRefreshFail();
				progressBar.setVisibility(View.GONE);
			}
		});
		
	}
	private void refreshData(HashMap<String, Object> data) {
			FinalHttp finalHttp = new FinalHttp();
			AjaxParams params = new AjaxParams();
			params.put("group", index);
			params.put("pg", pageIndex+"");
			pageIndex++;
			params.put("ps", "20");
			String position = "0";
			if (data!=null&&data.containsKey("sort")) {
//				0是销量，1是价格升，2 价格降，3 发布时间
				this.data.putAll(data);
				position = data.get("sort").toString();
				params.put("sort", position);
			}
			if (data!=null&&data.containsKey("zones")) {
//				城市ID，有TopNavigationZones传过来
				this.data.putAll(data);
				position = data.get("zones").toString();
				params.put("city", position);
			}
			if (data!=null&&data.containsKey("rightCategory")) {
				this.data.putAll(data);
				position = data.get("rightCategory").toString();
				params.put("group", position);
			}
			
			finalHttp.get(GlobalData.ip+GlobalData.FOODCATEGORY, params, new AjaxCallBack<Object>() {
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					super.onStart();
					if (isFirstRefresh) {
						progressBar.setVisibility(View.VISIBLE);
					}
					isFirstRefresh = false;
				}
				@Override
				public void onSuccess(Object t) {
					super.onSuccess(t);
					CategaryFoods categarys = new CategaryFoods(t.toString());
					ListUtils.addAllToFirst(list, categarys.list);
					adapter.setList(list);
					listView.setSelection(0);
					listView.setRefreshSuccess();
					progressBar.setVisibility(View.GONE);
				}
				@Override
				public void onFailure(Throwable t, int errorNo, String strMsg) {
					// TODO Auto-generated method stub
					super.onFailure(t, errorNo, strMsg);
					listView.setRefreshFail();
					progressBar.setVisibility(View.GONE);
				}
			});
			
	}
	public void loadMore(HashMap<String, Object> data){
		FinalHttp finalHttp = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("group", index);
		params.put("pg", pageIndex+"");
		params.put("ps", "20");
		String position = "0";
		if (data!=null&&data.containsKey("sort")) {
//			0是销量，1是价格升，2 价格降，3 发布时间
			this.data = data; 
			position = data.get("sort").toString();
		}
		if (data!=null&&data.containsKey("zones")) {
			this.data = data; 
			position = data.get("sort").toString();
		}
		params.put("sort", position);
		
		pageIndex++;
		
		finalHttp.get(GlobalData.ip+GlobalData.FOODCATEGORY, params, new AjaxCallBack<Object>() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				progressBar.setVisibility(View.VISIBLE);
			}
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				CategaryFoods categarys = new CategaryFoods(t.toString());
				list.addAll(categarys.list);
				adapter.setList(list);
				progressBar.setVisibility(View.GONE);
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				progressBar.setVisibility(View.GONE);
			}
		});
	}
	@Override
	public void onUpdateLoadDate(HashMap<String, Object> data) {
		if (data.containsKey("category")) {
			Log.i("onUpdateLoadDate", data.get("category").toString());
			data.remove("category");
		}
		requestdate(data);
	}
}
