package com.example.WGUSoftware2.controller;


import com.example.WGUSoftware2.model.Appointments;
import com.example.WGUSoftware2.utility.Library;
import com.example.WGUSoftware2.utility.UserSessionInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

import static com.example.WGUSoftware2.model.Appointments.setAppointmentsTable;

public class AppointmentsController implements Initializable {

    // Handlers
    public Stage stage;

    @FXML
    private TableColumn<Appointments, Integer> appointmentIdCol;

    @FXML
    private TableView<Appointments> appointmentTable;

    @FXML
    private TableColumn<Appointments, Integer> userIdCol;

    @FXML
    private TableColumn<Appointments, Integer> customerIdCol;

    @FXML
    private TableColumn<Appointments, Integer> contactIdCol;

    @FXML
    private TableColumn<Appointments, String> descriptionCol;

    @FXML
    private TableColumn<Appointments, ZonedDateTime> endDateCol;

    @FXML
    private TableColumn<Appointments, ZonedDateTime> endTimeCol;

    @FXML
    private TableColumn<Appointments, String> locationCol;

    @FXML
    private RadioButton showAllRB;

    @FXML
    private RadioButton sortMonthRB;

    @FXML
    private ToggleGroup sortTG;

    @FXML
    private RadioButton sortWeekRB;

    @FXML
    private TableColumn<Appointments, ZonedDateTime> startDateCol;

    @FXML
    private TableColumn<Appointments, ZonedDateTime> startTimeCol;

    @FXML
    private TableColumn<Appointments, String> titleCol;

    @FXML
    private TableColumn<Appointments, String> typeCol;

    /**
     * Switches to add appointment page.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void addHandler(ActionEvent event) throws IOException {
        Library.switchScreen(event, Library.addAppointmentUrl);
    }

    /**
     * Switches to customer page.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void customersHandler(ActionEvent event) throws IOException {
        Library.switchScreen(event, Library.customersUrl);
    }

    /**
     * Deletes the selected item in the table after showing a confirmation
     * LOGIC ERROR: Fixed a bug that would call the setAppointmentsTable function after any deletion which would reset
     * the table to the sortAll form instead of going back to the respective button
     * @param event action on a button
     */
    @FXML
    void deleteHandler(ActionEvent event) throws SQLException {
        if (appointmentTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to delete");
            alert.showAndWait();
        }else {
            Appointments.deleteAppointment(appointmentTable.getSelectionModel().getSelectedItem().getAppointmentID());
            if (sortWeekRB.isSelected()){
                Appointments.sortWeek(appointmentTable, appointmentIdCol, titleCol, descriptionCol, locationCol, typeCol, startDateCol, startTimeCol, endDateCol, endTimeCol, customerIdCol, userIdCol, contactIdCol);
            }else if (sortMonthRB.isSelected()){
                Appointments.sortMonth(appointmentTable, appointmentIdCol, titleCol, descriptionCol, locationCol, typeCol, startDateCol, startTimeCol, endDateCol, endTimeCol, customerIdCol, userIdCol, contactIdCol);
            }else{
                setAppointmentsTable(appointmentTable, appointmentIdCol, titleCol, descriptionCol, locationCol, typeCol, startDateCol, startTimeCol, endDateCol, endTimeCol, customerIdCol, userIdCol, contactIdCol);
            }
        }
    }

    @FXML
    void logoutHandler(ActionEvent event) throws IOException {
        // End session
        Library.switchScreen(event, Library.loginUrl);
    }

    @FXML
    void reportsHandler(ActionEvent event) throws IOException {
        Library.switchScreen(event, Library.reportsUrl);
    }

    /**
     * Takes the selected appointment and switches to modify appointment page.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void modifyHandler(ActionEvent event) throws IOException, SQLException {
        // Send selected appointment to next page
        if (appointmentTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to modify");
            alert.showAndWait();
        }else {
            Appointments.modifySelectedAppointment(this, appointmentTable, event);
        }
    }

    /**
     * Shows all appointments.
     * @param event action on a radio button
     */
    @FXML
    void sortAll(ActionEvent event) {
        try {
            setAppointmentsTable(appointmentTable, appointmentIdCol, titleCol, descriptionCol, locationCol, typeCol, startDateCol, startTimeCol, endDateCol, endTimeCol, customerIdCol, userIdCol, contactIdCol);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Shows appointments by month.
     * @param event action on a radio button
     */
    @FXML
    void sortMonth(ActionEvent event) throws SQLException {
        Appointments.sortMonth(appointmentTable, appointmentIdCol, titleCol, descriptionCol, locationCol, typeCol, startDateCol, startTimeCol, endDateCol, endTimeCol, customerIdCol, userIdCol, contactIdCol);
    }

    /**
     * Shows appointments by week.
     * @param event action on a radio button
     */
    @FXML
    void sortWeek(ActionEvent event) throws SQLException {
        Appointments.sortWeek(appointmentTable, appointmentIdCol, titleCol, descriptionCol, locationCol, typeCol, startDateCol, startTimeCol, endDateCol, endTimeCol, customerIdCol, userIdCol, contactIdCol);
    }

    /**
     * Initialize form.
     * @param url of current form
     * @param resourceBundle bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("User's time zone: " + UserSessionInfo.getCurrentUserTimeZone());
            setAppointmentsTable(appointmentTable, appointmentIdCol, titleCol, descriptionCol, locationCol, typeCol, startDateCol, startTimeCol, endDateCol, endTimeCol, customerIdCol, userIdCol, contactIdCol);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
