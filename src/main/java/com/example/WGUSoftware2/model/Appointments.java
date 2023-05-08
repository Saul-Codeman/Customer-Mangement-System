package com.example.WGUSoftware2.model;

import com.example.WGUSoftware2.controller.AppointmentsController;
import com.example.WGUSoftware2.controller.ModifyAppointmentController;
import com.example.WGUSoftware2.utility.Database;
import com.example.WGUSoftware2.utility.Library;
import com.example.WGUSoftware2.utility.UserSessionInfo;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * Appointments class that contains functions for the appointments and makes database calls
 */
public class Appointments {

    private Integer appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private ZonedDateTime createDate;
    private String createBy;
    private Timestamp lastUpdateDateTime;
    private String lastUpdateBy;
    private Integer customerID;
    private Integer userID;
    private Integer contactID;

    /**
     * Appointments class constructor
     * @param appointmentID integer primary key - unique auto generated appointment key
     * @param title varchar - appointment title
     * @param description varchar - short description of appointment
     * @param location varchar - location of appointment
     * @param type varchar - type of appointment
     * @param startDateTime datetime - appointment start time
     * @param endDateTime datetime - appointment end time
     * @param createDate datetime - appointment created date
     * @param createBy varchar - user who created appointment
     * @param lastUpdateDateTime datetime - time when appointment was most recently created or modified
     * @param lastUpdateBy varchar - user who updated appointment
     * @param customerID integer foreign key - customer with appointment
     * @param userID integer foreign key - user who set appointment
     * @param contactID integer foreign key - contact of appointment
     */
    public Appointments(Integer appointmentID, String title, String description, String location, String type,
                        ZonedDateTime startDateTime, ZonedDateTime endDateTime, ZonedDateTime createDate, String createBy,
                        Timestamp lastUpdateDateTime, String lastUpdateBy, Integer customerID, Integer userID,
                        Integer contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdateDateTime = lastUpdateDateTime;
        this.lastUpdateBy = lastUpdateBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Getter - appointment ID.
     * @return ID of the appointment.
     */
    public Integer getAppointmentID() {
        return appointmentID;
    }

    /**
     * Setter - appointment ID.
     * @param appointmentID the appointment ID to set.
     */
    public void setAppointmentID(Integer appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Getter - title.
     * @return title of the appointment.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter - title.
     * @param title the appointment title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter - description.
     * @return description of the appointment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter - description.
     * @param description the appointment description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter - location.
     * @return location of the appointment.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Setter - location.
     * @param location the appointment location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter - type.
     * @return type of the appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * Setter - type.
     * @param type the appointment type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter - start date time.
     * @return start date and time of the appointment.
     */
    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Setter - start date time.
     * @param startDateTime the appointment start date and time to set.
     */
    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Getter - end date time.
     * @return end date and time of the appointment.
     */
    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Setter - end date time.
     * @param endDateTime the appointment end date and time to set.
     */
    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Getter - create date.
     * @return create date of the appointment.
     */
    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Setter - create date.
     * @param createDate the appointment create date to set.
     */
    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter - create by.
     * @return creator of the appointment.
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * Setter - create by.
     * @param createBy the creator of the appointment to set.
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * Getter - last update date time.
     * @return the last update date and time of the appointment.
     */
    public Timestamp getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    /**
     * Setter - last update date time.
     * @param lastUpdateDateTime the appointment last update date and time to set.
     */
    public void setLastUpdateDateTime(Timestamp lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    /**
     * Getter - last update by.
     * @return the person who last updated the appointment.
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /**
     * Setter - last update by.
     * @param lastUpdateBy the person who last updated the appointment to set.
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /**
     * Getter - customer ID.
     * @return ID of the customer.
     */
    public Integer getCustomerID() {
        return customerID;
    }

    /**
     * Setter - customer ID.
     * @param customerID the customer ID to set.
     */
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    /**
     * Getter - user ID.
     * @return ID of the user.
     */
    public Integer getUserID() {
        return userID;
    }

    /**
     * Setter - user ID.
     * @param userID the user ID to set.
     */
    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    /**
     * Getter - contact ID.
     * @return ID of the contact.
     */
    public Integer getContactID() {
        return contactID;
    }

    /**
     * Setter - contact ID.
     * @param contactID the contact ID to set.
     */
    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }


    /**
     * Inserts a new appointment into the database with the given parameters.
     * @param title the title of the appointment.
     * @param description the description of the appointment.
     * @param location the location of the appointment.
     * @param type the type of the appointment.
     * @param contactID the ID of the contact associated with the appointment.
     * @param customerID the ID of the customer associated with the appointment.
     * @param userID the ID of the user associated with the appointment.
     * @param startDate the start date and time of the appointment.
     * @param endDate the end date and time of the appointment.
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void insertAppointment(String title, String description, String location, String type, Integer contactID, Integer customerID, Integer userID, ZonedDateTime startDate, ZonedDateTime endDate) throws SQLException {
        // Data entered by PC and user
        String createBy = UserSessionInfo.getCurrentUser().getUsername();
        Instant now = Instant.now();
        Instant createDate = now;
        String lastUpdateBy = UserSessionInfo.getCurrentUser().getUsername();
        Instant lastUpdate = now;

        // Data from appointment
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.from(startDate.toInstant()));
        ps.setTimestamp(6, Timestamp.from(endDate.toInstant()));
        ps.setTimestamp(7, Timestamp.from(createDate));
        ps.setString(8, createBy);
        ps.setTimestamp(9, Timestamp.from(lastUpdate));
        ps.setString(10, lastUpdateBy);
        ps.setInt(11, customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);
        int rowsAffected = ps.executeUpdate();

        if(rowsAffected > 0) {
            System.out.println("Insertion Successful!");
        }else{
            System.out.println("Insertion Failed");
        }
    }

    /**
     * Updates an existing appointment in the database with the given parameters.
     * @param appointmentID the ID of the appointment to update.
     * @param title the updated title of the appointment.
     * @param description the updated description of the appointment.
     * @param location the updated location of the appointment.
     * @param type the updated type of the appointment.
     * @param contactID the updated ID of the contact associated with the appointment.
     * @param customerID the updated ID of the customer associated with the appointment.
     * @param userID the updated ID of the user associated with the appointment.
     * @param startDateTime the updated start date and time of the appointment.
     * @param endDateTime the updated end date and time of the appointment.
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void updateAppointment(Integer appointmentID, String title, String description, String location, String type, Integer contactID, Integer customerID, Integer userID, ZonedDateTime startDateTime, ZonedDateTime endDateTime) throws SQLException {
        // Data entered by PC and user
        Instant now = Instant.now();
        String lastUpdateBy = UserSessionInfo.getCurrentUser().getUsername();
        Instant lastUpdate = now;

        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.from(startDateTime.toInstant()));
        ps.setTimestamp(6, Timestamp.from(endDateTime.toInstant()));
        ps.setTimestamp(7, Timestamp.from(lastUpdate));
        ps.setString(8, lastUpdateBy);
        ps.setInt(9, customerID);
        ps.setInt(10, userID);
        ps.setInt(11, contactID);
        ps.setInt(12, appointmentID);
        int rowsAffected = ps.executeUpdate();

        if(rowsAffected > 0) {
            System.out.println("Update Successful!");
        }else{
            System.out.println("Update Failed");
        }
    }

    /**
     * Deletes the appointment with the appointment ID provided
     * @param appointmentID id of the appointment to be deleted
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void deleteAppointment(Integer appointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        int rowsAffected = ps.executeUpdate();

        if(rowsAffected > 0) {
            System.out.println("Deletion Successful!");
        }else{
            System.out.println("Deletion Failed");
        }
    }

    /**
     * Sets the appointments table in the appointments form
     * @param appointmentsTable appointments tableview
     * @param appointmentIdCol appointment id col
     * @param titleCol title col
     * @param descriptionCol description col
     * @param locationCol location col
     * @param typeCol type col
     * @param startDateCol startDate col
     * @param startTimeCol startTime col
     * @param endDateCol endDate col
     * @param endTimeCol endTime col
     * @param customerIdCol customer ID col
     * @param userIdCol user ID col
     * @param contactIdCol contact ID col
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void setAppointmentsTable(TableView<Appointments> appointmentsTable, TableColumn<Appointments, Integer> appointmentIdCol, TableColumn<Appointments, String> titleCol, TableColumn<Appointments, String> descriptionCol, TableColumn<Appointments, String> locationCol, TableColumn<Appointments, String> typeCol, TableColumn<Appointments, ZonedDateTime> startDateCol, TableColumn<Appointments, ZonedDateTime> startTimeCol, TableColumn<Appointments, ZonedDateTime> endDateCol, TableColumn<Appointments, ZonedDateTime> endTimeCol, TableColumn<Appointments, Integer> customerIdCol, TableColumn<Appointments, Integer> userIdCol, TableColumn<Appointments, Integer> contactIdCol) throws SQLException {
        String sql = "SELECT * FROM appointments";
        ObservableList<Appointments> data = FXCollections.observableArrayList();
        data.clear();
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Go through each row of the result set and make an appointment
            while (rs.next()) {

                // Convert the utc times to user local
                ZonedDateTime startDateTimeUtc = rs.getTimestamp("Start").toInstant().atZone(ZoneId.of("UTC"));
                ZonedDateTime endDateTimeUtc = rs.getTimestamp("End").toInstant().atZone(ZoneId.of("UTC"));
                ZonedDateTime createdDateUtc = rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.of("UTC"));
                ZonedDateTime lastUpdateUtc = rs.getTimestamp("Last_Update").toInstant().atZone(ZoneId.of("UTC"));

                ZonedDateTime startDateTimeLocal = TimeZoneConverter.utcToLocal(startDateTimeUtc.toLocalDateTime());
                ZonedDateTime endDateTimeLocal = TimeZoneConverter.utcToLocal(endDateTimeUtc.toLocalDateTime());
                ZonedDateTime createdDateLocal = TimeZoneConverter.utcToLocal(createdDateUtc.toLocalDateTime());
                ZonedDateTime lastUpdateLocal = TimeZoneConverter.utcToLocal(lastUpdateUtc.toLocalDateTime());



                Appointments appointment = new Appointments(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        startDateTimeLocal,
                        endDateTimeLocal,
                        createdDateLocal,
                        rs.getString("Created_By"),
                        Timestamp.from(Instant.from(lastUpdateLocal)),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID")
                );
                data.add(appointment);
            }
            rs.close();
            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        // Set items into table
        appointmentsTable.setItems(data);
        // Specific columns to be set
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellValueFactory(cellData -> {
            ZonedDateTime start1 = cellData.getValue().getStartDateTime();
            String startTime = start1.format(DateTimeFormatter.ofPattern("hh:mm a"));
            return new SimpleObjectProperty(startTime);
        });
        endTimeCol.setCellValueFactory(cellData -> {
            ZonedDateTime end1 = cellData.getValue().getEndDateTime();
            String endTime = end1.format(DateTimeFormatter.ofPattern("hh:mm a"));
            return new SimpleObjectProperty(endTime);
        });
        startDateCol.setCellValueFactory(cellData -> {
            ZonedDateTime start2 = cellData.getValue().getStartDateTime();
            String startDate = start2.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(startDate);
        });

        endDateCol.setCellValueFactory(cellData -> {
            ZonedDateTime end2 = cellData.getValue().getEndDateTime();
            String endDate = end2.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(endDate);
        });
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
    }

    /**
     * Returns a list of unique customer IDs
     * @return ObservableList
     * @throws SQLException catches RUNTIME ERROR
     */
    public static ObservableList<Integer> getAllCustomerIDs() throws SQLException{
        String sql = "SELECT DISTINCT Customer_ID FROM customers";
        ObservableList<Integer> customerIDs = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                customerIDs.add(rs.getInt("Customer_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerIDs;
    }
    /**
     * Returns a list of unique contact IDs
     * @return ObservableList
     * @throws SQLException catches RUNTIME ERROR
     */
    public static ObservableList<Integer> getAllContactIDs() throws SQLException{
        String sql = "SELECT DISTINCT Contact_ID FROM contacts";
        ObservableList<Integer> contactIDs = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                contactIDs.add(rs.getInt("Contact_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactIDs;
    }
    /**
     * Returns a list of unique user IDs
     * @return ObservableList
     * @throws SQLException catches RUNTIME ERROR
     */
    public static ObservableList<Integer> getAllUserIDs() throws SQLException{
        String sql = "SELECT DISTINCT User_ID FROM users";
        ObservableList<Integer> userIDs = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                userIDs.add(rs.getInt("User_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userIDs;
    }


    /**
     * Checks appointment overlaps when a new appointment is added.
     * @param customerID id of the customer to be checked against
     * @param startDateTime startdatetime of appointment
     * @param endDateTime enddatetime of appointment
     * @return boolean
     * @throws SQLException catches RUNTIME ERROR
     */
    public static boolean addCheckAppointmentOverlap(int customerID, ZonedDateTime startDateTime, ZonedDateTime endDateTime) throws SQLException {
        // Create SQL query to check for overlapping appointments
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ? AND (? BETWEEN Start AND End OR ? BETWEEN Start AND End OR Start BETWEEN ? AND ? OR End BETWEEN ? AND ?)";

        // Create PreparedStatement object and set parameters
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.setTimestamp(2, Timestamp.valueOf(startDateTime.toLocalDateTime()));
        ps.setTimestamp(3, Timestamp.valueOf(endDateTime.toLocalDateTime()));
        ps.setTimestamp(4, Timestamp.valueOf(startDateTime.toLocalDateTime()));
        ps.setTimestamp(5, Timestamp.valueOf(endDateTime.toLocalDateTime()));
        ps.setTimestamp(6, Timestamp.valueOf(startDateTime.toLocalDateTime()));
        ps.setTimestamp(7, Timestamp.valueOf(endDateTime.toLocalDateTime()));

        // Execute query and get result set
        ResultSet rs = ps.executeQuery();

        // If there are any rows in the result set, there is an overlapping appointment
        return rs.next();
    }

    /**
     * Checks for appointment overlaps once an appointment has been modified
     * @param customerID customer ID to be checked against
     * @param startDateTime startdatetime of appointment
     * @param endDateTime enddatetime of appointment
     * @param appointmentID appointment ID of the modified appointment
     * @return boolean
     * @throws SQLException catches RUNTIME ERROR
     */
    public static boolean modifyCheckAppointmentOverlap(int customerID, ZonedDateTime startDateTime, ZonedDateTime endDateTime, int appointmentID) throws SQLException {
        // Create SQL query to check for overlapping appointments
        String sql =  "SELECT * FROM appointments WHERE Customer_ID = ? AND (? BETWEEN Start AND End OR ? BETWEEN Start AND End OR (Start BETWEEN ? AND ? AND End BETWEEN ? AND ?)) AND Appointment_ID != ?";

        // Create PreparedStatement object and set parameters
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ps.setTimestamp(2, Timestamp.valueOf(startDateTime.toLocalDateTime()));
        ps.setTimestamp(3, Timestamp.valueOf(endDateTime.toLocalDateTime()));
        ps.setTimestamp(4, Timestamp.valueOf(startDateTime.toLocalDateTime()));
        ps.setTimestamp(5, Timestamp.valueOf(endDateTime.toLocalDateTime()));
        ps.setTimestamp(6, Timestamp.valueOf(startDateTime.toLocalDateTime()));
        ps.setTimestamp(7, Timestamp.valueOf(endDateTime.toLocalDateTime()));
        ps.setInt(8, appointmentID);

        // Execute query and get result set
        ResultSet rs = ps.executeQuery();

        // If there are any rows in the result set, there is an overlapping appointment
        return rs.next();
    }

    /**
     * Takes the selected appointment of the appointments table to the modify appointment page through other functions
     * @param appointmentsController controller where appointment is from
     * @param appointmentsTableView tableview of appointment
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void modifySelectedAppointment(AppointmentsController appointmentsController, TableView<Appointments> appointmentsTableView, ActionEvent event) throws IOException, SQLException {
            // Get the result set of the selected item
            Integer appointmentID = appointmentsTableView.getSelectionModel().getSelectedItem().getAppointmentID();
            String sql = "SELECT * FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ps.setInt(1, appointmentID);
            ResultSet rs = ps.executeQuery();
            // Make an appointment object from the result set
            Appointments appointment = null;
            if (rs.next()) {
                appointment = new Appointments(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update"),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID")
                );
            }
            // Close rs and ps
            rs.close();
            ps.close();

            // Prepare the next page
            if (appointment != null) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(appointmentsController.getClass().getResource(Library.modifyAppointmentUrl));
                loader.load();
                ModifyAppointmentController modifyAppointmentController = loader.getController();
                // Send the item selected to the controller to set the fields
                modifyAppointmentController.sendAppointment(appointment);
                // Show the next page
                appointmentsController.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                appointmentsController.stage.setScene(new Scene(scene));
                appointmentsController.stage.show();
            }
    }

    /**
     * Sets the fields of an appointment object with the given parameters.
     * @param appointment the appointment object to set the fields for.
     * @param appointmentID the TextField containing the appointment ID.
     * @param title the TextField containing the appointment title.
     * @param description the TextField containing the appointment description.
     * @param location the TextField containing the appointment location.
     * @param type the TextField containing the appointment type.
     * @param contactID the ComboBox containing the selected contact ID.
     * @param customerID the ComboBox containing the selected customer ID.
     * @param userID the ComboBox containing the selected user ID.
     * @param startDate the DatePicker containing the appointment start date.
     * @param endDate the DatePicker containing the appointment end date.
     * @param startTime the Spinner containing the appointment start time.
     * @param endTime the Spinner containing the appointment end time.
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void setAppointmentFields(Appointments appointment, TextField appointmentID, TextField title, TextField description, TextField location, TextField type, ComboBox<Integer> contactID, ComboBox<Integer> customerID, ComboBox<Integer> userID, DatePicker startDate, DatePicker endDate, Spinner<LocalTime> startTime, Spinner<LocalTime> endTime) throws SQLException {
        // Set Fields equal to value of result set
        appointmentID.setText(String.valueOf(appointment.getAppointmentID()));
        title.setText(appointment.getTitle());
        description.setText(appointment.getDescription());
        location.setText(appointment.getLocation());
        type.setText(appointment.getType());
        contactID.setValue(appointment.getContactID());
        customerID.setValue(appointment.getCustomerID());
        userID.setValue(appointment.getUserID());
        ZonedDateTime startDateTime = appointment.getStartDateTime().withZoneSameInstant(ZoneId.systemDefault());
        startDate.setValue(startDateTime.toLocalDate());
        startTime.getValueFactory().setValue(startDateTime.toLocalTime());
        ZonedDateTime endDateTime = appointment.getEndDateTime().withZoneSameInstant(ZoneId.systemDefault());
        endDate.setValue(endDateTime.toLocalDate());
        endTime.getValueFactory().setValue(endDateTime.toLocalTime());

    }

    /**
     * Filters the appointments table to display appointments for the month.
     * @param appointmentsTable the TableView containing the appointments.
     * @param appointmentIdCol the TableColumn containing the appointment ID.
     * @param titleCol the TableColumn containing the appointment title.
     * @param descriptionCol the TableColumn containing the appointment description.
     * @param locationCol the TableColumn containing the appointment location.
     * @param typeCol the TableColumn containing the appointment type.
     * @param startDateCol the TableColumn containing the appointment start date.
     * @param startTimeCol the TableColumn containing the appointment start time.
     * @param endDateCol the TableColumn containing the appointment end date.
     * @param endTimeCol the TableColumn containing the appointment end time.
     * @param customerIdCol the TableColumn containing the customer ID.
     * @param userIdCol the TableColumn containing the user ID.
     * @param contactIdCol the TableColumn containing the contact ID.
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void sortMonth(TableView<Appointments> appointmentsTable, TableColumn<Appointments, Integer> appointmentIdCol, TableColumn<Appointments, String> titleCol, TableColumn<Appointments, String> descriptionCol, TableColumn<Appointments, String> locationCol, TableColumn<Appointments, String> typeCol, TableColumn<Appointments, ZonedDateTime> startDateCol, TableColumn<Appointments, ZonedDateTime> startTimeCol, TableColumn<Appointments, ZonedDateTime> endDateCol, TableColumn<Appointments, ZonedDateTime> endTimeCol, TableColumn<Appointments, Integer> customerIdCol, TableColumn<Appointments, Integer> userIdCol, TableColumn<Appointments, Integer> contactIdCol) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE MONTH(Start) = ? ORDER BY Start ASC";
        int month = LocalDateTime.now().getMonthValue();
        ObservableList<Appointments> data = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ps.setInt(1, month);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointments appointment = new Appointments(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update"),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID")
                );
                data.add(appointment);
            }
            // Close rs and ps
            rs.close();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        // Set items into table
        appointmentsTable.setItems(data);
        // Specific columns to be set
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellValueFactory(cellData -> {
            ZonedDateTime start1 = cellData.getValue().getStartDateTime();
            String startTime = start1.format(DateTimeFormatter.ofPattern("hh:mm a"));
            return new SimpleObjectProperty(startTime);
        });
        endTimeCol.setCellValueFactory(cellData -> {
            ZonedDateTime end1 = cellData.getValue().getEndDateTime();
            String endTime = end1.format(DateTimeFormatter.ofPattern("hh:mm a"));
            return new SimpleObjectProperty(endTime);
        });
        startDateCol.setCellValueFactory(cellData -> {
            ZonedDateTime start2 = cellData.getValue().getStartDateTime();
            String startDate = start2.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(startDate);
        });

        endDateCol.setCellValueFactory(cellData -> {
            ZonedDateTime end2 = cellData.getValue().getEndDateTime();
            String endDate = end2.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(endDate);
        });
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
    }

    /**
     * Filters the appointments table to display appointments for the current week.
     * @param appointmentsTable the TableView containing the appointments.
     * @param appointmentIdCol the TableColumn containing the appointment ID.
     * @param titleCol the TableColumn containing the appointment title.
     * @param descriptionCol the TableColumn containing the appointment description.
     * @param locationCol the TableColumn containing the appointment location.
     * @param typeCol the TableColumn containing the appointment type.
     * @param startDateCol the TableColumn containing the appointment start date.
     * @param startTimeCol the TableColumn containing the appointment start time.
     * @param endDateCol the TableColumn containing the appointment end date.
     * @param endTimeCol the TableColumn containing the appointment end time.
     * @param customerIdCol the TableColumn containing the customer ID.
     * @param userIdCol the TableColumn containing the user ID.
     * @param contactIdCol the TableColumn containing the contact ID.
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void sortWeek(TableView<Appointments> appointmentsTable, TableColumn<Appointments, Integer> appointmentIdCol, TableColumn<Appointments, String> titleCol, TableColumn<Appointments, String> descriptionCol, TableColumn<Appointments, String> locationCol, TableColumn<Appointments, String> typeCol, TableColumn<Appointments, ZonedDateTime> startDateCol, TableColumn<Appointments, ZonedDateTime> startTimeCol, TableColumn<Appointments, ZonedDateTime> endDateCol, TableColumn<Appointments, ZonedDateTime> endTimeCol, TableColumn<Appointments, Integer> customerIdCol, TableColumn<Appointments, Integer> userIdCol, TableColumn<Appointments, Integer> contactIdCol) throws SQLException {

        LocalDate today = LocalDate.now();
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDate endOfWeek = startOfWeek.plusDays(7);

        String sql = "SELECT * FROM appointments WHERE Start >= ? AND Start <= ? ORDER BY Start ASC";
        ObservableList<Appointments> data = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ps.setObject(1, startOfWeek.atStartOfDay(ZoneId.systemDefault()));
            ps.setObject(2, endOfWeek.plusDays(1).atStartOfDay(ZoneId.systemDefault()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointments appointment = new Appointments(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update"),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID")
                );
                data.add(appointment);
            }
            // Close rs and ps
            rs.close();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        // Set items into table
        appointmentsTable.setItems(data);
        // Specific columns to be set
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellValueFactory(cellData -> {
            ZonedDateTime start1 = cellData.getValue().getStartDateTime();
            String startTime = start1.format(DateTimeFormatter.ofPattern("hh:mm a"));
            return new SimpleObjectProperty(startTime);
        });
        endTimeCol.setCellValueFactory(cellData -> {
            ZonedDateTime end1 = cellData.getValue().getEndDateTime();
            String endTime = end1.format(DateTimeFormatter.ofPattern("hh:mm a"));
            return new SimpleObjectProperty(endTime);
        });
        startDateCol.setCellValueFactory(cellData -> {
            ZonedDateTime start2 = cellData.getValue().getStartDateTime();
            String startDate = start2.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(startDate);
        });

        endDateCol.setCellValueFactory(cellData -> {
            ZonedDateTime end2 = cellData.getValue().getEndDateTime();
            String endDate = end2.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(endDate);
        });
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
    }

    /**
     * Sets the appointments table to display appointments associated with the specified contact id.
     * @param contactID the ID of the contact to filter appointments by.
     * @param appointmentsTable the TableView containing the appointments.
     * @param appointmentIdCol the TableColumn containing the appointment ID.
     * @param titleCol the TableColumn containing the appointment title.
     * @param descriptionCol the TableColumn containing the appointment description.
     * @param typeCol the TableColumn containing the appointment type.
     * @param startDateCol the TableColumn containing the appointment start date.
     * @param startTimeCol the TableColumn containing the appointment start time.
     * @param endDateCol the TableColumn containing the appointment end date.
     * @param endTimeCol the TableColumn containing the appointment end time.
     * @param customerIdCol the TableColumn containing the customer ID.
     * @param contactIdCol the TableColumn containing the contact ID.
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void setContactScheduleAppointments(int contactID, TableView<Appointments> appointmentsTable, TableColumn<Appointments, Integer> appointmentIdCol, TableColumn<Appointments, String> titleCol, TableColumn<Appointments, String> descriptionCol, TableColumn<Appointments, String> typeCol, TableColumn<Appointments, ZonedDateTime> startDateCol, TableColumn<Appointments, ZonedDateTime> startTimeCol, TableColumn<Appointments, ZonedDateTime> endDateCol, TableColumn<Appointments, ZonedDateTime> endTimeCol, TableColumn<Appointments, Integer> customerIdCol, TableColumn<Appointments, Integer> contactIdCol) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        ObservableList<Appointments> data = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointments appointment = new Appointments(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update"),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID")
                );
                data.add(appointment);
            }
            // Close rs and ps
            rs.close();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        // Set items into table
        appointmentsTable.setItems(data);
        // Specific columns to be set
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellValueFactory(cellData -> {
            ZonedDateTime start1 = cellData.getValue().getStartDateTime();
            String startTime = start1.format(DateTimeFormatter.ofPattern("hh:mm a"));
            return new SimpleObjectProperty(startTime);
        });
        endTimeCol.setCellValueFactory(cellData -> {
            ZonedDateTime end1 = cellData.getValue().getEndDateTime();
            String endTime = end1.format(DateTimeFormatter.ofPattern("hh:mm a"));
            return new SimpleObjectProperty(endTime);
        });
        startDateCol.setCellValueFactory(cellData -> {
            ZonedDateTime start2 = cellData.getValue().getStartDateTime();
            String startDate = start2.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(startDate);
        });

        endDateCol.setCellValueFactory(cellData -> {
            ZonedDateTime end2 = cellData.getValue().getEndDateTime();
            String endDate = end2.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(endDate);
        });
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));

    }

    /**
     * Sets the appointments table to display appointments associated with the specified customer ID.
     * @param customerID the ID of the customer to filter appointments by.
     * @param appointmentsTable the TableView containing the appointments.
     * @param appointmentIdCol the TableColumn containing the appointment ID.
     * @param titleCol the TableColumn containing the appointment title.
     * @param descriptionCol the TableColumn containing the appointment description.
     * @param typeCol the TableColumn containing the appointment type.
     * @param startDateCol the TableColumn containing the appointment start date.
     * @param startTimeCol the TableColumn containing the appointment start time.
     * @param endDateCol the TableColumn containing the appointment end date.
     * @param endTimeCol the TableColumn containing the appointment end time.
     * @param customerIdCol the TableColumn containing the customer ID.
     * @param contactIdCol the TableColumn containing the contact ID.
     */
    public static void setCustomerAppointments(int customerID, TableView<Appointments> appointmentsTable, TableColumn<Appointments, Integer> appointmentIdCol, TableColumn<Appointments, String> titleCol, TableColumn<Appointments, String> descriptionCol, TableColumn<Appointments, String> typeCol, TableColumn<Appointments, ZonedDateTime> startDateCol, TableColumn<Appointments, ZonedDateTime> startTimeCol, TableColumn<Appointments, ZonedDateTime> endDateCol, TableColumn<Appointments, ZonedDateTime> endTimeCol, TableColumn<Appointments, Integer> customerIdCol, TableColumn<Appointments, Integer> contactIdCol){
        String sql = "SELECT * FROM appointments JOIN customers ON appointments.Customer_ID = customers.Customer_ID WHERE customers.Customer_ID = ? ORDER BY MONTH(appointments.Start) ASC";
        ObservableList<Appointments> data = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointments appointment = new Appointments(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update"),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID")
                );
                data.add(appointment);
            }
            // Close rs and ps
            rs.close();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        // Set items into table
        appointmentsTable.setItems(data);
        // Specific columns to be set
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startTimeCol.setCellValueFactory(cellData -> {
            ZonedDateTime start1 = cellData.getValue().getStartDateTime();
            String startTime = start1.format(DateTimeFormatter.ofPattern("hh:mm a"));
            return new SimpleObjectProperty(startTime);
        });
        endTimeCol.setCellValueFactory(cellData -> {
            ZonedDateTime end1 = cellData.getValue().getEndDateTime();
            String endTime = end1.format(DateTimeFormatter.ofPattern("hh:mm a"));
            return new SimpleObjectProperty(endTime);
        });
        startDateCol.setCellValueFactory(cellData -> {
            ZonedDateTime start2 = cellData.getValue().getStartDateTime();
            String startDate = start2.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(startDate);
        });

        endDateCol.setCellValueFactory(cellData -> {
            ZonedDateTime end2 = cellData.getValue().getEndDateTime();
            String endDate = end2.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(endDate);
        });
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
    }
}

