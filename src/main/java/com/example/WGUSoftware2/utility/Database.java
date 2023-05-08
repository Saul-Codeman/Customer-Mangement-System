package com.example.WGUSoftware2.utility;
import java.sql.*;

/**
 * Database class that handles setting up and connecting to the database
 */
public class Database {
 private static final String protocol = "jdbc";
     private static final String vendor = ":mysql:";
         private static final String location = "//localhost/";
             private static final String databaseName = "client_schedule";
                 private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
        private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
        private static final String userName = "sqlUser"; // Username
        private static String password = "Passw0rd!"; // Password
        public static Connection connection;  // Connection Interface
        public static PreparedStatement preparedStatement;

    /**
     * Makes a connection to the database and displays a message when connected
     */
    public static void makeConnection() {

          try {
              Class.forName(driver); // Locate Driver
              //password = Details.getPassword(); // Assign password
              connection = DriverManager.getConnection(jdbcUrl, userName, password); // reference Connection object
              System.out.println("Connection successful!");
          }
                  catch(ClassNotFoundException e) {
                      System.out.println("Error:" + e.getMessage());
                  }
                  catch(SQLException e) {
                      System.out.println("Error:" + e.getMessage());
                  }
          }

    /**
     * Getter- gets the connection to the database
     * @return the connection
     */
    public static Connection getConnection() {
                return connection;
            }

    /**
     * Closes the connection to the database and shows message closed
     */
    public static void closeConnection() {
                 try {
                     connection.close();
                     System.out.println("Connection closed!");
                 } catch (SQLException e) {
                     System.out.println(e.getMessage());
                 }
             }

    /**
     * Makes a prepared statement
     * @param sqlStatement sql statement
     * @param conn connection to database
     * @throws SQLException catches RUNTIME ERROR
     */
    public static void makePreparedStatement(String sqlStatement, Connection conn) throws SQLException {
           if (conn != null)
               preparedStatement = conn.prepareStatement(sqlStatement);
           else
               System.out.println("Prepared Statement Creation Failed!");
       }

    /**
     * Getter- gets the prepared statement
     * @return prepared statement
     * @throws SQLException catches RUNTIME ERROR
     */
       public static PreparedStatement getPreparedStatement() throws SQLException {
           if (preparedStatement != null)
               return preparedStatement;
           else System.out.println("Null reference to Prepared Statement");
           return null;
       }
       




}