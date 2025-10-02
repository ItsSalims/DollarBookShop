//Connect.java
package Util;

import java.sql.*;

public class Connect {
	
	private final String USER = "root";
	private final String PASS = "";
	private static final String CONNECTION = "jdbc:mysql://localhost:3306/dollarbookshop";
	
	private Connection conn;
	private Statement st;
	public ResultSet rs;
	public ResultSetMetaData rsm;
	
	private static Connect instance;
	
	//Singleton
	public static Connect getInstance() {
		if (instance == null) instance = new Connect();
		
		return instance;
	}
	
	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(CONNECTION, USER, PASS);
			st = conn.createStatement();
			
			if (conn != null) {
                System.out.println("Koneksi ke database berhasil.");
            } else {
                System.out.println("Gagal terhubung ke database.");
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet execQuery(String query) {
		try {
			rs = st.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public void execUpdate(String query) {
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public PreparedStatement prepareStatement(String query) {
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}

}
