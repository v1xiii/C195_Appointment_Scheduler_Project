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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Customer;
import model.DBController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class AddAppointmentController implements Initializable {

    @FXML private TableView<Customer> table_customers;
    @FXML private TableColumn<Customer, Integer> table_customer_id;
    @FXML private TableColumn<Customer, String> table_customer_name;

    @FXML private TextField input_title;
    @FXML private TextField input_description;
    @FXML private TextField input_location;
    @FXML private TextField input_contact;
    @FXML private TextField input_type;
    @FXML private TextField input_url;
    @FXML private DatePicker datepicker_date;
    @FXML private ChoiceBox dropdown_time_from;
    @FXML private ChoiceBox dropdown_time_to;

    public void initialize(URL url, ResourceBundle rb) {
        // populate customers table
        ObservableList<Customer> allCustomersList = FXCollections.observableArrayList();
        table_customer_id.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        table_customer_name.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        table_customers.refresh();
        try {
            table_customers.setItems(DBController.getCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // disable dates in datepicker prior to today and also weekends
        datepicker_date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                setDisable(empty || date.compareTo(today) < 0 || dayOfWeek == SATURDAY || dayOfWeek == SUNDAY);
            }
        });
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveButtonHandler (ActionEvent event) throws IOException {
        Customer selectedCustomer = table_customers.getSelectionModel().getSelectedItem();

        Integer customerId = selectedCustomer.getCustomerId();
        Integer userId = LoginScreenController.getCurrUserId();
        String title = input_title.getText();
        String description = input_description.getText();
        String location = input_location.getText();
        String contact = input_contact.getText();
        String type = input_type.getText();
        String url = input_url.getText();
        LocalDate date = datepicker_date.getValue();
        Timestamp start = Timestamp.valueOf(dropdown_time_from.getValue().toString());
        Timestamp end = Timestamp.valueOf(dropdown_time_to.getValue().toString());

        //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //stage.close();
    }
}