package com.example.WGUSoftware2.controller;

import com.example.WGUSoftware2.model.Appointments;
import com.example.WGUSoftware2.model.TimeZoneConverter;
import com.example.WGUSoftware2.utility.Library;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.converter.LocalTimeStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for adding appointments to the database
 */
public class AddAppointmentController implements Initializable {
    @FXML
    private TextField appointmentIdTxt;

    @FXML
    private ComboBox<Integer> contactIdCB;

    @FXML
    private ComboBox<Integer> customerIdCB;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private DatePicker endDateDP;

    @FXML
    private Spinner<LocalTime> startTimeSpinner;

    @FXML
    private TextField locationTxt;

    @FXML
    private DatePicker startDateDP;

    @FXML
    private Spinner<LocalTime> endTimeSpinner;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private ComboBox<Integer> userIdCB;

    /**
     * Saves the items in the text fields to the database and catches errors on user input and goes to the next page.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     * @throws SQLException catches RUNTIME ERROR
     */
    @FXML
    void addHandler(ActionEvent event) throws IOException, SQLException {
        // Save the items in the fields and run exception handling
        try {
            String title = titleTxt.getText();
            String description = descriptionTxt.getText();
            String location = locationTxt.getText();
            String type = typeTxt.getText();

            // Check if fields are empty
            if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Input fields cannot be empty.");
                alert.showAndWait();
                return;
            }

            int contactID = (int) contactIdCB.getValue();
            int customerID = (int) customerIdCB.getValue();
            int userID = (int) userIdCB.getValue();

            // Initial available dates and times
            LocalDate startDate = startDateDP.getValue();
            LocalTime startTime = startTimeSpinner.getValue();
            LocalDate endDate = endDateDP.getValue();
            LocalTime endTime = endTimeSpinner.getValue();

            // Combine LocalDate and LocalTime into LocalDateTime
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

            // Conversion of local date time to utc
            ZonedDateTime startDateTimeUtc = TimeZoneConverter.localToUtc(startDateTime);
            ZonedDateTime endDateTimeUtc = TimeZoneConverter.localToUtc(endDateTime);

            // Conversion of provided system (local) time to EST
            LocalDateTime startDateTimeEst = TimeZoneConverter.localToEst(startDateTime);
            LocalDateTime endDateTimeEst = TimeZoneConverter.localToEst(endDateTime);


            // Check if startDateTime is in the past
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/New_York"));

            // Business start and end time
            LocalTime businessStart = LocalTime.of(8, 0);
            LocalDateTime businessStartDateTime = startDateTimeEst.with(businessStart);
            LocalTime businessEnd = LocalTime.of(22, 0);
            LocalDateTime businessEndDateTime = startDateTimeEst.with(businessEnd);

            if (startDateTimeEst.isBefore(ChronoLocalDateTime.from(now))) {
                // Display an error message to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid appointment time");
                alert.setContentText("Appointments cannot be scheduled in the past.");
                alert.showAndWait();
                return; // Exit the method without saving the appointment
            }

            // Check if startDateTimeEst and endDateTimeEst are within business hours
            if (startDateTimeEst.toLocalTime().isBefore(businessStart) || endDateTimeEst.toLocalTime().isAfter(businessEnd)) {
                // Display an error message to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid appointment time");
                alert.setContentText("Appointments can only be scheduled between 8:00 a.m. and 10:00 p.m. EST, including weekends.");
                alert.showAndWait();
                return; // Exit the method without saving the appointment
            }

            // Check if appointment spans across a business day
            if (startDateTimeEst.isBefore(businessStartDateTime) || endDateTimeEst.isAfter(businessEndDateTime)){
                // Display an error message to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid appointment time");
                alert.setContentText("Appointments cannot span across days.");
                alert.showAndWait();
                return; // Exit the method without saving the appointment
            }

            // Check if startDateTimeEst is before endDateTimeEst
            if (startDateTimeEst.toLocalDate().isAfter(endDateTimeEst.toLocalDate())) {
                // Display an error message to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid appointment time");
                alert.setContentText("Start date must be before or equal to end date.");
                alert.showAndWait();
                return; // Exit the method without saving the appointment
            }

            // Check if startDateTimeEst is before endDateTimeEst
            if (!startDateTimeEst.toLocalTime().isBefore(endDateTimeEst.toLocalTime())) {
                // Display an error message to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid appointment time");
                alert.setContentText("Start time must be before end time.");
                alert.showAndWait();
                return; // Exit the method without saving the appointment
            }

            // Check for overlapping appointments
            if (Appointments.addCheckAppointmentOverlap(customerID, startDateTimeUtc, endDateTimeUtc)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Overlapping appointment");
                alert.setContentText("The selected customer already has an appointment scheduled during the specified time.");
                alert.showAndWait();
                return;
            }
            Appointments.insertAppointment(title, description, location, type, contactID, customerID, userID, startDateTimeUtc, endDateTimeUtc);
            Library.switchScreen(event, Library.appointmentsUrl);
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Please enter a valid value for each input field");
            alert.showAndWait();
        }catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Please enter a valid value for each input field");
            alert.showAndWait();
        }

    }

    /**
     * Cancels the process and shows a confirmation if the user wants to go back.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void cancelHandler(ActionEvent event) throws IOException {
        // Pop up confirmation box to confirm if the person wants to switch
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Library.switchScreen(event, Library.appointmentsUrl);
        }
    }

    /**
     * Initialize addappointment form and setup up the spinners to be used for times
     * @param url of the current form
     * @param resourceBundle bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Load available ids into combo box
            ObservableList<Integer> contactIDs = Appointments.getAllContactIDs();
            contactIdCB.setItems(contactIDs);
            ObservableList<Integer> customerIDs = Appointments.getAllCustomerIDs();
            customerIdCB.setItems(customerIDs);
            ObservableList<Integer> userIDs = Appointments.getAllUserIDs();
            userIdCB.setItems(userIDs);

            // Start time spinner
            SpinnerValueFactory<LocalTime> startTimeValueFactory = new SpinnerValueFactory<LocalTime>() {
                {
                    // Set initial value and wrap-around behavior
                    setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("h:mm a"), null));
                    setValue(LocalTime.of(8, 0));
                    setWrapAround(true);
                }

                /**
                 * decrements time spinner
                 * @param steps increments of 15
                 */
                @Override
                public void decrement(int steps) {
                    // Decrement the value by the specified number of steps
                    setValue(getValue().minusMinutes(steps * 15));
                }

                /**
                 * increments time spinner
                 * @param steps increments of 15
                 */
                @Override
                public void increment(int steps) {
                    // Increment the value by the specified number of steps
                    setValue(getValue().plusMinutes(steps * 15));
                }
            };

            // End time spinner
            SpinnerValueFactory<LocalTime> endTimeValueFactory = new SpinnerValueFactory<LocalTime>() {
                {
                    // Set initial value and wrap-around behavior
                    setConverter(new LocalTimeStringConverter(DateTimeFormatter.ofPattern("h:mm a"), null));
                    setValue(LocalTime.of(8, 15));
                    setWrapAround(false);
                }

                /**
                 * decrements time spinner
                 * @param steps increments of 15
                 */
                @Override
                public void decrement(int steps) {
                    // Decrement the value by the specified number of steps
                    setValue(getValue().minusMinutes(steps * 15));
                }

                /**
                 * increments time spinner
                 * @param steps increments of 15
                 */
                @Override
                public void increment(int steps) {
                    // Increment the value by the specified number of steps
                    setValue(getValue().plusMinutes(steps * 15));
                }
            };
            startTimeSpinner.setValueFactory(startTimeValueFactory);
            endTimeSpinner.setValueFactory(endTimeValueFactory);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
