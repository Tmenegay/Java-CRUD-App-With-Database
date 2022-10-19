package controller;

import helper.AppointmentsQuery;
import helper.ContactsQuery;
import helper.ReportQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Reports;
import java.io.IOException;
import java.sql.SQLException;

/**
 *This class runs reports based on data from the database.
 * */
public class Report {
    @FXML
    private ComboBox<String> contactScheduleCombo;
    @FXML
    private TableView<Appointments> allAppointmentsTable;
    @FXML
    private TableColumn appointmentsIDCol;
    @FXML
    private TableColumn appointmentsTitleCol;
    @FXML
    private TableColumn appointmentsDescriptionCol;
    @FXML
    private TableColumn appointmentsLocationCol;
    @FXML
    private TableColumn appointmentType;
    @FXML
    private TableColumn appointmentStart;
    @FXML
    private TableColumn appointmentEnd;
    @FXML
    private TableColumn appointmentCustomerID;
    @FXML
    private TableColumn tableContactID;
    @FXML
    private TableView customersCountryTable;
    @FXML
    private TableColumn countryNameCol;
    @FXML
    private TableColumn countryTotalCol;

    @FXML
    private TextArea reportTextField;

    /**
     * initialize sets the data by default for the tables and uses a lambda expression to populate the Contacts ComboBox.
     */
    public void initialize() throws SQLException {
        countryNameCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        countryTotalCol.setCellValueFactory(new PropertyValueFactory<>("countryCount"));
        appointmentsIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentsID"));
        appointmentsTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentsTitle"));
        appointmentsDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentsDescription"));
        appointmentsLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentsLocation"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentsType"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("appointmentsStart"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("appointmentsEnd"));
        appointmentCustomerID.setCellValueFactory(new PropertyValueFactory<>("appointmentsCustomerID"));
        tableContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));


        ObservableList<Contacts> contactsObservableList = ContactsQuery.getAllContacts();
        ObservableList<String> allContactsNames = FXCollections.observableArrayList();
        //Lambda
        contactsObservableList.forEach(contacts -> allContactsNames.add(contacts.getContactName()));
        contactScheduleCombo.setItems(allContactsNames);
    }
    /**
     * When the Appointment Totals tab is opened, it populates a TextArea with data pulled from AppointmentsQuery using
     * the reportTotalsByTypeAndMonth() method.
     * @param event
     */
    public void onAppointmentTotals(Event event) throws SQLException {
        ObservableList<String> reportStrings = AppointmentsQuery.reportTotalsByTypeAndMonth();

        for (String str : reportStrings) {
            reportTextField.appendText(str);
        }
    }

    /**
     * When the Customers By Country tab is opened, it populates a Table with data pulled from ReportQuery using
     * the getCountries() method.
     * @param event
     */
    public void onCustomerCountry(Event event) {
        try {

            ObservableList<Reports> getCountries = ReportQuery.getCountries();
            ObservableList<Reports> countriesToAdd = FXCollections.observableArrayList();


            getCountries.forEach(countriesToAdd::add);

            customersCountryTable.setItems(countriesToAdd);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    /**
     *When the back button is pressed it will take the user back to the main menu.
     * @param actionEvent
     * */
    @FXML
    public void onBackButton(ActionEvent actionEvent) throws IOException {
        Parent appointmentMenu = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        Scene scene = new Scene(appointmentMenu);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     *The onContactScheduleCombo method will change the appointmentsTable depending on which contact is selected, it
     * pulls appointment information of the contact from the AppointmentsQuery getAllAppointments() method.
     * @param actionEvent
     * */
    @FXML
    public void onContactScheduleCombo(ActionEvent actionEvent) {
        try {

            int contactID = 0;

            ObservableList<Appointments> getAllAppointmentData = AppointmentsQuery.getAllAppointments();
            ObservableList<Appointments> appointmentInfo = FXCollections.observableArrayList();
            ObservableList<Contacts> getAllContacts = ContactsQuery.getAllContacts();

            Appointments contactAppointmentInfo;

            String contactName = contactScheduleCombo.getSelectionModel().getSelectedItem();

            for (Contacts contact : getAllContacts) {
                if (contactName.equals(contact.getContactName())) {
                    contactID = contact.getContactID();
                }
            }

            for (Appointments appointment : getAllAppointmentData) {
                if (appointment.getContactID() == contactID) {
                    contactAppointmentInfo = appointment;
                    appointmentInfo.add(contactAppointmentInfo);
                }
            }

            allAppointmentsTable.setItems(appointmentInfo);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
