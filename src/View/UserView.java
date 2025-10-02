//UserView.java
package View;

import java.util.Vector;

import Controller.UserController;
import Model.Carts;
import Model.Products;
import Model.Session;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.Window;
import Util.ShowError;

public class UserView {
	private Scene scene;
	private VBox userPane;
	private Label greetLbl;
	private Button addCartBtn;
	private MenuBar menuBar;
	private Menu actionMenu;
	private MenuItem homeItem;
    private MenuItem cartItem;
    private MenuItem logoutItem;
    private TableView<Products> products;
    private Vector<Products> productss = new Vector<Products>();
    private ShowError se = new ShowError();
    private UserController uc = new UserController();

	public UserView(Stage stage, Users currUser) {
		userPane = new VBox();
		
		greetLbl = new Label(String.format("Hello, %s", currUser.getUserName()));
		greetLbl.setFont(new Font("System Bold", 30));
		
		addCartBtn = new Button("Add to Cart");
		userPane.setMargin(addCartBtn, new Insets(0, 0, 0, 205));
		addCartBtn.setStyle("-fx-background-color: #737373; -fx-text-fill: white;");
		addCartBtn.setMinHeight(45);
		addCartBtn.setMinWidth(170);
		addCartBtn.setOnMouseEntered(event -> {
			addCartBtn.setStyle("-fx-background-color: #3F3F3F; -fx-text-fill: white;");
			addCartBtn.setCursor(Cursor.HAND);
		});
		addCartBtn.setOnMouseExited(event -> {
			addCartBtn.setStyle("-fx-background-color: #737373; -fx-text-fill: white;");
			addCartBtn.setCursor(Cursor.DEFAULT);
		});
		
		menuBar = new MenuBar();
		actionMenu = new Menu("Action");
		homeItem = new MenuItem("Home");
		cartItem = new MenuItem("Cart");
		logoutItem = new MenuItem("Logout");
		
		actionMenu.getItems().addAll(homeItem, cartItem, new SeparatorMenuItem(), logoutItem);
        menuBar.getMenus().add(actionMenu);
        
        homeItem.setOnAction(event -> {
        	UserView userView = new UserView(stage, currUser);
			stage.setScene(userView.getScene());
			stage.setTitle("Home Page");
			se.centerWindow(stage);
        });
        
        cartItem.setOnAction(event -> {
        	CartView cartView = new CartView(stage, currUser);
			stage.setScene(cartView.getScene());
			stage.setTitle("Cart Page");
			se.centerWindow(stage);
        });
        
        logoutItem.setOnAction(event -> {
            Session.clearSession();

            LoginView loginView = new LoginView(stage);
            stage.setScene(loginView.getScene());
            stage.setTitle("Login Page");
            se.centerWindow(stage);
        });
        
        products = new TableView<>();
        TableColumn<Products, String> idColumn = new TableColumn("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<Products, String>("productId"));
		
		TableColumn<Products, String> nameColumn = new TableColumn("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Products, String>("name"));
		
		TableColumn<Products, String> genreColumn = new TableColumn("Genre");
		genreColumn.setCellValueFactory(new PropertyValueFactory<Products, String>("genre"));
		
		TableColumn<Products, Integer> stockColumn = new TableColumn("Stock");
		stockColumn.setCellValueFactory(new PropertyValueFactory<Products, Integer>("stock"));
		
		TableColumn<Products, Integer> priceColumn = new TableColumn("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<Products, Integer>("price"));
		
		idColumn.setMinWidth(120);
		nameColumn.setMinWidth(140);
		genreColumn.setMinWidth(140);
		stockColumn.setMinWidth(80);
		priceColumn.setMinWidth(120);
		
		products.getColumns().addAll(idColumn, nameColumn, genreColumn, stockColumn, priceColumn);
		
		userPane.getChildren().addAll(menuBar, greetLbl, products, addCartBtn);
		refreshTable();
		
		StackPane sp = new StackPane();
		
		sp.getChildren().add(userPane);
		
		scene = new Scene(sp, 600, 510);
		
		addCartBtn.setOnAction(event -> {
		    Products selectedProduct = products.getSelectionModel().getSelectedItem();

		    if (selectedProduct == null) {
		        Alert alert = new Alert(Alert.AlertType.WARNING);
		        alert.setContentText("Please select a product to add to cart!");
		        alert.show();
		        return;
		    }
		    
		    uc.getDataCarts(currUser.getUserId());
		    Carts selectedCart = uc.getCart(currUser.getUserId(), selectedProduct.getProductId());
		    
		    Window popupWindow = new Window("Add to Cart");
		    popupWindow.setPrefSize(300, 200);

		    Label productName = new Label("Name: " + selectedProduct.getName());
		    Label genreName = new Label("Genre: " + selectedProduct.getGenre());
		    Label priceName = new Label("Price: " + selectedProduct.getPrice());
		    Spinner<Integer> quantity = new Spinner<>();
		    quantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, selectedProduct.getStock(), selectedCart != null ? selectedCart.getQuantity() : 0));
		    Button addBtn;
		    if (selectedCart == null) {
		        addBtn = new Button("Add");
		    } else {
		        addBtn = new Button("Update");
		    }
		    
		    productName.setFont(new Font("System Bold", 30));

		    VBox popupLayout = new VBox(10);
		    popupLayout.setPadding(new Insets(5));
		    popupLayout.getChildren().addAll(productName, genreName, priceName, quantity, addBtn);

		    popupWindow.getContentPane().getChildren().add(popupLayout);

		    popupWindow.setMaxSize(400, 300);
		    popupWindow.setMinSize(400, 300);

		    Pane rootPane = (Pane) scene.getRoot();
		    rootPane.getChildren().add(popupWindow);

		    addBtn.setOnAction(e -> {
		        int selectedQuantity = quantity.getValue();

		        if (selectedQuantity <= 0) {
		        	se.showError("Quantity must be more than 0 !");
		        	return;
		        }
		        
		        if (selectedCart != null) {
		            uc.updateData(currUser.getUserId(), selectedProduct.getProductId(), selectedQuantity);
		            se.showInfoAndExecute("Product Updated Successfully!", () -> {
		                rootPane.getChildren().remove(popupWindow);
		            });
		            return;
		        }
		        
		        uc.addData(currUser.getUserId(), selectedProduct.getProductId(), selectedQuantity);
	            se.showInfoAndExecute("Product Added Successfully!", () -> {
	                rootPane.getChildren().remove(popupWindow);
	            });
		    });
		});
		
	}
	
	private void refreshTable() {
		productss = uc.getData();
		ObservableList<Products> obs = FXCollections.observableArrayList(productss);
		products.setItems(obs);
	}
	
	public Scene getScene() {
        return scene;
    }

}
