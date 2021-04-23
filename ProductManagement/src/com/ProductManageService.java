package com;

import javax.ws.*;
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


import model.ProductManage;

@Path("/Product")
public class ProductManageService {
ProductManage product_obj=new ProductManage();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPosts()
	{
		return product_obj.readProducts();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String createPost(
			@FormParam("productCode") String productCode,
			@FormParam("productName") String productName,
			@FormParam("productPrice") String productPrice,
			@FormParam("productDesc") String productDesc
			) {
		String output= product_obj.createProduct(productCode, productName, productPrice, productDesc);
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
		String productName = postObj.get("productName").getAsString();
		String productPrice = postObj.get("productPrice").getAsString();
		String productDesc = postObj.get("productDesc").getAsString();
		
		String output=product_obj.updatePost(ID, productCode, productName, productPrice, productDesc);
		
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
		
		String output=product_obj.deleteProduct(itemID);
		return output;
	}
}
