package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import helper.AppointmentsQuery;
import helper.CountryQuery;
import helper.CustomersQuery;
import helper.DivisionQuery;
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
import model.*;


/**
 *This class is the Controller for the EditCustomers.fxml form.
 * It is responsible for pulling, creating, updating, deleting customer data from the database.
 * */
public class CustomerMenu {

    @FXML TextField customerCustomerIDField;
    @FXML private TableView<Customers> customerTable;
    @FXML private TableColumn<?, ?> customerCustomerID;
    @FXML private TableColumn<?, ?> customerCustomerName;
    @FXML private TableColumn<?, ?> customersPhone;
    @FXML private TableColumn<?, ?> customerAddressColumn;
    @FXML private TableColumn<?, ?> customersStateColumn;
    @FXML private TableColumn<?, ?> customersCountryColumn;
    @FXML private TableColumn<?, ?> customersZipColumn;
    @FXML private  TextField customerCustomerNameField;
    @FXML private TextField customerAddressField;
    @FXML
    ComboBox<String> customerCountryCombo;
    @FXML
    ComboBox<String> customerProvinceCombo;
    @FXML private TextField customerZipField;
    @FXML private TextField customerPhoneField;



/**
 * initialize pulls data from that database and set's the items in the table. I use Lambda expressions to populate the ComboBoxes.
 * */
    public void initialize() throws SQLException {
        ObservableList<Customers> allCustomersList = CustomersQuery.getAllCustomers();



        customerCustomerID.setCellValueFactory((new PropertyValueFactory<>("customerCustomerID")));
        customerCustomerName.setCellValueFactory((new PropertyValueFactory<>("customerCustomerName")));
        customersPhone.setCellValueFactory((new PropertyValueFactory<>("customerPhone")));
        customerAddressColumn.setCellValueFactory((new PropertyValueFactory<>("customerAddress")));
        customersStateColumn.setCellValueFactory((new PropertyValueFactory<>("division")));
        customersCountryColumn.setCellValueFactory((new PropertyValueFactory<>("customerCountry")));
        customersZipColumn.setCellValueFactory((new PropertyValueFactory<>("customerPostalCode")));

        customerTable.setItems(allCustomersList);


        try {
            customerCountryCombo.setItems(CustomersQuery.getAllCountries());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //lambda
        customerCountryCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                customerProvinceCombo.getItems().clear();
                customerProvinceCombo.setDisable(true);

            }
            else {
                customerProvinceCombo.setDisable(false);
                try {
                    customerProvinceCombo.setItems(CustomersQuery.getFilterDivisions(customerCountryCombo.getValue()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     *When the Add button is pressed this method is called which will take data from the textfields and add it to the database.
     * This is done by calling the CustomersQuery insertCustomer() method.
     * @param actionEvent
     * */
    public void onCustomerAdd(ActionEvent actionEvent) throws SQLException, IOException {
        String customerName = customerCustomerNameField.getText();
        String customerAddress = customerAddressField.getText();
        String customerZip =  customerZipField.getText();
        String customerPhone = customerPhoneField.getText();
        String customerState = customerProvinceCombo.getValue();
        String countryID = customerCountryCombo.getValue();


        CustomersQuery.insertCustomer(customerName, customerAddress,  CustomersQuery.getSpecificDivisionID(customerState), customerZip, customerPhone);

        ObservableList<Customers> allCustomersList = CustomersQuery.getAllCustomers();
        customerCustomerID.setCellValueFactory((new PropertyValueFactory<>("customerCustomerID")));
        customerCustomerName.setCellValueFactory((new PropertyValueFactory<>("customerCustomerName")));
        customersPhone.setCellValueFactory((new PropertyValueFactory<>("customerPhone")));
        customerTable.setItems(allCustomersList);
    }

    /**
     *When the delete button is pressed this method is called and the selected item from the table will be deleted from the
     * customers table, and the subsequent appointments associated with the customer. This is done by calling the CustomersQuery
     * delete() method and then calling the AppointmentsQuery delete() method.
     * @param actionEvent
     * */
    public void onCustomerDelete(ActionEvent actionEvent) throws SQLException {
        Customers selectedCustomers = customerTable.getSelectionModel().getSelectedItem();

        if (selectedCustomers == null) {
            ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            Alert invalidInput = new Alert(Alert.AlertType.WARNING, "No selected Customer", clickOkay);
            invalidInput.showAndWait();
            return;
        }else{
            ButtonType clickYes = ButtonType.YES;
            ButtonType clickNo = ButtonType.NO;
            Alert deleteAlert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to delete Customer and Appointments: "
                    + selectedCustomers.getCustomerCustomerID() + " ?", clickYes, clickNo);
            Optional<ButtonType> result = deleteAlert.showAndWait();

            // if user confirms, delete appointment
            if (result.get() == ButtonType.YES) {
                ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                Alert deletedCustomer = new Alert(Alert.AlertType.CONFIRMATION, "Customer and Appointment deleted", clickOkay);
                deletedCustomer.showAndWait();

                CustomersQuery.delete(selectedCustomers.getCustomerCustomerID());
                int appointment = customerTable.getSelectionModel().getSelectedItem().getCustomerCustomerID();
                AppointmentsQuery.delete(appointment);

            } else {
                ButtonType clickOkay = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
                Alert deleteCustomer = new Alert(Alert.AlertType.WARNING, "Failed to delete Customer", clickOkay);
                deleteCustomer.showAndWait();
            }


            ObservableList<Customers> allCustomersList = CustomersQuery.getAllCustomers();
            customerTable.setItems(allCustomersList);


            customerCustomerID.setCellValueFactory((new PropertyValueFactory<>("customerCustomerID")));
            customerCustomerName.setCellValueFactory((new PropertyValueFactory<>("customerCustomerName")));
            customersPhone.setCellValueFactory((new PropertyValueFactory<>("customerPhone")));
            customerTable.setItems(allCustomersList);

        }
    }

    /**
     *When the back button is pressed it will take the user back to the main menu.
     * @param actionEvent
     * */
    @FXML
    public void onCustomerBack(ActionEvent actionEvent) throws IOException {
        Parent appointmentMenu = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        Scene scene = new Scene(appointmentMenu);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     *When the clear button is pressed, it will clear the text fields and ComboBoxes. I use 2 lambda expressions to
     * repopulate the ComboBoxes.
     * @param actionEvent
     * */
    public void onCustomerClear(ActionEvent actionEvent) throws SQLException {
        customerCustomerNameField.clear();
        customerAddressField.clear();
        customerProvinceCombo.getItems().clear();
        customerCountryCombo.getItems().clear();
        customerZipField.clear();
        customerPhoneField.clear();

        ObservableList<DivisionQuery> divisionList = DivisionQuery.getAllProvinces();
        ObservableList<String> allProvinceNames = FXCollections.observableArrayList();


        //lambda
        divisionList.forEach(divisions -> allProvinceNames.add(divisions.getDivisionName()));
        customerProvinceCombo.setItems(allProvinceNames);



        ObservableList<Country> countryList = CountryQuery.getAllCountries();
        ObservableList<String> allCountryNames = FXCollections.observableArrayList();


        //lambda
        countryList.forEach(country -> allCountryNames.add(country.getCountryName()));
        customerCountryCombo.setItems(allCountryNames);


    }

    /**
     *When the save button is pressed it calls this method. This method only works if the customerListener method has
     * has been called. This method takes the already populated items and updates the database by calling the CustomerQuery
     * updateCustomer() method.
     * */
    public void onCustomerEdit(ActionEvent actionEvent) throws SQLException {
        String customerID = customerCustomerIDField.getText();
        String customerName = customerCustomerNameField.getText();
        String customerAddress = customerAddressField.getText();
        String customerZip =  customerZipField.getText();
        String customerPhone = customerPhoneField.getText();
        String customerState = customerProvinceCombo.getValue();
        String countryID = customerCountryCombo.getValue();

        CustomersQuery.updateCustomer(customerID, customerName, customerAddress,CustomersQuery.getSpecificDivisionID(customerState), customerZip, customerPhone);

        ObservableList<Customers> allCustomersList = CustomersQuery.getAllCustomers();
        customerTable.setItems(allCustomersList);

    }

    /**
     *The customerListener method waits for a mouse click event in the customerTable, and when an item is pressed it populates
     * the TextFields and ComboBoxes with the appropriate data. I use lambda expressions for the ComboBoxes.
     * @param mouseEvent
     * */
    public void customerListener(MouseEvent mouseEvent) {
        try {
            Customers selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

            if (selectedCustomer != null) {

                ObservableList<DivisionQuery> divisionList = DivisionQuery.getAllProvinces();
                ObservableList<String> allProvinceNames = FXCollections.observableArrayList();
                String displayProvinceName = "";

                //lambda
                divisionList.forEach(divisions -> allProvinceNames.add(divisions.getDivisionName()));
               customerProvinceCombo.setItems(allProvinceNames);

                //get all contact info and fill ComboBox.
                ObservableList<Country> countryList = CountryQuery.getAllCountries();
                ObservableList<String> allCountryNames = FXCollections.observableArrayList();
                String displayCountryName = "";

                //lambda
                countryList.forEach(country -> allCountryNames.add(country.getCountryName()));
                customerCountryCombo.setItems(allCountryNames);


                for (FirstLevelDivisions division: divisionList) {
                    allProvinceNames.add(division.getDivisionName());
                    int countrySetID = division.getCountry_ID();

                    if (division.getDivisionID() == selectedCustomer.getDivisionID()) {
                        displayProvinceName = division.getDivisionName();

                        for (Country country: countryList) {
                            if (country.getCountryID() == countrySetID) {
                                displayCountryName = country.getCountryName();
                            }
                        }
                    }
                }

                customerCustomerIDField.setText(String.valueOf((selectedCustomer.getCustomerCustomerID())));
                customerCustomerNameField.setText(selectedCustomer.getCustomerCustomerName());
                customerAddressField.setText(selectedCustomer.getCustomerAddress());
                customerZipField.setText(selectedCustomer.getCustomerPostalCode());
                customerPhoneField.setText(selectedCustomer.getCustomerPhone());
                customerProvinceCombo.setValue(displayProvinceName);
                customerCountryCombo.setValue(displayCountryName);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
