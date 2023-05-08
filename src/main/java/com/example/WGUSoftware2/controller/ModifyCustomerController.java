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

public class ModifyCustomerController implements Initializable {
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
     * Confirms if user wants to switches pages and switches to customers page.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void cancelHandler(ActionEvent event) throws IOException {
        // Pop up a confirmation to confirm if the user wants to go back
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel any changes made, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Library.switchScreen(event, Library.customersUrl);
        }
    }

    /**
     * Takes the elements from the text fields and updates the element in the database and switches to customers page.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void modifyHandler(ActionEvent event) throws IOException {
        // Save changes made to the existing customer and handle exceptions
        try{
            int customerID = Integer.parseInt(customerIdTxt.getText());
            String customerName = customerNameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = postalCodeTxt.getText();
            String phone = phoneTxt.getText();
            String division = divisionDropDown.getValue();
            String country = countryDropDown.getValue();

            int divisionID = Customers.searchDivisionID(division);

            // Check if fields are empty
            if (customerName.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || division.isEmpty() || country.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Input fields cannot be empty.");
                alert.showAndWait();
                return;
            }
            // Used to handle a bug that used to occur that didnt change division when country changed
            if ((country.equals("Canada") && !Library.getCanadianDivisions().contains(division)) ||
                    (country.equals("US") && !Library.getAmericanDivisions().contains(division)) ||
                    (country.equals("UK") && !Library.getUKDivisions().contains(division))){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a valid country / division pair.");
                alert.showAndWait();
                return;
            }

            Customers.updateCustomer(customerID, customerName, address, postalCode, phone, divisionID);
            Library.switchScreen(event, Library.customersUrl);
        }catch (NumberFormatException | NullPointerException | SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Please enter a valid value for each input field");
            alert.showAndWait();
        }
    }

    /**
     * Receives the selected customer from the customers page, and calls a function to set the customer fields.
     * @param customer selected customer
     * @throws SQLException catches RUNTIME ERROR
     */
    public void sendCustomer(Customers customer) throws SQLException {
        Customers.setCustomerFields(customer, customerIdTxt, customerNameTxt, addressTxt, postalCodeTxt, phoneTxt, countryDropDown, divisionDropDown);
    }

    /**
     * Initializes the form and sets up combo boxes for countries and divisions
     * @param url of the form
     * @param resourceBundle bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> countries = FXCollections.observableArrayList("Canada", "UK", "US");
        countryDropDown.setItems(countries);

        countryDropDown.setOnAction(event -> {
            if (countryDropDown.getValue() != null) {
                switch (countryDropDown.getSelectionModel().getSelectedItem()) {
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
            }
        });
    }
}

