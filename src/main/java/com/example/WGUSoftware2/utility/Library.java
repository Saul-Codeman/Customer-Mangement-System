package com.example.WGUSoftware2.utility;

import com.example.WGUSoftware2.model.Appointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TimeZone;

public class Library {


    public static final String loginUrl = "/com/example/WGUSoftware2/view/login.fxml";
    public static final String dashboardUrl = "/com/example/WGUSoftware2/view/dashboard.fxml";
    public static final String appointmentsUrl = "/com/example/WGUSoftware2/view/appointments.fxml";
    public static final String customersUrl = "/com/example/WGUSoftware2/view/customers.fxml";
    public static final String addAppointmentUrl = "/com/example/WGUSoftware2/view/addAppointment.fxml";
    public static final String modifyAppointmentUrl = "/com/example/WGUSoftware2/view/modifyAppointment.fxml";
    public static final String addCustomerUrl = "/com/example/WGUSoftware2/view/addCustomer.fxml";
    public static final String modifyCustomerUrl = "/com/example/WGUSoftware2/view/modifyCustomer.fxml";
    public static final String reportsUrl = "/com/example/WGUSoftware2/view/reports.fxml";

    public static void switchScreen(ActionEvent event, String url) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Library.class.getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static ObservableList<String> getAmericanDivisions() throws SQLException {
        String sql = "SELECT DIVISION FROM first_level_divisions WHERE Division_ID <= 54";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<String> divisions = FXCollections.observableArrayList();
        while (rs.next()){
            divisions.add(rs.getString("Division"));
        }
        rs.close();
        ps.close();
        return divisions;
    }
    public static ObservableList<String> getCanadianDivisions() throws SQLException {
        String sql = "SELECT DIVISION FROM first_level_divisions WHERE Division_ID BETWEEN 60 AND 72";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<String> divisions = FXCollections.observableArrayList();
        while (rs.next()){
            divisions.add(rs.getString("Division"));
        }
        rs.close();
        ps.close();
        return divisions;
    }

    public static ObservableList<String> getUKDivisions() throws SQLException {
        String sql = "SELECT DIVISION FROM first_level_divisions WHERE Division_ID BETWEEN 101 AND 104";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<String> divisions = FXCollections.observableArrayList();
        while (rs.next()){
            divisions.add(rs.getString("Division"));
        }
        rs.close();
        ps.close();
        return divisions;
    }

    public static ObservableList<String> getCountryDivisions(String country) throws SQLException {
        return switch (country) {
            case "Canada" -> getCanadianDivisions();
            case "UK" -> getUKDivisions();
            case "US" -> getAmericanDivisions();
            default -> FXCollections.observableArrayList("Select Country");
        };
    }

    public static ObservableList<String> getContactIDs() throws SQLException {
        String sql = "SELECT Contact_ID FROM contacts";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<String> contactIDs = FXCollections.observableArrayList();
        while(rs.next()){
            contactIDs.add(rs.getString("Contact_ID"));
        }
        rs.close();
        ps.close();
        return contactIDs;
    }

    public static ObservableList<String> getTypes() throws SQLException {
        String sql = "SELECT DISTINCT Type FROM appointments";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<String> types = FXCollections.observableArrayList();
        while(rs.next()){
            types.add(rs.getString("Type"));
        }
        rs.close();
        ps.close();
        return types;
    }

    public void checkUpcomingAppointments() {
        LocalDateTime nextAppointment = getAppointmentsWithin15();

        if (nextAppointment != null) {
            LocalDateTime currentTime = LocalDateTime.now();
            Duration duration = Duration.between(currentTime, nextAppointment);

            if (duration.toMinutes() <= 15) {
                showAppointmentAlert(duration.toMinutes());
            }
        }
    }

    private ObservableList<Appointments> getAppointmentsWithin15(){

    }

    private void showAppointmentAlert(long minutesUntilAppointment) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Upcoming Appointment");
        alert.setHeaderText("You have an appointment coming up!");
        alert.setContentText("There is an appointment in " + minutesUntilAppointment + " minutes.");
        alert.showAndWait();
    }



}
