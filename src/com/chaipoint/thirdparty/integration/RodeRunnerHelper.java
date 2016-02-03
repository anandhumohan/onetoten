package com.chaipoint.thirdparty.integration;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import org.apache.log4j.Logger;

import com.chaipoint.thirdparty.constants.Constants;
import com.chaipoint.thirdparty.helper.roderunner.CallbackRoderunnr;
import com.chaipoint.thirdparty.helper.roderunner.City;
import com.chaipoint.thirdparty.helper.roderunner.ConfirmOrder;
import com.chaipoint.thirdparty.helper.roderunner.CustomerAddress;
import com.chaipoint.thirdparty.helper.roderunner.Drop;
import com.chaipoint.thirdparty.helper.roderunner.FullAddress;
import com.chaipoint.thirdparty.helper.roderunner.Geo;
import com.chaipoint.thirdparty.helper.roderunner.Item;
import com.chaipoint.thirdparty.helper.roderunner.Locality;
import com.chaipoint.thirdparty.helper.roderunner.OrderDetails;
import com.chaipoint.thirdparty.helper.roderunner.OrderItem;
import com.chaipoint.thirdparty.helper.roderunner.OrderItems;
import com.chaipoint.thirdparty.helper.roderunner.OrderRequest;
import com.chaipoint.thirdparty.helper.roderunner.OrderType;
import com.chaipoint.thirdparty.helper.roderunner.Pickup;
import com.chaipoint.thirdparty.helper.roderunner.Serviceable;
import com.chaipoint.thirdparty.helper.roderunner.ShipResponse;
import com.chaipoint.thirdparty.helper.roderunner.Status;
import com.chaipoint.thirdparty.helper.roderunner.Store;
import com.chaipoint.thirdparty.helper.roderunner.SubLocality;
import com.chaipoint.thirdparty.helper.roderunner.TrackDetails;
import com.chaipoint.thirdparty.helper.roderunner.User;
import com.chaipoint.thirdparty.helper.shadowfax.Data;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class RodeRunnerHelper {

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final Logger logger = Logger.getLogger(RodeRunnerHelper.class);

	public String handleRodeRunnerShipRequest(int orderId, String orderJson, String storeJson) {
		// get address id from confirm order and get confirm order details based
		// on the orderId
		ConfirmOrder confOrder = null;
		String shipOrderId = "";
		if (confOrder != null) {
			// using json will get all store details from here
			Store storeDetails = null;
			// get item details from json
			ArrayList<OrderItem> orderItems = null;
			// get address details from json
			CustomerAddress addressDetails = null;
			// get user details from json
			User user = null;
			// User user = userDetails.getUserbyId(addressDetails.getCustId());
			OrderRequest orderReq = createOrderRequest(confOrder, storeDetails, orderItems, addressDetails, user);
			if (orderReq != null) {
				// send this request to rode runner api return shipOrderid
				
				shipOrderId =sendRequestToRodeRunnr(orderReq, orderId);

			} else {
				return "Order invalid format";
			}

			// 2 means orderRequest can not be formatted
		}
		return shipOrderId;
	}

	boolean isServiceable = false;

	private String sendRequestToRodeRunnr(OrderRequest orderReq, int orderId) {
		Gson gson = new Gson();
		String json = gson.toJson(orderReq);
		isServiceable = checkIsServiceable(json);

		if (isServiceable) {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder().url(Constants.RODERNNR_SHIP_API)
					.addHeader("Authorization", Constants.RODERUNNR_AUTH_TOKEN)
					.addHeader("Content-Type", Constants.RODERUNNR_CONTENT_TYPE).post(RequestBody.create(JSON, json))
					.build();
			Response response = null;
			try {
				ShipResponse shipResp = null;
				response = client.newCall(request).execute();
				try {
					shipResp = (new Gson()).fromJson(response.body().charStream(), ShipResponse.class);
					return shipResp.getOrder_id();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
					return null;

				} catch (JsonIOException e) {
					e.printStackTrace();
					return null;
				}
				/*
				 * boolean isStatusUpdated = handleOrderUpdate(shipResp,
				 * orderId); if (isStatusUpdated) { return
				 * "Request sent to Roderunnr"; } else { return
				 * "Reqeust could not be sent to roderunnr"; }
				 */
			} catch (IOException e) {
				logger.error("Error while calling roderunner api " + e);
				e.printStackTrace();
				return null;
			}
		} else {
			return "Not serviceable please try after sometime";
		}
	//	return json;
	}
/*
	private boolean handleOrderUpdate(ShipResponse shipResp, int orderId) {

		// handle order updation here
		ConfirmOrderDao confirmOrder = new ConfirmOrderOperations();
		ConfirmOrder confOrder = confirmOrder.getCposOrderStatus(orderId);

		if (shipResp.isNew_trip()) {
			HibernateTemplate hbm = new HibernateTemplate();

			confOrder.setDeliveryBoy(shipResp.getDriver_name());
			confOrder.setDeliveryBoyContact(shipResp.getDriver_phone());
			confOrder.setShippingOrderId(shipResp.getOrder_id());
			confOrder.setDeliveryId(shipResp.getDelivery_id());
			confOrder.setDeliveryCompany("1");

			try {
				String respUpdate = hbm.update(confOrder);
				if (respUpdate.trim().equals(Constants.Return_Success)) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				logger.error("error while updating details");
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
*/
	private boolean checkIsServiceable(String orderReq) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(Constants.RODERNNR_SERVICEABLE_API)
				.addHeader("Authorization", Constants.RODERUNNR_AUTH_TOKEN)
				.addHeader("Content-Type", Constants.RODERUNNR_CONTENT_TYPE).post(RequestBody.create(JSON, orderReq))
				.build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			logger.error("Error while calling roderunner serviceable api " + e);
			e.printStackTrace();
			return false;
		}
		try {
			String resp = response.body().string();
			Serviceable serviceable = (new Gson()).fromJson(resp, Serviceable.class);
			boolean isServiceable = serviceable.isServiceable();

			// return isServiceable;
			return true;
		} catch (IOException e) {
			logger.error("Error while returning response from serviceable roderunner api " + e);
			e.printStackTrace();
			return false;
		}
	}

	private OrderRequest createOrderRequest(ConfirmOrder confOrder, Store storeDetails, ArrayList<OrderItem> orderItems,
			CustomerAddress addressDetails, User user) {
		try {
			OrderRequest orderReq = new OrderRequest();

			Pickup pickup = mapStoreToPickup(storeDetails);
			Drop drop = mapAddressToDrop(addressDetails, user);
			OrderDetails orderDetails = mapOrderDetailsToOrderItems(orderItems, confOrder);

			orderReq.setCallback_url(Constants.RODERUNNR_CALLBACK_URL);
			orderReq.setCreated_at(new Data().toString());
			// orderReq.setCreated_at(ChaipointDateUtils.getDateNow());
			if (drop != null && pickup != null && orderDetails != null) {
				orderReq.setDrop(drop);
				orderReq.setPickup(pickup);
				orderReq.setOrder_details(orderDetails);
			} else {
				return null;
			}
			return orderReq;
		} catch (Exception e) {
			logger.error("error in setting orderReq " + e);
			e.printStackTrace();
			return null;
		}
	}

	private OrderDetails mapOrderDetailsToOrderItems(ArrayList<OrderItem> orderItems, ConfirmOrder confOrder) {
		if (orderItems.size() < 1 || confOrder == null) {
			return null;
		}
		try {
			OrderDetails orderDetails = new OrderDetails();

			OrderType orderType = new OrderType();

			List<OrderItems> orderItemsList = new ArrayList<OrderItems>();

			for (OrderItem orderItem : orderItems) {

				OrderItems orderItemsObj = new OrderItems();
				Item item = new Item();
				item.setName(orderItem.getItemName());

				orderItemsObj.setItem(item);
				orderItemsObj.setQuantity(orderItem.getItemUnitCount());
				orderItemsObj.setPrice(orderItem.getFinalPrice());

				orderItemsList.add(orderItemsObj);
			}

			orderType.setName("COC");

			orderDetails.setAmount_to_be_collected(Double.toString(confOrder.getTotalPayableAmount()));
			orderDetails.setOrder_id(confOrder.getTransactionId());
			orderDetails.setOrder_value(Double.toString(confOrder.getTotalOrderAmount()));
			orderDetails.setOrder_type(orderType);
			orderDetails.setOrder_items(orderItemsList);

			return orderDetails;
		} catch (Exception e) {
			logger.error("Error in setting order Details " + e);
			e.printStackTrace();
			return null;
		}
	}

	private Drop mapAddressToDrop(CustomerAddress addressDetails, User user) {
		if (addressDetails == null || user == null) {
			return null;
		}
		try {
			Drop drop = new Drop();
			User usr = new User();
			Locality locality = new Locality();
			SubLocality subLoaclity = new SubLocality();
			City city = new City();
			Geo geo = new Geo();

			FullAddress addressFull = new FullAddress();

			// Full address setting
			locality.setName(addressDetails.getAddressLineSecond());
			subLoaclity.setName(addressDetails.getLandMark());
			city.setName(addressDetails.getCity());
			geo.setLatitude(addressDetails.getLatitude());
			geo.setLongitude(addressDetails.getLongitude());

			addressFull.setAddress(addressDetails.getAddressLineFirst());
			addressFull.setCity(city);
			addressFull.setGeo(geo);
			addressFull.setLocality(locality);
			addressFull.setSub_locality(subLoaclity);

			usr.setFull_address(addressFull); // setting drop details // user
												// details
			usr.setEmail(user.getEmail());// we don't have email id in store //
			usr.setExternal_id("CUST-" + user.getPhone_no());
			usr.setName(user.getName());
			// usr.setPhone_no(user.getContactPrimary());
			usr.setPhone_no(user.getPhone_no());
			usr.setType("customer");

			drop.setUser(usr);
			return drop;
		} catch (Exception e) {
			logger.error("Error in setting Drop " + e);
			e.printStackTrace();
			return null;
		}
	}

	private Pickup mapStoreToPickup(Store storeDetails) {
		if (storeDetails == null) {
			return null;
		}
		try {
			Pickup pickUp = new Pickup();
			User usr = new User();
			Locality locality = new Locality();
			SubLocality subLoaclity = new SubLocality();
			City city = new City();
			Geo geo = new Geo();
			FullAddress addressFull = new FullAddress();

			locality.setName("");
			subLoaclity.setName("");
			city.setName(storeDetails.getStoreCity());
			geo.setLatitude(storeDetails.getStoreLatitude() + "");
			geo.setLongitude(storeDetails.getStoreLongitude() + "");

			addressFull.setAddress(storeDetails.getStoreAddress());
			addressFull.setCity(city);
			addressFull.setGeo(geo);
			addressFull.setLocality(locality);
			addressFull.setSub_locality(subLoaclity);

			usr.setFull_address(addressFull); // setting drop details // user
												// details
			usr.setEmail("");// we don't have email id in store table // so it
								// will
			usr.setExternal_id("STORE-" + storeDetails.getStoreId());
			usr.setName(storeDetails.getStoreName());
			usr.setPhone_no(storeDetails.getStoreContactNumber().substring(0,
					storeDetails.getStoreContactNumber().indexOf(",")));
			usr.setType("merchant");

			pickUp.setUser(usr);
			return pickUp;
		} catch (Exception e) {
			logger.error("Error in setting pickUp " + e);
			e.printStackTrace();
			return null;
		}
	}

	public boolean handleCancelOrder(int orderId,int shipOrderId) {
		// ConfirmOrderDao confirmOrder = new ConfirmOrderOperations();
		// ConfirmOrder confOrder = confirmOrder.getCposOrderStatus(orderId);
		// String shipOrderId = confOrder.getShippingOrderId();
		String url = Constants.RODERUNNER_CANCEL_API + shipOrderId + "/cancel";
		Response resp = callRodeRunnerAPI(url);
		try {
			// HibernateTemplate hbm = new HibernateTemplate();
			Status status = (new Gson()).fromJson(resp.body().charStream(), Status.class);
			if (status.code == 200) {

				System.out.println("Shipping cancelled");
				/*
				 * ConfirmOrderDao confirmOrderObj = new
				 * ConfirmOrderOperations(); ConfirmOrder confOrderObj =
				 * confirmOrderObj.getCposOrderStatus(orderId);
				 * confOrderObj.setStatus("Shipping cancelled");
				 * 
				 * try { String respUpdate = hbm.update(confOrderObj); if
				 * (respUpdate.trim().equals(Constants.Return_Success)) { return
				 * true; } else { return false; } } catch (Exception e) { //
				 * TODO Auto-generated catch block logger.error(
				 * "error while updating details"); e.printStackTrace(); return
				 * false; }
				 */
			}
		} catch (Exception ex) {
			logger.error("Error in handling cancel order " + ex);
			ex.printStackTrace();
			return false;
		}
		return false;
	}

	public String handleTrackOrder(int orderId,int shippingOrderId) {
		String resp = "";
		// ConfirmOrderDao confirmOrder = new ConfirmOrderOperations();
		// ConfirmOrder confOrder = confirmOrder.getCposOrderStatus(orderId);
		// String shippingOrderId = confOrder.getShippingOrderId();
		
		String url = Constants.RODERUNNER_CANCEL_API + shippingOrderId + "/track";
		Response response = callRodeRunnerAPI(url);
		TrackDetails track = null;
		try {
			track = (new Gson()).fromJson(response.body().charStream(), TrackDetails.class);

		} catch (JsonSyntaxException | JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return resp;
		}
		// change it later
		Gson gson = new Gson();
		resp = gson.toJson(track);
		String escaped = resp.replace("\\", "=");
		return escaped;
	}

	private Response callRodeRunnerAPI(String url) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(url).addHeader("Authorization", Constants.RODERUNNR_AUTH_TOKEN)
				.addHeader("Content-Type", Constants.RODERUNNR_CONTENT_TYPE).get().build();
		Response response = null;
		try {
			response = client.newCall(request).execute();

		} catch (IOException e) {
			logger.error("Error while calling roderunner " + e);
			e.printStackTrace();
			return null;
		}
		return response;
	}
	/*
	 * public String handleRoderunnrCallBack(CallbackRoderunnr reqBody) { String
	 * resp = ""; if (reqBody.getOrder_id().trim() == "" ||
	 * reqBody.getOrder_id() == null) { return
	 * "orderId can not be null or empty"; } ConfirmOrderDao confirmOrder = new
	 * ConfirmOrderOperations(); ConfirmOrder confOrder =
	 * confirmOrder.getConfirmOrderByShippingId(reqBody.getOrder_id());
	 * HibernateTemplate hbm = new HibernateTemplate();
	 * confOrder.setStatus(reqBody.getStatus()); String updateResp =
	 * hbm.update(confOrder); if
	 * (updateResp.trim().equals(Constants.Return_Error)) { logger.error(
	 * "updated request success from Roderunner for ShippingId:" +
	 * reqBody.getOrder_id() + " Status:" + reqBody.getStatus()); resp =
	 * "Success update"; } else { logger.error(
	 * "updated request Failed from Roderunner for ShippingId:" +
	 * reqBody.getOrder_id() + " Status:" + reqBody.getStatus()); resp =
	 * "Failed update"; } return resp; }
	 */

}
