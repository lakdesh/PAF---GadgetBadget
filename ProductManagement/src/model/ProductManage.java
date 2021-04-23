package model;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ProductManage {
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
	
	public String createProduct(String productCode,String productName, String productPrice, String productDesc) {
		String output="";
		
		try {
			Connection con=connect();
			
			if(con==null)
			{
				return "Error";
			}
			//LocalDate date= LocalDate.now();
			//LocalTime time= LocalTime.now();
			String  query= "insert into products('productCode','productName','productPrice','productDesc')"
							+
							" values(?,?,?,?) ";
			PreparedStatement ps=con.prepareStatement(query);
			
			ps.setString(1, productCode);
			ps.setString(2, productName);
			ps.setString(3, productPrice);
			ps.setString(4, productDesc);
			
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
	
	public String readProducts() {
		String output="";
		
		try {
			Connection con=connect();
			
			if(con==null)
			{
				return "Error";
			}
			output="<table><tr><th>Product ID</th><th>Product Code</th><th>Product Name</th><th>Product Price</th><th>Product Description</th><th>Update</th><th>Remove</th></tr>";
			String query="select * from products";
			Statement st= con.createStatement();
			ResultSet rs= st.executeQuery(query);
			
			while(rs.next())
			{
				String Product_id= Integer.toString(rs.getInt("id"));
				String Product_code= rs.getString("productCode");
				String Product_name = rs.getString("productName");
				String Product_price = rs.getString("productPrice");// How to Get Date as A String - Doubt
				String Product_description = rs.getString("productDesc");// How to Get Date as A String - Doubt
				
				output +="<tr><td>"+Product_id+"</td>";
				output +="<tr><td>"+Product_code+"</td>";
				output +="<tr><td>"+Product_name+"</td>";
				output +="<tr><td>"+Product_price+"</td>";
				output +="<tr><td>"+Product_description+"</td>";
				
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
	
	
	public String updatePost(String ID, String productCode,String productName, String productPrice, String productDesc) 
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
	 String query = "UPDATE products SET productCode=?,productName=?,productPrice=?,productDesc=? WHERE id=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setString(1, productCode); 
	 preparedStmt.setString(2, productName); 
	 preparedStmt.setString(3, productPrice); 
	 preparedStmt.setString(4, productDesc); 
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
	
	
	
	public String deleteProduct(String ID) 
	 { 
	 String output = ""; 
	 try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 {return "Error while connecting to the database for deleting."; } 
	 // create a prepared statement
	 String query = "delete from products where id=?"; 
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
