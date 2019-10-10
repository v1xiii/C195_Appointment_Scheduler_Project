package view_controller;

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
import javafx.stage.Stage;
import model.Appointment;
import model.DBController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewAppointmentController implements Initializable {

    @FXML public Button button_delete;
    @FXML public Button button_close;
    @FXML public Button button_view;
    @FXML private TableView<Appointment> table_appointments;
    @FXML private TableColumn<Appointment, Integer> col_customer_id;
    @FXML private TableColumn<Appointment, String> col_title;
    @FXML private TableColumn<Appointment, String> col_type;
    @FXML private TableColumn<Appointment, String> col_location;
    @FXML private TableColumn<Appointment, ZonedDateTime> col_start;
    @FXML private ChoiceBox dropdown_filter;

    private ObservableList<Appointment> allAppointments;
    private static Appointment appointmentToModify;

    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.allAppointments = DBController.getAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // populate appointments table
        col_customer_id.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        col_start.setCellValueFactory(new PropertyValueFactory<>("start"));
        table_appointments.refresh();
        table_appointments.setItems(allAppointments);
    }

    private void setAppointmentToModify(Appointment appointment){
        appointmentToModify = appointment;
    }

    static Appointment getAppointmentToModify(){
        return appointmentToModify;
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void editButtonHandler(ActionEvent event) throws IOException {
        setAppointmentToModify(table_appointments.getSelectionModel().getSelectedItem());

        Parent root = FXMLLoader.load(getClass().getResource("EditAppointment.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Edit Appointment");
        stage.setScene(new Scene(root, 700, 550));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void deleteButtonHandler() throws SQLException {
        Appointment appointment = table_appointments.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Removing appointment");
        alert.setContentText("Are you sure you want to remove this appointment?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table_appointments.getItems().remove(appointment);
            DBController.deleteAppointment(appointment);
        }
    }

    @FXML
    private void filterChoiceHandler() {
        ZonedDateTime minDate;
        ZonedDateTime maxDate;
        ZonedDateTime now = ZonedDateTime.now();
        ObservableList<Appointment> appointments = null;

        try {
            appointments = DBController.getAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String filter = (String) dropdown_filter.getSelectionModel().getSelectedItem();

        switch(filter) {
            case "This Month":
                minDate = now;
                maxDate = now.plusMonths(1);
                break;
            case "This Week":
                minDate = now;
                maxDate = now.plusWeeks(1);
                break;
            default:
                minDate = now.minusYears(100);
                maxDate = now.plusYears(100);
        }

        ZonedDateTime finalMinDate = minDate;
        ZonedDateTime finalMaxDate = maxDate;
        if (appointments != null) {
            appointments.removeIf(appointment -> appointment.getStart().isBefore(finalMinDate) || appointment.getStart().isAfter(finalMaxDate));
        }

        col_customer_id.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        col_start.setCellValueFactory(new PropertyValueFactory<>("start"));
        table_appointments.refresh();
        table_appointments.setItems(appointments);
    }
}