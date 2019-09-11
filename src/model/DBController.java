package model;

import view_controller.LoginScreenController;

import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

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
        Statement stmt = conn.createStatement();
        ResultSet rs = ps.executeQuery();

        String retrievedUsername = null;
        String retrievedPassword = null;

        while (rs.next()) {
            retrievedUsername = rs.getString(2);
            retrievedPassword = rs.getString(3);
        }
        rs.close();
        return username.equals(retrievedUsername) && password.equals(retrievedPassword); // interesting IDE simplification of if/else return true/false
    }

    public static void addCustomer(Customer customer) throws SQLException {
        System.out.println("Process started...");
        System.out.println("addCountry up next... three remaining");

        Integer countryID = addCountry(customer.getCountry());
        System.out.println("addCountry() returns "+countryID);

        System.out.println("addCity up next... two remaining");

        Integer cityID = addCity(customer.getCity(), countryID);
        System.out.println("addCity() returns "+cityID);

        System.out.println("addAddress up next... one remaining");

        Integer addressID = addAddress(customer.getAddress1(), customer.getAddress2(), cityID, customer.getPostalCode(), customer.getPhone());
        System.out.println("addAddress() returns "+addressID);

        System.out.println("**Customer added**");

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, 1, NOW(), ?, NOW(), ?)");
        ps.setString(1, customer.getCustomerName());
        ps.setInt(2, addressID);
        ps.setString(3, LoginScreenController.getCurrUser());
        ps.setString(4, LoginScreenController.getCurrUser());
        Statement stmt = conn.createStatement();
        ps.executeUpdate();
    }

    private static Integer addCountry(String country) throws SQLException {
        Integer countryID = null;

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("SELECT countryId FROM country WHERE country = ?");
        ps.setString(1, country);
        Statement stmt = conn.createStatement();
        ResultSet currID = ps.executeQuery();

        if (currID.next()){ // if there is already a matching country
            countryID = currID.getInt(1);
            currID.close();
            return countryID; // return the ID
        } else { // else add a new country
            currID.close();
            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO country (country, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, NOW(), ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS);
            ps2.setString(1, country);
            ps2.setString(2, LoginScreenController.getCurrUser());
            ps2.setString(3, LoginScreenController.getCurrUser());
            Statement stmt2 = conn.createStatement();
            ps2.executeUpdate();
            ResultSet nextID = ps2.getGeneratedKeys(); // get the autoincrement value that was generated during the last query

            if(nextID.next()){
                countryID = nextID.getInt(1);
                nextID.close();
            }
        }
        return countryID; // and return that as the id
    }

    private static Integer addCity(String city, Integer countryID) throws SQLException {
        Integer cityID = null;

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("SELECT cityId FROM city WHERE city = ? AND countryId = ?");
        ps.setString(1, city);
        ps.setInt(2, countryID);
        Statement stmt = conn.createStatement();
        ResultSet currID = ps.executeQuery();

        if (currID.next()){ // if there is already a matching city
            cityID = currID.getInt(1);
            currID.close();
            return cityID; // return the ID
        } else { // else add a new city
            currID.close();
            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO city (city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, NOW(), ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS);
            ps2.setString(1, city);
            ps2.setInt(2, countryID);
            ps2.setString(3, LoginScreenController.getCurrUser());
            ps2.setString(4, LoginScreenController.getCurrUser());
            Statement stmt2 = conn.createStatement();
            ps2.executeUpdate();
            ResultSet nextID = ps2.getGeneratedKeys(); // get the autoincrement value that was generated during the last query

            if(nextID.next()){
                cityID = nextID.getInt(1);
                nextID.close();
            }
        }
        return cityID; // and return that as the id
    }

    private static Integer addAddress(String address1, String address2, Integer cityID, String postalCode, String phone) throws SQLException {
        Integer addressID = null;

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("SELECT addressId FROM address WHERE address = ? AND address2 = ? AND cityId = ? AND postalCode = ? AND phone = ?");
        ps.setString(1, address1);
        ps.setString(2, address2);
        ps.setInt(3, cityID);
        ps.setString(4, postalCode);
        ps.setString(5, phone);
        Statement stmt = conn.createStatement();
        ResultSet currID = ps.executeQuery();

        if (currID.next()){ // if there is already a matching address
            addressID = currID.getInt(1);
            currID.close();
            return addressID; // return the ID
        } else { // else add a new address
            currID.close();
            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO address (address, address2, cityID, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, NOW(), ?, NOW(), ?)", Statement.RETURN_GENERATED_KEYS);
            ps2.setString(1, address1);
            ps2.setString(2, address2);
            ps2.setInt(3, cityID);
            ps2.setString(4, postalCode);
            ps2.setString(5, phone);
            ps2.setString(6, LoginScreenController.getCurrUser());
            ps2.setString(7, LoginScreenController.getCurrUser());
            Statement stmt2 = conn.createStatement();
            ps2.executeUpdate();
            ResultSet nextID = ps2.getGeneratedKeys(); // get the autoincrement value that was generated during the last query

            if(nextID.next()){
                addressID = nextID.getInt(1);
                nextID.close();
            }
        }
        return addressID; // and return that as the id
    }
}