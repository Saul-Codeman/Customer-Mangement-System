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
import java.time.temporal.WeekFields;
import java.util.Locale;

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

    public Integer getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Integer appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Timestamp getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    public void setLastUpdateDateTime(Timestamp lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getContactID() {
        return contactID;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }


    public static void insertAppointment(String title, String description, String location, String type, Integer contactID, Integer customerID, Integer userID, ZonedDateTime startDate, ZonedDateTime endDate) throws SQLException {
        // Data entered by PC and user
        String createBy = "user";
        ZonedDateTime createDate = TimeZoneConverter.localToUtc(ZonedDateTime.now().toLocalDateTime());
        String lastUpdateBy = "user";
        Timestamp lastUpdateDateTime = Timestamp.valueOf(LocalDateTime.now().atZone(UserSessionInfo.getCurrentUserTimeZone()).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime());

        // Data from appointment
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(startDate.toLocalDateTime()));
        ps.setTimestamp(6, Timestamp.valueOf(endDate.toLocalDateTime()));
        ps.setTimestamp(7, Timestamp.valueOf(createDate.toLocalDateTime()));
        ps.setString(8, createBy);
        ps.setTimestamp(9, lastUpdateDateTime);
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

    public static void updateAppointment(Integer appointmentID, String title, String description, String location, String type, Integer contactID, Integer customerID, Integer userID, ZonedDateTime startDateTime, ZonedDateTime endDateTime) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(startDateTime.toLocalDateTime()));
        ps.setTimestamp(6, Timestamp.valueOf(endDateTime.toLocalDateTime()));
        ps.setInt(7, customerID);
        ps.setInt(8, userID);
        ps.setInt(9, contactID);
        ps.setInt(10, appointmentID);
        int rowsAffected = ps.executeUpdate();

        if(rowsAffected > 0) {
            System.out.println("Update Successful!");
        }else{
            System.out.println("Update Failed");
        }
    }
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
                ZonedDateTime startDateTime = TimeZoneConverter.utcToLocal(rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.of("UTC")));
                ZonedDateTime endDateTime = TimeZoneConverter.utcToLocal(rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.of("UTC")));

                System.out.println("User Local Start Time: " + startDateTime);
                System.out.println("User Local End Time: " + endDateTime);

                Appointments appointment = new Appointments(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Type"),
                        startDateTime,
                        endDateTime,
                        rs.getTimestamp("Create_Date").toLocalDateTime().atZone(UserSessionInfo.getCurrentUserTimeZone()),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update"),
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

    public static void sortWeek(TableView<Appointments> appointmentsTable, TableColumn<Appointments, Integer> appointmentIdCol, TableColumn<Appointments, String> titleCol, TableColumn<Appointments, String> descriptionCol, TableColumn<Appointments, String> locationCol, TableColumn<Appointments, String> typeCol, TableColumn<Appointments, ZonedDateTime> startDateCol, TableColumn<Appointments, ZonedDateTime> startTimeCol, TableColumn<Appointments, ZonedDateTime> endDateCol, TableColumn<Appointments, ZonedDateTime> endTimeCol, TableColumn<Appointments, Integer> customerIdCol, TableColumn<Appointments, Integer> userIdCol, TableColumn<Appointments, Integer> contactIdCol) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE WEEK(Start) = ? ORDER BY Start ASC";
        ObservableList<Appointments> data = FXCollections.observableArrayList();
        int week = LocalDate.now().get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ps.setInt(1, week);
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

