package com.example.WGUSoftware2.controller;

import com.example.WGUSoftware2.utility.Database;
import com.example.WGUSoftware2.utility.Library;
import com.example.WGUSoftware2.utility.LoginLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<String> languageDropDown;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLbl;

    @FXML
    private Label timeZoneLbl;

    @FXML
    private Label userLocationLbl;

    @FXML
    private Label languageLbl;

    @FXML
    private Label loginLbl;

    @FXML
    private Label usernameLbl;

    @FXML
    private TextField usernameField;

    /**
     * Takes items from text fields and compares to element in the database and switches to the dashboard page if user
     * passes.
     * @param event action on a button
     * @throws IOException catches RUNTIME ERROR
     */
    public void handleLogin(ActionEvent event) throws IOException, SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        // If the username and password match a specific database user
        /*
        if (Database.validateCredentials(username, password)){
            LoginLogger.log(username, true);
            Library.switchScreen(event, Library.dashboardUrl);
        }
        // Else give them an error and they remain on the same page
        else {
            LoginLogger.log(username, false);
            errorLabel.setText("Invalid username or password");
        }
         */
        Library.switchScreen(event, Library.dashboardUrl);
    }
    @FXML
    void languageHandler(ActionEvent event) {
        String selectedLanguage = languageDropDown.getSelectionModel().getSelectedItem();
        Locale.setDefault(selectedLanguage.equals("English") ? Locale.ENGLISH : Locale.FRENCH);
        updateLanguage();
    }

    void updateLanguage(){
        ResourceBundle resources = ResourceBundle.getBundle("com/example/WGUSoftware2/language_property/login", Locale.getDefault());
        loginLbl.setText(resources.getString("loginLabel"));
        usernameLbl.setText(resources.getString("usernameLabel"));
        passwordLbl.setText(resources.getString("passwordLabel"));
        loginButton.setText(resources.getString("loginButton"));
    }

    /**
     * Displays the languages available in the drop down box.
     */
    public void populateLanguageDropDown(){
        ObservableList<String> languages = FXCollections.observableArrayList("English", "French");
        languageDropDown.setItems(languages);
        languageDropDown.getSelectionModel().selectFirst();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateLanguageDropDown();

        Locale userLocale = Locale.getDefault();
        userLocationLbl.setText(ZoneId.systemDefault().toString());

        resources = ResourceBundle.getBundle("com/example/WGUSoftware2/language_property/login", userLocale);

        loginLbl.setText(resources.getString("loginLabel"));
        usernameLbl.setText(resources.getString("usernameLabel"));
        passwordLbl.setText(resources.getString("passwordLabel"));
        loginButton.setText(resources.getString("loginButton"));

    }
}