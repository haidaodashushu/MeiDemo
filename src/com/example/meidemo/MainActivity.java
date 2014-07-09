package com.example.meidemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends FragmentActivity implements OnClickListener,
		OnPageChangeListener {
	private ViewPager mainPager;
	private MainPagerAdapter pagerAdapter;
	int[] title = new int[] { R.string.group, R.string.business,
			R.string.localService, R.string.myinfo, R.string.more };

	private ImageView[] images;

	private int currentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();

		// 初始化底部菜单
		initMenu();
	}

	private void initView() {
		mainPager = (ViewPager) findViewById(R.id.main_pager);
		pagerAdapter = new MainPagerAdapter(this, getSupportFragmentManager(),
				title);
		mainPager.setAdapter(pagerAdapter);
		mainPager.setOnPageChangeListener(this);
	}

	private void initMenu() {
		images = new ImageView[title.length];
		LinearLayout tabmenu = (LinearLayout) findViewById(R.id.tabMenu);
		for (int i = 0; i < title.length; i++) {
			RelativeLayout relaLayout = (RelativeLayout) tabmenu.getChildAt(i);
			ImageView img = (ImageView) relaLayout.getChildAt(0);
			img.setEnabled(false);
			images[i] = img;
			relaLayout.setOnClickListener(this);
			relaLayout.setTag(i);// 保存编号
		}
		images[currentIndex].setEnabled(true);
	}

	@Override
	public void onClick(View v) {
		if (v instanceof RelativeLayout) {
			int position = (Integer) v.getTag();
			if (position>=0&&position<title.length) {
				mainPager.setCurrentItem(position, true);
			}
			
		}
	}

	private void setCurrentMenu(int position) {
		if (position == currentIndex) {
			return;
		}
		images[currentIndex].setEnabled(false);
		images[position].setEnabled(true);
		currentIndex = position;
		
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setCurrentMenu(arg0);
	}

}
