package com.chaipoint.deliverypartner;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.chaipoint.thirdparty.helper.roderunner.CallbackRoderunnr;
import com.chaipoint.thirdparty.integration.RodeRunnerHelper;
import com.google.gson.Gson;

@Path("/roderunner")
public class RoadRunnerAPI {

	@GET
	@Path("/submit/{orderId}")
	public Response shipOrder(@PathParam("orderId") int orderId) {
		String orderJson = "";
		String storeJson = "";
		RodeRunnerHelper rodeRunnerHandler = new RodeRunnerHelper();
		String response = rodeRunnerHandler.handleRodeRunnerShipRequest(orderId, orderJson, storeJson);
		Response resp = Response.status(200).entity(response).build();
		return resp;
	}

	@GET
	@Path("/cancel/{orderId}")
	public String cancelOrder(@PathParam("orderId") int orderId) {
		int shipOrderId = 0;
		RodeRunnerHelper rodeRunnerHandler = new RodeRunnerHelper();
		boolean response = rodeRunnerHandler.handleCancelOrder(orderId, shipOrderId);
		if (response) {
			return "cancelled successfully";
		} else {
			return "failed to cancel please retry";
		}
	}

	@GET
	@Path("/track/{orderId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response trackOrder(@PathParam("orderId") int orderId) {
		int shipOrderId = 0;
		RodeRunnerHelper rodeRunnerHandler = new RodeRunnerHelper();
		String response = rodeRunnerHandler.handleTrackOrder(orderId, shipOrderId);
		Response resp = Response.status(200).entity(response).build();
		return resp;
	}
	/*
	@POST
	@Path("/callback")
	public Response callBackRoderunner(String reqBody){
		RodeRunnerHelper rodeRunnerHandler = new RodeRunnerHelper();
		CallbackRoderunnr reqObj = (new Gson()).fromJson(reqBody, CallbackRoderunnr.class);
		Response resp = null;
		//	String response = rodeRunnerHandler.handleRoderunnrCallBack(reqObj);
		//Response resp = Response.status(200).entity(response).build();
		return resp;
	}
	*/
}





