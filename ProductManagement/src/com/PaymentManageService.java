package com;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;

import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.PaymentManage;


@Path("/Payment")
public class PaymentManageService {
PaymentManage payment_obj=new PaymentManage();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPosts()
	{
		return payment_obj.readPayments();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String createPost(
			@FormParam("productCode") String productCode,
			@FormParam("paymentdate") String paymentdate,
			@FormParam("paymentamount") String paymentamount,
			@FormParam("paymentstatus") String paymentstatus
			) {
		String output= payment_obj.createPayments(productCode, paymentdate, paymentamount, paymentstatus);
				return output;
	}
	
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String postData)
	{
		//Convert the input string to a JSON object
		JsonObject postObj = new JsonParser().parse(postData).getAsJsonObject();
		
		String ID = postObj.get("pID").getAsString();
		String productCode = postObj.get("productCode").getAsString();
		String paymentdate = postObj.get("paymentdate").getAsString();
		String paymentamount = postObj.get("paymentamount").getAsString();
		String paymentstatus = postObj.get("paymentstatus").getAsString();
		
		String output=payment_obj.updatePost(ID, productCode, paymentdate, paymentamount, paymentstatus);
		
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String itemData)
	{
		Document doc= Jsoup.parse(itemData,"",Parser.xmlParser());
		
		String itemID=doc.select("pID").text();
		
		String output=payment_obj.deletePayments(itemID);
		return output;
	}
}
