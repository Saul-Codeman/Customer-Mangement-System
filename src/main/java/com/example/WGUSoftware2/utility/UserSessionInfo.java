package com.example.WGUSoftware2.utility;

import com.example.WGUSoftware2.model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;

import com.example.WGUSoftware2.model.CurrentUser;

public class UserSessionInfo {

    private static CurrentUser currentUser;
    private static Locale currentUserLocale;
    private static ZoneId currentUserTimeZone;
    private static String languagePreference;


    public static CurrentUser getCurrentUser(){
        return currentUser;
    }
    public static Locale getCurrentUserLocale(){
        return currentUserLocale;
    }
    public static ZoneId getCurrentUserTimeZone(){
        return currentUserTimeZone;
    }
    public static String getLanguagePreference(){
        return languagePreference;
    }


    public static boolean validateCredentials(String username, String password, String language) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet resultSet = ps.executeQuery();
        if(resultSet.next()) {
            currentUser = new CurrentUser(resultSet.getString("User_Name"), resultSet.getInt("User_ID"));
            currentUserLocale = Locale.getDefault();
            currentUserTimeZone = ZoneId.systemDefault();
            languagePreference = language;
            return true;
        }else{
            return false;
        }
    }



}
