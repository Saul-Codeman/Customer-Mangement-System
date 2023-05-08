package com.example.WGUSoftware2.utility;

import com.example.WGUSoftware2.model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;

import com.example.WGUSoftware2.model.CurrentUser;

/**
 * Class that contains the current users info for the session
 */
public class UserSessionInfo {

    private static CurrentUser currentUser;
    private static Locale currentUserLocale;
    private static ZoneId currentUserTimeZone;
    private static String languagePreference;


    /**
     * Getter - current user.
     * @return the current user.
     */
    public static CurrentUser getCurrentUser(){
        return currentUser;
    }
    /**
     * Getter - current user locale.
     * @return the current user's locale.
     */
    public static Locale getCurrentUserLocale(){
        return currentUserLocale;
    }
    /**
     * Getter - current user time zone.
     * @return the current user's time zone.
     */
    public static ZoneId getCurrentUserTimeZone(){
        return currentUserTimeZone;
    }
    /**
     * Getter - language preference.
     * @return the preferred language for the user.
     */
    public static String getLanguagePreference(){
        return languagePreference;
    }


    /**
     * Function that validates whether or not the user is in the database
     * @param username username of the user logging in
     * @param password password of the user logging in
     * @param language language preference of the user
     * @return boolean of login attempt
     * @throws SQLException catches RUNTIME ERROR
     */
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
