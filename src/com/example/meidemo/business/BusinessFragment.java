package com.example.meidemo.business;

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
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.example.meidemo.BaseFragment;
import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.ListUtils;
import com.example.meidemo.Popwindow.MyPopupWindow;
import com.example.meidemo.baiduLocation.LocationDate;
import com.example.meidemo.baiduLocation.MyLocationClient;
import com.example.meidemo.baiduLocation.MyBDLocationListener.OnLocationListener;
import com.example.meidemo.data.GlobalData;
import com.example.meidemo.view.PopupCategoryView;
import com.example.meidemo.view.PopupWindowRentView;
import com.example.meidemo.view.TopClassifyView;
import com.example.meidemo.view.TopClassifyView.OnItemClick;
import com.example.meidemo.view.TopMenuView;
import com.example.meidemo.view.TopNavigationCategory;
import com.example.meidemo.view.TopNavigationSort;
import com.example.meidemo.view.TopNavigationZones;
import com.example.meidemo.view.adapter.BusinessListViewAdapter;
import com.example.meidemo.view.entity.BusinessData;
import com.example.meidemo.view.entity.BusinessViewGrid;
import com.example.meidemo.view.entity.CategoryInfos;
import com.example.meidemo.view.entity.CategoryInfos.CategoryInfo;
import com.example.meidemo.view.interfaces.ICategory;
import com.example.meidemo.view.interfaces.OnListItemClickListener;

public class BusinessFragment extends  BaseFragment implements OnItemClick,OnClickListener{
	private Activity mActivity;
	
	/** 分类 */
	String category = "";
	/** 该分类编号 */
	String index;
	HashMap<CategoryInfo, List<CategoryInfo>> categorys ;
	String[] zones;
	
	private Button btn;
	private View view;
	private TopMenuView topmenu;
	private TopClassifyView tcView;
	private int page = 1;
	/** 顶部选择栏 */
	FrameLayout headcategory, headzone, headsort;
//	PullToRefreshListView mPullToRefreshListView;
//	ListView listView;
	private List<BusinessViewGrid> blist;
	private ZrcListView listView;
	private Handler handler;
	private BusinessListViewAdapter adapter;
	private int pageId = -1;
	private PopupWindow pop;
	
	TextView location_text;
	/*
	Handler handler  = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what==1) {
				mItems.addFirst("add first11111");
			}else {
				mItems.addFirst("add last22222");
			}
//			mPullToRefreshListView.onRefreshComplete();
			Toast.makeText(getActivity(), "刷新", 2000).show();
		}
	};
	
   */

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.business_fragment, container, false);
		
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		mPullToRefreshListView = (PullToRefreshListView) view
//				.findViewById(R.id.pull_refresh_list);
//		listView = mPullToRefreshListView.getRefreshableView();
		listView = (ZrcListView)view.findViewById(R.id.pull_refresh_list);
		topmenu = (TopMenuView)view.findViewById(R.id.topmenu);
		topmenu.setcityData();
		handler = new Handler();
		category = "全部";
		tcView = (TopClassifyView) view.findViewById(R.id.classify);
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
		
		
		location_text = (TextView) view.findViewById(R.id.location_text);
		location_text.setOnClickListener(this);
		if (LocationDate.getInstance().addr != null
				&& !"".equals(LocationDate.getInstance().addr)) {
			location_text.setText(LocationDate.getInstance().addr);
		} else {
			MyLocationClient.getInstance().initLocation(mActivity,
					new OnLocationListener() {

						@Override
						public void onListener(BDLocation data) {
							location_text.setText(data.getAddrStr());
						}
					});
		}
		
		
		tcView.setOnItemClick(this);
		
		pop = new MyPopupWindow(mActivity, tcView).getPop();
		
		 // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(mActivity);
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        listView.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(mActivity);
        footer.setCircleColor(0xff33bbee);
        listView.setFootable(footer);

        // 设置列表项出现动画（可选）
//        listView.setItemAnimForTopIn(R.anim.topitem_in);
//        listView.setItemAnimForBottomIn(R.anim.bottomitem_in);
        // 下拉刷新事件回调（可选）
        listView.setOnRefreshStartListener(new OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        // 加载更多事件回调（可选）
        listView.setOnLoadMoreStartListener(new OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });
        blist = new ArrayList<BusinessViewGrid>();
        adapter = new BusinessListViewAdapter(blist,listView, mActivity);
        listView.setAdapter(adapter);
        
        requestCategoryInfos();
        
        //初始化商家列表
        refresh();
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		topmenu.setcityData();
		 
		 
	}
	
	public void refresh(){
		FinalHttp finalHttp = new FinalHttp();
		final AjaxParams params = new AjaxParams();
		params.put("cid", GlobalData.clientID);
		params.put("ty", GlobalData.channel);
		params.put("sid", GlobalData.sid);
		params.put("cityid", "0");
		params.put("groupid", "23");
		params.put("pg", "1");
		params.put("ps", "20");
		params.put("callback", "1");
		finalHttp.get(GlobalData.ip + "tuan/partner/getList", new AjaxCallBack<Object>() {
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				Log.v("tuan", "商家列表 ：initData onFailure");
				listView.setRefreshFail("加载失败");
			}

			@Override
			public void onStart() {
			}

			@Override
			public void onSuccess(Object t) {
				pageId = 1;
				String data = t.toString();
				Log.v("tuan", "商家列表 :"+ data);
				
				BusinessData partnerData = new BusinessData(data);
				ListUtils.addAllToFirst(blist, partnerData.topInfos);
				
				adapter.setLeftList(blist);
				
                listView.setRefreshSuccess("加载成功"); // 通知加载成功
                listView.startLoadMore(); // 开启LoadingMore功能
			}
		});
	}
	
	private void loadMore(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageId++;
                if(pageId<80){
                	FinalHttp finalHttp = new FinalHttp();
            		final AjaxParams params = new AjaxParams();
            		params.put("cid", GlobalData.clientID);
            		params.put("ty", GlobalData.channel);
            		params.put("sid", GlobalData.sid);
            		params.put("cityid", "0");
            		params.put("groupid", "23");
            		params.put("pg", pageId+"");
            		params.put("ps", "20");
            		params.put("callback", "1");
            	
            		finalHttp.post(GlobalData.ip + "tuan/partner/getList?groupid=23&pg="+pageId+"&ps=20", new AjaxCallBack<Object>() {
            			@Override
            			public void onFailure(Throwable t, int errorNo, String strMsg) {
            				Log.v("tuan2", "商家列表 ：initData onFailure");
            			}

            			@Override
            			public void onStart() {
            			}

            			@Override
            			public void onSuccess(Object t) {
            		 
            				String data = t.toString();
            				Log.v("tuan2", "商家列表 :"+" " + data);
            				
            				BusinessData partnerData = new BusinessData(data);
            				 
            				blist.addAll(partnerData.topInfos);
            				adapter.notifyDataSetChanged();
                            listView.setLoadMoreSuccess();
            			}
            		}); 
                   
                }else{
                    listView.stopLoadMore();
                }
            }
        }, 2 * 1000);
    }
	
	 
	
	

	/**
	 * 设置顶部选择栏的点击效果
	 * 
	 * @param index
	 *            当前点击的选择框的下标
	 *//*
	private void setFrameLayout(int index) {
		int count = tableRow.getChildCount();
		for (int i = 0; i < count; i += 2) {
			View temp = tableRow.getChildAt(i);
			Log.v("frame", temp.toString());
			TextView temptext = null;
			View tempView = null;
			if (temp instanceof FrameLayout) {
				FrameLayout layout = (FrameLayout) temp;
				if (layout.getChildCount() > 1) {
					temptext = (TextView) layout.getChildAt(0);
					tempView = (View) layout.getChildAt(1);
				}
			}
			if (temptext == null || tempView == null) {
				continue;
			} else {
				if (i == (index * 2)) {
					// Drawable daDrawable =
					// mActivity.getResources().getDrawable(R.drawable.ic_global_arrow_green_fold_normal);
					// if (daDrawable==null) {
					// Log.i("tag", "null");
					// }else {
					// Log.i("tag", "notnull");
					// }
					temptext.setTextColor(mActivity.getResources().getColor(
							R.color.h_text_select));
					// temptext.setCompoundDrawablesWithIntrinsicBounds(left,
					// top, right, bottom)
					temptext.setCompoundDrawablesWithIntrinsicBounds(
							null,
							null,
							mActivity
									.getResources()
									.getDrawable(
											R.drawable.ic_global_arrow_green_unfold_normal),
							null);
					tempView.setVisibility(View.VISIBLE);
				} else {
					temptext.setTextColor(mActivity.getResources().getColor(
							R.color.h_text_unselect));
					temptext.setCompoundDrawablesWithIntrinsicBounds(
							null,
							null,
							mActivity
									.getResources()
									.getDrawable(
											R.drawable.ic_global_arrow_green_fold_normal),
							null);
					tempView.setVisibility(View.GONE);
				}

			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.headcategory:
			//setFrameLayout(0);
			pop.setContentView(new PopupWindowRentView(mActivity, pop));
			pop.showAsDropDown(v, 0, 0);

			break;
		case R.id.headzone:
			setFrameLayout(1);
			pop.setContentView(new PopupCategoryView(mActivity, pop, this));
			pop.showAsDropDown(v, 0, 0);

			break;
		default:
			break;
		}
	}*/


	@Override
	public void onItemClick(View view) {
		switch (view.getId()) {
		case 0:
			tcView.setFrameLayout(0);
			// pop.setContentView(new PopupWindowAgentView(this, pop, null));
			pop.setContentView(new TopNavigationCategory(mActivity, pop, category,
					categorys, new OnListItemClickListener() {

						@Override
						public void itemClickListener(
								HashMap<String, Object> data) {
							Toast.makeText(mActivity, "点击了左边的事件",
									2000).show();
						}
					}, new OnListItemClickListener() {

						@Override
						public void itemClickListener(
								HashMap<String, Object> data) {
							Toast.makeText(mActivity, "点击了右边的事件",
									2000).show();
						}
					}));
			pop.showAsDropDown(view, 0, 0);

			break;
		case 1:
			tcView.setFrameLayout(1);
			// ((ViewGroup)pop.getContentView()).removeAllViews();
			// ((ViewGroup)pop.getContentView()).addView(new
			// PopupWindowRentView(mActivity, pop));
			
			pop.setContentView(new TopNavigationZones(mActivity, pop, zones, new OnListItemClickListener() {
				
				@Override
				public void itemClickListener(HashMap<String, Object> data) {
					// TODO Auto-generated method stub
					
				}
			}));
			pop.showAsDropDown(tcView, 0, 0);

			break;
		case 2:
			tcView.setFrameLayout(2);
			pop.setContentView(new TopNavigationSort(mActivity, pop,
					new OnListItemClickListener() {

						@Override
						public void itemClickListener(
								HashMap<String, Object> data) {
							data.put("category", category);
//							((ICategory) contentView).onUpdateLoadDate(data);
							refresh();
						}
					}));
			pop.showAsDropDown(view, 0, 0);
			break;
		case 3:
			tcView.setFrameLayout(3);
			pop.showAsDropDown(view, 0, 0);
			break;

		default:
			break;
		}
		
	}

	private void requestCategoryInfos() {
		FinalHttp finalHttp = new FinalHttp();
		finalHttp.configTimeout(5000);
		finalHttp.get(GlobalData.ip+GlobalData.TOPCLASSIFY, null,new AjaxCallBack<Object>() {
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
				/*categorys = new HashMap<String, List<CategoryInfo>>();
				Iterator<CategoryInfo> iterator = infos.categorys.keySet().iterator();
				while (iterator.hasNext()) {
					CategoryInfo categoryInfo = iterator.next();
					if (categoryInfo.name.contains(category)) {
						infos.categorys.get(categoryInfo);
					}
				}*/
				tcView.setViewEnable();
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				Log.i("Category", "shibai");
//				tcView.setViewInable();
				Toast.makeText(mActivity, "数据加载失败，请检查网络", Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.location_text:
			location_text.setText("正在定位。。");
			MyLocationClient.getInstance().initLocation(mActivity,
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