package com.example.WGUSoftware2.model;

import com.example.WGUSoftware2.utility.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;

/**
 * CurrentUser class that contains functions for getting, setting, and constructing the current user that logged in
 */
public class CurrentUser {

    private String username;
    private Integer userID;

    /**
     * Constructor for current user
     * @param username name of user
     * @param userID ID of user
     */
    public CurrentUser(String username, Integer userID){
        this.username = username;
        this.userID = userID;
    }

    /**
     * Getter - user name.
     * @return name of the user.
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Getter - user ID.
     * @return ID of the user.
     */
    public Integer getUserID(){
        return this.userID;
    }

}
