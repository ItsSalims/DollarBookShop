//AdminController.java
package Controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import Model.Products;
import Util.Connect;
import Util.ShowError;

public class AdminController {
	Connect connect = Connect.getInstance();
	ShowError se = new ShowError();

	public AdminController() {
		System.out.println("Admin Controller Connected!");
	}
	
	public boolean isNumeric(String str) {
	    if (str == null || str.trim().isEmpty()) {
	        return false;
	    }
	    
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	public void deleteBooks(String productID) {
	    try {
	        String deleteCart = "DELETE FROM carts WHERE ProductID = ?";
	        PreparedStatement ps = connect.prepareStatement(deleteCart);
	        
	        ps.setString(1, productID);
	        ps.executeUpdate();
	        
	        String deleteProduct = "DELETE FROM products WHERE ProductID = ?";
	        ps = connect.prepareStatement(deleteProduct);
	        
	        ps.setString(1, productID);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	}
	
	public void updateData(String productId, String productName, String productGenre, Integer productStock, Integer productPrice) {
		String queryCarts = "UPDATE products " +
				"SET Name = ?, " +
			    "Genre = ?, " +
			    "Stock = ?, " +
			    "Price = ? " +
			"WHERE ProductID = ?";

		PreparedStatement ps = connect.prepareStatement(queryCarts);
		
		try {
			ps.setString(1, productName);
			ps.setString(2, productGenre);
			ps.setInt(3, productStock);
			ps.setInt(4, productPrice);
			ps.setString(5, productId);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addData(String nameField, String genreField, Integer stockField, Integer priceField) {
		String queryGet = "SELECT ProductID FROM products ORDER BY ProductID DESC LIMIT 1";
	    connect.rs = connect.execQuery(queryGet);
	    
	    try {
			if (connect.rs.next()) {
				String newID = se.generateNewID(connect.rs.getString("ProductID"), "PD");
				
				String query = "INSERT INTO products VALUES (?, ?, ?, ?, ?)";
	    		
	            PreparedStatement ps = connect.prepareStatement(query);
	            
	            ps.setString(1, newID);
	            ps.setString(2, nameField);
	            ps.setString(3, genreField);
	            ps.setInt(4, stockField);
	            ps.setInt(5, priceField);
	            
	            ps.executeUpdate();
			} else {
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
	}
	
	public Vector<Products> getData() {
		Vector<Products> productss = new Vector<Products>();
		
		productss.removeAllElements();
		String query = "SELECT * FROM products";
		connect.rs = connect.execQuery(query);
		
		try {
			while(connect.rs.next()) {
				String productId = connect.rs.getString("ProductID");
				String name = connect.rs.getString("Name");
				String genre = connect.rs.getString("Genre");
				Integer stock = connect.rs.getInt("Stock");
				Integer price = connect.rs.getInt("Price");
				
				productss.add(new Products(productId, name, genre, stock, price));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return productss;
	}

}
