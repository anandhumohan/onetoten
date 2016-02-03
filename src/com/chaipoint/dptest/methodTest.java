package com.chaipoint.dptest;

import java.util.ArrayList;
import java.util.Date;

import com.chaipoint.dphelper.DPHelper;
import com.chaipoint.dphelper.TimeHelper;
import com.chaipoint.dpselection.DPselector;

public class methodTest {

	public static void main(String[] args) {
		TimeHelper helper = new TimeHelper();
		DPHelper dphelper = new DPHelper();
		DPselector dPselector = new DPselector();
		String result = helper.getTimeSlice(new Date());
		System.out.println(result);
		Date newTime = helper.addMinute(new Date(), 10);
		System.out.println(newTime);

		int value = helper.getUnitKmTime("STR1", "11-3");
		System.out.println(value);

		// ArrayList<String> list = dphelper.getDPList("STR1");
		// System.out.println(list);

	}

}
