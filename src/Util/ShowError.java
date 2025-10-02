//ShowError.java
package Util;

import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.stage.Stage;

public class ShowError {

	public ShowError() {
		
	}
	
	public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
	public void centerWindow(Stage stage) {
	    javafx.stage.Screen screen = javafx.stage.Screen.getPrimary();
	    javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();

	    double centerX = (bounds.getWidth() - stage.getWidth()) / 2;
	    double centerY = (bounds.getHeight() - stage.getHeight()) / 2;

	    stage.setX(centerX);
	    stage.setY(centerY);
	}
	
	public void showInfoAndExecute(String message, Runnable onOkAction) {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Information");
	    alert.setHeaderText(null);
	    alert.setContentText(message);

	    alert.showAndWait().ifPresent(response -> {
	        if (response == ButtonType.OK) {
	            onOkAction.run();
	        }
	    });
	}
	
	public void showConfirmationAndExecute(String message, Runnable onYesAction) {
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("Confirmation");
	    alert.setHeaderText(null);
	    alert.setContentText(message);

	    ButtonType buttonYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
	    ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.NO);

	    alert.getButtonTypes().setAll(buttonYes, buttonNo);

	    alert.showAndWait().ifPresent(response -> {
	        if (response == buttonYes) {
	            onYesAction.run();
	        }
	    });
	}
	
	public static String generateNewID(String lastID, String prefix) {
        int lastNumber = Integer.parseInt(lastID.substring(prefix.length()));
        
        int newNumber = lastNumber + 1;
        
        String newID = prefix + String.format("%03d", newNumber);
        
        return newID;
    }


}
