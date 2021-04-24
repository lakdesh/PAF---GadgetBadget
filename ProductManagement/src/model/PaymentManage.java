package model;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class PaymentManage {
	private Connection connect()
	{
		Connection con=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			//Provide Correct Database Details
			con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return con;
	}
	
	public String createPayments(String productCode,String paymentdate, String paymentamount, String paymentstatus) {
		String output="";
		
		try {
			Connection con=connect();
			
			if(con==null)
			{
				return "Error";
			}
			LocalDate date= LocalDate.now();
			LocalTime time= LocalTime.now();
			String  query= "insert into products('productCode','paymentdate','paymentamount','paymentstatus')"
							+
							" values(?,?,?,?) ";
			PreparedStatement ps=con.prepareStatement(query);
			
			ps.setString(1, productCode);
			ps.setString(2,paymentdate.toString());
			ps.setString(3,paymentamount.toString());
			ps.setString(4, paymentstatus);
			
			ps.execute();
			con.close();
			
			output="Insert Success";
		}
		catch (Exception e) {
			// TODO: handle exception
			output="Error while inserting the Item";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String readPayments() {
		String output="";
		
		try {
			Connection con=connect();
			
			if(con==null)
			{
				return "Error";
			}
			output="<table><tr><th>Payment ID</th><th>Product Code</th><th>Payment date</th><th>Payment amount</th><th>Payment status</th><th>Update</th><th>Remove</th></tr>";
			String query="select * from products";
			Statement st= con.createStatement();
			ResultSet rs= st.executeQuery(query);
			
			while(rs.next())
			{
				String Payment_id= Integer.toString(rs.getInt("id"));
				String Product_code= rs.getString("productCode");
				String Payment_date = rs.getString("paymentdate");
				String Payment_amount = rs.getString("paymentamount");// How to Get Date as A String - Doubt
				String Payment_status = rs.getString("paymentstatus");// How to Get Date as A String - Doubt
				
				output +="<tr><td>"+Payment_id+"</td>";
				output +="<tr><td>"+Product_code+"</td>";
				output +="<tr><td>"+Payment_date+"</td>";
				output +="<tr><td>"+Payment_amount+"</td>";
				output +="<tr><td>"+Payment_status+"</td>";
				
				output +="<td><input name=\\\"btnUpdate\\\" type=\\\"button\\\" \r\n" + 
						" value=\\\"Update\\\" class=\\\"btn btn-secondary\\\"></td>\"\r\n" + 
						" + \"<td><form method=\\\"post\\\" action=\\\"posts.jsp\\\">\"\r\n" + 
						" + \"<input name=\\\"btnRemove\\\" type=\\\"submit\\\" value=\\\"Remove\\\" \r\n" + 
						" class=\\\"btn btn-danger\\\">\"\r\n" + 
						" + \"<input name=\\\"id\\\" type=\\\"hidden\\\" value=\\\"\" + id\r\n" + 
						" + \"\\\">\" + \"</form></td></tr>";
				
			}
			con.close();
			output+="</tabel>";
		} catch (Exception e) {
			// TODO: handle exception
			output="Error while reading the Posts";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	public String updatePost(String ID, String productCode,String paymentdate, String paymentamount, String paymentstatus) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for updating."; } 
	 // create a prepared statement
	 //LocalDate date= LocalDate.now();
	 //LocalTime time= LocalTime.now();
	 String query = "UPDATE payments SET productCode=?,paymentdate=?,paymentamount=?,paymentstatus=? WHERE id=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setString(1, productCode); 
	 preparedStmt.setString(2, paymentdate); 
	 preparedStmt.setString(3, paymentamount); 
	 preparedStmt.setString(4, paymentstatus); 
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
	
	
	
	public String deletePayments(String ID) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for deleting."; } 
	 // create a prepared statement
	 String query = "delete from Payment where id=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(ID)); 
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

	
}
