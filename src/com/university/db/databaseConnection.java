package com.university.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/oops_project";
    private static final String username = "root";
    private static final String password = "veddub0110";

    // Return Connection
    public Connection getConnection() throws SQLException{
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers Loaded Successfully");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected Successfully");

        } catch (ClassNotFoundException ex) {
            System.out.println("Driver error: " + ex.getMessage());
            ex.printStackTrace();

        } catch (SQLException ex) {
            System.out.println("SQL error: " + ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
        return connection;
    }
}
