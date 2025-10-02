//LoginView.java
package View;

import Controller.LoginController;
import Model.Session;
import Util.ShowError;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginView {
	private Scene scene;
	private GridPane loginForm;
	private BorderPane loginBp;
	private Label loginLbl, emailLbl, passwordLbl;
	private TextField emailTf;
	private PasswordField passTf;
	private Button signInBtn;
	private Hyperlink linkRegister;
	
	private ShowError se = new ShowError();
	private LoginController lc = new LoginController();

	public LoginView(Stage stage) {
		loginForm = new GridPane();
		loginForm.setAlignment(Pos.CENTER);
		
		loginLbl = new Label("Login");
		loginLbl.setFont(new Font("System Bold", 36));
		loginForm.setMargin(loginLbl, new Insets(0, 0, 0, 50));
		
		emailLbl = new Label("Email");
		emailLbl.setFont(new Font("System Bold", 18));
		
		passwordLbl = new Label("Password");
		passwordLbl.setFont(new Font("System Bold", 18));
		
		emailTf = new TextField();
		emailTf.setPromptText("Email Address");
		emailTf.setMinHeight(35);
		
		passTf = new PasswordField();
		passTf.setPromptText("Password");
		passTf.setMinHeight(35);
		
		signInBtn = new Button("Sign In");
		signInBtn.setStyle("-fx-background-color: #00cccc; -fx-text-fill: white;");
		signInBtn.setMinHeight(35);
		signInBtn.setMinWidth(225);
		signInBtn.setOnMouseEntered(event -> {
		    signInBtn.setStyle("-fx-background-color: #009999; -fx-text-fill: white;");
		    signInBtn.setCursor(Cursor.HAND);
		});
		signInBtn.setOnMouseExited(event -> {
		    signInBtn.setStyle("-fx-background-color: #00cccc; -fx-text-fill: white;");
		    signInBtn.setCursor(Cursor.DEFAULT);
		});
		
        linkRegister = new Hyperlink("Don't have an account? Register here!");
        linkRegister.setFont(new Font("System Bold", 12));
        
        loginForm.add(loginLbl, 1, 0);
        loginForm.add(emailLbl, 1, 1);
        loginForm.add(emailTf, 1, 2);
        loginForm.add(passwordLbl, 1, 3);
        loginForm.add(passTf, 1, 4);
        loginForm.add(signInBtn, 1, 5);
        loginForm.add(linkRegister, 1, 6);
        
        loginForm.setMargin(signInBtn, new Insets(10, 0, 0, 0));
		
        loginBp = new BorderPane();
        loginBp.setCenter(loginForm);
        
        signInBtn.setOnAction(event -> {
            String email = emailTf.getText().trim();
            String password = passTf.getText().trim();
            if (lc.validateLogin(email, password)) {
                if (Session.getCurrentUser().getRole().equalsIgnoreCase("Admin")) {
                	AdminView adminLayout = new AdminView(stage, Session.getCurrentUser());
                	stage.setScene(adminLayout.getScene());
                	stage.setTitle("Admin Page");
                	se.centerWindow(stage);
                } else {
					UserView userView = new UserView(stage, Session.getCurrentUser());
					stage.setScene(userView.getScene());
					stage.setTitle("Home Page");
					se.centerWindow(stage);
                }
            } else {
                se.showError("Invalid credentials!");
            }
        });
        
        linkRegister.setOnAction(event -> {
            RegisterView registerLayout = new RegisterView(stage);
            stage.setScene(registerLayout.getScene());
            stage.setTitle("Register Page");
            se.centerWindow(stage);
        });
        
        scene = new Scene(loginBp, 600, 400);
	}
	
	public Scene getScene() {
        return scene;
    }

}
