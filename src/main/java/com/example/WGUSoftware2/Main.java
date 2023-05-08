package com.example.WGUSoftware2;

import com.example.WGUSoftware2.utility.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class of the scheduler application.
 * @author Sean Roberts
 */
public class Main extends Application {

    /**
     * Loads the stage to display on screen
     * @param primaryStage login page on screen
     * @throws Exception catches RUNTIME ERROR
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/login.fxml"));
        primaryStage.setTitle("Scheduler");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }


    /**
     * Start of application and opens and closes database connection
     * @param args arguments passed to application
     */
    public static void main(String[] args) {
        Database.makeConnection();
        launch(args);
        Database.closeConnection();
    }


}
