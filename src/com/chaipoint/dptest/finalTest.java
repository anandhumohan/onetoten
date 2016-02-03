package com.chaipoint.dptest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.chaipoint.dphelper.DPHelper;
import com.chaipoint.dpselection.CategoryCount;

public class finalTest {

	public static void main(String[] args) {
		DPHelper dpHelper = new DPHelper();
		CategoryCount categoryCount = new CategoryCount();
		ArrayList<String> ids = new ArrayList<>();
		//Map<String, Integer> map = new HashMap<String, Integer>();
		//map.put("ITEM1", 2);
		//map.put("ITEM2", 4);
		ids.add("ITEM1");
		ids.add("ITEM2");
		categoryCount = dpHelper.setCategoryCount(ids);
		System.out.println("finish");

	}

}
