//CartView.java
package View;

import java.util.Vector;

import Controller.CartController;
import Model.Carts;
import Model.Session;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import Util.Connect;
import Util.ShowError;

public class CartView {
	private Scene scene;
	private VBox userPane;
	private Label greetLbl;
	private MenuBar menuBar;
	private Menu actionMenu;
	private MenuItem homeItem;
	private MenuItem cartItem;
	private MenuItem logoutItem;
	private TableView<Carts> cartsTable;
	private Vector<Carts> carts = new Vector<>();
	private Spinner<Integer> quantity;
	private HBox buttonList;
    private Button removeBtn, checkoutBtn, updateBtn;
    
    private Connect connect = Connect.getInstance();
    private ShowError se = new ShowError();
    private CartController cc = new CartController();

	public CartView(Stage stage, Users currUser) {
		userPane = new VBox();
		buttonList = new HBox();
		
		greetLbl = new Label(String.format("%s's Cart", currUser.getUserName()));
		greetLbl.setFont(new Font("System Bold", 30));
		
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
        
        cartsTable = new TableView<>();
        TableColumn<Carts, String> idColumn = new TableColumn("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<Carts, String>("productID"));
		
		TableColumn<Carts, String> nameColumn = new TableColumn("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<Carts, String>("productName"));
		
		TableColumn<Carts, String> genreColumn = new TableColumn("Genre");
		genreColumn.setCellValueFactory(new PropertyValueFactory<Carts, String>("productGenre"));
		
		TableColumn<Carts, Integer> priceColumn = new TableColumn("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<Carts, Integer>("productPrice"));
		
		TableColumn<Carts, Integer> quantityColumn = new TableColumn("Quantity");
		quantityColumn.setCellValueFactory(new PropertyValueFactory<Carts, Integer>("quantity"));
		
		TableColumn<Carts, Integer> totalColumn = new TableColumn("Total");
		totalColumn.setCellValueFactory(new PropertyValueFactory<Carts, Integer>("productTotalPrice"));
		
		idColumn.setMinWidth(80);
		nameColumn.setMinWidth(164);
		genreColumn.setMinWidth(164);
		priceColumn.setMinWidth(120);
		quantityColumn.setMinWidth(70);
		totalColumn.setMinWidth(150);
		
		cartsTable.getColumns().addAll(idColumn, nameColumn, genreColumn, priceColumn, quantityColumn, totalColumn);
		
		Carts selectedCarts = cartsTable.getSelectionModel().getSelectedItem();
		
		quantity = new Spinner<>();
		quantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0));

		cartsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue != null) {
		        String queryStock = "SELECT Stock FROM products WHERE ProductID = '" + newValue.getProductID() + "'";
		        connect.rs = connect.execQuery(queryStock);

		        try {
		            if (connect.rs.next()) {
		                int stock = connect.rs.getInt("Stock");
		                
		                quantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
		                    0,
		                    stock,
		                    newValue.getQuantity()
		                ));
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    } else {
		        quantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0, 0));
		    }
		});


	    
	    removeBtn = new Button("Remove");
	    checkoutBtn = new Button("Checkout");
	    updateBtn = new Button("Update");
	    
	    removeBtn.setOnAction(event -> {
	    	Carts selectedCartss = cartsTable.getSelectionModel().getSelectedItem();
	    	
	    	if (selectedCartss == null) {
	    		se.showError("You Must Select 1 Book !!");
	    		return;
	    	} else {
	    		se.showConfirmationAndExecute("Are you sure you want to delete " + selectedCartss.getProductName() + "?", () -> {
	    			cc.deleteBooks(selectedCartss.getUserID(), selectedCartss.getProductID());
	    		});
	    		refreshTable(currUser);
	    		return;
	    	}
	    });
	    
	    checkoutBtn.setOnAction(event -> {
	    	Carts selectedCartss = cartsTable.getSelectionModel().getSelectedItem();
	    	
	    	if (selectedCartss == null) {
	    		se.showError("You Must Select 1 Book !!");
	    		return;
	    	} else {
	    		se.showConfirmationAndExecute("Are you sure you want to checkout " + selectedCartss.getProductName() + " " + selectedCartss.getQuantity() + "x ?", () -> {
	    			cc.addData(selectedCartss.getUserID(), selectedCartss.getProductID(), selectedCartss.getQuantity());
	    		});
	    		refreshTable(currUser);
	    		return;
	    	}
	    });
	    
	    updateBtn.setOnAction(event -> {
	    	Carts selectedCartss = cartsTable.getSelectionModel().getSelectedItem();
	    	
	    	if (selectedCartss == null) {
	    		se.showError("You Must Select 1 Book !!");
	    		return;
	    	} else {
	    		if (quantity.getValue() > 0) {
	    			se.showConfirmationAndExecute("Are you sure you want to update " + selectedCartss.getProductName() + " to " + quantity.getValue() + "x ?", () -> {
		    			cc.updateData(selectedCartss.getUserID(), selectedCartss.getProductID(), quantity.getValue());
		    		});
	    		}
	    		refreshTable(currUser);
	    		return;
	    	}
	    });
		
	    buttonList.getChildren().addAll(removeBtn, checkoutBtn, updateBtn);
	    userPane.setMargin(buttonList, new Insets(10, 0, 0, 0));
	    
		userPane.getChildren().addAll(menuBar, greetLbl, cartsTable, quantity, buttonList);
		refreshTable(currUser);
		
		StackPane sp = new StackPane();
		
		sp.getChildren().add(userPane);
		
		scene = new Scene(sp, 750, 530);
	}
	
	private void refreshTable(Users user) {
		carts = cc.getDataCarts(user.getUserId());
		ObservableList<Carts> obs = FXCollections.observableArrayList(carts);
		cartsTable.setItems(obs);
	}
	
	public Scene getScene() {
        return scene;
    }

}
