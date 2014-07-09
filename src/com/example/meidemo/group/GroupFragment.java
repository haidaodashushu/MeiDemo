package com.example.meidemo.group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import com.example.meidemo.BaseFragment;
import com.example.meidemo.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class GroupFragment extends BaseFragment {
	Activity mActivity;
	View view;
	PullToRefreshListView mPullToRefreshListView;
	ListView listView;
	LinkedList<String> mItems;
	ArrayAdapter<String> adapter;
	Handler handler  = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what==1) {
				mItems.addFirst("add first11111");
			}else {
				mItems.addFirst("add last22222");
			}
			adapter.notifyDataSetChanged();
			mPullToRefreshListView.onRefreshComplete();
			Toast.makeText(getActivity(), "刷新", 2000).show();
		}
	};
	
	private String[] mStrings = { "Abbaye de Belloc",
			"Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu",
			"Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler",
			"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam",
			"Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis",
			"Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler" };

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

		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mPullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		listView = mPullToRefreshListView.getRefreshableView();
		mItems = new LinkedList<String>();
		mItems.addAll(Arrays.asList(mStrings));
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, mItems);
		mPullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						mPullToRefreshListView.setRefreshing(true);
						new Thread() {
							public void run() {
								try {
									Thread.sleep(6000);
									handler.sendEmptyMessage(1);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							};
						}.start();

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						new Thread() {
							public void run() {
								try {
									Thread.sleep(2000);
									handler.sendEmptyMessage(2);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							};
						}.start();

					}

				});

		listView.setAdapter(adapter);
		
	}
}
