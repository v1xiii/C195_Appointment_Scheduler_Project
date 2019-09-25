package model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view_controller.LoginScreenController;
import java.sql.*;

import static java.lang.Boolean.TRUE;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class DBController{

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_NAME = "U05oua";
    private static final String DB_URL = "jdbc:mysql://52.206.157.109/" + DB_NAME;
    private static final String DB_USER = "U05oua";
    private static final String DB_PASS = "53688564939";

    public static Integer checkLogin(String username, String password) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE userName = ? AND password = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        Statement stmt = conn.createStatement();
        ResultSet rs = ps.executeQuery();

        Integer retrievedUserId = null;
        String retrievedUsername = null;
        String retrievedPassword = null;

        while (rs.next()) {
            retrievedUserId = rs.getInt(1);
            retrievedUsername = rs.getString(2);
            retrievedPassword = rs.getString(3);
        }
        rs.close();

        if (username.equals(retrievedUsername) && password.equals(retrievedPassword)){
            return retrievedUserId;
        } else {
            return -1;
        }
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

    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("" +
                "SELECT cu.customerId, cu.customerName, a.address, a.address2, ci.city, co.country, a.postalCode, a.phone, cu.active, a.addressId " +
                "FROM customer cu " +
                "LEFT JOIN address a ON cu.addressId = a.addressId " +
                "LEFT JOIN city ci ON a.cityId = ci.cityId " +
                "LEFT JOIN country co ON ci.countryId = co.countryId;");

        Statement stmt = conn.createStatement();
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Customer customer = new Customer();
            customer.setCustomerId(rs.getInt(1));
            customer.setCustomerName(rs.getString(2));
            customer.setAddress1(rs.getString(3));
            customer.setAddress2(rs.getString(4));
            customer.setCity(rs.getString(5));
            customer.setCountry(rs.getString(6));
            customer.setPostalCode(rs.getString(7));
            customer.setPhone(rs.getString(8));
            customer.setActive(rs.getBoolean(9));
            customer.setAddressId(rs.getInt(10));

            allCustomers.add(customer);
        }
        rs.close();

        return allCustomers;
    }

    public static void deleteCustomer(Customer customer) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        PreparedStatement ps = conn.prepareStatement("DELETE FROM customer WHERE customerId = ?");
        ps.setInt(1, customer.getCustomerId());
        Statement stmt = conn.createStatement();
        ps.executeUpdate();

        /*
        PreparedStatement ps2 = conn.prepareStatement("DELETE FROM address WHERE addressId = ?");
        ps2.setInt(1, customer.getAddressId());
        Statement stmt2 = conn.createStatement();
        ps2.executeUpdate();
        */
    }

    public static void updateCustomer(Customer customer) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        System.out.println("Update started...");
        System.out.println("Checking country table...");

        Integer countryId = addCountry(customer.getCountry());

        System.out.println("addCountry() returns "+countryId);
        System.out.println("Checking city table...");

        Integer cityId = addCity(customer.getCity(), countryId);

        System.out.println("addCity() returns "+cityId);
        System.out.println("Updating address table...");

        PreparedStatement ps2 = conn.prepareStatement("UPDATE address SET address = ?, address2 = ?,  cityId = ?, postalCode = ?, phone = ?, lastUpdate = NOW(), lastUpdateBy = ? WHERE addressId = ?");
        ps2.setString(1, customer.getAddress1());
        ps2.setString(2, customer.getAddress2());
        ps2.setInt(3, cityId);
        ps2.setString(4, customer.getPostalCode());
        ps2.setString(5, customer.getPhone());
        ps2.setString(6, LoginScreenController.getCurrUser());
        ps2.setInt(7, customer.getAddressId());
        Statement stmt2 = conn.createStatement();
        ps2.executeUpdate();

        System.out.println("Address table updated");
        System.out.println("Updating customer table...");

        PreparedStatement ps = conn.prepareStatement("UPDATE customer SET customerName = ?, lastUpdate = NOW(), lastUpdateBy = ? WHERE customerId = ?");
        ps.setString(1, customer.getCustomerName());
        ps.setString(2, LoginScreenController.getCurrUser());
        ps.setInt(3, customer.getCustomerId());
        Statement stmt = conn.createStatement();
        ps.executeUpdate();

        System.out.println("Customer table updated");
        System.out.println("**Customer update complete**");
    }
    public static Integer addAppointment(Appointment appointment) throws SQLException {
        Timestamp startTS = Timestamp.valueOf(appointment.getStart().toLocalDateTime());
        Timestamp endTS = Timestamp.valueOf(appointment.getEnd().toLocalDateTime());

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("" +
                "INSERT INTO appointment (" +
                    "customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy" +
                ") " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?)" // wow, there must be a better way to do these prepared statements...
        );

        ps.setInt(1, appointment.getCustomerId());
        ps.setInt(2, appointment.getUserId());
        ps.setString(3, appointment.getTitle());
        ps.setString(4, appointment.getDescription());
        ps.setString(5, appointment.getLocation());
        ps.setString(6, appointment.getContact());
        ps.setString(7, appointment.getType());
        ps.setString(8, appointment.getUrl());
        ps.setTimestamp(9, startTS);
        ps.setTimestamp(10, endTS);
        ps.setString(11, LoginScreenController.getCurrUser());
        ps.setString(12, LoginScreenController.getCurrUser());
        Statement stmt = conn.createStatement();
        ps.executeUpdate();

        return 1;
    }
}