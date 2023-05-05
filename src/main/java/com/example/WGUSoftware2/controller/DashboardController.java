package com.example.WGUSoftware2.controller;

import com.example.WGUSoftware2.utility.Library;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    /**
     * Switches to appointments page.
     * @param event action on a button
     * @throws IOException catches a RUNTIME ERROR
     */
    @FXML
    void appointmentsHandler(ActionEvent event) throws IOException {
        Library.switchScreen(event, Library.appointmentsUrl);
    }

    /**
     * Switches to customers page.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void customersHandler(ActionEvent event) throws IOException {
        Library.switchScreen(event, Library.customersUrl);
    }

    @FXML
    void reportsHandler(ActionEvent event) throws IOException {
        Library.switchScreen(event, Library.reportsUrl);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Library.checkUpcomingAppointments();
    }
}
