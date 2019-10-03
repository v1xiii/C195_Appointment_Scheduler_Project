package view_controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.DBController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class ViewAppointmentController implements Initializable {

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
    private void cancelButtonHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void editButtonHandler(ActionEvent event) throws IOException {
        setAppointmentToModify(table_appointments.getSelectionModel().getSelectedItem());

        Parent root = FXMLLoader.load(getClass().getResource("EditAppointment.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Edit Appointment");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 700, 550));
        stage.show();

        //Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //thisStage.close();
    }

    @FXML
    private void deleteButtonHandler(ActionEvent event) throws IOException {

    }

    @FXML
    private void filterChoiceHandler(ActionEvent event) throws IOException {
        ZonedDateTime minDate = null;
        ZonedDateTime maxDate = null;
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