//LoginController.java
package Controller;

import java.util.Vector;

import Model.Session;
import Model.Users;
import Util.Connect;
import Util.ShowError;

public class LoginController {
	private Connect connect = Connect.getInstance();
	private ShowError se = new ShowError();
	private Vector<Users> users = new Vector<>();
	
	public LoginController() {
		
	}

	public void getData() {
		users.removeAllElements();
		String query = "SELECT * FROM users";
		connect.rs = connect.execQuery(query);
		
		try {
			while (connect.rs.next()) {
				String userId = connect.rs.getString("UserID");
				String email = connect.rs.getString("Email");
				String userName = connect.rs.getString("Username");
				String password = connect.rs.getString("Password");
				String dob = connect.rs.getString("DOB");
				String role = connect.rs.getString("Role");
				
				users.add(new Users(userId, email, userName, password, dob, role));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean validateLogin(String email, String password) {
		boolean isValidAccount = false;
		Users loggedInUser = null;
		getData();
		
		for (Users user : users) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				isValidAccount = true;
				loggedInUser = user;
                break;
            }
		}
		
		if (email.isEmpty() || password.isEmpty()) {
			se.showError("Please fill all the fields!");
			return false;
		} else if (!email.contains("@")) {
			se.showError("Please input an valid Email!");
			return false;
		} else if (!isValidAccount) {
			se.showError("Invalid Email or Password!");
			return false;
		}
		
		Session.setCurrentUser(loggedInUser);
		
		return true;
	}
}
