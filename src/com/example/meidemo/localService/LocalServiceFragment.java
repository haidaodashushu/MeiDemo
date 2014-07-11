package com.example.meidemo.localService;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.example.meidemo.BaseFragment;
import com.example.meidemo.R;
import com.example.meidemo.Constants.ConstansUrls;
import com.example.meidemo.net.HttpConnection;
import com.example.meidemo.net.HttpConnection.HttpConnectionListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LocalServiceFragment extends BaseFragment {
	View view;
	Activity mActivity;
	PullToRefreshListView refreshListView;
	ListView listView;
	LocalServiceListAdapter adapter;
	ObjectMapper objectMapper;
	private static final String TGA = "LocalServiceFragment";
	Handler handler;

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
		refreshListView = (PullToRefreshListView) view
				.findViewById(R.id.local_service_pull_refresh_list);
		listView = refreshListView.getRefreshableView();

		adapter = new LocalServiceListAdapter();

		listView.setAdapter(adapter);
		LocalServiceTask task = new LocalServiceTask();
		task.start();
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = (Bundle) msg.obj;
				String result = bundle.getString("message");
				try {
					LocalServiceEntitys localServiceEntitys = objectMapper
							.readValue(result, LocalServiceEntitys.class);
					
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	class LocalServiceTask extends Thread {

		@Override
		public void run() {
			Log.i(TGA, "开始执行");
			List<NameValuePair> data = new LinkedList<NameValuePair>();
			// data.add(new BasicNameValuePair("cb", "jsonp"));
			data.add(new BasicNameValuePair("ty", "0"));
			data.add(new BasicNameValuePair("pb", "0"));
			data.add(new BasicNameValuePair("pg", "1"));
			data.add(new BasicNameValuePair("ps", "20"));
			HttpConnection connection = new HttpConnection();
			String string = null;
			try {
				string = connection.handleConnection(ConstansUrls.House_Rent,
						mActivity, data, new HttpConnectionListener() {

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
								msg.what = 1;
								Bundle bundle = new Bundle();
								bundle.putString("message", message);
								msg.obj = bundle;
								handler.sendMessage(msg);
								return message;
							}
						});

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}