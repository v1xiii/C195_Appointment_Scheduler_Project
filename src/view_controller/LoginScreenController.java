package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;

import static model.DBController.checkLogin;

public class LoginScreenController implements Initializable {

    private static String currUser;
    private static Integer currUserId;

    public Button button_login;

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

    private static void setCurrUser(String userName) {
        currUser = userName;
    }

    private static void setCurrUserId(Integer userId) {
        currUserId = userId;
    }

    public static String getCurrUser(){
        return currUser;
    }

    public static Integer getCurrUserId(){
        return currUserId;
    }

    @FXML
    private void loginButtonHandler(ActionEvent event) throws IOException, SQLException {
        String username = input_username.getText();
        String password = input_password.getText();

        ResourceBundle rb = ResourceBundle.getBundle("Resources/Login", Locale.getDefault());
        /*
        if (username.equals("") || password.equals("")) {
            Alert emptyFields = new Alert(Alert.AlertType.WARNING);
            emptyFields.setTitle(rb.getString("warning"));
            emptyFields.setHeaderText(rb.getString("empty_header"));
            emptyFields.setContentText(rb.getString("empty_content"));
            emptyFields.showAndWait();

        } else {
            setCurrUserId(checkLogin(username, password)); //This may be broken when re-activated due to setCurrUserId being untested
        }

        if (getCurrUserId() > -1){
        */
            setCurrUser("test"); // DELETE THIS WHEN UNCOMMENTING LOGIN VERIFICATION
            setCurrUserId(1); // DELETE THIS WHEN UNCOMMENTING LOGIN VERIFICATION
            Path path = Paths.get("logins.txt");
            Files.write(path, Collections.singletonList("User:" + currUser + " -- Login Time: " + Date.from(Instant.now()).toString() + "."), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Scheduler Main Screen");
            stage.setScene(new Scene(root, 550, 700));
            stage.centerOnScreen();
            stage.show();
        /*
        } else {
            Alert emptyFields = new Alert(Alert.AlertType.WARNING);
            emptyFields.setTitle(rb.getString("warning"));
            emptyFields.setHeaderText(rb.getString("invalid_header"));
            emptyFields.setContentText(rb.getString("invalid_content"));
            emptyFields.showAndWait();
        }
         */
    }
}