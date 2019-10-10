package view_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    public Button button_add_appointment;
    public Button button_edit_appointment;
    public Button button_edit_customer;

    @FXML private Button button_logout;
    @FXML private Button button_add_customer;

    public void initialize(URL url, ResourceBundle rb) {
        button_logout.setOnAction(event -> { // wow, this is seems to be a horrible way to use lambdas, takes up more lines than it did before
            try {
                Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Scheduler Login");
                stage.setScene(new Scene(root, 550, 500));
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        button_add_customer.setOnAction(event -> { // lambdas continue to be pointless, the button/function worked fine without this entire block of code previously. All it does it remove the onAction in the FXML
            try {
                addCustomerButtonHandler();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /*
    @FXML
    private void logoutButtonHandler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Scheduler Login");
        stage.setScene(new Scene(root, 550, 500));
        stage.centerOnScreen();
        stage.show();
    }
    */

    @FXML
    private void addCustomerButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Customer");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 500));
        stage.show();
    }

    @FXML
    private void editCustomerButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EditCustomer.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Edit Customer");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    @FXML
    private void addAppointmentButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddAppointment.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Appointment");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    @FXML
    private void viewAppointmentButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewAppointment.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Appointments");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    @FXML
    private void reportAppointmentTypesButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ReportAppointmentTypes.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Appointment Types");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 700));
        stage.show();
    }

    @FXML
    private void reportConsultantScheduleButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ReportConsultantSchedule.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Consultant Schedule");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 700));
        stage.show();
    }

    @FXML
    private void reportCustomersPerCityButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ReportCustomersPerCity.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Customers Per City");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 700));
        stage.show();
    }
}