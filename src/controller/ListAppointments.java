package controller;


import helper.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static model.TimeConversion.convertTimeDateUTC;

/**
 *This class is the Controller for the ListAppointments.fxml form.
 * It is responsible for pulling, creating, updating, deleting appointment data from the database.
 * */
public class ListAppointments {


    public DatePicker addAppointmentStartDate;
    public DatePicker addAppointmentEndDate;
    @FXML private ComboBox<String> addAppointmentStartTime;
    @FXML private ComboBox<String> addAppointmentEndTime;
    @FXML private ComboBox<String> addAppointmentContact;
    @FXML private TableView<Appointments> appointmentTable;
    @FXML private TableColumn<?, ?> appointmentsID;
    @FXML private TableColumn<?, ?> appointmentsTitle;
    @FXML private TableColumn<?, ?> appointmentsDescription;
    @FXML private TableColumn<?, ?> appointmentsLocation;
    @FXML private TableColumn<?, ?> appointmentsType;
    @FXML private TableColumn<?, ?> appointmentsStart;
    @FXML private TableColumn<?, ?> appointmentsEnd;
    @FXML private TableColumn<?, ?> appointmentsCustomerID;
    @FXML private TableColumn<?, ?> tableContactsID;
    @FXML private TableColumn<?, ?> tableUsersID;
    @FXML private TextField updateAppointmentTitle;
    @FXML private TextField addAppointmentDescription;
    @FXML private TextField addAppointmentType;
    @FXML private TextField addAppointmentLocation;
    @FXML private TextField addAppointmentCustomerID;
    @FXML private TextField updateAppointmentID;
    @FXML private TextField addAppointmentUserID;


    /**
     * initialize pulls data from that database and set's the items in the table. I use Lambda expression to populate the Contact ComboBox.
     * There is an alert that is displayed if there is no items from the database to populate the table.
     * */
    public void initialize() throws SQLException {
        ObservableList<Appointments> allAppointmentList = AppointmentsQuery.getAllAppointments();

        appointmentsID.setCellValueFactory((new PropertyValueFactory<>("appointmentsID")));
        appointmentsTitle.setCellValueFactory((new PropertyValueFactory<>("appointmentsTitle")));
        appointmentsDescription.setCellValueFactory((new PropertyValueFactory<>("appointmentsDescription")));
        appointmentsLocation.setCellValueFactory((new PropertyValueFactory<>("appointmentsLocation")));
        appointmentsType.setCellValueFactory(new PropertyValueFactory<>("appointmentsType"));
        appointmentsStart.setCellValueFactory((new PropertyValueFactory<>("appointmentsStart")));
        appointmentsEnd.setCellValueFactory((new PropertyValueFactory<>("appointmentsEnd")));
        appointmentsCustomerID.setCellValueFactory((new PropertyValueFactory<>("appointmentsCustomerID")));
        tableContactsID.setCellValueFactory((new PropertyValueFactory<>("contactID")));
        tableUsersID.setCellValueFactory((new PropertyValueFactory<>("tableUsersID")));

        appointmentTable.setItems(allAppointmentList);



        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22, 0);

        while (start.isBefore(end.plusSeconds(1))){
            addAppointmentStartTime.getItems().add(String.valueOf(start));
            addAppointmentEndTime.getItems().add(String.valueOf(start));
            start = start.plusMinutes(10);
        }
        addAppointmentStartTime.getSelectionModel().select(String.valueOf(LocalTime.of(8,0)));
        addAppointmentEndTime.getSelectionModel().select(String.valueOf(LocalTime.of(8,30)));


        ObservableList<Contacts> contactsObservableList = ContactsQuery.getAllContacts();
        ObservableList<String> allContactsNames = FXCollections.observableArrayList();

        //lambda
        contactsObservableList.forEach(contacts -> allContactsNames.add(contacts.getContactName()));

        addAppointmentContact.setItems(allContactsNames);
    }
    /**
     * The appoinmentWeekSelected method is for filtering appoinment data using a radio button. It's primary responsibility
     * is to display only appointments scheduled for the current week. There is a lambda expression to make this possible.
     * @param actionEvent
     * */
    public void appointmentWeekSelected(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointments> allAppointmentsList = AppointmentsQuery.getAllAppointments();
        ObservableList<Appointments> appointmentsWeek = FXCollections.observableArrayList();

        LocalDateTime weekStart = LocalDateTime.now().minusWeeks(1);
        LocalDateTime weekEnd = LocalDateTime.now().plusWeeks(1);

        if (allAppointmentsList != null)
            //Lambda
            allAppointmentsList.forEach(appointment -> {
                if (appointment.getAppointmentsEnd().isAfter(weekStart) && appointment.getAppointmentsEnd().isBefore(weekEnd)) {
                    appointmentsWeek.add(appointment);
                }
                appointmentTable.setItems(appointmentsWeek);
            });
    }

    /**
     * The appoinmentMonthSelected method is for filtering appoinment data using a radio button. It's primary responsibility
     * is to display only appointments scheduled for the current Month. There is a lambda expression to make this possible.
     * @param actionEvent
     * */
    public void appointmentMonthSelected(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointments> allAppointmentsList = AppointmentsQuery.getAllAppointments();
        ObservableList<Appointments> appointmentsMonth = FXCollections.observableArrayList();

        LocalDateTime currentMonthStart = LocalDateTime.now().minusMonths(1);
        LocalDateTime currentMonthEnd = LocalDateTime.now().plusMonths(1);


        if (allAppointmentsList != null)
            //Lambda
            allAppointmentsList.forEach(appointment -> {
                if (appointment.getAppointmentsEnd().isAfter(currentMonthStart) && appointment.getAppointmentsEnd().isBefore(currentMonthEnd)) {
                    appointmentsMonth.add(appointment);
                }
                appointmentTable.setItems(appointmentsMonth);
            });
    }

    /**
     * The appointmentAllSelected method is for filtering appoinment data using a radio button. It's primary responsibility
     * is to display all appointments and it is the default radio button selected.
     * @param actionEvent
     * */
    public void appointmentAllSelected(ActionEvent actionEvent) throws SQLException {
        ObservableList<Appointments> allAppointmentList = AppointmentsQuery.getAllAppointments();

        appointmentsID.setCellValueFactory((new PropertyValueFactory<>("appointmentsID")));
        appointmentsTitle.setCellValueFactory((new PropertyValueFactory<>("appointmentsTitle")));
        appointmentsDescription.setCellValueFactory((new PropertyValueFactory<>("appointmentsDescription")));
        appointmentsLocation.setCellValueFactory((new PropertyValueFactory<>("appointmentsLocation")));
        appointmentsType.setCellValueFactory(new PropertyValueFactory<>("appointmentsType"));
        appointmentsStart.setCellValueFactory((new PropertyValueFactory<>("appointmentsStart")));
        appointmentsEnd.setCellValueFactory((new PropertyValueFactory<>("appointmentsEnd")));
        appointmentsCustomerID.setCellValueFactory((new PropertyValueFactory<>("appointmentsCustomerID")));
        tableContactsID.setCellValueFactory((new PropertyValueFactory<>("contactID")));
        tableUsersID.setCellValueFactory((new PropertyValueFactory<>("tableUsersID")));

        appointmentTable.setItems(allAppointmentList);
    }
    /**
     *When the Add button is pressed this method is called which will take data from the textfields and add it to the database.
     * This is done by calling the AppointmentsQuery insertAppointment() method. We also convert the Timezone.
     * @param actionEvent
     * */
    public void onAddAppointment(ActionEvent actionEvent) throws SQLException {
        String aptTitle = updateAppointmentTitle.getText();
        String aptDesc = addAppointmentDescription.getText();
        String aptLocation = addAppointmentLocation.getText();
        String aptType = addAppointmentType.getText();
        String aptCustID = addAppointmentCustomerID.getText();
        String aptContID = ContactsQuery.findContactID(addAppointmentContact.getValue());
        String aptUserID = addAppointmentUserID.getText();

        if (!updateAppointmentTitle.getText().isEmpty() && !addAppointmentDescription.getText().isEmpty() && !addAppointmentLocation.getText().isEmpty() &&
                !addAppointmentType.getText().isEmpty() && addAppointmentStartDate.getValue() != null && addAppointmentEndDate.getValue() != null &&
                !addAppointmentStartTime.getValue().isEmpty() && !addAppointmentEndTime.getValue().isEmpty() && !addAppointmentCustomerID.getText().isEmpty()) {

            ObservableList<Customers> getAllCustomers = CustomersQuery.getAllCustomers();
            ObservableList<Integer> storeCustomerIDs = FXCollections.observableArrayList();
            ObservableList<Users> getAllUsers = UsersQuery.getAllUsers();
            ObservableList<Integer> storeUserIDs = FXCollections.observableArrayList();
            ObservableList<Appointments> getAllAppointments = AppointmentsQuery.getAllAppointments();


            getAllCustomers.stream().map(Customers::getCustomerCustomerID).forEach(storeCustomerIDs::add);
            getAllUsers.stream().map(Users::getUserID).forEach(storeUserIDs::add);

            LocalDate localDateEnd = addAppointmentEndDate.getValue();
            LocalDate localDateStart = addAppointmentStartDate.getValue();

            DateTimeFormatter minHourFormat = DateTimeFormatter.ofPattern("HH:mm");
            String appointmentStartDate = addAppointmentStartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String appointmentStartTime = addAppointmentStartTime.getValue();

            String appointmentEndDate = addAppointmentEndDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String appointmentEndTime = addAppointmentEndTime.getValue();

            System.out.println("thisDate + thisStart " + appointmentStartDate + " " + appointmentStartTime + ":00");
            String startUTC = convertTimeDateUTC(appointmentStartDate + " " + appointmentStartTime + ":00");
            String endUTC = convertTimeDateUTC(appointmentEndDate + " " + appointmentEndTime + ":00");

            LocalTime localTimeStart = LocalTime.parse(addAppointmentStartTime.getValue(), minHourFormat);
            LocalTime LocalTimeEnd = LocalTime.parse(addAppointmentEndTime.getValue(), minHourFormat);

            LocalDateTime dateTimeStart = LocalDateTime.of(localDateStart, localTimeStart);
            LocalDateTime dateTimeEnd = LocalDateTime.of(localDateEnd, LocalTimeEnd);

            ZonedDateTime zoneDtStart = ZonedDateTime.of(dateTimeStart, ZoneId.systemDefault());
            ZonedDateTime zoneDtEnd = ZonedDateTime.of(dateTimeEnd, ZoneId.systemDefault());

            ZonedDateTime convertStartEST = zoneDtStart.withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime convertEndEST = zoneDtEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

            LocalTime startAppointmentTimeToCheck = convertStartEST.toLocalTime();
            LocalTime endAppointmentTimeToCheck = convertEndEST.toLocalTime();

            DayOfWeek startAppointmentDayToCheck = convertStartEST.toLocalDate().getDayOfWeek();
            DayOfWeek endAppointmentDayToCheck = convertEndEST.toLocalDate().getDayOfWeek();

            int startAppointmentDayToCheckInt = startAppointmentDayToCheck.getValue();
            int endAppointmentDayToCheckInt = endAppointmentDayToCheck.getValue();

            int workWeekStart = DayOfWeek.MONDAY.getValue();
            int workWeekEnd = DayOfWeek.FRIDAY.getValue();

            LocalTime estBusinessStart = LocalTime.of(8, 0, 0);
            LocalTime estBusinessEnd = LocalTime.of(22, 0, 0);

            if (startAppointmentDayToCheckInt < workWeekStart || startAppointmentDayToCheckInt > workWeekEnd || endAppointmentDayToCheckInt < workWeekStart || endAppointmentDayToCheckInt > workWeekEnd) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Day is outside of business operations (Monday-Friday)");
                Optional<ButtonType> confirmation = alert.showAndWait();
                System.out.println("day is outside of business hours");
                return;
            }

            if (startAppointmentTimeToCheck.isBefore(estBusinessStart) || startAppointmentTimeToCheck.isAfter(estBusinessEnd) || endAppointmentTimeToCheck.isBefore(estBusinessStart) || endAppointmentTimeToCheck.isAfter(estBusinessEnd)) {
                System.out.println("time is outside of business hours");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Time is outside of business hours (8am-10pm EST): " + startAppointmentTimeToCheck + " - " + endAppointmentTimeToCheck + " EST");
                Optional<ButtonType> confirmation = alert.showAndWait();
                return;
            }

            int newAppointmentID = Integer.parseInt(String.valueOf((int) (Math.random() * 100)));
            int customerID = Integer.parseInt(addAppointmentCustomerID.getText());

            if (dateTimeStart.isAfter(dateTimeEnd)) {
                System.out.println("Appointment has start time after end time");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has start time after end time");
                Optional<ButtonType> confirmation = alert.showAndWait();
                return;
            }

            if (dateTimeStart.isEqual(dateTimeEnd)) {
                System.out.println("Appointment has same start and end time");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has same start and end time");
                Optional<ButtonType> confirmation = alert.showAndWait();
                return;
            }
            for (Appointments appointment : getAllAppointments) {
                LocalDateTime checkStart = appointment.getAppointmentsStart();
                LocalDateTime checkEnd = appointment.getAppointmentsEnd();


                if ((customerID == appointment.getAppointmentsCustomerID()) && (newAppointmentID != appointment.getAppointmentsID()) &&
                        (dateTimeStart.isBefore(checkStart)) && (dateTimeEnd.isAfter(checkEnd))) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment overlaps with existing appointment.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("Appointment overlaps with existing appointment.");
                    return;
                }

                if ((customerID == appointment.getAppointmentsCustomerID()) && (newAppointmentID != appointment.getAppointmentsID()) &&
                        (dateTimeStart.isEqual(checkStart)) && (dateTimeEnd.isAfter(checkEnd))) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment overlaps with existing appointment.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("Appointment overlaps with existing appointment.");
                    return;
                }

                if ((customerID == appointment.getAppointmentsCustomerID()) && (newAppointmentID != appointment.getAppointmentsID()) &&
                        (dateTimeStart.isEqual(checkStart)) && (dateTimeEnd.isEqual(checkEnd))) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment overlaps with existing appointment.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("Appointment overlaps with existing appointment.");
                    return;
                }

                if ((customerID == appointment.getAppointmentsCustomerID()) && (newAppointmentID != appointment.getAppointmentsID()) &&
                        (dateTimeStart.isBefore(checkStart)) && (dateTimeEnd.isEqual(checkEnd))) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment overlaps with existing appointment.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("Appointment overlaps with existing appointment.");
                    return;
                }


                if ((customerID == appointment.getAppointmentsCustomerID()) && (newAppointmentID != appointment.getAppointmentsID()) &&
                        (dateTimeStart.isAfter(checkStart)) && (dateTimeStart.isBefore(checkEnd))) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Start time overlaps with existing appointment.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("Start time overlaps with existing appointment.");
                    return;
                }


                if (customerID == appointment.getAppointmentsCustomerID() && (newAppointmentID != appointment.getAppointmentsID()) &&
                        (dateTimeEnd.isAfter(checkStart)) && (dateTimeEnd.isBefore(checkEnd))) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "End time overlaps with existing appointment.");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("End time overlaps with existing appointment.");
                    return;
                }
            }
            String startDate = addAppointmentStartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String startTime = addAppointmentStartTime.getValue();

            String endDate = addAppointmentEndDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String endTime = addAppointmentEndTime.getValue();

            String startingUTC = convertTimeDateUTC(startDate + " " + startTime + ":00");
            String endingUTC = convertTimeDateUTC(endDate + " " + endTime + ":00");


            AppointmentsQuery.insertAppointment(aptTitle, aptDesc, aptLocation, aptType, startingUTC, endingUTC, aptCustID, aptContID, aptUserID);


            ObservableList<Appointments> allAppointmentsList = AppointmentsQuery.getAllAppointments();
            appointmentTable.setItems(allAppointmentsList);
        }
    }
    /**
     *When the delete button is pressed this method is called and the selected item from the table will be deleted from the
     * Appointments table. This is done by  calling the AppointmentsQuery delete() method.
     * @param actionEvent
     * */
    public void onDeleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointments selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            Alert invalidInput = new Alert(Alert.AlertType.WARNING, "No selected Appointment", clickOkay);
            invalidInput.showAndWait();
            return;
        } else {
            ButtonType clickYes = ButtonType.YES;
            ButtonType clickNo = ButtonType.NO;
            Alert deleteAlert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete Appointment: "
                    + selectedAppointment.getAppointmentsID() + " ?", clickYes, clickNo);
            Optional<ButtonType> result = deleteAlert.showAndWait();

            // if user confirms, delete appointment
            if (result.get() == ButtonType.YES) {
                    ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                    Alert deletedAppt = new Alert(Alert.AlertType.CONFIRMATION, "Appointment deleted", clickOkay);
                    deletedAppt.showAndWait();

                     AppointmentsQuery.delete(selectedAppointment.getAppointmentsID());

                } else {
                    ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                    Alert deleteAppt = new Alert(Alert.AlertType.WARNING, "Failed to delete Appointment", clickOkay);
                    deleteAppt.showAndWait();
                }


            }
            ObservableList<Appointments> allAppointmentsList = AppointmentsQuery.getAllAppointments();
            appointmentTable.setItems(allAppointmentsList);
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
     *When the Update button is pressed this method is called his method only works if the customerListener method has
     * has been called. It will call AppointmentsQuery update() method which will update the data in the database.
     * We also convert the Timezone.
     * @param actionEvent
     * */
    public void onUpdateAppointment(ActionEvent actionEvent) throws SQLException {
        String aptId = updateAppointmentID.getText();
        String aptTitle = updateAppointmentTitle.getText();
        String aptDesc = addAppointmentDescription.getText();
        String aptLocation = addAppointmentLocation.getText();
        String aptType = addAppointmentType.getText();
        String aptCustID = addAppointmentCustomerID.getText();
        String aptContID = ContactsQuery.findContactID(addAppointmentContact.getValue());
        String aptUserID = addAppointmentUserID.getText();
        try {

            if (!updateAppointmentTitle.getText().isEmpty() && !addAppointmentDescription.getText().isEmpty() && !addAppointmentLocation.getText().isEmpty() && !addAppointmentType.getText().isEmpty() && addAppointmentStartDate.getValue() != null && addAppointmentEndDate.getValue() != null && !addAppointmentStartTime.getValue().isEmpty() && !addAppointmentEndTime.getValue().isEmpty() && !addAppointmentCustomerID.getText().isEmpty())
            {
                ObservableList<Customers> getAllCustomers = CustomersQuery.getAllCustomers();
                ObservableList<Integer> storeCustomerIDs = FXCollections.observableArrayList();
                ObservableList<Users> getAllUsers = UsersQuery.getAllUsers();
                ObservableList<Integer> storeUserIDs = FXCollections.observableArrayList();
                ObservableList<Appointments> getAllAppointments = AppointmentsQuery.getAllAppointments();


                getAllCustomers.stream().map(Customers::getCustomerCustomerID).forEach(storeCustomerIDs::add);
                getAllUsers.stream().map(Users::getUserID).forEach(storeUserIDs::add);

                LocalDate localDateEnd = addAppointmentEndDate.getValue();
                LocalDate localDateStart = addAppointmentStartDate.getValue();

                DateTimeFormatter minHourFormat = DateTimeFormatter.ofPattern("HH:mm");

                LocalTime localTimeStart = LocalTime.parse(addAppointmentStartTime.getValue(), minHourFormat);
                LocalTime LocalTimeEnd = LocalTime.parse(addAppointmentEndTime.getValue(), minHourFormat);

                LocalDateTime dateTimeStart = LocalDateTime.of(localDateStart, localTimeStart);
                LocalDateTime dateTimeEnd = LocalDateTime.of(localDateEnd, LocalTimeEnd);

                ZonedDateTime zoneDtStart = ZonedDateTime.of(dateTimeStart, ZoneId.systemDefault());
                ZonedDateTime zoneDtEnd = ZonedDateTime.of(dateTimeEnd, ZoneId.systemDefault());

                ZonedDateTime convertStartEST = zoneDtStart.withZoneSameInstant(ZoneId.of("America/New_York"));
                ZonedDateTime convertEndEST = zoneDtEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

                if (convertStartEST.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SATURDAY.getValue()) || convertStartEST.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SUNDAY.getValue()) || convertEndEST.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SATURDAY.getValue())  || convertEndEST.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SUNDAY.getValue()) )
                {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Day is outside of business operations (Monday-Friday)");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    System.out.println("day is outside of business hours");
                    return;
                }

                if (convertStartEST.toLocalTime().isBefore(LocalTime.of(8, 0, 0)) || convertStartEST.toLocalTime().isAfter(LocalTime.of(22, 0, 0)) || convertEndEST.toLocalTime().isBefore(LocalTime.of(8, 0, 0)) || convertEndEST.toLocalTime().isAfter(LocalTime.of(22, 0, 0)))
                {
                    System.out.println("time is outside of business hours");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Time is outside of business hours (8am-10pm EST): " + convertStartEST.toLocalTime() + " - " + convertEndEST.toLocalTime() + " EST");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    return;
                }

                int newCustomerID = Integer.parseInt(addAppointmentCustomerID.getText());
                int appointmentID = Integer.parseInt(updateAppointmentID.getText());


                if (dateTimeStart.isAfter(dateTimeEnd)) {
                    System.out.println("Appointment has start time after end time");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has start time after end time");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    return;
                }

                if (dateTimeStart.isEqual(dateTimeEnd)) {
                    System.out.println("Appointment has same start and end time");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment has same start and end time");
                    Optional<ButtonType> confirmation = alert.showAndWait();
                    return;
                }

                for (Appointments appointment: getAllAppointments)
                {
                    LocalDateTime checkStart = appointment.getAppointmentsStart();
                    LocalDateTime checkEnd = appointment.getAppointmentsEnd();

                    //"outer verify" meaning check to see if an appointment exists between start and end.
                    if ((newCustomerID == appointment.getAppointmentsCustomerID()) && (appointmentID != appointment.getAppointmentsID()) &&
                            (dateTimeStart.isBefore(checkStart)) && (dateTimeEnd.isAfter(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Appointment overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Appointment overlaps with existing appointment.");
                        return;
                    }

                    if ((newCustomerID == appointment.getAppointmentsCustomerID()) && (appointmentID != appointment.getAppointmentsID()) &&
                            (dateTimeStart.isAfter(checkStart)) && (dateTimeStart.isBefore(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Start time overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("Start time overlaps with existing appointment.");
                        return;
                    }



                    if (newCustomerID == appointment.getAppointmentsCustomerID() && (appointmentID != appointment.getAppointmentsID()) &&
                            (dateTimeEnd.isAfter(checkStart)) && (dateTimeEnd.isBefore(checkEnd))) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "End time overlaps with existing appointment.");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        System.out.println("End time overlaps with existing appointment.");
                        return;
                    }
                }

                String startDate = addAppointmentStartDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String startTime = addAppointmentStartTime.getValue();

                String endDate = addAppointmentEndDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String endTime = addAppointmentEndTime.getValue();

                String startUTC = convertTimeDateUTC(startDate + " " + startTime + ":00");
                String endUTC = convertTimeDateUTC(endDate + " " + endTime + ":00");

                AppointmentsQuery.update(aptId, aptTitle, aptDesc, aptLocation, aptType, startUTC, endUTC, aptCustID, aptContID, aptUserID);

                ObservableList<Appointments> allAppointmentsList = AppointmentsQuery.getAllAppointments();
                appointmentTable.setItems(allAppointmentsList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *The appointmentListener method waits for a mouse click event in the appointmentTable, and when an item is pressed it populates
     * the TextFields and ComboBox with the appropriate data. I use lambda expressions for Contact ComboBox.
     * @param mouseEvent
     * */
    public void appointmentListener(MouseEvent mouseEvent) {
        try {
            Appointments selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

            if (selectedAppointment != null) {


                ObservableList<Contacts> contactsObservableList = ContactsQuery.getAllContacts();
                ObservableList<String> allContactsNames = FXCollections.observableArrayList();
                String displayContactName = "";

                //lambda
                contactsObservableList.forEach(contacts -> allContactsNames.add(contacts.getContactName()));
                addAppointmentContact.setItems(allContactsNames);

                for (Contacts contact: contactsObservableList) {
                    if (selectedAppointment.getContactID() == contact.getContactID()) {
                        displayContactName = contact.getContactName();
                    }
                }

                updateAppointmentID.setText(String.valueOf(selectedAppointment.getAppointmentsID()));
                updateAppointmentTitle.setText(selectedAppointment.getAppointmentsTitle());
                addAppointmentDescription.setText(selectedAppointment.getAppointmentsDescription());
                addAppointmentLocation.setText(selectedAppointment.getAppointmentsLocation());
                addAppointmentType.setText(selectedAppointment.getAppointmentsType());
                addAppointmentCustomerID.setText(String.valueOf(selectedAppointment.getAppointmentsCustomerID()));
                addAppointmentStartDate.setValue(selectedAppointment.getAppointmentsStart().toLocalDate());
                addAppointmentEndDate.setValue(selectedAppointment.getAppointmentsEnd().toLocalDate());
                addAppointmentStartTime.setValue(String.valueOf(selectedAppointment.getAppointmentsStart().toLocalTime()));
                addAppointmentEndTime.setValue(String.valueOf(selectedAppointment.getAppointmentsEnd().toLocalTime()));
                addAppointmentUserID.setText(String.valueOf(selectedAppointment.getTableUsersID()));
                addAppointmentContact.setValue(displayContactName);

                ObservableList<String> appointmentTimes = FXCollections.observableArrayList();

                LocalTime firstAppointment = LocalTime.MIN.plusHours(8);
                LocalTime lastAppointment = LocalTime.MAX.minusHours(1).minusMinutes(45);

                if (!firstAppointment.equals(0) || !lastAppointment.equals(0)) {
                    while (firstAppointment.isBefore(lastAppointment)) {
                        appointmentTimes.add(String.valueOf(firstAppointment));
                        firstAppointment = firstAppointment.plusMinutes(15);
                    }
                }
                addAppointmentStartTime.setItems(appointmentTimes);
                addAppointmentEndTime.setItems(appointmentTimes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

