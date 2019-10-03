package view_controller;

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
import model.Appointment;
import model.Customer;
import model.DBController;

import javax.swing.text.View;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

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
    @FXML private ChoiceBox<String> dropdown_time_from;
    @FXML private ChoiceBox<String> dropdown_time_to;

    private Appointment appointment;

    public void initialize(URL url, ResourceBundle rb) {
        appointment = ViewAppointmentController.getAppointmentToModify();

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

        // set the datepicker value
        LocalDate date = appointment.getStart().toLocalDate();
        datepicker_date.setValue(date);

        // set the times
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h:mm a");

        String start = appointment.getStart().format(formatTime);
        dropdown_time_from.setValue(start);

        String end = appointment.getEnd().format(formatTime);
        dropdown_time_to.setValue(end);

        // populate customers table
        table_customer_id.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        table_customer_name.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        table_customers.refresh();
        try {
            table_customers.setItems(DBController.getCustomers());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // select the customer for this appointment
        table_customers.getSelectionModel().select(appointment.getCustomerId());
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

        String startSelection = (String) dropdown_time_from.getSelectionModel().getSelectedItem();
        String endSelection = (String) dropdown_time_to.getSelectionModel().getSelectedItem();

        int startHourEnd = startSelection.indexOf(":");
        int endHourEnd = endSelection.indexOf(":");
        String startHour = startSelection.substring(0 , startHourEnd);
        String startAmPm = startSelection.substring(startSelection.lastIndexOf(' ') + 1);
        String endHour = endSelection.substring(0 , endHourEnd);
        String endAmPm = endSelection.substring(endSelection.lastIndexOf(' ') + 1);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");

        LocalDateTime startLDT = LocalDateTime.parse(date.toString() + " " + startHour + ":" + "00" + " " + startAmPm, dateFormat);
        LocalDateTime endLDT = LocalDateTime.parse(date.toString() + " " + endHour + ":" + "00" + " " + endAmPm, dateFormat);

        ZoneId systemTZ = ZoneId.of(TimeZone.getDefault().getID());

        ZonedDateTime startZDT = ZonedDateTime.of(startLDT, systemTZ);
        ZonedDateTime endZDT = ZonedDateTime.of(endLDT, systemTZ);

        ZonedDateTime startUTC = startZDT.withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endUTC = endZDT.withZoneSameInstant(ZoneId.of("UTC"));

        appointment.setCustomerId(customerId);
        appointment.setUserId(userId);
        appointment.setTitle(title);
        appointment.setDescription(description);
        appointment.setLocation(location);
        appointment.setContact(contact);
        appointment.setType(type);
        appointment.setUrl(url);
        appointment.setStart(startUTC);
        appointment.setEnd((endUTC));

        int response = 0;
        try {
            response = DBController.addAppointment(appointment);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (response == 1){
            /*
            Parent root = FXMLLoader.load(getClass().getResource("ViewAppointment.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Appointments");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 700, 550));
            stage.show();
            */

            // UP NEXT - delete appointments
            // check into issue where customers are not being selected and duplicated appointments are being created when editing appointment (might just need to delete all the old records)
            // Make view appointments update somehow after editing an appointment (maybe do a scene switch to and from instead of new stage?)

            Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            thisStage.close();
        }
    }
}