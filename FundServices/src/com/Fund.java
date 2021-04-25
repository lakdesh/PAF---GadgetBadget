package com;

import model.F_Item;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Items")
public class Fund
{
	F_Item itemObj = new F_Item();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems()
	{
		return itemObj.readItems();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertfund(@FormParam("fundID") String fundID,
			@FormParam("fundName") String fundName,
			@FormParam("fundAmount") String fundAmount,
			@FormParam("Date") int Date)
	{
		String output = itemObj.insertfund(fundID, fundName, fundAmount, Date);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String itemData)
	{
		//Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		//Read the values from the JSON object
		String itemID = itemObject.get("itemID").getAsString();
		String fundID = itemObject.get("fundID").getAsString();
		String fundName = itemObject.get("fundName").getAsString();
		String fundAmount = itemObject.get("fundAmount").getAsString();
		String Date = itemObject.get("Date").getAsString();
	
		String output = itemObj.updateItem(itemID, fundID, fundName, fundAmount, Date);
			return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String itemData)
	{
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());
		//Read the value from the element <itemID>
		String itemID = doc.select("itemID").text();
		
		String output = itemObj.deleteItem(itemID);
			return output;
	}
	
	
}