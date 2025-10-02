//UserController.java
package Controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import Model.Carts;
import Model.Products;
import Util.Connect;

public class UserController {
	private Connect connect = Connect.getInstance();
	private Vector<Carts> carts = new Vector<>();
	private Vector<Products> productss = new Vector<Products>();

	public UserController() {
		
	}
	
	public Carts getCart(String UserId, String ProductId) {
	    for (Carts cart : carts) {
	        if (cart.getProductID().equals(ProductId) && cart.getUserID().equals(UserId)) {
	            return cart;
	        }
	    }
	    return null;
	}
	
	public void addData(String userId, String productId, Integer quantity) {
		String query = "INSERT INTO carts VALUES (?, ?, ?)";
		
		PreparedStatement ps = connect.prepareStatement(query);
		
		try {
			ps.setString(1, userId);
			ps.setString(2, productId);
			ps.setInt(3, quantity);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateData(String userId, String productId, Integer quantity) {
		String query = "UPDATE carts SET Quantity = ? WHERE UserID = ? AND ProductID = ?";
		
		PreparedStatement ps = connect.prepareStatement(query);
		
		try {
			ps.setInt(1, quantity);
			ps.setString(2, userId);
			ps.setString(3,  productId);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public Vector<Products> getData() {
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
	
	public void getDataCarts(String userIds) {
		carts.removeAllElements();
		String query = "SELECT * FROM carts CA JOIN products PR ON CA.ProductID = PR.ProductID WHERE UserID = '" + userIds + "'";
		connect.rs = connect.execQuery(query);
		
		try {
			while(connect.rs.next()) {
				String userId = connect.rs.getString("UserID");
				String productId = connect.rs.getString("ProductID");
				Integer quantity = connect.rs.getInt("Quantity");
				String productName = connect.rs.getString("Name");
				String productGenre = connect.rs.getString("Genre");
				Integer productPrice = connect.rs.getInt("Price");
				
				carts.add(new Carts(userId, productId, quantity, productName, productGenre, productPrice));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
