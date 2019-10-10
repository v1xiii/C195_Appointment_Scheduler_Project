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

public class ReportCustomersPerCityController implements Initializable {
    @FXML private TableView<ReportItem> table_cities;
    @FXML private TableColumn<ReportItem, String> col_city;
    @FXML private TableColumn<ReportItem, String> col_country;
    @FXML private TableColumn<ReportItem, String> col_customerCount;
    public Button button_close;

    private ObservableList<ReportItem> customersPerCity;

    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.customersPerCity = DBController.getCustomersPerCity();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // populate appointments table
        col_city.setCellValueFactory(new PropertyValueFactory<>("city"));
        col_country.setCellValueFactory(new PropertyValueFactory<>("country"));
        col_customerCount.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        table_cities.refresh();
        table_cities.setItems(customersPerCity);
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}