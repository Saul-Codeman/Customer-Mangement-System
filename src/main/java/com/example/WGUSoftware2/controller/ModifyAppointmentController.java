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
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {

    private Appointments selectedAppointment;

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
    private Spinner<LocalTime> endTimeSpinner;

    @FXML
    private TextField locationTxt;

    @FXML
    private DatePicker startDateDP;

    @FXML
    private Spinner<LocalTime> startTimeSpinner;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField typeTxt;

    @FXML
    private ComboBox<Integer> userIdCB;

    /**
     * Confirms if the user wants to go back and switches to the appointments page.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void cancelHandler(ActionEvent event) throws IOException {
        // Pop up a confirmation to confirm if the user wants to go back
        Library.switchScreen(event, Library.appointmentsUrl);
    }

    /**
     * Takes the items in the text fields and updates the element in the database and switches to appointments page.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void modifyHandler(ActionEvent event) throws IOException {
        // Save the contents to the database and run exception handling
        try {
            int appointmentID = Integer.parseInt(appointmentIdTxt.getText());
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

            int contactID = contactIdCB.getValue();
            int customerID = customerIdCB.getValue();
            int userID = userIdCB.getValue();

            // Initial available dates
            LocalDate startDate = startDateDP.getValue();
            LocalTime startTime = startTimeSpinner.getValue();
            LocalDate endDate = endDateDP.getValue();
            LocalTime endTime = endTimeSpinner.getValue();

            // Combine dates and times
            LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
            LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

            // Conversion of local date time to utc
            ZonedDateTime startDateTimeUtc = TimeZoneConverter.localToUtc(startDateTime);
            ZonedDateTime endDateTimeUtc = TimeZoneConverter.localToUtc(endDateTime);

            // Conversion of provided system (local) time to EST
            LocalDateTime startDateTimeEst = TimeZoneConverter.localToEst(startDateTime);
            LocalDateTime endDateTimeEst = TimeZoneConverter.localToEst(endDateTime);


            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/New_York"));

            // Check if appointment has changed
            if (selectedAppointment.getTitle().equals(title)
                    && selectedAppointment.getDescription().equals(description)
                    && selectedAppointment.getLocation().equals(location)
                    && selectedAppointment.getType().equals(type)
                    && selectedAppointment.getStartDateTime().equals(startDateTime.atZone(ZoneId.systemDefault()))
                    && selectedAppointment.getEndDateTime().equals(endDateTime.atZone(ZoneId.systemDefault()))
                    && selectedAppointment.getCustomerID() == customerID
                    && selectedAppointment.getUserID() == userID
                    && selectedAppointment.getContactID() == contactID) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No changes made to the appointment.");
                alert.showAndWait();
                return;
            }
            // Check if startTime and endTime are within business hours
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
            if (startDateTimeEst.toLocalTime().isBefore(LocalTime.of(8, 0)) || endDateTimeEst.toLocalTime().isAfter(LocalTime.of(22, 0))) {
                // Display an error message to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Invalid appointment time");
                alert.setContentText("Appointments can only be scheduled between 8:00 a.m. and 10:00 p.m. EST, including weekends.");
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
            if (Appointments.modifyCheckAppointmentOverlap(customerID, startDateTimeUtc, endDateTimeUtc, appointmentID)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Overlapping appointment");
                alert.setContentText("The selected customer already has an appointment scheduled during the specified time.");
                alert.showAndWait();
                return;
            }
            Appointments.updateAppointment(appointmentID, title, description, location, type, contactID, customerID, userID, startDateTimeUtc, endDateTimeUtc);
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void sendAppointment(Appointments appointment) throws SQLException {
        Appointments.setAppointmentFields(appointment, appointmentIdTxt, titleTxt, descriptionTxt, locationTxt, typeTxt, contactIdCB, customerIdCB, userIdCB, startDateDP, endDateDP, startTimeSpinner, endTimeSpinner);
        selectedAppointment = appointment;
    }

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
                    setWrapAround(true);
                }

                @Override
                public void decrement(int steps) {
                    // Decrement the value by the specified number of steps
                    setValue(getValue().minusMinutes(steps * 15));
                }

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
                    setWrapAround(false);
                }

                @Override
                public void decrement(int steps) {
                    // Decrement the value by the specified number of steps
                    setValue(getValue().minusMinutes(steps * 15));
                }

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
