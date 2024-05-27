package org.steep.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import org.steep.Database.DatabaseManagement;

public class Register {

    public int registerMethod() {
        String hashedPassword = "";
        boolean existingUser = true;
        int rowsAffected = 0;
        String password = "";
        String username = "";

        Scanner registrationScanner = new Scanner(System.in);

        try {
            while (existingUser) {
                // Take input from user
                System.out.println("Insert your username: ");
                username = registrationScanner.nextLine();

                System.out.println("Insert your password: ");
                password = registrationScanner.nextLine();

                try (Connection connection = DatabaseManagement.connectToDB();
                        PreparedStatement statement = connection
                                .prepareStatement("SELECT benutzer_name FROM benutzer WHERE benutzer_name = ?")) {
                    statement.setString(1, username);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            System.out.println("Username already exists, please choose another one");
                        } else {
                            existingUser = false;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("An unexpected error has occurred!");
                }
            }

            if (!existingUser) {
                try (Connection connection = DatabaseManagement.connectToDB();
                        PreparedStatement insertStatement = connection
                                .prepareStatement("INSERT INTO benutzer(benutzer_name, passwort) VALUES (?, ?)")) {
                    hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                    insertStatement.setString(1, username);
                    insertStatement.setString(2, hashedPassword);
                    rowsAffected = insertStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            registrationScanner.close();
        }

        return rowsAffected;
    }
}
