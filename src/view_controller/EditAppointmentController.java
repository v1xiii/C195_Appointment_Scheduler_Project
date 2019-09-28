package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class EditAppointmentController implements Initializable {
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
        Appointment appointment = ViewAppointmentController.getAppointmentToModify();
        input_title.setText(appointment.getTitle());
        input_description.setText(appointment.getDescription());
        input_location.setText(appointment.getLocation());
        input_contact.setText(appointment.getContact());
        input_type.setText(appointment.getType());
        input_url.setText(appointment.getUrl());

        // disable dates in datepicker prior to today and also weekends
        datepicker_date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            LocalDate today = LocalDate.now();
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            setDisable(empty || date.compareTo(today) < 0 || dayOfWeek == SATURDAY || dayOfWeek == SUNDAY);
            }
        });
        //datepicker_date.setValue();
        //dropdown_time_from.set;
        //dropdown_time_to;
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveButtonHandler (ActionEvent event) throws IOException {


        //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //stage.close();
    }
}