package org.steep.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import org.steep.Database.DatabaseManagement;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Login {
    public ArrayList<UserAuthenticated> loginMethod(String username, String password) {
        ArrayList<UserAuthenticated> authenticatedUser = new ArrayList<>();

        // Establishing connection
        try (Connection connection = DatabaseManagement.connectToDB()) {
            int userId = getUserId(username);

            if (userId != 0) {
                String checkPass = "SELECT passwort FROM benutzer WHERE benutzer_id = ?";
                try (PreparedStatement statement2 = connection.prepareStatement(checkPass)) {
                    statement2.setInt(1, userId);
                    try (ResultSet resultSet = statement2.executeQuery()) {
                        if (resultSet.next()) {
                            String hashedPassword = resultSet.getString("passwort");
                            if (BCrypt.checkpw(password, hashedPassword)) {
                                authenticatedUser.add(new UserAuthenticated(username, userId, true));
                                return authenticatedUser;
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
        return authenticatedUser;
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
