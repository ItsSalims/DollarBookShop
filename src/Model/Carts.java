//Carts.java
package Model;

public class Carts {

	private String userID;
	private String productID;
	private Integer quantity;
	private String productName;
	private String productGenre;
	private Integer productPrice;
	private Integer productTotalPrice;
	
	
	public Carts(String userID, String productID, Integer quantity, String productName, String productGenre, Integer productPrice) {
		super();
		this.userID = userID;
		this.productID = productID;
		this.quantity = quantity;
		this.productName = productName;
		this.productGenre = productGenre;
		this.productPrice = productPrice;
		this.productTotalPrice = productPrice * quantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductGenre() {
		return productGenre;
	}

	public void setProductGenre(String productGenre) {
		this.productGenre = productGenre;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductTotalPrice() {
		return productTotalPrice;
	}

	public void setProductTotalPrice(Integer productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
