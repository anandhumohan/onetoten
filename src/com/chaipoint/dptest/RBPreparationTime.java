package com.chaipoint.dptest;

import com.chaipoint.dphelper.DPHelper;
import com.chaipoint.dpselection.CategoryCount;

public class RBPreparationTime {

	public static void main(String[] args) {
		
		DPHelper helper = new DPHelper();
		CategoryCount categoryCount = new CategoryCount();
		categoryCount.setNoOfHotBeverages(2);
		categoryCount.setNoOfColdBeverages(1);
		categoryCount.setNoOfRMFoods(3);
		categoryCount.setNoOfRTPFoods(1);
		long time = helper.PreparationTime(categoryCount, "STR1");
		System.out.println(time);

	}

}
