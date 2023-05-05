package com.example.WGUSoftware2.utility;
import java.sql.*;

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

            public static Connection getConnection() {
                return connection;
            }
             public static void closeConnection() {
                 try {
                     connection.close();
                     System.out.println("Connection closed!");
                 } catch (SQLException e) {
                     System.out.println(e.getMessage());
                 }
             }

       public static void makePreparedStatement(String sqlStatement, Connection conn) throws SQLException {
           if (conn != null)
               preparedStatement = conn.prepareStatement(sqlStatement);
           else
               System.out.println("Prepared Statement Creation Failed!");
       }
       public static PreparedStatement getPreparedStatement() throws SQLException {
           if (preparedStatement != null)
               return preparedStatement;
           else System.out.println("Null reference to Prepared Statement");
           return null;
       }
       


    public static boolean validateCredentials(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = Database.connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet resultSet = ps.executeQuery();
        if(resultSet.next()) {
            return true;
        }else{
            return false;
        }
    }



}