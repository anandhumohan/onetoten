package com.chaipoint.dptest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import com.chaipoint.dphelper.HibernateTemplate;
import com.chaipoint.dppojos.ItemCategoryTable;
import com.chaipoint.dppojos.ItemCodeCategory;
import com.chaipoint.dppojos.StoreItemTimeMapping;
import com.chaipoint.dppojos.TimeSliceDistanceTravel;
import com.chaipoint.dpselection.DPselector;
import com.chaipoint.dpselection.ItemDetails;
import com.chaipoint.dpselection.OrderRootObject;
import com.google.gson.Gson;

public class RoundRobinTest {

	public static void main(String[] args) {
		RoundRobinTest robinTest = new RoundRobinTest();

		robinTest.setStoreTime();
		robinTest.setTimeSliceMapping();
		robinTest.setItemCategoryTable();
		// robinTest.setStore();
		robinTest.setItemCodeCategory();

		String result = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader("C:/Users/anandh/Documents/order.txt"));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		System.out.println("here is our json");

		OrderRootObject order = new Gson().fromJson(result, OrderRootObject.class);
		String storeId = order.getStoreId();
		ArrayList<String> ids = new ArrayList<String>();
		for (ItemDetails id : order.getItemDetails()) {
			ids.add(id.getItemId());

		}
		for (int i = 0; i < 4; i++) {
			String id = new DPselector().getDP(storeId);
			System.out.println(id);
		}

	}

	public void setItemCodeCategory() {
		ItemCodeCategory category = new ItemCodeCategory();
		category.setItemId("ITEM1");
		category.setCategory("HB");
		ItemCodeCategory cat = new ItemCodeCategory();
		cat.setItemId("ITEM2");
		cat.setCategory("CB");

		HibernateTemplate hbm = new HibernateTemplate();
		try {
			hbm.save(category);
			hbm.save(cat);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/*
	 * public void setStore() { Stores stores = new Stores();
	 * stores.setStoreId("STR1"); ArrayList<String> DPlist = new
	 * ArrayList<String>(); String[] list = { "boy1", "boy2", "boy3", "boy4" };
	 * Collections.addAll(DPlist, list); stores.setStoreDeliveryPartner(DPlist);
	 * 
	 * HibernateTemplate hbm = new HibernateTemplate(); try { hbm.save(stores);
	 * } catch (Exception e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * }
	 */
	public void setStoreTime() {

		StoreItemTimeMapping itemTimeMapping = new StoreItemTimeMapping();
		itemTimeMapping.setStoreId("STR1");
		itemTimeMapping.setCbTime(2);
		itemTimeMapping.setHbTime(3);
		itemTimeMapping.setTbpTime(2);
		itemTimeMapping.setTbpTime(2);

		HibernateTemplate hbm = new HibernateTemplate();
		System.out.println(hbm);
		System.out.println("template got");
		try {
			hbm.save(itemTimeMapping);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void setTimeSliceMapping() {
		TimeSliceDistanceTravel distanceTravel = new TimeSliceDistanceTravel();
		distanceTravel.setStoreId("STR1");
		distanceTravel.setFirstSlice(2);
		distanceTravel.setSecondSlice(1);
		distanceTravel.setThirdSlice(1);
		distanceTravel.setFourthSlice(1);
		distanceTravel.setFifthSlice(3);

		HibernateTemplate hbm = new HibernateTemplate();
		try {
			hbm.save(distanceTravel);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void setItemCategoryTable() {
		ItemCategoryTable categoryTable = new ItemCategoryTable();
		categoryTable.setItemId("ITEM1");
		categoryTable.setItemName("dum chai");
		categoryTable.setCategoryName("HB");

		HibernateTemplate hbm = new HibernateTemplate();
		try {
			hbm.save(categoryTable);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
