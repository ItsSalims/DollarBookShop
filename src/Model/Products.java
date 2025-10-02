//Products.java
package Model;

public class Products {
	private String productId;
	private String name;
	private String genre;
	private Integer stock;
	private Integer price;
	
	public Products(String productId, String name, String genre, Integer stock, Integer price) {
		super();
		this.productId = productId;
		this.name = name;
		this.genre = genre;
		this.stock = stock;
		this.price = price;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
