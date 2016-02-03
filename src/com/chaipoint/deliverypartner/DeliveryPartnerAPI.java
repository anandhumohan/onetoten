package com.chaipoint.deliverypartner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.chaipoint.dpselection.DPselector;
import com.chaipoint.dpselection.ItemDetails;
import com.chaipoint.dpselection.OrderRootObject;
import com.google.gson.Gson;

@Path("/dp")
public class DeliveryPartnerAPI {
	@Path("/selector")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getDpId(String order) {

		Map<String, String> jsonToMap = new HashMap<String, String>();
		jsonToMap = new Gson().fromJson(order, HashMap.class);
		String orderJson = jsonToMap.get("order");
		jsonToMap = new Gson().fromJson(orderJson, HashMap.class);
		// jsonToMap.get("order-details");
		OrderRootObject details = new Gson().fromJson(jsonToMap.get("order-details"), OrderRootObject.class);
		ArrayList<String> ids = new ArrayList<String>();
		for (ItemDetails id : details.getItemDetails()) {
			ids.add(id.getItemId());

		}
		String DPId = new DPselector().setCofirmOrder(details.getStoreId(), ids);
		return Response.ok(DPId, MediaType.TEXT_PLAIN).build();

	}

	@Path("/dispatched")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public Response dispatchStatus(String id) {
		String storeId = id;
		String result = new DPselector().getDP(storeId);
		return Response.ok(result, MediaType.TEXT_PLAIN).build();

	}

	@Path("/entry")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response entryStatus(String status) {
		String storeId = null;
		String DPId = null;
		String result = new DPselector().setDP(storeId, DPId, false, null);
		return Response.ok(result, MediaType.TEXT_PLAIN).build();

	}

	@Path("/delivered")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response delieryStatus(String status) {
		String storeId = null;
		String DPId = null;
		String result = new DPselector().setDP(storeId, DPId, false, null);
		return Response.ok(result, MediaType.TEXT_PLAIN).build();

	}

	@Path("/flush")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response dpFlush() {
		String result = new DPselector().forceFlush();
		return Response.ok(result, MediaType.TEXT_PLAIN).build();

	}

}
