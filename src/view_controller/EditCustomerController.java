package view_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Customer;
import model.DBController;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditCustomerController implements Initializable {

    @FXML private TableView<Customer> table_customers;
    @FXML private TableColumn<Customer, Integer> table_customer_id;
    @FXML private TableColumn<Customer, String> table_customer_name;
    @FXML private TextField input_name;
    @FXML private TextField input_address;
    @FXML private TextField input_address2;
    @FXML private TextField input_city;
    @FXML private TextField input_zip;
    @FXML private TextField input_country;
    @FXML private TextField input_phone;

    public void initialize(URL url, ResourceBundle rb) {
        // populate customers table
        ObservableList <Customer> allCustomersList = FXCollections.observableArrayList();
        table_customer_id.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        table_customer_name.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        table_customers.refresh();
        try {
            table_customers.setItems(DBController.getCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelButtonHandler (ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveButtonHandler (ActionEvent event) throws IOException {
        //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //stage.close();
    }

    @FXML
    private void deleteButtonHandler (ActionEvent event) throws IOException {

    }

    @FXML
    private void tableClickHandler (MouseEvent event){ // fills the text fields for the selected customer
        Customer selectedCustomer = table_customers.getSelectionModel().getSelectedItem();
        input_name.setText(selectedCustomer.getCustomerName());
        input_address.setText(selectedCustomer.getAddress1());
        input_address2.setText(selectedCustomer.getAddress2());
        input_city.setText(selectedCustomer.getCity());
        input_zip.setText(selectedCustomer.getPostalCode());
        input_country.setText(selectedCustomer.getCountry());
        input_phone.setText(selectedCustomer.getPhone());
    }
}