//AdminView.java
package View;

import java.util.Vector;

import Controller.AdminController;
import Model.Products;
import Model.Session;
import Model.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import Util.ShowError;

public class AdminView {
	private Scene scene;
	private VBox userPane, inputSec;
	private HBox buttonList;
	private Label greetLbl, nameLbl, genreLbl, stockLbl, priceLbl;
	private TextField nameTf, genreTf, priceTf;
	private Spinner<Integer> stockSpin;
	private Button deleteBtn, addBtn, updateBtn;
	private MenuBar menuBar;
	private Menu actionMenu;
	private MenuItem logoutItem;
	private TableView<Products> products;
	private Vector<Products> productss = new Vector<Products>();
	private ShowError se = new ShowError();
	private AdminController ac;

	public AdminView(Stage stage, Users currUser) {
		ac = new AdminController();
		userPane = new VBox();
		inputSec = new VBox();
		
		greetLbl = new Label("Welcome Back, Admin");
		greetLbl.setFont(new Font("System Bold", 30)); 
		
		menuBar = new MenuBar();
		logoutItem = new MenuItem("Logout");
		
		deleteBtn = new Button("Delete");
		addBtn = new Button("Add");
		updateBtn = new Button("Update");
		buttonList = new HBox(25);
		nameLbl = new Label("Name");
		genreLbl = new Label("Genre");
		stockLbl = new Label("Stock");
		priceLbl = new Label("Price");
		
		nameTf = new TextField();
		nameTf.setPromptText("Name");
		
		genreTf = new TextField();
		genreTf.setPromptText("Genre");
		
		stockSpin = new Spinner<>();
		stockSpin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20000, 0));
		
		priceTf = new TextField();
		priceTf.setPromptText("Price");
		
		priceTf.addEventFilter(KeyEvent.KEY_TYPED, event -> {
		    char c = event.getCharacter().charAt(0);
		    if (!(c >= '0' && c <= '9')) {
		        event.consume();
		    }
		});
		
		priceTf.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue != null && !ac.isNumeric(newValue)) {
		        priceTf.setText(oldValue);
		    }
		});
		
		nameTf.setMaxWidth(350);
		genreTf.setMaxWidth(350);
		priceTf.setMaxWidth(350);
		nameTf.setMinWidth(350);
		genreTf.setMinWidth(350);
		priceTf.setMinWidth(350);
		
		deleteBtn.setMinWidth(100);
		addBtn.setMinWidth(100);
		updateBtn.setMinWidth(100);
		
		buttonList.getChildren().addAll(deleteBtn, addBtn, updateBtn);
		actionMenu = new Menu("Action");
		
		actionMenu.getItems().addAll(logoutItem);
        menuBar.getMenus().add(actionMenu);
        
        logoutItem.setOnAction(event -> {
            Session.clearSession();

            LoginView loginLayout = new LoginView(stage);
            stage.setScene(loginLayout.getScene());
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
		
		products.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue != null) {
		    	stockSpin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
	                    0,
	                    20000,
	                    newValue.getStock()
		    	));
		    	nameTf.setText(newValue.getName());
		    	genreTf.setText(newValue.getGenre());
		    	priceTf.setText(String.format("%d", newValue.getPrice()));
		    } else {
		    	stockSpin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20000, 0));
		    }
		});
		
		deleteBtn.setOnAction(event -> {
			Products selectedProductss = products.getSelectionModel().getSelectedItem();
	    	
	    	if (selectedProductss == null) {
	    		se.showError("You Must Select 1 Book !!");
	    		return;
	    	} else {
	    		se.showConfirmationAndExecute("Are you sure you want to delete " + selectedProductss.getName() + "?", () -> {
	    			ac.deleteBooks(selectedProductss.getProductId());
	    		});
	    		refreshTable();
	    		
	    		nameTf.clear();
		    	genreTf.clear();
		    	priceTf.setText("0");
		    	stockSpin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20000, 0));
	    		return;
	    	}
	    });
		
		updateBtn.setOnAction(e -> {
			Products selectedProductss = products.getSelectionModel().getSelectedItem();
			
			if (selectedProductss == null) {
				se.showError("You Must Select 1 Book !!");
	    		return;
			} else {
				se.showConfirmationAndExecute("Are you sure you want to update " + selectedProductss.getName() + "?", () -> {
	    			ac.updateData(selectedProductss.getProductId(), nameTf.getText(), genreTf.getText(), stockSpin.getValue(), Integer.parseInt(priceTf.getText()));
	    		});
	    		refreshTable();
	    		
	    		nameTf.clear();
		    	genreTf.clear();
		    	priceTf.setText("0");
		    	stockSpin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20000, 0));
	    		return;
			}
		});
		
		addBtn.setOnAction(e -> {
			String genreField = genreTf.getText();
			String nameField = nameTf.getText();
			
			if (genreField.isEmpty() || nameField.isEmpty() || priceTf.getText().isEmpty()) {
				se.showError("All fields must be filled!");
				return;
			}
			
			if (!ac.isNumeric(priceTf.getText())) {
				se.showError("Price must be numbers!");
				return;
			}
			
			Integer priceField = Integer.parseInt(priceTf.getText());
			Integer stockField = stockSpin.getValue();
			
			if (stockField < 1) {
				se.showError("Stock amount must higher than 0!");
				return;
			}
			
			ac.addData(nameField, genreField, stockField, priceField);
			
			refreshTable();
    		
    		nameTf.clear();
	    	genreTf.clear();
	    	priceTf.setText("0");
	    	stockSpin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20000, 0));
		});
		
		idColumn.setMinWidth(120);
		nameColumn.setMinWidth(210);
		genreColumn.setMinWidth(200);
		stockColumn.setMinWidth(80);
		priceColumn.setMinWidth(140);
		userPane.setMargin(inputSec, new Insets(0, 0, 0, 195));
		
		products.getColumns().addAll(idColumn, nameColumn, genreColumn, stockColumn, priceColumn);
		inputSec.getChildren().addAll(nameLbl, nameTf, genreLbl, genreTf, stockLbl, stockSpin, priceLbl, priceTf, buttonList);
		userPane.getChildren().addAll(menuBar, greetLbl, products, inputSec);
		refreshTable();
		
		StackPane sp = new StackPane();
		
		sp.getChildren().add(userPane);
		
		scene = new Scene(sp, 750, 670);
	}
	
	private void refreshTable() {
		productss = ac.getData();
		ObservableList<Products> obs = FXCollections.observableArrayList(productss);
		products.setItems(obs);
	}
	
	public Scene getScene() {
        return scene;
    }

}
