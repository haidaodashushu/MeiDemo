package com.example.meidemo.group;

import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;
import zrc.widget.ZrcListView.OnStartListener;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meidemo.BaseFragment;
import com.example.meidemo.R;
import com.example.meidemo.data.GlobalData;
import com.example.meidemo.view.GroupHeadView;
import com.example.meidemo.view.TopMenuView;
import com.example.meidemo.view.adapter.GroupListViewAdapter;
import com.example.meidemo.view.entity.TuanInfo;
import com.example.meidemo.view.entity.TuanInfos;

public class GroupFragment extends BaseFragment {
	Activity mActivity;
	View view;
	// PullToRefreshListView mPullToRefreshListView;
	// ListView listView;
	TopMenuView topMenuView;
	
	TextView tipText;
	ZrcListView listView;
	ArrayList<TuanInfo> mItems;
	GroupListViewAdapter adapter;
	ProgressBar progressBar;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.group_fragment, container, false);
		// initLocation();
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		topMenuView.setcityData();
	}
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		topMenuView = (TopMenuView) view.findViewById(R.id.topmenu);
		
		listView = (ZrcListView) view.findViewById(R.id.pull_refresh_list);
		tipText = (TextView) view.findViewById(R.id.tipText);
		progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		mItems = new ArrayList<TuanInfo>();

		// 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
		SimpleHeader header = new SimpleHeader(mActivity);
		header.setTextColor(0xff0066aa);
		header.setCircleColor(0xff33bbee);
		listView.setHeadable(header);

//		// 设置加载更多的样式（可选）
//		SimpleFooter footer = new SimpleFooter(mActivity);
//		footer.setCircleColor(0xff33bbee);
//		listView.setFootable(footer);

		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(false);

		// 下拉刷新事件回调（可选）
		listView.setOnRefreshStartListener(new OnStartListener() {
			@Override
			public void onStart() {
				requestDate(1);
			}
		});
		// // 设置列表项出现动画（可选）
		// listView.setItemAnimForTopIn(R.anim.topitem_in);
		// listView.setItemAnimForBottomIn(R.anim.bottomitem_in);
		
		GroupHeadView headView = new GroupHeadView(mActivity);
		listView.addHeaderView(headView);
		adapter = new GroupListViewAdapter(mItems, mActivity);
		listView.setAdapter(adapter);
		topMenuView.setcityData();
		
		requestDate(0);
	}

	/** 请求“猜你喜欢”数据 */
	private void requestDate(final int flag) {
		FinalHttp finalHttp = new FinalHttp();
		finalHttp.configTimeout(5000);
		finalHttp.get(GlobalData.ip + GlobalData.Tuan_INDEXTEAM, null,
				new AjaxCallBack<Object>() {
					@Override
					public void onStart() {
						if (flag == 1) {
							progressBar.setVisibility(View.GONE);
						} else if (flag == 0) {
							progressBar.setVisibility(View.VISIBLE);
						}

					}

					@Override
					public void onSuccess(Object t) {
						TuanInfos tuanInfos = new TuanInfos(t.toString());
						mItems.clear();
						ArrayList<TuanInfo> list = tuanInfos.getTuaninfos();
						if (list ==null||list.size()==0) {
							Toast.makeText(mActivity, "暂无数据", Toast.LENGTH_LONG).show();
						}else {
							mItems.addAll(list);
							adapter.setLeftList(mItems);
						}
						progressBar.setVisibility(View.GONE);
						if (flag == 1) {
							listView.setRefreshSuccess();
						}

					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						// TODO Auto-generated method stub
						if (flag == 1) {
							listView.setRefreshFail("加载失败");
						}

						progressBar.setVisibility(View.GONE);
					}
				});
	}

}
