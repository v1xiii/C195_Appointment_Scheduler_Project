package view_controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.DBController;
import model.ReportItem;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportAppointmentTypesController implements Initializable {
    @FXML private TableView<ReportItem> table_appointments;
    @FXML private TableColumn<Appointment, String> col_month;
    @FXML private TableColumn<Appointment, String> col_type;
    @FXML private TableColumn<Appointment, String> col_quantity;
    public Button button_close;

    private ObservableList<ReportItem> appointmentsByType;

    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.appointmentsByType = DBController.getAppointmentsByType();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // populate appointments table
        col_month.setCellValueFactory(new PropertyValueFactory<>("month"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        table_appointments.refresh();
        table_appointments.setItems(appointmentsByType);
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
