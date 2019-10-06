package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.DBController;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class AddAppointmentController implements Initializable {

    public Button button_cancel;
    public Button button_save;

    @FXML private TableView<Customer> table_customers;
    @FXML private TableColumn<Customer, Integer> table_customer_id;
    @FXML private TableColumn<Customer, String> table_customer_name;

    @FXML private TextField input_title;
    @FXML private TextField input_description;
    @FXML private TextField input_location;
    @FXML private TextField input_contact;
    @FXML private ChoiceBox dropdown_type;
    @FXML private TextField input_url;
    @FXML private DatePicker datepicker_date;
    @FXML private ChoiceBox dropdown_time_from;
    @FXML private ChoiceBox dropdown_time_to;

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
    private void cancelButtonHandler(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveButtonHandler (ActionEvent event) {
        Customer selectedCustomer = table_customers.getSelectionModel().getSelectedItem();

        Integer customerId = selectedCustomer.getCustomerId();
        Integer userId = LoginScreenController.getCurrUserId();
        String title = input_title.getText();
        String description = input_description.getText();
        String location = input_location.getText();
        String contact = input_contact.getText();
        String type = dropdown_type.getValue().toString();
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

        Appointment appointment = new Appointment();
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}