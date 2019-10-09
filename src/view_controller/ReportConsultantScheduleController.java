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
import model.DBController;
import model.ReportItem;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportConsultantScheduleController implements Initializable {
    @FXML private TableView<ReportItem> table_appointments;
    @FXML private TableColumn<ReportItem, String> col_consultant;
    @FXML private TableColumn<ReportItem, String> col_customer;
    @FXML private TableColumn<ReportItem, String> col_type;
    @FXML private TableColumn<ReportItem, String> col_dateTime;
    public Button button_close;

    private ObservableList<ReportItem> consultantSchedules;

    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.consultantSchedules = DBController.getConsultantSchedules();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // populate appointments table
        col_consultant.setCellValueFactory(new PropertyValueFactory<>("userName"));
        col_customer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_dateTime.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        table_appointments.refresh();
        table_appointments.setItems(consultantSchedules);
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
