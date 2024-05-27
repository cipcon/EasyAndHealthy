package org.steep.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManagement {
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/EasyAndHealthyDB";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    // Private constructor to prevent instantiation
    private DatabaseManagement() {
    }

    // Method to establish a new database connection
    public static Connection connectToDB() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Method to close resources
    public static void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        }  
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            

    }
}
