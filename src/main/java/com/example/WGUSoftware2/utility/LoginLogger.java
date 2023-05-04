package com.example.WGUSoftware2.utility;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LoginLogger {
    private static final String LOG_FILE = "login_activity.txt";

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
