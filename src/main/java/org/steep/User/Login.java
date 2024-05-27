package org.steep.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import org.steep.Database.DatabaseManagement;

public class Login {
    
    public User loginMethod() {
        User user = new User();

        // Establishing connection
        try (Connection connection = DatabaseManagement.connectToDB()) {
            // Open scanner
            Scanner scan = new Scanner(System.in);
        
            // Implement while loop
            final int MAX_ATTEMPTS = 3;
            int attempts = 0;
            boolean isAuthenticated = false;
            int setUserId = 0;

            while (attempts < MAX_ATTEMPTS && !isAuthenticated) {
                // Take input from user
                System.out.println("Username: ");
                String username = scan.nextLine();
                System.out.println("Password: ");
                String password = scan.nextLine();

                String sqlGetUserId = "SELECT benutzer_id FROM benutzer WHERE benutzer_name = ?";

                // Read data from db
                try (PreparedStatement statement = connection.prepareStatement(sqlGetUserId)) {
                    statement.setString(1, username);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            setUserId = resultSet.getInt("benutzer_id");
                            user.setId(setUserId);
                            user.setCurrentUsername(username);
                        } else {
                            System.out.println("No user found, please check your input and try again");
                            continue;
                        }
                    }

                    String readFromDB = "SELECT passwort FROM benutzer WHERE benutzer_name = ?";
                    try (PreparedStatement statement2 = connection.prepareStatement(readFromDB)) {
                        statement2.setString(1, user.getCurrentUsername());
                        try (ResultSet resultSet = statement2.executeQuery()) {
                            if (resultSet.next()) {
                                String hashedPasswordFromDB = resultSet.getString("passwort");
                                if (BCrypt.checkpw(password, hashedPasswordFromDB)) {
                                    isAuthenticated = true;
                                    return user;
                                } else {
                                    System.out.println("Invalid username or password.");
                                    continue;
                                }
                            } else {
                                System.out.println("No user found, please check your input and try again");
                                continue;
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                attempts++;
            }

            if (attempts == MAX_ATTEMPTS) {
                System.out.println("Your account is temporarily blocked. Please try again in a couple of minutes");
            }

            // Close scanner
            scan.close();
            System.out.println("User login failed");
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return user;
        }
    }
    public static void main(String[] args) {
        Login login = new Login();
        System.out.println(login.loginMethod().getCurrentUsername());
    }
}
