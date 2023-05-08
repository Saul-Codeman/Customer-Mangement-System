package com.example.WGUSoftware2.controller;

import com.example.WGUSoftware2.model.Customers;
import com.example.WGUSoftware2.utility.Library;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    @FXML
    private TextField addressTxt;

    @FXML
    private ComboBox<String> countryDropDown;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private ComboBox<String> divisionDropDown;

    @FXML
    private TextField phoneTxt;

    @FXML
    private TextField postalCodeTxt;

    /**
     * Saves the items in the text fields to the database and catches errors on user input and goes to the next page.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void addHandler(ActionEvent event) throws IOException {
        // Save the new customer to the database and handle exceptions
        try{
            String customerName = customerNameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = postalCodeTxt.getText();
            String phone = phoneTxt.getText();
            String country = countryDropDown.getValue();
            String division = divisionDropDown.getValue();
            int divisionID = Customers.searchDivisionID(division);

            if (customerName.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || country.isEmpty() || division.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Input fields cannot be empty.");
                alert.showAndWait();
                return;
            }
            if (divisionID == 0){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a valid country and division.");
                alert.showAndWait();
                return;
            }
            Customers.insertCustomer(customerName, address, postalCode, phone, divisionID);
            Library.switchScreen(event, Library.customersUrl);
        }catch(NumberFormatException | NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Please enter a valid value for each input field");
            alert.showAndWait();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Cancels the process and shows a confirmation if the user wants to go back.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void cancelHandler(ActionEvent event) throws IOException {
        // Pop up confirmation page to confirm if the user wants to go back
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Library.switchScreen(event, Library.customersUrl);
        }
    }

    /**
     * Initialize addcustomer form
     * @param url url
     * @param resourceBundle bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> countries = FXCollections.observableArrayList("Canada", "UK", "US");
        countryDropDown.setItems(countries);

        divisionDropDown.setDisable(true);
        countryDropDown.setOnAction(event -> {
            if (countryDropDown.getSelectionModel().getSelectedItem() != null) {
                divisionDropDown.setDisable(false); // Enable the division combo box when a country is selected
                switch (countryDropDown.getValue()) {
                    case "Canada":
                        try {
                            divisionDropDown.setItems(Library.getCanadianDivisions());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        break;
                    case "US":
                        try {
                            divisionDropDown.setItems(Library.getAmericanDivisions());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        break;
                    case "UK":
                        try {
                            divisionDropDown.setItems(Library.getUKDivisions());
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        break;
                }
            } else {
                divisionDropDown.setDisable(true); // Disable the division combo box if no country is selected
            }
        });
    }
}
