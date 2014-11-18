package com.example.meidemo.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;
import zrc.widget.ZrcListView.OnStartListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.ListUtils;
import com.example.meidemo.CommonUtils.ScreenUtil;
import com.example.meidemo.data.GlobalData;
import com.example.meidemo.view.adapter.CategoryVideoListViewAdapter;
import com.example.meidemo.view.entity.CategaryFoods;
import com.example.meidemo.view.entity.CategoryVideo;
import com.example.meidemo.view.entity.CategoryVideoPager;
import com.example.meidemo.view.entity.CategoryVideoPagers;
import com.example.meidemo.view.entity.CategoryVideos;
import com.example.meidemo.view.interfaces.ICategory;

public class CategoryVideoView extends LinearLayout implements ICategory {
	public static final String TAG = "CategoryVideo";
	/** 分类*/
	String category= "";
	/**该分类编号*/
	String index;
	int pageIndex;// 页数
	HashMap<String, Object> data;// 由onUpdateLoadDate()方法传入的参数，目前用于排序
	public boolean isFirstRefresh = true;// 判断是否第一次刷新,默认为true

	Context context;
	// ViewPager videoPager;
	ProgressBar progressBar;
	ZrcListView listView;
	CategoryVideoListViewAdapter adapter;
	List<CategoryVideoPager> list;// 顶部的电影推荐
	List<CategoryVideo> listCategorys;// 电影列表信息
	LinearLayout videoPager;

	public CategoryVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CategoryVideoView(Context context,String category,String index) {
		super(context);
		this.context = context;
		this.category = category;
		this.index = index;
		initView();
	}

	private void initView() {
		View view = inflate(context, R.layout.category_video, this);
		CategoryVideoPagerView videoPagerView = new CategoryVideoPagerView(context);
		videoPager = videoPagerView.getVideoPager();
		
		progressBar = (ProgressBar) findViewById(R.id.progressbar);

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
				requestdate(data);
			}
		});
		listView.setOnLoadMoreStartListener(new OnStartListener() {

			@Override
			public void onStart() {
				loadMore(data);
			}
		});
		listView.addHeaderView(videoPagerView);
		listCategorys = new ArrayList<CategoryVideo>();
		adapter = new CategoryVideoListViewAdapter(context, listCategorys);
		listView.setAdapter(adapter);
		requestdate(null);
		requestdatePagerVideo();
	}

	private void requestdate(HashMap<String, Object> data) {
		FinalHttp finalHttp = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("group", "538");
		params.put("pg", "1");
		params.put("ps", "20");
		String position = "0";
		if (data != null && data.containsKey("sort")) {
			// 0是销量，1是价格升，2 价格降，3 发布时间
			this.data = data; 
			position = data.get("position").toString();
		}
		params.put("sort", position);

		finalHttp.get(GlobalData.ip + GlobalData.FILECATEGORY, params,
				new AjaxCallBack<Object>() {
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
						CategoryVideos videos = new CategoryVideos(t.toString());
						ListUtils.addAllToFirst(listCategorys, videos.getList());
						adapter.setList(listCategorys);
						listView.setSelection(0);
						listView.setRefreshSuccess();
						progressBar.setVisibility(View.GONE);
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						// TODO Auto-generated method stub
						super.onFailure(t, errorNo, strMsg);
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
			position = data.get("position").toString();
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
				CategoryVideos categarys = new CategoryVideos(t.toString());
				listCategorys.addAll(categarys.getList());
				adapter.setList(listCategorys);
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
	/**
	 * 请求顶部电影推荐
	 * 
	 * @param data
	 */
	private void requestdatePagerVideo() {
		FinalHttp finalHttp = new FinalHttp();
		finalHttp.get(GlobalData.ip + GlobalData.FILMTOPLIST, null,
				new AjaxCallBack<Object>() {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}

					@Override
					public void onSuccess(Object t) {
						super.onSuccess(t);
						Log.i(TAG, t.toString());
						// CategaryFoods categarys = new
						// CategaryFoods(t.toString());
						CategoryVideoPagers pagers = new CategoryVideoPagers(t
								.toString());
						list = pagers.getList();
						if (list != null && list.size() > 0) {
							int width = ScreenUtil.getWidth(context)
									/ ((list.size() >= 4) ? 4 : list.size());
							LayoutParams params = new LayoutParams(width,
									LayoutParams.WRAP_CONTENT);
							params.gravity = Gravity.CENTER;
							params.weight = 1;
							FinalBitmap fb = FinalBitmap.create(context);
							Bitmap loadingBitmap;
							Bitmap laodfailBitmap;
							loadingBitmap = BitmapFactory.decodeResource(
									context.getResources(),
									R.drawable.ic_launcher);
							laodfailBitmap = BitmapFactory.decodeResource(
									context.getResources(),
									R.drawable.ic_launcher);

							for (int i = 0; i < list.size(); i++) {
								View view = LayoutInflater
										.from(context)
										.inflate(
												R.layout.category_video_pager_item,
												null);
								ImageView image = (ImageView) view
										.findViewById(R.id.videoImage);
								fb.display(image, list.get(i).image,
										loadingBitmap, laodfailBitmap);
								TextView videoName = (TextView) view
										.findViewById(R.id.videoName);
								videoName.setText(list.get(i).title);
								TextView videoScore = (TextView) view
										.findViewById(R.id.videoScore);
								videoScore.setText(list.get(i).score);
								videoPager.addView(view, params);
							}
						}
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						// TODO Auto-generated method stub
						super.onFailure(t, errorNo, strMsg);
					}
				});
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {
		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// to refresh frameLayout
			// if (viewPagerContainer != null) {
			// viewPagerContainer.invalidate();
			// }
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public void onUpdateLoadDate(HashMap<String, Object> data) {
		// TODO Auto-generated method stub

	}

}
