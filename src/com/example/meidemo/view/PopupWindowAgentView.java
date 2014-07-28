package com.example.meidemo.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.ScreenUtil;
import com.example.meidemo.view.adapter.PopupWindowAgentAdapter;
import com.example.meidemo.view.adapter.PopupWindowAgentAdapter.Holder;
import com.example.meidemo.view.adapter.PopupWindowAgentRightAdapter;

public class PopupWindowAgentView extends LinearLayout {
	PopupWindow pop;
	Context context;
	ListView leftListView;// 左边的listView
	PopupWindowAgentAdapter leftAdapter;
	ListView rightListView;// 右边的listView
	PopupWindowAgentRightAdapter rightAdapter;
	View oldView;
	OnListItemClickListener itemClickListener;
	String[] str = new String[] { "王一", "王二", "王三", "王一", "王二", "王三", "王一",
			"王二", "王三", "王一", "王二", "王三", "王一", "王二", "王三", "王一", "王二", "王三",
			"王一", "王二", "王三", "王一", "王二", "王三", "王一", "王二", "王三", "王一", "王二",
			"王三", "王一", "王二", "王三", "王一", "王二", "王三" };
	String[][] str2 = new String[][] { { "王一", "王一", "王一", "王一", "王一", "王一" },
			{ "王一", "王一", "王一", "王一" }, { "王一", "王一", "王一", "王一", "王一" },
			{ "王一", "王一", "王一" } };
	String[] str3 = new String[] { "张一", "张二", "张三", "张一", "张二", "张三", "张一",
			"张二", "张三", "张一", "张二", "张三", "张一", "张二", "张三", "张一", "张二", "张三",
			"张一", "张二", "张三", "张一", "张二", "张三", "张一", "张二", "张三", "张一", "张二",
			"张三", "张一", "张二", "张三", "张一", "张二", "张三" };

	public PopupWindowAgentView(Context context, PopupWindow pop,OnListItemClickListener itemClickListener) {
		// TODO Auto-generated constructor stub
		super(context);
		this.pop = pop;
		this.context = context;
		this.itemClickListener = itemClickListener;
		initView();
	}

	public PopupWindowAgentView(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context, attrs);
	}

	public PopupWindowAgentView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void initView() {
		View view = inflate(context, R.layout.hr_popwindow_agent, null);
		// View view = LayoutInflater.from(context).inflate(
		// R.layout.hr_popwindow_rent, null);

		leftListView = (ListView) view.findViewById(R.id.h_pop_list);
		// android.view.ViewGroup.LayoutParams params = new
		// LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,(int)
		// (ScreenUtil.getHeight(context)*0.6));
		leftListView.getLayoutParams().height = (int) (ScreenUtil
				.getHeight(context) * 0.6);
		leftAdapter = new PopupWindowAgentAdapter(Arrays.asList(str), context);
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
		// android.R.layout.simple_list_item_1, android.R.id.text1, str);
		leftListView.setAdapter(leftAdapter);
		leftListView.setOnItemClickListener(leftListViewListener);

		rightListView = (ListView) view.findViewById(R.id.h_pop_list1);
		rightListView.getLayoutParams().height = (int) (ScreenUtil
				.getHeight(context) * 0.6);
		rightAdapter = new PopupWindowAgentRightAdapter(Arrays.asList(str),
				context);
		rightListView.setAdapter(rightAdapter);
		rightListView.setOnItemClickListener(new RightListViewListener());
		this.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		view.startAnimation(AnimationUtils.loadAnimation(context,
				R.anim.in_toptobottom));
		this.setFocusable(true);
		this.setFocusableInTouchMode(true);
	}

	OnItemClickListener leftListViewListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			if (leftListView.getFirstVisiblePosition() <= leftAdapter.current
					&& leftListView.getLastVisiblePosition() >= leftAdapter.current) {
				// View convertView =
				// leftListView.getChildAt(leftAdapter.current);
				if (oldView == null){
					oldView = leftListView.getChildAt(leftAdapter.current);
				}
				Holder holder = (Holder) oldView.getTag();
				holder.img.setVisibility(View.GONE);
				holder.text.setBackgroundColor(context.getResources().getColor(
						R.color.h_popwindow_agent_left_text_unselect));
				
			}
			view.findViewById(R.id.h_popwindow_agent_left_image).setVisibility(
					View.VISIBLE);
			view.findViewById(R.id.h_popwindow_agent_left_text)
					.setBackgroundColor(
							context.getResources().getColor(
									R.color.h_popwindow_agent_left_text_select));
			oldView = view;
			leftAdapter.current = position;
			Log.i("POPAGENT", leftListView.getFirstVisiblePosition() + "::"
					+ leftListView.getLastVisiblePosition() + "::"
					+ leftAdapter.current);
		}
	};
	public interface OnListItemClickListener{
		public void itemClickListener(List<NameValuePair> data);
	}
	
	public OnListItemClickListener getItemClickListener() {
		return itemClickListener;
	}

	public void setItemClickListener(OnListItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}

	class RightListViewListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
				pop.dismiss();
				if (itemClickListener!=null) {
					List<NameValuePair> data = new ArrayList<NameValuePair>();
					data.add(new BasicNameValuePair("ty", "0"));
					data.add(new BasicNameValuePair("pb", "0"));
					data.add(new BasicNameValuePair("pg", "1"));
					data.add(new BasicNameValuePair("ps", "20"));
					data.add(new BasicNameValuePair("zn", "梅江"));
					itemClickListener.itemClickListener(data);
						
				}
		}
	}
}
