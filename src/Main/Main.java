//Main.java
package Main;

import View.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage pStage) throws Exception {
		LoginView loginLayout = new LoginView(pStage);
		pStage.setScene(loginLayout.getScene());
		pStage.setTitle("Login Page");
		pStage.show();
	}
}
