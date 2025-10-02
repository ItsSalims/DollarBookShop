//RegisterController.java
package Controller;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.Period;
import java.util.Vector;

import Model.Users;
import Util.Connect;
import Util.ShowError;

public class RegisterController {
	private Connect connect = Connect.getInstance();
	private ShowError se = new ShowError();
	private Vector<Users> usersList = new Vector<>();

	public RegisterController() {
		
	}
	
	public void addData(String email, String username, String password, String dob) {
		String queryGet = "SELECT UserID FROM users ORDER BY UserID DESC LIMIT 1";
	    connect.rs = connect.execQuery(queryGet);
		
	    try {
	        if (connect.rs.next()) {
	            String newID = se.generateNewID(connect.rs.getString("UserID"), "US");
	            
	            String query = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?)";
	    		
	            PreparedStatement ps = connect.prepareStatement(query);
	            
	            ps.setString(1, newID);
	            ps.setString(2, email);
	            ps.setString(3, username);
	            ps.setString(4, password);
	            ps.setString(5, dob);
	            ps.setString(6, "user");
	            
	            ps.executeUpdate();
	        } else {
	        	String query = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?)";

	        	PreparedStatement ps = connect.prepareStatement(query);
	            
	            ps.setString(1, "US001");
	            ps.setString(2, email);
	            ps.setString(3, username);
	            ps.setString(4, password);
	            ps.setString(5, dob);
	            ps.setString(6, "user");
	            
	            ps.executeUpdate();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void getData() {
		usersList.removeAllElements();
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
				
				usersList.add(new Users(userId, email, userName, password, dob, role));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean validateRegister(String email, String username, String password, String cfPassword, String dob) {
		getData();
		int uniqueUser = 1;
		int uniqueEmail = 1;
		
		for (Users user : usersList) {
			if (user.getUserName().equals(username)) {
				uniqueUser = 0;
				System.out.println(user.getUserName() + " " + username);
				break;
			}
		}
		
		for (Users user : usersList) {
			System.out.println(email);
			if (user.getEmail().equals(email)) {
				uniqueEmail = 0;
				System.out.println(user.getEmail() + " " + email);
				break;
			}
		}
		
		if (email.isEmpty() || username.isEmpty() || password.isEmpty() || cfPassword.isEmpty() || dob.isEmpty()) {
			se.showError("Please fill all the fields!");
			return false;
		} else if (username.length() <= 5) {
			se.showError("Username must be more the 5 characters!");
			return false;
		} else if (uniqueUser == 0) {
			se.showError("Username is owned!");
			return false;
		} else if (!email.contains("@")) {
			se.showError("Email is invalid!");
			return false;
		} else if (uniqueEmail == 0) {
			se.showError("Please use another email!");
			return false;
		} else if (password.length() <= 8) {
			se.showError("Password must be more than 8 charcaters!");
			return false;
		} else if (!isAlphanumeric(password)) {
			se.showError("Password must be alphanumeric!");
			return false;
		} else if (!password.equals(cfPassword)) {
			se.showError("Confirm Password must same as password!");
			return false;
		} else if (!isAgeValid(LocalDate.parse(dob))) {
			se.showError("You must be 18 years old for sign up!");
			return false;
		}
		
		return true;
	}
	
	public boolean isAlphanumeric(String password) {
	    if (password == null || password.isEmpty()) {
	        return false;
	    }
	    
	    for (char c : password.toCharArray()) {
	        if (!((c >= 'a' && c <= 'z') || 
	              (c >= 'A' && c <= 'Z') || 
	              (c >= '0' && c <= '9'))) {
	            return false;
	        }
	    }
	    return true;
	}
	
	public boolean isAgeValid(LocalDate dob) {
	    LocalDate today = LocalDate.now();

	    int age = Period.between(dob, today).getYears();

	    return age >= 18;
	}

}
