package com.example.meidemo.CommonUtils;

import java.util.List;

public class ListUtils {
	public static void addAllToFirst(List parentList,List childList){
		if (parentList==null||childList==null) {
			return;
		}
		for (int i = childList.size()-1; i >= 0; i--) {
			parentList.add(0, childList.get(i));
		}
	}
}
