package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    @FXML
    private Label label_username, label_password, label_login;
    @FXML
    private TextField input_username, input_password;

    public void initialize(URL url, ResourceBundle rb) { // strings "empty" and "incorrect are the other choices
        rb = ResourceBundle.getBundle("Resources/Login", Locale.getDefault());
        label_username.setText(rb.getString("username"));
        label_password.setText(rb.getString("password"));
        label_login.setText(rb.getString("log_in"));
        //System.out.println(Locale.getDefault());
        //System.out.println(rb.getString("intro"));
    }

    @FXML
    private void loginButtonHandler(ActionEvent event) throws IOException {
        String username = input_username.getText();
        String password = input_password.getText();

        ResourceBundle rb = ResourceBundle.getBundle("Resources/Login", Locale.getDefault());

        if (username.equals("") || password.equals("")) {
            Alert emptyFields = new Alert(Alert.AlertType.WARNING);
            emptyFields.setTitle(rb.getString("warning"));
            emptyFields.setHeaderText(rb.getString("empty_header"));
            emptyFields.setContentText(rb.getString("empty_content"));
            emptyFields.showAndWait();

        } else if (0==1) {
            // incorrect login check and alert, need to hook to database first
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Scheduler Main Screen");
            stage.setScene(new Scene(root, 550, 700));
            stage.centerOnScreen();
            stage.show();
        }
    }
}