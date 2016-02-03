package com.chaipoint.thirdparty.integration;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.chaipoint.dphelper.HibernateTemplate;
import com.chaipoint.thirdparty.constants.Constants;
import com.chaipoint.thirdparty.helper.roderunner.CustomerAddress;
import com.chaipoint.thirdparty.helper.roderunner.User;
import com.chaipoint.thirdparty.helper.shadowfax.CallbackResponse;
import com.chaipoint.thirdparty.helper.shadowfax.CallbackSFX;
import com.chaipoint.thirdparty.helper.shadowfax.ConfirmOrder;
import com.chaipoint.thirdparty.helper.shadowfax.CustomerDetails;
import com.chaipoint.thirdparty.helper.shadowfax.OrderDetails;
import com.chaipoint.thirdparty.helper.shadowfax.RequestOrder;
import com.chaipoint.thirdparty.helper.shadowfax.ResponseOrder;
import com.chaipoint.thirdparty.helper.shadowfax.Status;
import com.chaipoint.thirdparty.helper.shadowfax.Store;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class ShadowFaxHelper {

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final Logger logger = Logger.getLogger(ShadowFaxHelper.class);

	public String handleShadowFaxShipRequest(String orderId) {

		ConfirmOrder confOrder = null;
		if (confOrder != null) {

			// will get all store details from here
			Store storeDetails = null;
			// get address details from address Id
			CustomerAddress addressDetails = null;
			User user = null;
			RequestOrder orderReq = createOrderRequest(confOrder, storeDetails, addressDetails, user);

			if (orderReq != null) {
				// send this request to SFX api
				return sendRequestToSFX(orderReq, orderId);

			} else {
				return "Order invalid format";
			}

			// 2 means orderRequest can not be formatted
		}
		return null;
	}

	private String sendRequestToSFX(RequestOrder orderReq, String orderId) {
		Gson gson = new Gson();
		String json = gson.toJson(orderReq);
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url(Constants.SFX_SHIP_API)
				.addHeader("Authorization", Constants.SFX_AUTH_TOKEN)
				.addHeader("Content-Type", Constants.SFX_CONTENT_TYPE).post(RequestBody.create(JSON, json)).build();
		Response response = null;
		try {
			ResponseOrder shipResp = null;
			response = client.newCall(request).execute();
			try {
				shipResp = (new Gson()).fromJson(response.body().charStream(), ResponseOrder.class);
				
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return null;

			} catch (JsonIOException e) {
				e.printStackTrace();
				return null;
			}
			// get tracking details also here so that we can update rider
			// details also
			// here we will call hit our local API which will just update the
			// status
			/*
			 * boolean isStatusUpdated = handleOrderUpdate(shipResp, orderId);
			 * if (isStatusUpdated) { return
			 * "Request sent to SFX Shipping id : " +
			 * shipResp.getData().getSfx_order_id(); } else { return
			 * "Reqeust could not be sent to SFX"; }
			 */
		} catch (IOException e) {
			// logger.error("Error while calling roderunner api " + e);
			e.printStackTrace();
			return null;
		}
		return null;

	}

	/*
	 * private boolean handleOrderUpdate(ResponseOrder shipResp, String orderId)
	 * { // update
	 * 
	 * ConfirmOrderDao confirmOrder = new ConfirmOrderOperations(); ConfirmOrder
	 * confOrder = confirmOrder.getCposOrderStatus(Integer.parseInt(orderId));
	 * HibernateTemplate hbm = new HibernateTemplate();
	 * 
	 * confOrder.setDeliveryBoy(""); confOrder.setDeliveryBoyContact("");
	 * confOrder.setShippingOrderId(shipResp.getData().getSfx_order_id());
	 * confOrder.setDeliveryId(""); confOrder.setDeliveryCompany("2");
	 * 
	 * try { String respUpdate = hbm.update(confOrder); if
	 * (respUpdate.trim().equals(Constants.Return_Success)) { return true; }
	 * else { return false; } } catch (Exception e) { // TODO Auto-generated
	 * catch block logger.error("error while updating details");
	 * e.printStackTrace(); return false; } }
	 * 
	 * 
	 * private boolean handleOrderTrackUpdate(ResponseOrder shipResp, String
	 * orderId) { // update
	 * 
	 * ConfirmOrderDao confirmOrder = new ConfirmOrderOperations(); ConfirmOrder
	 * confOrder = confirmOrder.getCposOrderStatus(Integer.parseInt(orderId));
	 * HibernateTemplate hbm = new HibernateTemplate();
	 * 
	 * confOrder.setDeliveryBoy(shipResp.getData().getRider_details().
	 * getRider_name());
	 * confOrder.setDeliveryBoyContact(shipResp.getData().getRider_details().
	 * getRider_phone());
	 * confOrder.setShippingOrderId(shipResp.getData().getSfx_order_id());
	 * confOrder.setDeliveryId(""); confOrder.setDeliveryCompany("2");
	 * 
	 * try { String respUpdate = hbm.update(confOrder); if
	 * (respUpdate.trim().equals(Constants.Return_Success)) { return true; }
	 * else { return false; } } catch (Exception e) { // TODO Auto-generated
	 * catch block logger.error("error while updating details");
	 * e.printStackTrace(); return false; } }
	 */
	private RequestOrder createOrderRequest(ConfirmOrder confOrder, Store storeDetails, CustomerAddress addressDetails,
			User user) {

		RequestOrder reqOrder = new RequestOrder();
		OrderDetails orderDetails = new OrderDetails();
		CustomerDetails custDetails = new CustomerDetails();

		custDetails.setAddress_line_1(addressDetails.getAddressLineFirst());
		custDetails.setAddress_line_2(addressDetails.getAddressLineSecond());
		custDetails.setCity(addressDetails.getCity());
		custDetails.setContact_number(addressDetails.getContactPrimary());
		custDetails.setLatitude(Double.parseDouble(addressDetails.getLatitude()));
		custDetails.setLongitude(Double.parseDouble(addressDetails.getLongitude()));
		custDetails.setName(addressDetails.getName());

		Double d = new Double(confOrder.getTotalPayableAmount());
		orderDetails.setClient_order_id(Integer.toString(confOrder.getCposOrderId()));
		orderDetails.setOrder_value(d.intValue());
		orderDetails.setPaid(Integer.toString(confOrder.getIsPaid()));
		orderDetails.setPreparation_time(Constants.ORDER_PREPARATION_TIME);
		orderDetails.setScheduled_time("");

		reqOrder.setStore_code("chai001");
		reqOrder.setPickup_contact_number(
				storeDetails.getStoreContactNumber().substring(0, storeDetails.getStoreContactNumber().indexOf(",")));
		reqOrder.setCustomer_details(custDetails);
		reqOrder.setOrder_details(orderDetails);

		return reqOrder;

	}

	public String handleShadowFaxCancelRequest(String orderId) {
		String resp = "";
		/*
		 * ConfirmOrderDao confirmOrder = new ConfirmOrderOperations();
		 * ConfirmOrder confOrder =
		 * confirmOrder.getCposOrderStatus(Integer.parseInt(orderId));
		 * 
		 * if (confOrder.getDeliveryCompany().trim().equals("2")) { String
		 * shipOrderId = confOrder.getShippingOrderId();
		 */
		if (!(orderId == null)) {
			resp = callSFXCancelAPI(orderId);
		} else {
			resp = "Invalid request";
		}
		return resp;
	}

	private String callSFXCancelAPI(String shipOrderId) {
		Status status = new Status();
		status.setStatus("CANCELLED");
		Gson gson = new Gson();
		String reqBody = gson.toJson(status);

		String url = Constants.SFX_SHIP_API + shipOrderId + "/status/";
		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url(Constants.SFX_SHIP_API)
				.addHeader("Authorization", Constants.SFX_AUTH_TOKEN)
				.addHeader("Content-Type", Constants.SFX_CONTENT_TYPE).put(RequestBody.create(JSON, reqBody)).build();
		Response response = null;
		try {
			ResponseOrder cancelResp = null;
			response = client.newCall(request).execute();
			try {
				cancelResp = (new Gson()).fromJson(response.body().charStream(), ResponseOrder.class);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return "Unsuccessful please retry after sometime";

			} catch (JsonIOException e) {
				e.printStackTrace();
				return "Unsuccessful please retry after sometime";
			}

			if (cancelResp.getData().getStatus().trim().equals("CANCELLED")) {
				return "Successfully cancelled";
			} else {
				return "Unsuccessful please retry after sometime";
			}
		} catch (Exception ex) {
			logger.error("Exception while cancelling SFX ");
			return "Unsuccessful please retry after sometime";
		}
	}

	public String handleShadowFaxTrackRequest(String orderId, String shipOrderId) {
		String resp = "";
		if (!(orderId == null) && !(shipOrderId == null)) {
			resp = callSFXTrackAPI(shipOrderId, orderId);
		} else {
			resp = "Invalid request";
		}
		return resp;
	}

	private String callSFXTrackAPI(String shipOrderId, String orderId) {
		// get tracking details
		// extract rider details
		// update necssery information in DB
		// return it to user

		OkHttpClient client = new OkHttpClient();
		String resp = "";
		Request request = new Request.Builder().url(Constants.SFX_SHIP_API + shipOrderId + "rider_info=true")
				.addHeader("Authorization", Constants.SFX_AUTH_TOKEN)
				.addHeader("Content-Type", Constants.SFX_CONTENT_TYPE).get().build();
		Response response = null;
		try {
			ResponseOrder shipResp = null;
			response = client.newCall(request).execute();
			try {
				shipResp = (new Gson()).fromJson(response.body().charStream(), ResponseOrder.class);
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return "Invalid Request please retry";

			} catch (JsonIOException e) {
				e.printStackTrace();
				return "Error please retry";
			} catch (Exception e) {
				e.printStackTrace();
				return "Error please retry";
			}
			// get tracking details also here so that we can update rider
			// details also
			// here we will call hit our local API which will just update the
			// status

			// boolean isStatusUpdated = handleOrderTrackUpdate(shipResp,
			// orderId);

			Gson gson = new Gson();
			resp = gson.toJson(shipResp);
			return resp;

		} catch (IOException e) {
			// logger.error("Error while calling roderunner api " + e);
			e.printStackTrace();
			return "Invalid Request please retry";
		} catch (Exception e) {
			e.printStackTrace();
			return "Invalid Request please retry";
		}
	}
/*
	public CallbackResponse handleShadowFaxCallback(String callbackReq) {
		CallbackResponse callbackResp = new CallbackResponse();

		CallbackSFX callbackObj = (new Gson()).fromJson(callbackReq, CallbackSFX.class);
		ConfirmOrderDao confirmOrder = new ConfirmOrderOperations();
		ConfirmOrder confOrder = confirmOrder.getConfirmOrderByShippingId(callbackObj.getSfx_order_id());
		HibernateTemplate hbm = new HibernateTemplate();
		confOrder.setStatus(callbackObj.getOrder_status());
		confOrder.setCancellationReason(callbackObj.getCancel_reason());
		confOrder.setDeliveryBoy(callbackObj.getRider_name());
		// confOrder.setDeliveryDate(callbackObj.getDelivery_time());
		confOrder.setDeliveryBoyContact(Long.toString(callbackObj.getRider_contact()));
		// confOrder.setDispatchDateinLong(dispatchDateinLong);

		String resp = hbm.update(confOrder);
		if (resp.trim().equals(Constants.Return_Error)) {
			callbackResp.setMessage("done successfully");
		} else {
			callbackResp.setMessage("failed to update status");
		}
		return callbackResp;
	}
	*/

}
