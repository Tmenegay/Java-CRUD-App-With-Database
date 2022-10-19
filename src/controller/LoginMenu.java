package controller;


import helper.AppointmentsQuery;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.Login;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *This class is the Controller for the LoginPage.fxml form.
 * It is responsible for checking locale timezone, language settings, and logging the user into the rest of the application.
 * */

public class LoginMenu implements Initializable {
    @FXML
    private TextField loginUsername;
    @ FXML
    private TextField loginPassword;
    @ FXML
    private Label loginField;
    @ FXML
    private Label username;
    @ FXML
    private Label password;
    @ FXML
    private Button loginButton;
    @ FXML
    private Button exitButton;
    @ FXML
    private Label locationText;
    @ FXML
    private Label loginScreenLocation;

    /**
     *When the this method is called when the login button is pressed. It checks to see if there is an appointment for the
     * user trying to login within the next 15 minutes, logs the login and makes sure that the username and password used are correct.
     * @param event
     */
    public void onLoginButton(javafx.event.ActionEvent event){
        try {
            ObservableList<Appointments> getAllAppointments = AppointmentsQuery.getAllAppointments();
            LocalDateTime currentTimeMinus15Min = LocalDateTime.now().minusMinutes(15);
            LocalDateTime currentTimePlus15Min = LocalDateTime.now().plusMinutes(15);
            LocalDateTime startTime;
            int getAppointmentID = 0;
            LocalDateTime displayTime = null;
            boolean appointmentWithin15Min = false;

            String userName = loginUsername.getText();
            String password = loginPassword.getText();


            boolean logon = Login.attemptLogon(userName, password);
            if (logon) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("login_activity.txt", true));
                writer.append("user: " + userName + " failed login attempt at: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
                System.out.println("New login recorded in log file.");
                writer.flush();
                writer.close();

                Parent mainMenu = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
                Scene scene = new Scene(mainMenu);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();



                for (Appointments appointment: getAllAppointments) {
                    startTime = appointment.getAppointmentsStart();
                    if ((startTime.isAfter(currentTimeMinus15Min) || startTime.isEqual(currentTimeMinus15Min)) && (startTime.isBefore(currentTimePlus15Min) || (startTime.isEqual(currentTimePlus15Min)))) {
                        getAppointmentID = appointment.getAppointmentsID();
                        displayTime = startTime;
                        appointmentWithin15Min = true;
                    }
                }
                if (appointmentWithin15Min !=false) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment within 15 minutes: " + getAppointmentID + " and appointment start time of: " + displayTime);
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("There is an appointment within 15 minutes");
                }

                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "No upcoming appointments.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("no upcoming appointments");
                }
            } else {
                Alert failedLogon = new Alert(Alert.AlertType.WARNING, "Failed to logon, make sure you are using correct username and password.");
                failedLogon.showAndWait();


            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * When the cancel button is pressed this method is called and closes the application.
     * @param actionEvent
     */
    public void onCancelButton(javafx.event.ActionEvent actionEvent){
        System.exit(0);
    }

    /**
     * When the class is loaded, the initialize method will set the Label text based upon the default language setting of the machine.
     * @param rb
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Locale location = Locale.getDefault();
            Locale.setDefault(location);

            ZoneId timeZone = ZoneId.systemDefault();

            loginScreenLocation.setText(String.valueOf(timeZone));

            rb = ResourceBundle.getBundle("Properties/Login", location);
            loginField.setText(rb.getString("Login"));
            username.setText(rb.getString("Username"));
            password.setText(rb.getString("Password"));
            loginButton.setText(rb.getString("Login"));
            exitButton.setText(rb.getString("Cancel"));
            locationText.setText(rb.getString("Location"));


        } catch (Exception e){
            System.out.println(e);
        }

    }

}