package com.example.meidemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.example.meidemo.CommonUtils.SharedPreferenceUtil;
import com.example.meidemo.activity.LoginActivity;
import com.example.meidemo.activity.RegisterActivity;
import com.example.meidemo.baiduLocation.MyBDLocationListener.OnLocationListener;
import com.example.meidemo.baiduLocation.LocationDate;
import com.example.meidemo.baiduLocation.MyLocationClient;
import com.example.meidemo.data.GlobalData;

public class MainActivity extends FragmentActivity implements OnClickListener,
		OnPageChangeListener {
	private ViewPager mainPager;
	private MainPagerAdapter pagerAdapter;
	long stopMis;
	boolean flag;
	String from;
	int[] title = new int[] { R.string.group, R.string.business,
			R.string.localService, R.string.myinfo, R.string.more };

	private ImageView[] images;

	private int currentIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = getIntent();
		if (intent.hasExtra("from")) {
			from  = intent.getStringExtra("from");
		}
		initView();

		// 初始化底部菜单
		initMenu();

		// 开启定位
		MyLocationClient.getInstance().initLocation((Activity)this,
				new OnLocationListener() {

					@Override
					public void onListener(BDLocation data) {
//						top_city_text.setText(LocationDate.getInstance().city);
//						top_city_text.setText(LocationDate.getInstance().district);
						GlobalData.city = LocationDate.getInstance().district;
						if (GlobalData.city==null||"".equals(GlobalData.city)) {
							Toast.makeText(MainActivity.this, "定位失败，请手动选择地理信息", Toast.LENGTH_LONG).show();
						}
					}
				});
		/*if (from!=null&&(from.equals("Login")||from.equals("Register"))) {
			mainPager.setCurrentItem(2, false);
		}*/
		

	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//
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
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0==1 &&( arg1 == GlobalData.LOING_RESULT_OK||arg1==GlobalData.REGISTER_RESULT_OK)) {
			mainPager.setCurrentItem(2, false);
		}else {
			mainPager.setCurrentItem(0, false);
		}
	}
	@Override
	public void onClick(View v) {
		if (v instanceof RelativeLayout) {
			int position = (Integer) v.getTag();
			
			if (position >= 0 && position < title.length) {
				if (position ==2) {
					//个人中心
					if ("".equals(SharedPreferenceUtil.getString(this, GlobalData.SPINFOTOKEN))) {
						Intent intent = new Intent(MainActivity.this,LoginActivity.class);
						startActivityForResult(intent, 1);
						return;
					}
				}
				mainPager.setCurrentItem(position, false);
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
	
	@Override
	public void onBackPressed() {
		if (!flag) {
			Toast.makeText(this, "再按一次推出团购", Toast.LENGTH_SHORT).show();
			stopMis = System.currentTimeMillis();
			flag = true;
		}else {
			if ((System.currentTimeMillis()-stopMis)<2000) {
				//推出
				this.finish();
				System.exit(0);
				flag = false;
			}else {
				flag = false;
				stopMis = System.currentTimeMillis();
			}
		}
		
	}
}
