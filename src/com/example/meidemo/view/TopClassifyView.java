package com.example.meidemo.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.meidemo.R;
/**
 * 分类的View，，本地服务，美食分类，电影分类等使用
 * @author wangzk
 *
 */
public class TopClassifyView extends LinearLayout {
	Context context;
//	TableRow tableRow;
	LinearLayout topView;
	OnItemClick onItemClick;
	int[] rights;
	int[] rights_sel;
	int[] lefts_sel;
	int[] lefts;
	List<View> views = new ArrayList<View>();
	public TopClassifyView(Context context) {
		super(context);
		this.context = context;
		initView();
	}

	public TopClassifyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
		// TODO Auto-generated constructor stub
	}

	private void initView() {
		View view = inflate(context, R.layout.classify, this);
		this.topView = (LinearLayout) view.findViewById(R.id.toprow);
	}
	/**
	 * 
	 * @param ids 
	 * @param names f分类名
	 * @param lefts 左边正常效果图片
	 * @param lefts_sel 左边选中的效果图片
	 * @param rights 右边正常效果图片
	 * @param rights_unsel 右边选中的效果图片
	 * @throws Exception
	 */
	public void addAllView(int[] ids,String[] names,int[] lefts,int[] lefts_sel,int[] rights,int[] rights_sel) throws Exception{
		if (ids.length>names.length||ids.length>lefts.length||ids.length>rights.length) {
			throw new Exception("请为所有的Ids添加资源");
		}
		this.lefts_sel = lefts_sel;
		this.rights_sel = rights_sel;
		this.lefts = lefts;
		this.rights = rights;
		LayoutParams lineParams_v = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		lineParams_v.width = 1;
		for (int i = 0; i < ids.length; i++) {
			addChildView(ids[i], names[i], lefts[i], rights[i]);
			if (i!=ids.length-1) {
				View view_item_v = LayoutInflater.from(context).inflate(R.layout.line_v, null);
				topView.addView(view_item_v,lineParams_v);
			}
		}
	}
	/**
	 * 添加子View
	 * 
	 * @param name
	 *            分类名
	 * @param left
	 *            左侧图标 不需要则为0
	 * @param right
	 *            右侧图标
	 */
	
	private void addChildView(int id, String name, int left, int right) {
		FrameLayout itemView = (FrameLayout) LayoutInflater.from(context).inflate(
				R.layout.classify_item,null);
		itemView.setId(id);
		views.add(itemView);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
		layoutParams.width = 0;
		layoutParams.weight = 1;
		topView.addView(itemView,layoutParams);
		
		TextView text = (TextView) itemView.findViewById(R.id.text);
		text.setText(name);
		text.setCompoundDrawablesWithIntrinsicBounds((left==0)?null:context.getResources().getDrawable(left), null,(right==0)?null:context.getResources().getDrawable(right), null);
		
		itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (onItemClick != null) {
					onItemClick.onItemClick(v);
				}
			}
		});
		
		
	}
	/**
	 * 设置不可用
	 */
	public void setViewInable() {
		if (views.size()<=0) {
			return;
		}
		
		for (int i = 0; i < views.size(); i++) {
			views.get(i).setEnabled(false);
		}
	}
	/**
	 * 设置可用
	 */
	public void setViewEnable() {
		if (views.size()<=0) {
			return;
		}
		
		for (int i = 0; i < views.size(); i++) {
			views.get(i).setEnabled(true);
		}
	}
	public interface OnItemClick {
		public void onItemClick(View view);
	}

	public OnItemClick getOnItemClick() {
		return onItemClick;
	}

	public void setOnItemClick(OnItemClick onItemClick) {
		this.onItemClick = onItemClick;
	}

	/**
	 * 设置顶部选择栏的点击效果
	 * 
	 * @param index
	 *            当前点击的选择框的下标
	 */
	public void setFrameLayout(int index) {
		int count = topView.getChildCount();
		for (int i = 0; i < count; i += 2) {
			View temp = topView.getChildAt(i);
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
					temptext.setTextColor(context.getResources().getColor(R.color.h_text_select));
					temptext.setCompoundDrawablesWithIntrinsicBounds((lefts[index]==0)?null:context.getResources().getDrawable(lefts[index]), null,(rights[index]==0)?null:context.getResources().getDrawable(rights[index]), null);
					tempView.setVisibility(View.VISIBLE);
				} else {
					temptext.setTextColor(context.getResources().getColor(R.color.h_text_unselect));
					temptext.setCompoundDrawablesWithIntrinsicBounds((lefts_sel[index]==0)?null:context.getResources().getDrawable(lefts_sel[index]), null,(rights_sel[index]==0)?null:context.getResources().getDrawable(rights_sel[index]), null);
					tempView.setVisibility(View.GONE);
				}

			}
		}
	}

}
