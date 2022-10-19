package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *This class loads the different forms for the application.
 * */
public class MainController {

    @FXML private Button mainMenuAppointmentClick;
    @FXML private Button mainMenuCustomerClick;
    @FXML private Button mainMenuExitClick;
    @FXML private Button mainMenuReportsClick;

    /**
     *Loads the appointment controller.
     * @param event
     */
    @FXML
    void mainMenuAppointmentHandler(ActionEvent event) throws IOException {
        Parent appointmentMenu = FXMLLoader.load(getClass().getResource("../view/ListAppointments.fxml"));
        Scene scene = new Scene(appointmentMenu);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     *Loads the customer controller.
     * @param event
     */
    @FXML
    void mainMenuCustomerHandler(ActionEvent event) throws IOException {

        Parent customerMenu = FXMLLoader.load(getClass().getResource("../view/EditCustomers.fxml"));
        Scene scene = new Scene(customerMenu);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     *Loads the reports controller.
     */
    @FXML
    void mainMenuReportsHandler(ActionEvent event) throws IOException {

        Parent reportMenu = FXMLLoader.load(getClass().getResource("../view/RunReports.fxml"));
        Scene scene = new Scene(reportMenu);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     *Exits the application.
     */
    public void mainMenuExitHandler(ActionEvent ExitButton) {
        Stage stage = (Stage) ((Node) ExitButton.getSource()).getScene().getWindow();
        stage.close();
    }
}
