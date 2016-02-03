package com.chaipoint.dpselection;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.chaipoint.dphelper.DPHelper;
import com.chaipoint.dphelper.TimeHelper;

public class DPselector {

	public static Map<String, Queue<String>> DPQueues = new HashMap<String, Queue<String>>();
	public static Map<String, DPStatus> dpStatus = new HashMap<String, DPStatus>();
	public static DPHelper dpHelper = new DPHelper();
	DPStatus status = null;
	Queue<String> queue = null;

	// call when dispatching
	public String getDP(String storeId) {

		queue = DPQueues.get(storeId);

		String DPId = null;
		if (!queue.isEmpty()) {
			if (queue.size() > 1) {

				ArrayList<String> queurToList = new ArrayList<>(queue);
				DPId = dpHelper.getMinNoOfDelivered(dpStatus, queurToList);
				queue.remove(DPId);
				dpStatus.get(DPId).setBusy(true);
				System.out.println("alloted DP" + DPId);

			} else {
				// case with queue contains only one DP
				DPId = queue.poll();
				dpStatus.get(DPId).setBusy(true);
				System.out.println("alloted DP" + DPId);

			}

		} else

		{
			System.out.println("thirdparty");
			// give order details to thirdparty-using below class we can
			// request thirdparty

		}
		return DPId;

	}

	// will call this funtion when.1 login 2.delivered and 3.when one DP is
	// available.
	// enters
	// have to change this method to handle above cases.
	public String setDP(String storeId, String DPId, boolean busy, Date deliveryTime) {

		// creating maps first time
		if (!DPQueues.containsKey(storeId)) {
			queue = new LinkedList<String>();
			queue.add(DPId);
			DPQueues.put(storeId, queue);
			status = new DPStatus();
			status.setDPname(DPId);
			status.setBusy(busy);
			dpStatus.put(DPId, status);
		} else {

			queue = DPQueues.get(storeId);
			DPStatus dp = dpStatus.get(DPId);
			dp.setBusy(false);

		}
		if (!(deliveryTime == null)) {
			DPStatus dp = dpStatus.get(DPId);
			dp.setNoOfOrdersDelivered(dp.getNoOfOrdersDelivered()+1);

		}

		return DPId;
	}

	// this method will call when an order is confirmed.
	public String setCofirmOrder(String storeId, ArrayList<String> itemIds) {

		CategoryCount categoryCount = dpHelper.setCategoryCount(itemIds);

		String DPId = null;
		int threshold = 10;
		if (queue.isEmpty()) {

			long prepTime = dpHelper.PreparationTime(categoryCount, storeId);
			System.out.println("prep" + prepTime);
			Date expectedPrep = new TimeHelper().addMinute(new Date(), prepTime);
			System.out.println("expectedPrep" + expectedPrep);
			TimeCalculator cal = dpHelper.minReturnTimeDPId(dpStatus, queue, storeId, DPQueues);
			Date returnTime = cal.getExpectedReturnTIme();

			System.out.println("returnTime" + returnTime);
			Date addExpcAndThreshold = new TimeHelper().addMinute(expectedPrep, threshold);
		//	expectedPrep = new TimeHelper().addMinute(expectedPrep, threshold);
			if (returnTime.getTime() < addExpcAndThreshold.getTime()) {
				//possible dpId
				DPId = cal.getDPId();
				System.out.println("possible DP" + DPId);

			} else {
				System.out.println("reached here");
				// give order details to thirdparty-using below class we can
				// request thirdparty

			}

		}
		return DPId;
	}

	// for flushing the values after closing
	public String forceFlush() {
		DPQueues.clear();
		dpStatus.clear();
		return "success";
	}
}
