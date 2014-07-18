package com.example.meidemo.localService;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meidemo.BaseFragment;
import com.example.meidemo.R;
import com.example.meidemo.Constants.ConstansUrls;
import com.example.meidemo.net.HttpConnection;
import com.example.meidemo.net.HttpConnection.HttpConnectionListener;
import com.example.meidemo.view.PopupWindowAgentView;
import com.example.meidemo.view.PopupWindowAgentView.OnListItemClickListener;
import com.example.meidemo.view.PopupWindowRentView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class LocalServiceFragment extends BaseFragment implements
		OnClickListener, OnListItemClickListener {
	View view;
	Activity mActivity;
	PullToRefreshListView refreshListView;
	ListView listView;
	LocalServiceListAdapter adapter;
	ObjectMapper objectMapper;
	ProgressBar progressBar;
	View line;
	TableRow tableRow;
	/** 顶部选择栏 */
	FrameLayout agent, rent, hallroom, origin;
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
		pop = createPopupWindow();
		tableRow = (TableRow) view.findViewById(R.id.tablerow);
		line = (View) view.findViewById(R.id.line);
		agent = (FrameLayout) view.findViewById(R.id.agent);
		rent = (FrameLayout) view.findViewById(R.id.rent);
		hallroom = (FrameLayout) view.findViewById(R.id.hallroom);
		origin = (FrameLayout) view.findViewById(R.id.origin);
		agent.setOnClickListener(this);
		rent.setOnClickListener(this);
		hallroom.setOnClickListener(this);
		origin.setOnClickListener(this);
		progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
		refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.local_service_pull_refresh_list);

		listView = refreshListView.getRefreshableView();

		adapter = new LocalServiceListAdapter(mActivity);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(itemClickListener);
		handler = new WeakHandler(this);
		// task.start();
		requestData();

	}

	class WeakHandler extends Handler {
		LocalServiceFragment fragment;
		WeakReference<LocalServiceFragment> weakReference;
		public WeakHandler(LocalServiceFragment fragment) {
			this.fragment = fragment;
			weakReference = new WeakReference<LocalServiceFragment>(
						fragment);
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
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Toast.makeText(mActivity, "点击了" + position + "item",
					Toast.LENGTH_LONG).show();
			adapter.getList().get(position - 1).click = true;
			adapter.notifyDataSetChanged();
			// TextView v = (TextView)view.findViewById(R.id.hr_title);
			// v.setEnabled(false);
			// v.setTextColor(mActivity.getResources().getColor(R.color.hr_title_select));
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
		HttpConnection.getInstance().start(ConstansUrls.House_Rent, mActivity,
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.agent:
			setFrameLayout(0);
			pop.setContentView(new PopupWindowAgentView(mActivity, pop, this));
			if (pop.isShowing()) {
				pop.dismiss();
			} else {
				pop.showAsDropDown(v, 0, 0);
			}

			break;
		case R.id.rent:
			setFrameLayout(1);
			// v.setFocusable(true);
			// View view =
			// LayoutInflater.from(mActivity).inflate(R.layout.hr_popwindow_rent,
			// null);
			if (pop.isShowing()) {
				pop.dismiss();
			} else {
				pop.setContentView(new PopupWindowRentView(mActivity, pop));
				pop.showAsDropDown(v, 0, 0);
				// pop.showAtLocation(v, Gravity.CENTER, 0, 0);
			}

			break;
		case R.id.hallroom:
			setFrameLayout(2);
			pop.showAsDropDown(v, 0, 0);
			break;
		case R.id.origin:
			setFrameLayout(3);
			pop.showAsDropDown(v, 0, 0);
			break;

		default:
			break;
		}
	}

	/**
	 * 设置顶部选择栏的点击效果
	 * 
	 * @param index
	 *            当前点击的选择框的下标
	 */
	private void setFrameLayout(int index) {
		int count = tableRow.getChildCount();
		for (int i = 0; i < count; i += 2) {
			View temp = tableRow.getChildAt(i);
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

	public MotionEvent event1;

	private PopupWindow createPopupWindow() {
		PopupWindow pop = new PopupWindow(mActivity);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.MATCH_PARENT);
		// PopupWindow pop = new
		// PopupWindow(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

		ColorDrawable dw = new ColorDrawable(0x80000000);
		pop.setBackgroundDrawable(dw);
		pop.setFocusable(true);
		// pop.setOutsideTouchable(true);
		pop.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("pop", "dianjiPOP");
				event1 = event;
				return false;
			}
		});
		pop.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// 在这里借用popwindow的点击事件来判断点击是否落在一下4个按钮上，触发他们的点击
				View[] view = new View[] { agent, rent, hallroom, origin };
				int[] location = new int[2];
				for (int i = 0; i < view.length; i++) {
					view[i].getLocationOnScreen(location);
					if (event1.getRawX() < location[0] + view[i].getWidth()
							&& event1.getRawX() > location[0]
							&& event1.getRawY() < location[1]
									+ view[i].getHeight()
							&& event1.getRawY() > location[1]) {
						view[i].performClick();
					}
				}
			}
		});

		return pop;
	}

	public void updateAgent(List<NameValuePair> data) {

		try {
			HttpConnection.getInstance().start(
					ConstansUrls.House_Rent, mActivity, data,
					new HttpConnectionListener() {

						@Override
						public String onSuccess(String message) {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public String onFailure(String message) {
							// TODO Auto-generated method stub
							return null;
						}
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void itemClickListener(List<NameValuePair> data) {
		updateAgent(data);
	}

}