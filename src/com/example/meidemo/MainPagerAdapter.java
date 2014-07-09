package com.example.meidemo;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter{
	int[] title;
	Context context;

	public MainPagerAdapter(Context context,FragmentManager fm,int[] title) {
		super(fm);
		this.title = title;
		this.context = context;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return FragmentFactory.getFragment(FragmentFactory.Type.getTypeByValue(arg0));
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return title.length;
	}

}
