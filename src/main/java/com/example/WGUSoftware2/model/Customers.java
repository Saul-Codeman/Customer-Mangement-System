package com.example.WGUSoftware2.model;

import com.example.WGUSoftware2.controller.CustomerController;
import com.example.WGUSoftware2.controller.ModifyCustomerController;
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

/**
 * Customers class that contains functions for the customer and makes database calls
 */
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

    /**
     * Customers class constructor
     * @param customerID integer primary key - unique auto generated customer ID
     * @param customerName varchar - name of customer
     * @param address varchar - address of customer
     * @param postalCode varchar - postal code of customer
     * @param phone varchar - customer phone number
     * @param createDate datetime - date customer was created
     * @param createdBy varchar - user who created customer
     * @param lastUpdate datetime - last time customer was created or updated
     * @param lastUpdatedBy varchar - user who last updated customer
     * @param divisionID integer foreign key - division ID of customer
     */
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
     * Getter - customer name.
     * @return name of the customer.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter - customer name.
     * @param customerName the customer name to set.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter - customer address.
     * @return address of the customer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter - customer address.
     * @param address the customer address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter - customer postal code.
     * @return postal code of the customer.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Setter - customer postal code.
     * @param postalCode the customer postal code to set.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Getter - customer phone.
     * @return phone number of the customer.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter - customer phone.
     * @param phone the customer phone number to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter - customer creation date.
     * @return creation date of the customer.
     */
    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Setter - customer creation date.
     * @param createDate the customer creation date to set.
     */
    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter - customer created by.
     * @return the name of the user who created the customer.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter - customer created by.
     * @param createdBy the name of the user who created the customer.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter - customer last update.
     * @return last update timestamp of the customer.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Setter - customer last update.
     * @param lastUpdate the last update timestamp to set.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter - customer last updated by.
     * @return the name of the user who last updated the customer.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Setter - customer last updated by.
     * @param lastUpdatedBy the name of the user who last updated the customer.
     */
    public void setUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Getter - customer division ID.
     * @return division ID of the customer.
     */
    public Integer getDivisionID() {
        return divisionID;
    }

    /**
     * Setter - customer division ID.
     * @param divisionID the customer division ID to set.
     */
    public void setDivisionID(Integer divisionID) {
        this.divisionID = divisionID;
    }


    /**
     * Search for the division ID given the name of the division
     * @param division division name to search
     * @return the division ID
     * @throws SQLException catches RUNTIME ERROR
     */
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

    /**
     * Insert the customer into the database
     * @param customerName name of the customer
     * @param address address
     * @param postalCode postal code
     * @param phone phone number
     * @param divisionID division ID
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void insertCustomer(String customerName, String address, String postalCode, String phone, int divisionID) throws SQLException {

        // Data entered by PC and user
        Instant now = Instant.now();
        String createdBy = UserSessionInfo.getCurrentUser().getUsername();
        Instant createDate = now;
        String lastUpdatedBy = UserSessionInfo.getCurrentUser().getUsername();
        Instant lastUpdateDateTime = now;

        // Data input from customer
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.from(createDate));
        ps.setString(6, createdBy);
        ps.setTimestamp(7, Timestamp.from(lastUpdateDateTime));
        ps.setString(8, lastUpdatedBy);
        ps.setInt(9, divisionID);

        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Insertion Successful!");
        } else {
            System.out.println("Insertion Failed");
        }
    }

    /**
     * Updates the customer at the given customer ID number
     * @param customerID ID of the customer to be updated
     * @param customerName name of the customer
     * @param address address
     * @param postalCode postal code
     * @param phone phone number
     * @param divisionID division ID
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void updateCustomer(Integer customerID, String customerName, String address, String postalCode, String phone, int divisionID) throws SQLException {

        // Data input by PC
        Instant now = Instant.now();
        String lastUpdatedBy = UserSessionInfo.getCurrentUser().getUsername();
        Instant lastUpdateDateTime = now;

        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        ps.setTimestamp(5, Timestamp.from(lastUpdateDateTime));
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

    /**
     * Deletes the customer from the database at the given customer ID
     * @param customerID ID of customer to be deleted
     * @throws SQLException catches RUNTIME ERROR
     */
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

    /**
     * Deletes the appointments of the customer at the given customer ID
     * @param customerId ID of the customer to delete from
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void deleteCustomerAppointments(Integer customerId) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Customer_ID=?";
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the customers tableview with all of the customers from the database
     * @param customersTable table view
     * @param customersIdCol customer ID col
     * @param nameCol customer name col
     * @param addressCol address col
     * @param postalCol postal col
     * @param phoneCol phone number col
     * @param createDateCol create date col
     * @param createdByCol created by col
     * @param lastUpdateCol last update col
     * @param lastUpdateByCol last updated by col
     * @param divisionIdCol division ID col
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void setCustomersTable(TableView<Customers> customersTable, TableColumn<Customers, Integer> customersIdCol, TableColumn<Customers, String> nameCol, TableColumn<Customers, String> addressCol, TableColumn<Customers, String> postalCol, TableColumn<Customers, String> phoneCol, TableColumn<Customers, ZonedDateTime> createDateCol, TableColumn<Customers, String> createdByCol, TableColumn<Customers, Timestamp> lastUpdateCol, TableColumn<Customers, String> lastUpdateByCol, TableColumn<Customers, Integer> divisionIdCol) throws SQLException {
        String sql = "SELECT * FROM customers";
        ObservableList<Customers> data = FXCollections.observableArrayList();
        data.clear();
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Go through each row of the result set and make an appointment
            while (rs.next()) {

                ZonedDateTime createdDateUtc = rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.of("UTC"));
                ZonedDateTime lastUpdateUtc = rs.getTimestamp("Last_Update").toInstant().atZone(ZoneId.of("UTC"));

                ZonedDateTime createdDateLocal = TimeZoneConverter.utcToLocal(createdDateUtc.toLocalDateTime());
                ZonedDateTime lastUpdateLocal = TimeZoneConverter.utcToLocal(lastUpdateUtc.toLocalDateTime());

                Customers customer = new Customers(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        createdDateLocal,
                        rs.getString("Created_By"),
                        Timestamp.from(Instant.from(lastUpdateLocal)),
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
        createDateCol.setCellValueFactory(cellData -> {
            ZonedDateTime createDate = cellData.getValue().getCreateDate();
            String formattedCreateDate = createDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(formattedCreateDate);
        });
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(cellData -> {
            Timestamp lastUpdate = cellData.getValue().getLastUpdate();
            LocalDateTime localDateTime = lastUpdate.toLocalDateTime();
            String formattedLastUpdate = localDateTime.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(formattedLastUpdate);
        });
        lastUpdateByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }

    /**
     * Takes the selected customer from the table view to the modify customer page
     * @param customerController controller where customer was selected
     * @param customersTableView table where customer was selected from
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     * @throws SQLException catches RUNTIME ERROR
     */
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

    /**
     * Sets the customer fields on the modify and add customer pages
     * @param customer customer to set fields for
     * @param customerID customer ID txt
     * @param customerName customer name txt
     * @param address customer address txt
     * @param postalCode postal code txt
     * @param phone phone number txt
     * @param country country CB
     * @param division division CB
     * @throws SQLException catches RUNTIME ERROR
     */
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

    /**
     * Check if the customer at the customerID has an appointment
     * @param customerID id of the customer to check
     * @return boolean
     * @throws SQLException catches RUNTIME ERROR
     */
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

    /**
     * Sets the table based on customer type
     * @param type type selected to sort by
     * @param customersTable table to set
     * @param customersIdCol customer ID col
     * @param nameCol customer name col
     * @param addressCol address col
     * @param postalCol postal col
     * @param phoneCol phone number col
     * @param createDateCol create date col
     * @param createdByCol created by col
     * @param lastUpdateCol last update col
     * @param lastUpdateByCol last updated by col
     * @param divisionIdCol division ID col
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void setCustomerType(String type, TableView<Customers> customersTable, TableColumn<Customers, Integer> customersIdCol, TableColumn<Customers, String> nameCol, TableColumn<Customers, String> addressCol, TableColumn<Customers, String> postalCol, TableColumn<Customers, String> phoneCol, TableColumn<Customers, ZonedDateTime> createDateCol, TableColumn<Customers, String> createdByCol, TableColumn<Customers, Timestamp> lastUpdateCol, TableColumn<Customers, String> lastUpdateByCol, TableColumn<Customers, Integer> divisionIdCol) throws SQLException {
        String sql = "SELECT * FROM customers JOIN appointments ON customers.Customer_ID = appointments.Customer_ID WHERE Type = ?";
        ObservableList<Customers> data = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = Database.connection.prepareStatement(sql);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                ZonedDateTime createdDateUtc = rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.of("UTC"));
                ZonedDateTime lastUpdateUtc = rs.getTimestamp("Last_Update").toInstant().atZone(ZoneId.of("UTC"));

                ZonedDateTime createdDateLocal = TimeZoneConverter.utcToLocal(createdDateUtc.toLocalDateTime());
                ZonedDateTime lastUpdateLocal = TimeZoneConverter.utcToLocal(lastUpdateUtc.toLocalDateTime());


                Customers customer = new Customers(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        createdDateLocal,
                        rs.getString("Created_By"),
                        Timestamp.from(Instant.from(lastUpdateLocal)),
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
        createDateCol.setCellValueFactory(cellData -> {
            ZonedDateTime createDate = cellData.getValue().getCreateDate();
            String formattedCreateDate = createDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(formattedCreateDate);
        });
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(cellData -> {
            Timestamp lastUpdate = cellData.getValue().getLastUpdate();
            LocalDateTime localDateTime = lastUpdate.toLocalDateTime();
            String formattedLastUpdate = localDateTime.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(formattedLastUpdate);
        });
        lastUpdateByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));

    }

    /**
     * Gets all customer IDs from the database
     * @return observable list
     * @throws SQLException catches RUNTIME ERROR
     */
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
     * Function that sorted customers based on the month of their appointments.
     * Fixed a LOGICAL ERROR with month being required to be an int for the SQL string.
     * @param month month to sort by
     * @param customersTable table to place customers in
     * @param customersIdCol customer ID col
     * @param nameCol customer name col
     * @param addressCol address col
     * @param postalCol postal col
     * @param phoneCol phone number col
     * @param createDateCol create date col
     * @param createdByCol created by col
     * @param lastUpdateCol last update col
     * @param lastUpdateByCol last updated by col
     * @param divisionIdCol division ID col
     * @throws SQLException catches RUNTIME ERROR
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

                ZonedDateTime createdDateUtc = rs.getTimestamp("Create_Date").toInstant().atZone(ZoneId.of("UTC"));
                ZonedDateTime lastUpdateUtc = rs.getTimestamp("Last_Update").toInstant().atZone(ZoneId.of("UTC"));

                ZonedDateTime createdDateLocal = TimeZoneConverter.utcToLocal(createdDateUtc.toLocalDateTime());
                ZonedDateTime lastUpdateLocal = TimeZoneConverter.utcToLocal(lastUpdateUtc.toLocalDateTime());

                Customers customer = new Customers(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        createdDateLocal,
                        rs.getString("Created_By"),
                        Timestamp.from(Instant.from(lastUpdateLocal)),
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
        createDateCol.setCellValueFactory(cellData -> {
            ZonedDateTime createDate = cellData.getValue().getCreateDate();
            String formattedCreateDate = createDate.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(formattedCreateDate);
        });
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(cellData -> {
            Timestamp lastUpdate = cellData.getValue().getLastUpdate();
            LocalDateTime localDateTime = lastUpdate.toLocalDateTime();
            String formattedLastUpdate = localDateTime.format(DateTimeFormatter.ofPattern("M/d/yyyy"));
            return new SimpleObjectProperty(formattedLastUpdate);
        });
        lastUpdateByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }
}
