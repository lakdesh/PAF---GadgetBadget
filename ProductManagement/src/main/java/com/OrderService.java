package com;

import model.Order;


//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

@Path("/Order") 
public class OrderService 
{
	
	Order orderObj = new Order();
	

	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readOrder() 
	{ 
		return orderObj.readorder(); 
	} 	

	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertOrder(
		 @FormParam("orderCode") String orderCode, 
		 @FormParam("orderType") String orderType, 
		 @FormParam("customerName") String customerName,
		 @FormParam("customerContact") String customerContact,
		 @FormParam("totalAmount") String totalAmount,
		 @FormParam("cardNo") String cardNo,
		 @FormParam("cvvNo") String cvvNo)
		 
	{ 
		String output = orderObj.insertorder(orderCode, orderType, customerName, customerContact,  totalAmount, cardNo, cvvNo); 
		return output; 
	}	
	
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateOrder(String orderData) 
	{ 
		//Convert the input string to a JSON object 
		 JsonObject orderObject = new JsonParser().parse(orderData).getAsJsonObject(); 
		//Read the values from the JSON object
		 String orderID = orderObject.get("orderID").getAsString(); 
		 String orderCode = orderObject.get("orderCode").getAsString();
		 String orderType = orderObject.get("orderType").getAsString();   
		 String customerName = orderObject.get("customerName").getAsString();
		 String customerContact = orderObject.get("customerContact").getAsString();
		 String totalAmount = orderObject.get("totalAmount").getAsString(); 
		 String cardNo = orderObject.get("cardNo").getAsString();  
		 String cvvNo = orderObject.get("cvvNo").getAsString(); 
		 
		// String output = orderObj.updateOrder(orderData);
		 
		 String output = orderObj.updateorder(orderID, orderCode, orderType,customerName, customerContact, totalAmount, cardNo, cvvNo); 
		 return output; 
	}
	
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteOrder(String orderData) 
	{ 
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(orderData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <itemID>
		 String orderID = doc.select("orderID").text(); 
		 String output = orderObj.deleteorder(orderID); 
		 return output; 
	}
}
