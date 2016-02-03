package com.chaipoint.dphelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Queue;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.chaipoint.dppojos.ItemCodeCategory;
import com.chaipoint.dppojos.StoreItemTimeMapping;
import com.chaipoint.dpselection.CategoryCount;
import com.chaipoint.dpselection.DPStatus;
import com.chaipoint.dpselection.TimeCalculator;

public class DPHelper {
	HibernateTemplate template = new HibernateTemplate();

	// give the DP details having minimum number of deliveries
	public String getMinNoOfDelivered(Map<String, DPStatus> dpStatus, ArrayList<String> queue) {

		ArrayList<DPStatus> count = new ArrayList<DPStatus>();
		for (String dpString : queue) {

			count.add(dpStatus.get(dpString));

		}

		Collections.sort(count, new Comparator<DPStatus>() {
			public int compare(DPStatus s1, DPStatus s2) {
				int result = 0;
				if ((s1 == null && s2 != null) || (s1.getNoOfOrdersDelivered() >= s2.getNoOfOrdersDelivered())) {
					result = 1;
				}
				if ((s1 != null && s2 == null) || (s1.getNoOfOrdersDelivered() < s2.getNoOfOrdersDelivered())) {
					result = -1;
				}
				return result;
			}
		});

		return count.get(0).getDPname();

	}

	// This method will return approximate return time of each DPs and return
	// min return time
	public TimeCalculator minReturnTimeDPId(Map<String, DPStatus> dpStatus, Queue<String> queue, String storeId,
			Map<String, Queue<String>> DPQueues) {

		queue = DPQueues.get(storeId);
		ArrayList<DPStatus> dpstatus = new ArrayList<>();

		for (String list : queue) {
			dpstatus.add(dpStatus.get(list));
		}

		TimeHelper helper = new TimeHelper();
		TimeCalculator dp = helper.TimeSetter(dpstatus, storeId);
		return dp;

	}

	public long PreparationTime(CategoryCount categoryCount, String storeId) {

		long prepTime;
		Criteria criteria = template.getSession().createCriteria(StoreItemTimeMapping.class);
		criteria.add(Restrictions.eq("storeId", storeId));
		ArrayList<StoreItemTimeMapping> DPList = (ArrayList<StoreItemTimeMapping>) template.get(criteria);
		StoreItemTimeMapping timeMapping = (StoreItemTimeMapping) (DPList).get(0);

		prepTime = ((categoryCount.getNoOfHotBeverages() * timeMapping.getHbTime())
				+ (categoryCount.getNoOfColdBeverages() * timeMapping.getCbTime())
				+ (categoryCount.getNoOfRTPFoods() * timeMapping.getTbpTime())
				+ (categoryCount.getNoOfRMFoods() * timeMapping.getRmfTime()));

		return prepTime;
	}

	// item category counting
	public CategoryCount setCategoryCount(ArrayList<String> itemIds) {

		CategoryCount categoryCount = new CategoryCount();

		for (String id : itemIds) {

			Criteria criteria = template.getSession().createCriteria(ItemCodeCategory.class);
			criteria.add(Restrictions.eq("itemId", id));
			criteria.setProjection(Projections.property("category"));
			ArrayList<String> DPList = (ArrayList<String>) template.get(criteria);
			String string = DPList.get(0);

			if (string.equalsIgnoreCase("HB")) {
				categoryCount.setNoOfHotBeverages(1);
			} else if (string.equalsIgnoreCase("CB")) {
				categoryCount.setNoOfColdBeverages(1);
			} else if (string.equalsIgnoreCase("TBPF")) {
				categoryCount.setNoOfColdBeverages(1);

			} else if (string.equalsIgnoreCase("RMF")) {
				categoryCount.setNoOfColdBeverages(1);
			}

		}
		return categoryCount;
	}

}
