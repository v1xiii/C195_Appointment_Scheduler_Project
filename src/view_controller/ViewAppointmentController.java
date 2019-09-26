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

    public void initialize(URL url, ResourceBundle rb) {
        // populate appointments table
        col_customer_id.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        col_start.setCellValueFactory(new PropertyValueFactory<>("start"));
        table_appointments.refresh();
        try {
            table_appointments.setItems(DBController.getAppointments());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void viewButtonHandler (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EditAppointment.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Edit Appointment");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    @FXML
    private void deleteButtonHandler (ActionEvent event) throws IOException {

    }
}