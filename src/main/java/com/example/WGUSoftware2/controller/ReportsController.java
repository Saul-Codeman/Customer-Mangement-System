package com.example.WGUSoftware2.controller;

import com.example.WGUSoftware2.model.Appointments;
import com.example.WGUSoftware2.model.Customers;
import com.example.WGUSoftware2.utility.Library;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/**
 * Credit to Ayun Daywhea for some of the design aspects of the reports form.
 */
public class ReportsController implements Initializable {

    @FXML
    private TableColumn<Customers, String> addressCol;

    @FXML
    private TableColumn<Appointments, Integer> appointmentIdCol;

    @FXML
    private TableView<Appointments> appointmentTable;

    @FXML
    private TableColumn<Customers, ZonedDateTime> createDateCol;

    @FXML
    private TableColumn<Customers, String> createdByCol;

    @FXML
    private TableColumn<Appointments, Integer> customerIdCol;

    @FXML
    private TableColumn<Appointments, Integer> contactIdCol;

    @FXML
    private TableColumn<Customers, Integer> customerIdCol2;

    @FXML
    private TableColumn<Customers, String> customerNameCol;

    @FXML
    private TableView<Customers> customersTable;

    @FXML
    private TableColumn<Appointments, String> descriptionCol;

    @FXML
    private TableColumn<Customers, Integer> divisionIdCol;

    @FXML
    private TableColumn<Appointments, ZonedDateTime> endDateCol;

    @FXML
    private TableColumn<Appointments, ZonedDateTime> endTimeCol;

    @FXML
    private TableColumn<Customers, Timestamp> lastUpdateCol;

    @FXML
    private TableColumn<Customers, String> lastUpdatedByCol;

    @FXML
    private TableColumn<Customers, String> phoneCol;

    @FXML
    private TableColumn<Customers, String> postalCodeCol;

    @FXML
    private Label universalLbl;

    @FXML
    private Label totalLbl;

    @FXML
    private ToggleGroup sortTG;

    @FXML
    private TableColumn<Appointments, ZonedDateTime> startDateCol;

    @FXML
    private TableColumn<Appointments, ZonedDateTime> startTimeCol;

    @FXML
    private TableColumn<Appointments, String> titleCol;

    @FXML
    private Label totalCustomersLbl;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    @FXML
    private ComboBox<String> universalCB;

    @FXML
    private RadioButton appointmentsRB;

    @FXML
    private RadioButton typeRB;

    @FXML
    private RadioButton monthRB;

    @FXML
    private RadioButton contactScheduleRB;

    /**
     * Switches user to appointments page
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void appointmentsHandler(ActionEvent event) throws IOException {
        Library.switchScreen(event, Library.appointmentsUrl);
    }

    /**
     * Switches user to customers page
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void customersHandler(ActionEvent event) throws IOException {
        Library.switchScreen(event, Library.customersUrl);
    }

    /**
     * Switches user to login page
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void logoutHandler(ActionEvent event) throws IOException {
        // End session and logout user
        Library.switchScreen(event, Library.loginUrl);
    }

    /**
     * Clears the previous table and CB values and allows the user to sort by contact ID from the CB
     * @param event action on a radio button
     * @throws SQLException catches RUNTIME ERROR
     */
    @FXML
    void sortContact(ActionEvent event) throws SQLException {

        // Clearing previous table and CB values
        universalCB.setValue(null);
        appointmentTable.setItems(null);
        customersTable.setItems(null);
        totalCustomersLbl.setText(null);
        totalLbl.setText("Total Appointments:");
        // Setting new values
        ObservableList<String> contactIDs = FXCollections.observableArrayList(Library.getContactIDs());
        universalLbl.setText("Enter Contact ID:");
        appointmentTable.setVisible(true);
        customersTable.setVisible(false);
        universalCB.setItems(contactIDs);

    }

    /**
     * Clears the previous table and CB values and allows the user to sort appointments by customer ID from the CB
     * @param event action on a radio button
     * @throws SQLException catches RUNTIME ERROR
     */
    @FXML
    void sortAppointments(ActionEvent event) throws SQLException {
        // Clearing previous table and CB values
        universalCB.setValue(null);
        appointmentTable.setItems(null);
        customersTable.setItems(null);
        totalCustomersLbl.setText(null);
        totalLbl.setText("Total Appointments:");
        // Setting new values
        ObservableList<String> customerIDs = FXCollections.observableArrayList(Customers.getCustomerIDs());
        universalLbl.setText("Enter Customer ID:");
        appointmentTable.setVisible(true);
        customersTable.setVisible(false);
        universalCB.setItems(customerIDs);

    }

    /**
     * Clears the previous table and CB values and allows the user to sort customers by month from the CB
     * @param event action on a radio button
     */
    @FXML
    void sortMonth(ActionEvent event) {
        // Clearing previous table and CB values
        universalCB.setValue(null);
        appointmentTable.setItems(null);
        customersTable.setItems(null);
        totalCustomersLbl.setText(null);
        totalLbl.setText("Total Customers:");
        // Setting new values
        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        universalLbl.setText("Enter Month:");
        appointmentTable.setVisible(false);
        customersTable.setVisible(true);
        universalCB.setItems(months);
    }

    /**
     * Clears the previous table and CB values and allows the user to sort by customer type from the CB
     * @param event action on a radio button
     * @throws SQLException catches RUNTIME ERROR
     */
    @FXML
    void sortType(ActionEvent event) throws SQLException {

        // Clearing previous table and CB values
        universalCB.setValue(null);
        appointmentTable.setItems(null);
        customersTable.setItems(null);
        totalCustomersLbl.setText(null);
        totalLbl.setText("Total Customers:");
        // Setting new values
        ObservableList<String> types = FXCollections.observableArrayList(Library.getTypes());
        universalLbl.setText("Enter Type:");
        appointmentTable.setVisible(false);
        customersTable.setVisible(true);
        universalCB.setItems(types);

    }

    /**
     * The drop down box that contains the elements to search by for each sort function
     * @param event action on a CB
     * @throws SQLException catches RUNTIME ERROR
     */
    @FXML
    void universalDropDown(ActionEvent event) throws SQLException {
        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        String universal = universalCB.getValue();
        int numRows = 0;
        if (contactScheduleRB.isSelected() && Library.getContactIDs().contains(universal)){
            int contactID = Integer.parseInt(universalCB.getValue());
            Appointments.setContactScheduleAppointments(contactID, appointmentTable, appointmentIdCol, titleCol, descriptionCol, typeCol, startDateCol, startTimeCol, endDateCol, endTimeCol, customerIdCol, contactIdCol);
            if (appointmentTable.getItems() != null) {
                numRows = appointmentTable.getItems().size();
            }

        }else if (monthRB.isSelected() && months.contains(universal)){
            String month = universalCB.getValue();
            Customers.sortMonth(month, customersTable, customerIdCol2, customerNameCol, addressCol, postalCodeCol, phoneCol, createDateCol, createdByCol, lastUpdateCol, lastUpdatedByCol, divisionIdCol);
            if (customersTable.getItems() != null) {
                numRows = customersTable.getItems().size();
            }

        }else if (typeRB.isSelected() && Library.getTypes().contains(universal)){
            String type = universalCB.getValue();
            Customers.setCustomerType(type, customersTable, customerIdCol2, customerNameCol, addressCol, postalCodeCol, phoneCol, createDateCol, createdByCol, lastUpdateCol, lastUpdatedByCol, divisionIdCol);
            if (customersTable.getItems() != null) {
                numRows = customersTable.getItems().size();
            }

        }else if (appointmentsRB.isSelected() && Customers.getCustomerIDs().contains(universal)){
            int customerID = Integer.parseInt(universalCB.getValue());
            Appointments.setCustomerAppointments(customerID, appointmentTable, appointmentIdCol, titleCol, descriptionCol, typeCol, startDateCol, startTimeCol, endDateCol, endTimeCol, customerIdCol, contactIdCol);
            if (appointmentTable.getItems() != null) {
                numRows = appointmentTable.getItems().size();
            }

        }
        totalCustomersLbl.setText(String.valueOf(numRows));
    }

    /**
     *  Initializes the form and sets the initial values to sort by contact id
     * @param url of the form
     * @param resourceBundle bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> contactIDs = null;
        try {
            contactIDs = FXCollections.observableArrayList(Library.getContactIDs());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        universalLbl.setText("Enter Contact ID:");
        appointmentTable.setVisible(true);
        customersTable.setVisible(false);
        universalCB.setItems(contactIDs);
    }
}
