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
import com.example.meidemo.net.HttpConnection;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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
		
		task.execute();
	}

	class LocalServiceTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			Log.i(TGA, "开始执行");
			List<NameValuePair> data = new LinkedList<NameValuePair>();
			data.add(new BasicNameValuePair("cb", "jsonp"));
			HttpConnection connection = new HttpConnection();
			String string = null;
			try {
				 string= connection
						.handleConnection(
								"http://192.168.219.131/project/gb/gb_interface/index.php/house/Index/index",
								mActivity, data);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return string;
		}
		@Override
		protected void onPostExecute(String result) {
			try {
				objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				
				LocalServiceEntity localServiceEntity = objectMapper.readValue(result, LocalServiceEntity.class);
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
			super.onPostExecute(result);
		}

	}

}