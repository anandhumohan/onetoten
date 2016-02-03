package com.chaipoint.dptest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.chaipoint.dphelper.DPHelper;
import com.chaipoint.dphelper.TimeHelper;
import com.chaipoint.dpselection.DPStatus;
import com.chaipoint.dpselection.DPselector;
import com.chaipoint.dpselection.TimeCalculator;

public class RBgetMinDelivery {

	public static void main(String[] args) {

		Map<String, DPStatus> dpStatus = new HashMap<String, DPStatus>();
		DPHelper dpHelper = new DPHelper();
		TimeHelper helper = new TimeHelper();
		DPStatus status = new DPStatus();
		status.setNoOfOrdersDelivered(2);
		status.setDPname("boy1");
		status.setLastTotalDistance(3);
		status.setLastDispatchTime(new Date());
		System.out.println(new Date());
		dpStatus.put("boy1", status);
		DPStatus st = new DPStatus();
		st.setNoOfOrdersDelivered(5);
		st.setDPname("boy2");
		dpStatus.put("boy2", st);
		status.setLastTotalDistance(3);
		status.setLastDispatchTime(new Date());
		System.out.println(new Date());
		ArrayList<DPStatus> dp = new ArrayList<DPStatus>();
		dp.add(status);
		dp.add(st);
		TimeCalculator date = helper.TimeSetter(dp, "STR1");
		System.out.println(date.getExpectedReturnTIme());
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("boy1");
		ids.add("boy2");
		DPselector sel = new DPselector();
		String result = dpHelper.getMinNoOfDelivered(dpStatus, ids);
		System.out.println("DP" + result);

	}

}
