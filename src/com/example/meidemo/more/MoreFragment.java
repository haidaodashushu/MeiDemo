package com.example.meidemo.more;

import com.example.meidemo.BaseFragment;
import com.example.meidemo.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MoreFragment extends BaseFragment{
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		
		return inflater.inflate(R.layout.more_fragment, container, false);
	}
}
