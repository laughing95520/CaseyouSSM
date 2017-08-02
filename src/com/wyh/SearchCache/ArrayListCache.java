package com.wyh.SearchCache;

import java.util.ArrayList;
import java.util.List;

import com.wyh.bean.SearchBean;

public class ArrayListCache {
	private static ArrayList<SearchBean> arrayList = new ArrayList<SearchBean>(100);
	
	//private static Map<String, SearchBean> map = new HashMap<>(100);
	public static ArrayList<SearchBean> getArrayList() {
		return arrayList;
	}

	public static void setArrayList(ArrayList<SearchBean> arrayList) {
		ArrayListCache.arrayList = arrayList;
	}

	public static List<SearchBean> add(List<SearchBean> searchBeans) {
		if (arrayList.size()!=0) {
			arrayList.clear();
			//map.clear();
		}
		
		arrayList.addAll(searchBeans);
		
		return arrayList;
	}
	
}
