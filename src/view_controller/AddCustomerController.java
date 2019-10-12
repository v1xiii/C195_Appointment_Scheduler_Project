package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.DBController;
import model.ThrownExceptions;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    public Button button_cancel;
    public TextField input_id;

    @FXML private TextField input_name;
    @FXML private TextField input_address1;
    @FXML private TextField input_address2;
    @FXML private TextField input_city;
    @FXML private TextField input_country;
    @FXML private TextField input_zip;
    @FXML private TextField input_phone;

    public void initialize(URL url, ResourceBundle rb) {}

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveButtonHandler (ActionEvent event) throws SQLException, ThrownExceptions.InvalidFieldsException {
        String customerName = input_name.getText();
        String address1 = input_address1.getText();
        String address2 = input_address2.getText();
        String city = input_city.getText();
        String country = input_country.getText();
        String postalCode = input_zip.getText();
        String phone = input_phone.getText();

        String error = Customer.hasValidFields(customerName, address1, city, country, postalCode, phone);

        if (error.length() == 0) {
            Customer customer = new Customer();
            customer.setCustomerName(customerName);
            customer.setAddress1(address1);
            customer.setAddress2(address2);
            customer.setCity(city);
            customer.setCountry(country);
            customer.setPostalCode(postalCode);
            customer.setPhone(phone);
            customer.setActive(true);

            DBController.addCustomer(customer);
        } else {
            throw new ThrownExceptions.InvalidFieldsException(error);
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}