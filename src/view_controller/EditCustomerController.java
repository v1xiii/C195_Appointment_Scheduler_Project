package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.DBController;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditCustomerController implements Initializable {
    public Button button_cancel;
    public Button button_delete;
    public Button button_save;

    @FXML private TableView<Customer> table_customers;
    @FXML private TableColumn<Customer, Integer> table_customer_id;
    @FXML private TableColumn<Customer, String> table_customer_name;
    @FXML private TextField input_name;
    @FXML private TextField input_address1;
    @FXML private TextField input_address2;
    @FXML private TextField input_city;
    @FXML private TextField input_zip;
    @FXML private TextField input_country;
    @FXML private TextField input_phone;

    public void initialize(URL url, ResourceBundle rb) {
        // populate customers table
        table_customer_id.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        table_customer_name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        table_customers.refresh();
        try {
            table_customers.setItems(DBController.getCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelButtonHandler (ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void tableClickHandler(){ // fills the text fields for the selected customer
        Customer selectedCustomer = table_customers.getSelectionModel().getSelectedItem();
        input_name.setText(selectedCustomer.getCustomerName());
        input_address1.setText(selectedCustomer.getAddress1());
        input_address2.setText(selectedCustomer.getAddress2());
        input_city.setText(selectedCustomer.getCity());
        input_zip.setText(selectedCustomer.getPostalCode());
        input_country.setText(selectedCustomer.getCountry());
        input_phone.setText(selectedCustomer.getPhone());
    }

    @FXML
    private void deleteButtonHandler() throws SQLException {
        Customer customer = table_customers.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Removing customer " + customer.getCustomerId() + " - " + customer.getCustomerName());
        alert.setContentText("Are you sure you want to remove this customer?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table_customers.getItems().remove(customer);
            DBController.deleteCustomer(customer);
        }
    }

    @FXML
    private void saveButtonHandler (ActionEvent event) throws SQLException {
        Customer customer = table_customers.getSelectionModel().getSelectedItem();

        customer.setCustomerName(input_name.getText());
        customer.setAddress1(input_address1.getText());
        customer.setAddress2(input_address2.getText());
        customer.setCity(input_city.getText());
        customer.setCountry(input_country.getText());
        customer.setPostalCode(input_zip.getText());
        customer.setPhone(input_phone.getText());

        DBController.updateCustomer(customer);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}