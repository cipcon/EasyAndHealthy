package org.steep.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import org.steep.Database.DatabaseManagement;
import org.steep.User.RegisterStatusAndResponse.RegisterResponse;
import org.steep.User.RegisterStatusAndResponse.RegisterStatus;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Login {
    public RegisterResponse loginMethod(String username, String password) {
        String message = "";

        // Establishing connection
        try (Connection connection = DatabaseManagement.connectToDB()) {
            int userId = getUserId(username);

            if (username.isEmpty() && password.isEmpty()) {
                message = "Password or username are empty";
                System.out.println(message);
                return new RegisterResponse(false, message, RegisterStatus.EXPECTATION_FAILED, 0,
                        username);
            }

            if (userId != 0) {
                String checkPass = "SELECT passwort FROM benutzer WHERE benutzer_id = ?";
                try (PreparedStatement statement2 = connection.prepareStatement(checkPass)) {
                    statement2.setInt(1, userId);
                    try (ResultSet resultSet = statement2.executeQuery()) {
                        if (resultSet.next()) {
                            String hashedPassword = resultSet.getString("passwort");
                            if (BCrypt.checkpw(password, hashedPassword)) {
                                message = "Successfully logged in";
                                System.out.println(username + " " + message);
                                return new RegisterResponse(true, message, RegisterStatus.SUCCESS,
                                        userId,
                                        username);
                            } else {
                                message = "Invalid password.";
                                System.out.println(message);
                            }
                        } else {
                            message = "No user found, please check your input and try again";
                            System.out.println(message);
                        }
                    }
                }
            } else {
                message = "No user found, please check your input and try again";
                System.out.println(message);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Login failed, return object with unauthenticated status
        return new RegisterResponse(false, message, RegisterStatus.ERROR, 0, username);
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
