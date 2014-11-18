package com.example.meidemo.localService;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import net.tsz.afinal.http.AjaxParams;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import zrc.widget.SimpleFooter;
import zrc.widget.SimpleHeader;
import zrc.widget.ZrcListView;
import zrc.widget.ZrcListView.OnItemClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.meidemo.BaseFragment;
import com.example.meidemo.R;
import com.example.meidemo.Popwindow.MyPopupWindow;
import com.example.meidemo.data.GlobalData;
import com.example.meidemo.net.HttpConnection;
import com.example.meidemo.net.HttpConnection.HttpConnectionListener;
import com.example.meidemo.view.PopupWindowAgentView;
import com.example.meidemo.view.PopupWindowRentView;
import com.example.meidemo.view.TopClassifyView;
import com.example.meidemo.view.TopClassifyView.OnItemClick;
import com.example.meidemo.view.interfaces.OnListItemClickListener;

public class LocalServiceFragment extends BaseFragment implements OnItemClick,
		OnListItemClickListener {
	View view;
	Activity mActivity;
	// PullToRefreshListView refreshListView;
	ZrcListView listView;
	LocalServiceListAdapter adapter;
	ObjectMapper objectMapper;
	ProgressBar progressBar;
	TopClassifyView tcView;
	/** 顶部选择栏 */
	// FrameLayout agent, rent, hallroom, origin;
	/** 弹出框 */
	PopupWindow pop;
	private WeakHandler handler;
	private static final String TGA = "LocalServiceFragment";

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
		objectMapper = new ObjectMapper();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.localservice_fragment, container,
				false);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		tcView = (TopClassifyView) view.findViewById(R.id.classify);
		try {
			tcView.addAllView(new int[] { 0, 1, 2, 3 }, new String[] { "区域","租金", "厅室", "来源" }, new int[] { 0, 0, 0, 0 }, new int[] {0, 0, 0, 0 }, 
					new int[] { R.drawable.ic_global_arrow_green_fold_normal,
							R.drawable.ic_global_arrow_green_fold_normal,
							R.drawable.ic_global_arrow_green_fold_normal,
							R.drawable.ic_global_arrow_green_fold_normal },
					new int[] {
							R.drawable.ic_global_arrow_green_unfold_normal,
							R.drawable.ic_global_arrow_green_unfold_normal,
							R.drawable.ic_global_arrow_green_unfold_normal,
							R.drawable.ic_global_arrow_green_unfold_normal });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tcView.setOnItemClick(this);
		progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		listView = (ZrcListView) view.findViewById(R.id.pull_refresh_list);
		// 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
		SimpleHeader header = new SimpleHeader(mActivity);
		header.setTextColor(0xff0066aa);
		header.setCircleColor(0xff33bbee);
		listView.setHeadable(header);

		// 设置加载更多的样式（可选）
		SimpleFooter footer = new SimpleFooter(mActivity);
		footer.setCircleColor(0xff33bbee);
		listView.setFootable(footer);
		adapter = new LocalServiceListAdapter(mActivity);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(itemClickListener);
		handler = new WeakHandler(this);
		// task.start();
		requestData();
		
		pop = new MyPopupWindow(mActivity, tcView).getPop();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		pop.dismiss();
	}

	OnKeyListener keyListener = new OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			return false;
		}
	};

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		

	}

	class WeakHandler extends Handler {
		LocalServiceFragment fragment;
		WeakReference<LocalServiceFragment> weakReference;

		public WeakHandler(LocalServiceFragment fragment) {
			this.fragment = fragment;
			weakReference = new WeakReference<LocalServiceFragment>(fragment);
		}

		@Override
		public void handleMessage(Message msg) {
			if (weakReference.get() == null) {
				return;
			}

			// Dialog progress =
			// DialogInstance.getInstance().getProgressDialog(mActivity);
			if (msg.what == 0) {
				// 开始下载数据，显示进度条
				// progress.show();
				progressBar.setVisibility(View.VISIBLE);
			} else if (msg.what == 1) {
				Bundle bundle = (Bundle) msg.obj;
				String result = bundle.getString("message");
				try {
					LocalServiceEntitys localServiceEntitys = objectMapper
							.readValue(result, LocalServiceEntitys.class);
					adapter.setList(localServiceEntitys.result);

				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					// progress.cancel();
					progressBar.setVisibility(View.GONE);
				}
			} else if (msg.what == -1) {
				// progress.cancel();
				// progress.dismiss();
				progressBar.setVisibility(View.GONE);
			}

		}
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(ZrcListView parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Toast.makeText(mActivity, "点击了" + position + "item",
					Toast.LENGTH_LONG).show();
			adapter.getList().get(position - 1).click = true;
			adapter.notifyDataSetChanged();
		}
	};

	public void requestData() {
		handler.sendEmptyMessage(0);
		List<NameValuePair> data = new LinkedList<NameValuePair>();
		// data.add(new BasicNameValuePair("cb", "jsonp"));
		data.add(new BasicNameValuePair("ty", "0"));
		data.add(new BasicNameValuePair("pb", "0"));
		data.add(new BasicNameValuePair("pg", "1"));
		data.add(new BasicNameValuePair("ps", "20"));
		HttpConnection.getInstance().start(GlobalData.ip+GlobalData.House_Rent, mActivity,
				data, new HttpConnectionListener() {

					@Override
					public String onSuccess(String message) {
						Log.i("onSuccess", message);
						Message msg = new Message();
						msg.what = 1;
						Bundle bundle = new Bundle();
						bundle.putString("message", message);
						msg.obj = bundle;
						handler.sendMessage(msg);
						return message;
					}

					@Override
					public String onFailure(String message) {
						Log.i("onFailure", message);
						Message msg = new Message();
						msg.what = -1;
						Bundle bundle = new Bundle();
						bundle.putString("message", message);
						msg.obj = bundle;
						handler.sendMessage(msg);
						return message;
					}
				});
	}

	

	

	public void updateAgent(HashMap<String, Object> data) {
		//改用finalhttp
		try {
//			HttpConnection.getInstance().start(GlobalData.ip+GlobalData.House_Rent,
//					mActivity, data, new HttpConnectionListener() {
//
//						@Override
//						public String onSuccess(String message) {
//							// TODO Auto-generated method stub
//							return null;
//						}
//
//						@Override
//						public String onFailure(String message) {
//							// TODO Auto-generated method stub
//							return null;
//						}
//					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void itemClickListener(HashMap<String, Object> data) {
		updateAgent(data);
	}

	@Override
	public void onItemClick(View v) {
		Log.i("LocalService", v.getId()+"::");
		switch (v.getId()) {
		case 0:
			tcView.setFrameLayout(0);
			pop.setContentView(new PopupWindowAgentView(mActivity, pop, this));
			pop.showAsDropDown(v, 0, 0);

			break;
		case 1:
			tcView.setFrameLayout(1);
//			((ViewGroup)pop.getContentView()).removeAllViews();
//			((ViewGroup)pop.getContentView()).addView(new PopupWindowRentView(mActivity, pop));
			
			pop.setContentView(new PopupWindowRentView(mActivity, pop));
			pop.showAsDropDown(tcView, 0, 0);

			break;
		case 2:
			tcView.setFrameLayout(2);
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

}