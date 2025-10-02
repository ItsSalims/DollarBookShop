//CartController.java
package Controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;

import Model.Carts;
import Util.Connect;
import Util.ShowError;

public class CartController {
	private Connect connect = Connect.getInstance();
	private ShowError se = new ShowError();
	private Vector<Carts> carts = new Vector<>();

	public CartController() {
		
	}

	public void updateData(String userID, String productID, Integer quantity) {
		String queryCarts = "UPDATE carts " +
	               "SET Quantity = ? " +
	               "WHERE UserID = ? AND ProductID = ?";
		
		PreparedStatement ps = connect.prepareStatement(queryCarts);

		try {
			ps.setInt(1, quantity);
			ps.setString(2, userID);
			ps.setString(3, productID);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addData(String userID, String productID, Integer quantity) {
		String queryGet = "SELECT TransactionID FROM transaction_headers ORDER BY TransactionID DESC LIMIT 1";
	    connect.rs = connect.execQuery(queryGet);
		
	    try {
	        if (connect.rs.next()) {
	            String newID = se.generateNewID(connect.rs.getString("TransactionID"), "TR");
	            
	            String query = "INSERT INTO transaction_headers VALUES (?, ?, CURDATE())";
	            PreparedStatement ps = connect.prepareStatement(query);
	            ps.setString(1, newID);
	            ps.setString(2, userID);
	            ps.executeUpdate();
	    		
	    		String queryTD = "INSERT INTO transaction_details VALUES (?, ?, ?)";
	    		ps = connect.prepareStatement(queryTD);
	    		ps.setString(1, newID);
	            ps.setString(2, productID);
	            ps.setInt(3, quantity);
	            ps.executeUpdate();
	    		
	    		String queryStock = "UPDATE products SET Stock = Stock - ? WHERE ProductID = ?";
	    		ps = connect.prepareStatement(queryStock);
	    		ps.setInt(1,  quantity);
	    		ps.setString(2, productID);
	    		ps.executeUpdate();
	    		
	    		String queryCarts = "UPDATE carts " +
	    	               "SET Quantity = Quantity - ? " +
	    	               "WHERE UserID = ? AND ProductID = ?";

	    		ps = connect.prepareStatement(queryCarts);
	    		ps.setInt(1,  quantity);
	    		ps.setString(2, userID);
	    		ps.setString(3, productID);
	    		ps.executeUpdate();
	    		
	    		
	    		String queryDelete = "DELETE FROM carts " +
	                     "WHERE UserID = ? AND ProductID = ? AND Quantity < 1";

	    		ps = connect.prepareStatement(queryDelete);
	    		ps.setString(1, userID);
	    		ps.setString(2, productID);
	    		ps.executeUpdate();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void deleteBooks(String userID, String productID) {
	    String query = "DELETE FROM carts WHERE UserID = ? AND ProductID = ?";
	    
	    PreparedStatement ps = connect.prepareStatement(query);
	    
	    try {
			ps.setString(1, userID);
			ps.setString(2, productID);
			
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Carts getCart(String UserId, String ProductId) {
	    for (Carts cart : carts) {
	        if (cart.getProductID().equals(ProductId) && cart.getUserID().equals(UserId)) {
	            return cart;
	        }
	    }
	    return null;
	}
	
	public Vector<Carts> getDataCarts(String userIds) {
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
		
		return carts;
	}
}
