package com.example.meidemo;

import com.example.meidemo.business.BusinessFragment;
import com.example.meidemo.group.GroupFragment;
import com.example.meidemo.localService.LocalServiceFragment;
import com.example.meidemo.more.MoreFragment;
import com.example.meidemo.myInfo.MyinfoFragment;

import android.support.v4.app.Fragment;
import android.util.SparseArray;


public class FragmentFactory {
	private static SparseArray<BaseFragment> fragments = new SparseArray<BaseFragment>();
	private FragmentFactory(){};
	
	public static BaseFragment getFragment(Type type){
		BaseFragment fragment =fragments.get(type.getValue());
		if (fragment == null) {
			switch (type) {
			case Group:
				fragment = new GroupFragment();
				break;
			case Business:
				fragment = new BusinessFragment();
				break;
			case LocalService:
				fragment = new LocalServiceFragment();
				break;
			case Myinfo:
				fragment = new MyinfoFragment();
				break;
			case More:
				fragment = new MoreFragment();
				break;
			default:
				break;
			}
			fragments.put(type.getValue(), fragment);
		}
		
		return fragment;
		
	}
	
	public enum Type {
		Group(0), Business(1), Myinfo(2), LocalService(3),More(4);

		private int value;
		private static final int size = values().length;

		private Type(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}

		public static int size() {
			return size;
		}

		public static Type getTypeByValue(int value) {
			switch (value) {
			case 0:
				return Group;
			case 1:
				return Business;
			case 2:
				return Myinfo;
			case 3:
				return LocalService;
			case 4:
				return More;
			default:
				return null;
			}
		}

	}
}
