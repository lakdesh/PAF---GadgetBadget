package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class F_Item {

	
	
	
	private Connection connect()
	{
	Connection con = null;
	try
	{
	Class.forName("com.mysql.jdbc.Driver");
	
	con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
	}
	catch (Exception e)
	{e.printStackTrace();}
	return con;
	}
	public String insertItem(String code, String name, String price, String desc)
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for inserting."; }
	// create a prepared statement
	String query = " insert into fund (`itemID`,`fundID`,`fundName`,`fundAmount`,`Date`)"
	+ " values (?, ?, ?, ?, ?)";
	PreparedStatement preparedStmt = con.prepareStatement(query);
	// binding values
	preparedStmt.setInt(1, 0);
	preparedStmt.setString(2, code);
	preparedStmt.setString(3, name);
	preparedStmt.setDouble(4, Double.parseDouble(price));
	preparedStmt.setString(5, desc);
	// execute the statement
	preparedStmt.execute();
	con.close();
	output = "Inserted successfully";
	}
	catch (Exception e)
	{
	output = "Error while inserting the item.";
	System.err.println(e.getMessage());
	}
	return output;
	}
	public String readItems()
	{
	String output = "";
	try
	{
	Connection con = connect();
	if (con == null)
	{return "Error while connecting to the database for reading."; }
	
	output = "<table border='1'><tr><th>Item Code</th><th>Item Name</th>" +
	"<th>Item Price</th>" +
	"<th>Item Description</th>" +
	"<th>Update</th><th>Remove</th></tr>";
	
	String query = "select * from items";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(query);
	
	// iterate through the rows in the result set
	while (rs.next())
	{
	String itemID = Integer.toString(rs.getInt("itemID"));
	String fundID = rs.getString("fundID");
	String fundName = rs.getString("fundName");
	String fundAmount = Double.toString(rs.getDouble("fundAmount"));
	String Date = rs.getString("Date");

	output += "<tr><td>" + fundID + "</td>";
	output += "<td>" + fundName + "</td>";
	output += "<td>" + fundAmount + "</td>";
	output += "<td>" + Date + "</td>";
	
	// buttons
	output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
			+ "<td><form method='post' action='items.jsp'>"
			+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
	+ "<input name='itemID' type='hidden' value='" + itemID
	+ "'>" + "</form></td></tr>";
	}
	con.close();
	
	output += "</table>";
	}
	catch (Exception e)
	{
	output = "Error while reading the items.";
	System.err.println(e.getMessage());
	}
	return output;
	}
	public String updateItem(String ID, String code, String name, String price, String desc)
	
	{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for updating."; }
		// create a prepared statement
		String query = "UPDATE items SET itemCode=?,itemName=?,itemPrice=?,itemDesc=?WHERE itemID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setString(1, code);
		preparedStmt.setString(2, name);
		preparedStmt.setDouble(3, Double.parseDouble(price));
		preparedStmt.setString(4, desc);
		preparedStmt.setInt(5, Integer.parseInt(ID));
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Updated successfully";
		}
		catch (Exception e)
		{
		output = "Error while updating the item.";
		System.err.println(e.getMessage());
		}
		return output;
		}
		public String deleteItem(String itemID)

		{
		String output = "";
		try
		{
		Connection con = connect();
		
		if (con == null)
		{
			return "Error while connecting to the database for deleting."; 
		
		}
		
		// create a prepared statement
		String query = "delete from items where itemID=?";
		
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(itemID));
		
		// execute the statement
		preparedStmt.execute();
		con.close();
		
		output = "Deleted successfully";
		}
		catch (Exception e)
		{
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
			return output;
		}
		public String insertfund(String fundID, String fundName, String fundAmount, int date) {
			// TODO Auto-generated method stub
			return null;
		}
		}

