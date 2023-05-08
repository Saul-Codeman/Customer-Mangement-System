package com.example.WGUSoftware2.controller;

import com.example.WGUSoftware2.model.Customers;
import com.example.WGUSoftware2.utility.Library;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.WGUSoftware2.model.Customers.hasAppointment;
import static com.example.WGUSoftware2.model.Customers.setCustomersTable;

public class CustomerController implements Initializable {

    // Handlers
    public Stage stage;

    @FXML
    private TableColumn<Customers, String> addressCol;

    @FXML
    private TableView<Customers> customersTable;

    @FXML
    private TableColumn<Customers, ZonedDateTime> createDateCol;

    @FXML
    private TableColumn<Customers, String> createdByCol;

    @FXML
    private TableColumn<Customers, Integer> customerIdCol;

    @FXML
    private TableColumn<Customers, String> customerNameCol;

    @FXML
    private TableColumn<Customers, Integer> divisionIdCol;

    @FXML
    private TableColumn<Customers, Timestamp> lastUpdateCol;

    @FXML
    private TableColumn<Customers, String> lastUpdatedByCol;

    @FXML
    private TableColumn<Customers, String> phoneCol;

    @FXML
    private TableColumn<Customers, String> postalCodeCol;


    /**
     * Switches to add customer page.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void addHandler(ActionEvent event) throws IOException {
        Library.switchScreen(event, Library.addCustomerUrl);
    }

    /**
     * Switches to appointments page.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void appointmentsHandler(ActionEvent event) throws IOException {
        Library.switchScreen(event, Library.appointmentsUrl);
    }

    /**
     * Deletes the selected customer from the table after showing a confirmation box.
     * @param event action on a button
     * @throws SQLException catches RUNTIME ERROR
     */
    @FXML
    void deleteHandler(ActionEvent event) throws SQLException {
        // Customer selected
        Customers selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        // Add a condition that prevents the deletion of a customer with an appointment
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to delete");
            alert.showAndWait();
        } else {
            // Add a condition that prevents the deletion of a customer with an appointment
            if (hasAppointment(selectedCustomer.getCustomerID())) {
                Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDelete.setTitle("Confirm Delete");
                confirmDelete.setHeaderText("Deleting this customer will also delete all associated appointments.");
                confirmDelete.setContentText("Do you want to proceed?");
                Optional<ButtonType> result = confirmDelete.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Delete the customer and all associated appointments
                    Customers.deleteCustomerAppointments(selectedCustomer.getCustomerID());
                    Customers.deleteCustomer(selectedCustomer.getCustomerID());
                    // Refresh the table view
                    setCustomersTable(customersTable, customerIdCol, customerNameCol, addressCol, postalCodeCol, phoneCol, createDateCol, createdByCol, lastUpdateCol, lastUpdatedByCol, divisionIdCol);

                }
            }else {
                Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDelete.setTitle("Confirm Delete");
                confirmDelete.setHeaderText("You are about to delete the selected customer.");
                confirmDelete.setContentText("Do you want to proceed?");
                Optional<ButtonType> result = confirmDelete.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Delete the customer and all associated appointments
                    Customers.deleteCustomer(selectedCustomer.getCustomerID());
                    // Refresh the table view
                    setCustomersTable(customersTable, customerIdCol, customerNameCol, addressCol, postalCodeCol, phoneCol, createDateCol, createdByCol, lastUpdateCol, lastUpdatedByCol, divisionIdCol);
                }
            }
        }
    }

    /**
     * Shows a confirmation to confirm if the user wants to log out, ends session, switch to login page.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void logoutHandler(ActionEvent event) throws IOException {
        // Pop up confirmation to confirm if the user wants to log out
        // End session
        Library.switchScreen(event, Library.loginUrl);
    }

    /**
     * Switches user to reports page
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void reportsHandler(ActionEvent event) throws IOException {
        Library.switchScreen(event, Library.reportsUrl);
    }

    /**
     * Takes the selected customer and switches to the modify customer page.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     * @throws SQLException catches RUNTIME ERROR
     */
    @FXML
    void modifyHandler(ActionEvent event) throws IOException, SQLException {
        // Send the selected customer to the modify customer page
        if (customersTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer to modify");
            alert.showAndWait();
        }else {
            Customers.modifySelectedCustomer(this, customersTable, event);
        }
    }

    /**
     * Initialize form.
     * @param url of current form
     * @param resourceBundle bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setCustomersTable(customersTable, customerIdCol, customerNameCol, addressCol, postalCodeCol, phoneCol, createDateCol, createdByCol, lastUpdateCol, lastUpdatedByCol, divisionIdCol);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
