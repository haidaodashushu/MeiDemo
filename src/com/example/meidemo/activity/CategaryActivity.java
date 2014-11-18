package com.example.meidemo.activity;

import java.util.HashMap;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.example.meidemo.R;
import com.example.meidemo.Popwindow.MyPopupWindow;
import com.example.meidemo.baiduLocation.LocationDate;
import com.example.meidemo.baiduLocation.MyBDLocationListener.OnLocationListener;
import com.example.meidemo.baiduLocation.MyLocationClient;
import com.example.meidemo.data.GlobalData;
import com.example.meidemo.view.CategoryFoodView;
import com.example.meidemo.view.CategoryKTVView;
import com.example.meidemo.view.CategoryVideoView;
import com.example.meidemo.view.TopClassifyView;
import com.example.meidemo.view.TopClassifyView.OnItemClick;
import com.example.meidemo.view.TopMenuView;
import com.example.meidemo.view.TopNavigationCategory;
import com.example.meidemo.view.TopNavigationSort;
import com.example.meidemo.view.TopNavigationZones;
import com.example.meidemo.view.entity.CategoryInfos;
import com.example.meidemo.view.entity.CategoryInfos.CategoryInfo;
import com.example.meidemo.view.interfaces.ICategory;
import com.example.meidemo.view.interfaces.OnListItemClickListener;

public class CategaryActivity extends Activity implements OnItemClick,
		OnClickListener {
	/** 分类 */
	String category = "";
	/** 该分类编号 */
	String index;
	HashMap<CategoryInfo, List<CategoryInfo>> categorys;
	String[] zones;
	TopClassifyView tcView;

	/** 弹出框 */
	PopupWindow pop;
	FrameLayout containner;// view容器
	View contentView;// 填充的View
	TextView location_text;
	TopMenuView topmenu;
	TopNavigationZones navigationZones;
	TopNavigationCategory navigationCategory;
	TopNavigationSort navigationSort;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category);
		Intent intent = getIntent();
		topmenu = (TopMenuView) findViewById(R.id.topmenu);
		topmenu.setcityData();
		if (intent.hasExtra("category") && intent.hasExtra("index")) {
			category = intent.getStringExtra("category");
			index = intent.getStringExtra("index");
		}
		tcView = (TopClassifyView) findViewById(R.id.classify);
		try {
			tcView.addAllView(new int[] { 0, 1, 2 }, new String[] { category,
					"全城", "离我最近" }, new int[] { 0, 0, 0 },
					new int[] { 0, 0, 0 }, new int[] {
							R.drawable.ic_global_arrow_green_fold_normal,
							R.drawable.ic_global_arrow_green_fold_normal,
							R.drawable.ic_global_arrow_green_fold_normal },
					new int[] { R.drawable.ic_global_arrow_green_unfold_normal,
							R.drawable.ic_global_arrow_green_unfold_normal,
							R.drawable.ic_global_arrow_green_unfold_normal });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tcView.setOnItemClick(this);

		pop = new MyPopupWindow(this, tcView).getPop();

		containner = (FrameLayout) findViewById(R.id.containner);
		location_text = (TextView) findViewById(R.id.location_text);
		if (category.equals("美食")) {
			contentView = new CategoryFoodView(this, category, index);
		} else if (category.equals("电影")) {
			contentView = new CategoryVideoView(this, category, index);
		} else if (category.equals("KTV")) {
			contentView = new CategoryKTVView(this, category, index);
		} else {
			contentView = new CategoryFoodView(this, category, index);
		}
		location_text.setOnClickListener(this);
		if (LocationDate.getInstance().addr != null
				&& !"".equals(LocationDate.getInstance().addr)) {
			location_text.setText(LocationDate.getInstance().addr);
		} else {
			MyLocationClient.getInstance().initLocation(this,
					new OnLocationListener() {

						@Override
						public void onListener(BDLocation data) {
							location_text.setText(data.getAddrStr());
						}
					});
		}

		requestCategoryInfos();
		containner.addView(contentView);
	}

	private void requestCategoryInfos() {
		FinalHttp finalHttp = new FinalHttp();
		finalHttp.configTimeout(5000);
		finalHttp.get(GlobalData.ip + GlobalData.TOPCLASSIFY, null,
				new AjaxCallBack<Object>() {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						Log.i("Category", "kaishi");
					}

					@Override
					public void onSuccess(Object t) {
						// TODO Auto-generated method stub
						Log.i("Category", "chenggong");
						CategoryInfos infos = new CategoryInfos(t.toString());
						Log.i("Category", t.toString());
						categorys = infos.categorys;
						zones = infos.zones;
						tcView.setViewEnable();
						initNavigation();
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						// TODO Auto-generated method stub
						super.onFailure(t, errorNo, strMsg);
						Log.i("Category", "shibai");
						// tcView.setViewInable();
						Toast.makeText(getApplicationContext(), "数据加载失败，请检查网络",
								Toast.LENGTH_SHORT).show();
					}
				});

	}

	/**
	 * 初始化顶部菜单
	 */
	private void initNavigation() {
		navigationCategory = new TopNavigationCategory(this, pop, category,
				categorys, new OnListItemClickListener() {

					@Override
					public void itemClickListener(HashMap<String, Object> data) {
						/*
						 * Toast.makeText(getApplicationContext(), "点击了左边的事件",
						 * 2000).show();
						 */
					}
				}, new OnListItemClickListener() {

					@Override
					public void itemClickListener(HashMap<String, Object> data) {
						Toast.makeText(getApplicationContext(), "点击了右边的事件",
								2000).show();
						data.put("category", category);
						((ICategory) contentView).onUpdateLoadDate(data);
					}
				});
		navigationZones = new TopNavigationZones(this, pop, zones,
				new OnListItemClickListener() {

					@Override
					public void itemClickListener(HashMap<String, Object> data) {
						data.put("category", category);
						((ICategory) contentView).onUpdateLoadDate(data);

					}
				});
		navigationSort = new TopNavigationSort(this, pop,
				new OnListItemClickListener() {

					@Override
					public void itemClickListener(HashMap<String, Object> data) {
						data.put("category", category);
						((ICategory) contentView).onUpdateLoadDate(data);
					}
				});
	}

	@Override
	public void onItemClick(View v) {
		Log.i("LocalService", v.getId() + "::");
		switch (v.getId()) {
		case 0:
			tcView.setFrameLayout(0);
			// pop.setContentView(new PopupWindowAgentView(this, pop, null));
			pop.setContentView(navigationCategory);
			pop.showAsDropDown(v, 0, 0);

			break;
		case 1:
			tcView.setFrameLayout(1);
			// ((ViewGroup)pop.getContentView()).removeAllViews();
			// ((ViewGroup)pop.getContentView()).addView(new
			// PopupWindowRentView(mActivity, pop));

			pop.setContentView(navigationZones);
			pop.showAsDropDown(tcView, 0, 0);

			break;
		case 2:
			tcView.setFrameLayout(2);
			pop.setContentView(navigationSort);
			pop.showAsDropDown(v, 0, 0);
			break;
		case 3:
			tcView.setFrameLayout(3);
			pop.showAsDropDown(v, 0, 0);
			break;

		default:
			break;
		}
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		pop.dismiss();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.location_text:
			location_text.setText("正在定位。。");
			MyLocationClient.getInstance().initLocation(this,
					new OnLocationListener() {

						@Override
						public void onListener(BDLocation data) {
							if (data.getLocType()==61||data.getLocType()==66|| 
									data.getLocType()==161||data.getLocType() ==65) {
								location_text.setText(data.getAddrStr());
							}else {
								location_text.setText("定位失败");
							}
							
						}
					});
			break;

		default:
			break;
		}
	}

}
