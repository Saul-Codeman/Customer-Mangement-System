package com.example.WGUSoftware2.controller;

import com.example.WGUSoftware2.utility.Library;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller that allows the user to choose which page to go to
 * FUTURE ENHANCEMENT: Allow for users to view important flow charts or calender schedules.
 */
public class DashboardController implements Initializable {

    @FXML
    private Label upcomingAppointmentsLbl;

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

    /**
     * Switches to reports page
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void reportsHandler(ActionEvent event) throws IOException {
        Library.switchScreen(event, Library.reportsUrl);
    }

    /**
     * Initializes page
     * @param url of the form
     * @param resourceBundle bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Library.checkUpcomingAppointments(upcomingAppointmentsLbl);
    }
}
