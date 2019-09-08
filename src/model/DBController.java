package model;

import view_controller.LoginScreenController;

import java.sql.*;

public class DBController{

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_NAME = "U05oua";
    private static final String DB_URL = "jdbc:mysql://52.206.157.109/" + DB_NAME;
    private static final String DB_USER = "U05oua";
    private static final String DB_PASS = "53688564939";

    public static boolean checkLogin(String username, String password) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE userName = ? AND password = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        ps.execute();
        Statement stmt = conn.createStatement();
        ResultSet rs = ps.executeQuery();

        String retrievedUsername = null;
        String retrievedPassword = null;

        while (rs.next()) {
            retrievedUsername = rs.getString(2);
            retrievedPassword = rs.getString(3);
            System.out.println(retrievedUsername);
        }
        return username.equals(retrievedUsername) && password.equals(retrievedPassword); // interesting IDE simplification of if/else return true/false
    }

    public static void addCustomer(Customer customer) throws SQLException {
        System.out.println("Let's add " + LoginScreenController.getCurrUser());

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, 1, 1, NOW(), ?, NOW(), ?)");
        ps.setString(1, customer.getCustomerName());
        ps.setString(2, LoginScreenController.getCurrUser());
        ps.setString(3, LoginScreenController.getCurrUser());
        //ps.execute();
        Statement stmt = conn.createStatement();
        ps.executeUpdate();
    }
}