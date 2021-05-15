package model;

import java.sql.*; 

public class Order {

private static Connection connect() {
		
		Connection con = null;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gadgetbadget?useTimezone=true&serverTimezone=UTC", "root", "");
			
			System.out.println("succsess");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
		
	}
	
	//insert
	public String insertorder(String order_code, String order_type ,String customer_name, String customer_contact , String total_amount, String card_no, String cvv_no) 
	 { 
		 String output = ""; 
		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for inserting."; 
			 } 
		 	 	 // create a prepared statement
			 	 String query = "INSERT INTO order_gui (orderCode, orderType, customerName, customerContact, totalAmount, cardNo, cvvNo) VALUES (?, ?, ?, ?, ?, ?, ?)"; 
			 	 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 					 
				 
				 // binding values
//				 preparedStmt.setInt(1, 0);
				 preparedStmt.setString(1, order_code);
				 preparedStmt.setString(2, order_type);
				 preparedStmt.setString(3, customer_name);
				 preparedStmt.setString(4, customer_contact);
				 preparedStmt.setDouble(5, Double.parseDouble(total_amount));
				 preparedStmt.setString(6, card_no);
				 preparedStmt.setString(7, cvv_no);
				 				 
				 preparedStmt.execute(); 
				 con.close(); 
				 String newOrder = readorder(); 
				 output = "{\"status\":\"success\", \"data\": \"" + newOrder + "\"}";
				 //output = "Inserted successfully"; 
		 } 
		 catch (Exception e) 
		 { 
			 //output = "Error while inserting the order.";
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the order.\"}";
			 System.err.println(e.getMessage());
			 System.out.println(e.getMessage());
				System.out.println(e);
				e.printStackTrace();
			
		 } 
	 	return output; 
	 } 
	
	//Read orders
	 public String readorder() 
	 { 
		 String output = ""; 

		 try
		 { 
			 Connection con = connect(); 
			 if (con == null) 
			 {
				 return "Error while connecting to the database for reading."; 
			 } 
			 
			 // Prepare the html table to be displayed
			 output = "<table border='1'>"+ "<tr><th>order ID</th>" + 
			 "<th>order Code</th>" + 
			 "<th>order Type</th>" + 
			 "<th>Customer Name</th>" + 
			 "<th>Customer Contact</th>" +
			 "<th>Total Amount</th>" +
			 "<th>Card No</th>" +
			 "<th>CVV No</th>" +
			 "<th>Update</th><th>Remove</th></tr>"; 
		 
			 
			 String query = "SELECT * FROM order_gui"; 
			 Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
				 String orderID = Integer.toString(rs.getInt("orderID")); 
				 String orderCode = rs.getString("orderCode"); 
				 String orderType = rs.getString("orderType");  
				 String customerName = rs.getString("customerName"); 
				 String customerContact = rs.getString("customerContact");
				 String totalAmount = Double.toString(rs.getDouble("totalAmount")); 
				 String cardNo = Integer.toString(rs.getInt("cardNo"));
				 String cvvNo = Integer.toString(rs.getInt("cvvNo"));
				 
				 // Add into the html table
				 output += "<td>" + orderID + "</td>";
				 output += "<td>" + orderCode + "</td>";
				 output += "<td>" + orderType + "</td>";   
				 output += "<td>" + customerName + "</td>"; 
				 output += "<td>" + customerContact + "</td>";
				 output += "<td>" + totalAmount + "</td>"; 
				 output += "<td>" + cardNo + "</td>"; 
				 output += "<td>" + cvvNo + "</td>"; 					 
				 
				 
				 // buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						 + "<td><button class='btnRemove btn btn-danger' name='btnRemove' id ='btnRemove' value='"+ orderID +"' >Remove</button></td></tr>";
			 } 
			 	 con.close(); 
			 	 // Complete the html table
			 	 output += "</table>"; 
		 } 
		 catch (Exception e) 
		 { 
			 output = "Error while reading the order"; 
			 System.err.println(e.getMessage()); 
		 } 
	 	 return output; 
	 } 
		//Update orders
	 public String updateorder(String orderID,String orderCode, String orderType , String customerName, String customerContact , String totalAmount, String cardNo, String cvvNo)
		{ 
			 String output = ""; 
			 try
			 { 
				 Connection con = connect(); 
				 if (con == null) 
				 {
					 return "Error while connecting to the database for updating."; 
				 } 
				 
				 // create a prepared statement
				 String query = "UPDATE order_gui SET orderCode=? ,orderType=? , customerName=? , customerContact=?,  totalAmount=? , cardNo=?, cvvNo=?  WHERE orderID=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setString(1, orderCode); 
				 preparedStmt.setString(2, orderType); 	  
				 preparedStmt.setString(3, customerName); 
				 preparedStmt.setString(4, customerContact);
				 preparedStmt.setDouble(5, Double.parseDouble(totalAmount)); 
				 preparedStmt.setString(6, cardNo); 
				 preparedStmt.setString(7, cvvNo); 
				 preparedStmt.setInt(8, Integer.parseInt(orderID)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 //output = "Updated successfully"; 
				 String newOrder = readorder(); 
				 output = "{\"status\":\"success\", \"data\": \"" + newOrder + "\"}"; 

			 } 
			 catch (Exception e) 
			 { 
				 //output = "Error while updating the item."; 
				 output = "{\"status\":\"error\", \"data\": \"Error while updating the order.\"}"; 
				 System.err.println(e.getMessage()); 
				 System.out.println(e);
			 } 
			 	return output; 
			 } 
		
		
			//Delete orders
			 public String deleteorder(String orderID) 
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
				 String query = "DELETE FROM order_gui WHERE orderID=?"; 
				 PreparedStatement preparedStmt = con.prepareStatement(query); 
				 
				 // binding values
				 preparedStmt.setInt(1, Integer.parseInt(orderID)); 
				 
				 // execute the statement
				 preparedStmt.execute(); 
				 con.close(); 
				 //output = "Deleted successfully"; 
				 String newOrder = readorder(); output = "{\"status\":\"success\", \"data\": \"" + newOrder + "\"}";

			 } 
			 catch (Exception e) 
			 { 
				 //output = "Error while deleting the order."; 
				 output = "{\"status\":\"error\", \"data\": \"Error while deleting the order.\"}"; 
				 System.err.println(e.getMessage()); 
				 System.out.println(e);
			 } 
			 return output;
			 }
}
