package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewAppointmentController implements Initializable {

    public void initialize(URL url, ResourceBundle rb) {

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