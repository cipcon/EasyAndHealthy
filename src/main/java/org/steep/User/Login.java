package org.steep.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import org.steep.Database.DatabaseManagement;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Login {
    public UserAuthenticated loginMethod(String username, String password) {

        // Establishing connection
        try (Connection connection = DatabaseManagement.connectToDB()) {
            int userId = getUserId(username);

            if (username.isEmpty() && password.isEmpty()) {
                System.out.println("Password or username are empty");
                return new UserAuthenticated(username, 0, false);
            }

            if (userId != 0) {
                String checkPass = "SELECT passwort FROM benutzer WHERE benutzer_id = ?";
                try (PreparedStatement statement2 = connection.prepareStatement(checkPass)) {
                    statement2.setInt(1, userId);
                    try (ResultSet resultSet = statement2.executeQuery()) {
                        if (resultSet.next()) {
                            String hashedPassword = resultSet.getString("passwort");
                            if (BCrypt.checkpw(password, hashedPassword)) {
                                System.out.println(username + " successfully logged in");
                                return new UserAuthenticated(username, userId, true);
                            } else {
                                System.out.println("Invalid password.");
                            }
                        } else {
                            System.out.println("No user found, please check your input and try again");
                        }
                    }
                }
            } else {
                System.out.println("User login failed: User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Login failed, return object with unauthenticated status
        return new UserAuthenticated(username, 0, false);
    }

    public static int getUserId(String username) {
        try (Connection connection = DatabaseManagement.connectToDB()) {
            String getUserIdQuery = "SELECT benutzer_id FROM benutzer WHERE benutzer_name = ?";
            // Read data from db
            try (PreparedStatement statement = connection.prepareStatement(getUserIdQuery)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("benutzer_id");
                    } else {
                        System.out.println("No user found with username: " + username);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
