//RegisterView.java
package View;

import java.time.LocalDate;

import Controller.RegisterController;
import Util.Connect;
import Util.ShowError;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RegisterView {
	private Scene scene;
	private GridPane registerForm;
	private BorderPane registerPane;
	private Label registerLbl, emailLbl, usernameLbl, passwordLbl, cfPasswordLbl, dobLbl;
	private TextField emailTf, usernameTf;
	private PasswordField passTf, cfPassTf;
	private DatePicker datePicker;
	private Button registerBtn;
	private Hyperlink loginLink;
	private ShowError se = new ShowError();
	private RegisterController rc = new RegisterController();
	
	public RegisterView(Stage stage) {
		registerForm = new GridPane();
		registerForm.setAlignment(Pos.CENTER);
		
		registerLbl = new Label("Register");
		registerLbl.setFont(new Font("System Bold", 36));
		
		emailLbl = new Label("Email");
		emailLbl.setFont(new Font("System Bold", 18));
		
		usernameLbl = new Label("Username");
		usernameLbl.setFont(new Font("System Bold", 18));
		
		passwordLbl = new Label("Password");
		passwordLbl.setFont(new Font("System Bold", 18));
		
		cfPasswordLbl = new Label("Confirm Password");
		cfPasswordLbl.setFont(new Font("System Bold", 18));
		
		dobLbl = new Label("Date Of Birth");
		dobLbl.setFont(new Font("System Bold", 18));
		
		emailTf = new TextField();
		emailTf.setPromptText("Email Address");
		
		usernameTf = new TextField();
		usernameTf.setPromptText("Username");
		
		passTf = new PasswordField();
		
		cfPassTf = new PasswordField();
		
		datePicker = new DatePicker();
		
		registerBtn = new Button("Sign Up");
		registerBtn.setStyle("-fx-background-color: #00cccc; -fx-text-fill: white;");
		registerBtn.setMinHeight(35);
		registerBtn.setMinWidth(225);
		registerBtn.setOnMouseEntered(event -> {
			registerBtn.setStyle("-fx-background-color: #009999; -fx-text-fill: white;");
			registerBtn.setCursor(Cursor.HAND);
		});
		registerBtn.setOnMouseExited(event -> {
			registerBtn.setStyle("-fx-background-color: #00cccc; -fx-text-fill: white;");
			registerBtn.setCursor(Cursor.DEFAULT);
		});
		
		loginLink = new Hyperlink("Already have an account? Login here!");
		loginLink.setFont(new Font("System Bold", 12));
		
		registerForm.add(registerLbl, 1, 0);
		registerForm.setMargin(registerLbl, new Insets(0, 0, 0, 35));
		
		registerForm.add(emailLbl, 1, 1);
		registerForm.add(emailTf, 1, 2);
		emailTf.setMinHeight(35);
		
		registerForm.add(usernameLbl, 1, 3);
		registerForm.add(usernameTf, 1, 4);
		usernameTf.setMinHeight(35);
		
		registerForm.add(passwordLbl, 1, 5);
		registerForm.add(passTf, 1, 6);
		passTf.setMinHeight(35);
		passTf.setPromptText("Password");
		
		registerForm.add(cfPasswordLbl, 1, 7);
		registerForm.add(cfPassTf, 1, 8);
		cfPassTf.setMinHeight(35);
		cfPassTf.setPromptText("Confirm Password");
		
		registerForm.add(dobLbl, 1, 9);
		registerForm.add(datePicker, 1, 10);
		
		registerForm.add(registerBtn, 1, 11);
		registerForm.add(loginLink, 1, 12);
		registerForm.setMargin(registerBtn, new Insets(10, 0, 0, 0));
		registerForm.setMargin(loginLink, new Insets(0, 0, 0, 3));
		
		
		registerPane = new BorderPane();
		registerPane.setCenter(registerForm);
		
		registerBtn.setOnAction(event -> {
            String email = emailTf.getText().trim();
            String username = usernameTf.getText().trim();
            String password = passTf.getText().trim();
            String cfPassword = cfPassTf.getText().trim();
            LocalDate dob = datePicker.getValue();
            
            if (dob == null) {
                se.showError("Date of Birth is required!");
                return;
            }
            
            System.out.println(email);
            if (rc.validateRegister(email, username, password, cfPassword, dob.toString())) {
            	rc.addData(email, username, password, dob.toString());
            	emailTf.clear();
            	usernameTf.clear();
            	passTf.clear();
            	cfPassTf.clear();
            	
            	se.showInfoAndExecute("Registration successful!", () -> {
            		LoginView loginLayout = new LoginView(stage);
                    stage.setScene(loginLayout.getScene());
                    stage.setTitle("Login Page");
                    se.centerWindow(stage);
                });
            }
        });
		
		loginLink.setOnAction(event -> {
			LoginView loginLayout = new LoginView(stage);
            stage.setScene(loginLayout.getScene());
            stage.setTitle("Login Page");
            se.centerWindow(stage);
        });
		
		scene = new Scene(registerPane, 400, 650);
	}
	
	public Scene getScene() {
        return scene;
    }
}
