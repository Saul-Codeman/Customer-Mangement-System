package com.example.WGUSoftware2.model;

import com.example.WGUSoftware2.utility.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;

public class CurrentUser {

    private String username;
    private Integer userID;

    public CurrentUser(String username, Integer userID){
        this.username = username;
        this.userID = userID;
    }

    public String getUsername(){
        return this.username;
    }

    public Integer getUserID(){
        return this.userID;
    }

}
