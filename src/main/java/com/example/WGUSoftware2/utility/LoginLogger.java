package com.example.WGUSoftware2.utility;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Class with a function to log the activity of users logging in via validate credentials
 */
public class LoginLogger {
    private static final String LOG_FILE = "login_activity.txt";

    /**
     * Writes to the login_activity.txt file about the login activity
     * @param username of user trying to login
     * @param success success or fail of login attempt
     */
    public static void log(String username, boolean success){
        try{
            FileWriter fileWriter = new FileWriter(LOG_FILE, true);
            String result = success ? "Success" : "Failed";
            String logEntry = String.format("%s - %s - %s%n", LocalDateTime.now(), username, result);
            fileWriter.write(logEntry);
            fileWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
