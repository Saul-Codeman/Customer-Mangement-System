package com.example.WGUSoftware2.model;

import com.example.WGUSoftware2.utility.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * Users class that allows for user information to be constructed, get, or set.
 */
public class Users {
    private Integer userID;
    private String userName;
    private String password;
    private ZonedDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    /**
     * Constructor for the Users class
     * @param userID integer primary key - id of the user
     * @param userName varchar - username
     * @param password varchar - password
     * @param createDate DATETIME - created date of user
     * @param createdBy varchar - user that created the user
     * @param lastUpdate DATETIME - last update of the user
     * @param lastUpdatedBy varchar - user that updated the user
     */
    public Users(Integer userID, String userName, String password, ZonedDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
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
     * Getter - user name.
     * @return name of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter - user name.
     * @param userName the user name to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter - user password.
     * @return password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter - user password.
     * @param password the user password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter - user creation date.
     * @return creation date of the user.
     */
    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Setter - user creation date.
     * @param createDate the user creation date to set.
     */
    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter - user created by.
     * @return the name of the user who created the user.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter - user created by.
     * @param createdBy the name of the user who created the user.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter - user last update.
     * @return last update timestamp of the user.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Setter - user last update.
     * @param lastUpdate the last update timestamp to set.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter - user last updated by.
     * @return the name of the user who last updated the user.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Setter - user last updated by.
     * @param lastUpdatedBy the name of the user who last updated the user.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
