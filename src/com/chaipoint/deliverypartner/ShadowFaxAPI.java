package com.chaipoint.deliverypartner;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.chaipoint.thirdparty.helper.shadowfax.CallbackResponse;
import com.chaipoint.thirdparty.integration.ShadowFaxHelper;

@Path("/shadowfax")
public class ShadowFaxAPI {

	@GET
	@Path("/submit/{orderId}")
	public Response shipOrder(@PathParam("orderId") String orderId) {

		ShadowFaxHelper helper = new ShadowFaxHelper();
		String response = helper.handleShadowFaxShipRequest(orderId);
		Response resp = Response.status(200).entity(response).build();
		return resp;
	}

	@GET
	@Path("/cancel/{orderId}")
	public Response cancelOrder(@PathParam("orderId") String orderId) {
		ShadowFaxHelper helper = new ShadowFaxHelper();
		String response = helper.handleShadowFaxCancelRequest(orderId);
		Response resp = Response.status(200).entity(response).build();
		return resp;
	}

	@GET
	@Path("/track/{orderId}")
	public Response trackOrder(@PathParam("orderId") String orderId) {
		String shipOrderId = "";
		ShadowFaxHelper helper = new ShadowFaxHelper();
		String response = helper.handleShadowFaxTrackRequest(orderId, shipOrderId);
		Response resp = Response.status(200).entity(response).build();
		return resp;
	}
/*
	@POST
	@Path("/callback/{orderId}/status")
	public Response callbackSFX(@PathParam("orderId") String shippingOrderId, String callbackReq) {
		Response resp = null;

		if (callbackReq == null || callbackReq.trim() == "" || shippingOrderId == null
				|| shippingOrderId.trim() == "") {
			return resp = Response.status(404).entity("Invalid Request").build();
		}
		ShadowFaxHelper helper = new ShadowFaxHelper();
		CallbackResponse response = helper.handleShadowFaxCallback(callbackReq);
		resp = Response.ok().entity(response).build();
		return resp;
	}
*/
}
