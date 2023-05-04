package com.example.WGUSoftware2.model;

import com.example.WGUSoftware2.controller.CustomerController;
import com.example.WGUSoftware2.controller.ModifyCustomerController;
import com.example.WGUSoftware2.utility.Database;
import com.example.WGUSoftware2.utility.Library;
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
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Customers {


    private Integer customerID;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private ZonedDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private Integer divisionID;

    public Customers(Integer customerID, String customerName, String address, String postalCode, String phone, ZonedDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, Integer divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Integer getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(Integer divisionID) {
        this.divisionID = divisionID;
    }


    public static int searchDivisionID(String division) throws SQLException {
        int divisionID = 0;
        String sql = "SELECT Division_ID FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setString(1, division);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            divisionID = rs.getInt("Division_ID");
        }
        return divisionID;
    }

    public static void insertCustomer(String customerName, String address, String postalCode, String phone, int divisionID) throws SQLException {

        // Data entered by PC and user
        String createdBy = "user";
        ZonedDateTime createDate = ZonedDateTime.now();
        String lastUpdatedBy = "user";
        Timestamp lastUpdateDateTime = Timestamp.valueOf(LocalDateTime.now());

        // Data input from customer
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.valueOf(createDate.toLocalDateTime()));
        ps.setString(6, createdBy);
        ps.setTimestamp(7, Timestamp.valueOf(lastUpdateDateTime.toLocalDateTime()));
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionID);

        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Insertion Successful!");
        } else {
            System.out.println("Insertion Failed");
        }
    }

    public static void updateCustomer(Integer customerID, String customerName, String address, String postalCode, String phone, int divisionID) throws SQLException {

        // Data input by PC
        String lastUpdatedBy = "user";
        Timestamp lastUpdateDateTime = Timestamp.valueOf(LocalDateTime.now());

        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, lastUpdateDateTime);
        ps.setString(6, lastUpdatedBy);
        ps.setInt(7, divisionID);
        ps.setInt(8, customerID);
        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Update Successful!");
        } else {
            System.out.println("Update Failed");
        }
    }

    public static void deleteCustomer(Integer customerID) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Deletion Successful!");
        } else {
            System.out.println("Deletion Failed");
        }
    }

    public static void setCustomersTable(TableView<Customers> customersTable, TableColumn<Customers, Integer> customersIdCol, TableColumn<Customers, String> nameCol, TableColumn<Customers, String> addressCol, TableColumn<Customers, String> postalCol, TableColumn<Customers, String> phoneCol, TableColumn<Customers, ZonedDateTime> createDateCol, TableColumn<Customers, String> createdByCol, TableColumn<Customers, Timestamp> lastUpdateCol, TableColumn<Customers, String> lastUpdateByCol, TableColumn<Customers, Integer> divisionIdCol) throws SQLException {
        String sql = "SELECT * FROM customers";
        ObservableList<Customers> data = FXCollections.observableArrayList();
        data.clear();
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Go through each row of the result set and make an appointment
            while (rs.next()) {
                Customers customer = new Customers(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update"),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Division_ID")
                );
                data.add(customer);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set items into table
        customersTable.setItems(data);
        // Specific columns to be set
        customersIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdateByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }

    public static void modifySelectedCustomer(CustomerController customerController, TableView<Customers> customersTableView, ActionEvent event) throws IOException, SQLException {
        // Get the result set of the selected item
        Integer customerID = customersTableView.getSelectionModel().getSelectedItem().getCustomerID();
        String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        // Make an appointment object from the result set
        Customers customer = null;
        if (rs.next()) {
            customer = new Customers(
                    rs.getInt("Customer_ID"),
                    rs.getString("Customer_Name"),
                    rs.getString("Address"),
                    rs.getString("Postal_Code"),
                    rs.getString("Phone"),
                    rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()),
                    rs.getString("Created_By"),
                    rs.getTimestamp("Last_Update"),
                    rs.getString("Last_Updated_By"),
                    rs.getInt("Division_ID")
            );
        }
        // Close rs and ps
        rs.close();
        ps.close();

        // Prepare the next page
        if (customer != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(customerController.getClass().getResource(Library.modifyCustomerUrl));
            loader.load();
            ModifyCustomerController modifyCustomerController = loader.getController();
            // Send the item selected to the controller to set the fields
            modifyCustomerController.sendCustomer(customer);
            // Show the next page
            customerController.stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            customerController.stage.setScene(new Scene(scene));
            customerController.stage.show();
        }

    }

    public static void setCustomerFields(Customers customer, TextField customerID, TextField customerName, TextField address, TextField postalCode, TextField phone, ComboBox<String> country, ComboBox<String> division) throws SQLException {
        // Set Fields equal to value of result set
        customerID.setText(String.valueOf(customer.getCustomerID()));
        customerName.setText(customer.getCustomerName());
        address.setText(customer.getAddress());
        postalCode.setText(customer.getPostalCode());
        phone.setText(customer.getPhone());
        // Query to get country and division from customer division ID
        String sql = "SELECT Division, Division_ID FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setInt(1, customer.getDivisionID());
        ResultSet rs = ps.executeQuery();
        String state;
        if (rs.next()) {
            state = rs.getString("Division");

            if (Library.getAmericanDivisions().contains(state)) {
                country.setValue("US");
            } else if (Library.getCanadianDivisions().contains(state)) {
                country.setValue("Canada");
            } else if (Library.getUKDivisions().contains(state)) {
                country.setValue("UK");
            }
            division.setValue(state);
            division.setItems(Library.getCountryDivisions(country.getValue()));
        }
    }

    public static boolean hasAppointment(int customerID) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
        return false;
    }

    public static void setCustomerType(String type, TableView<Customers> customersTable, TableColumn<Customers, Integer> customersIdCol, TableColumn<Customers, String> nameCol, TableColumn<Customers, String> addressCol, TableColumn<Customers, String> postalCol, TableColumn<Customers, String> phoneCol, TableColumn<Customers, ZonedDateTime> createDateCol, TableColumn<Customers, String> createdByCol, TableColumn<Customers, Timestamp> lastUpdateCol, TableColumn<Customers, String> lastUpdateByCol, TableColumn<Customers, Integer> divisionIdCol) throws SQLException {
        String sql = "SELECT * FROM customers JOIN appointments ON customers.Customer_ID = appointments.Customer_ID WHERE Type = ?";
        ObservableList<Customers> data = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customers customer = new Customers(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update"),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Division_ID")
                );
                data.add(customer);
            }
            // Close rs and ps
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Set items into table
        customersTable.setItems(data);
        // Specific columns to be set
        customersIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdateByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

    }

    public static ObservableList<String> getCustomerIDs() throws SQLException {
        String sql = "SELECT Customer_ID FROM customers";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ObservableList<String> customerIDs = FXCollections.observableArrayList();
        while (rs.next()) {
            customerIDs.add(rs.getString("Customer_ID"));
        }
        rs.close();
        ps.close();
        return customerIDs;
    }

    /**
     * Fixed a logical error with month being required to be an int for the SQL string.
     * @param month
     * @param customersTable
     * @param customersIdCol
     * @param nameCol
     * @param addressCol
     * @param postalCol
     * @param phoneCol
     * @param createDateCol
     * @param createdByCol
     * @param lastUpdateCol
     * @param lastUpdateByCol
     * @param divisionIdCol
     * @throws SQLException
     */
    public static void sortMonth(String month, TableView<Customers> customersTable, TableColumn<Customers, Integer> customersIdCol, TableColumn<Customers, String> nameCol, TableColumn<Customers, String> addressCol, TableColumn<Customers, String> postalCol, TableColumn<Customers, String> phoneCol, TableColumn<Customers, ZonedDateTime> createDateCol, TableColumn<Customers, String> createdByCol, TableColumn<Customers, Timestamp> lastUpdateCol, TableColumn<Customers, String> lastUpdateByCol, TableColumn<Customers, Integer> divisionIdCol) throws SQLException {
        String sql = "SELECT DISTINCT * FROM customers JOIN appointments ON customers.Customer_ID = appointments.Customer_ID WHERE MONTH(appointments.Start) = ? GROUP BY customers.Customer_ID ORDER BY appointments.Start ASC";
        ObservableList<Customers> data = FXCollections.observableArrayList();
        int monthNumber = Month.valueOf(month.toUpperCase()).getValue();
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ps.setInt(1, monthNumber);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customers customer = new Customers(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getTimestamp("Create_Date").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        rs.getString("Created_By"),
                        rs.getTimestamp("Last_Update"),
                        rs.getString("Last_Updated_By"),
                        rs.getInt("Division_ID")
                );
                data.add(customer);
            }
            // Close rs and ps
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Set items into table
        customersTable.setItems(data);
        // Specific columns to be set
        customersIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdateByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }
}
