package org.steep.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import org.steep.Database.DatabaseManagement;

public class Register {

    public enum RegisterStatus {
        SUCCESS,
        USERNAME_EXISTS,
        ERROR
    }

    public static class RegisterResponse {
        private RegisterStatus status;
        private String message;
        private int userId;

        public RegisterResponse(RegisterStatus status, String message, int userId) {
            this.status = status;
            this.message = message;
            this.userId = userId;
        }

        public RegisterStatus getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public int getUserId() {
            return userId;
        }
    }

    public RegisterResponse registerMethod(String user, String pass) {
        String hashedPassword = "";
        boolean existingUser = true;
        String username = user;
        String password = pass;

        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT benutzer_name FROM benutzer WHERE benutzer_name = ?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new RegisterResponse(RegisterStatus.USERNAME_EXISTS,
                            "Username already exists, please choose another one", 0);
                } else {
                    existingUser = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new RegisterResponse(RegisterStatus.ERROR, "An unexpected error has occurred!", 0);
        }

        if (!existingUser) {
            try (Connection connection = DatabaseManagement.connectToDB();
                    PreparedStatement insertStatement = connection
                            .prepareStatement("INSERT INTO benutzer(benutzer_name, passwort) VALUES (?, ?)")) {
                hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                insertStatement.setString(1, username);
                insertStatement.setString(2, hashedPassword);
                insertStatement.executeUpdate();
                return new RegisterResponse(RegisterStatus.SUCCESS, "User registered successfully",
                        Login.getUserId(username));

            } catch (SQLException e) {
                e.printStackTrace();
                return new RegisterResponse(RegisterStatus.ERROR, "An unexpected error has occurred!", 0);
            }
        }

        return new RegisterResponse(RegisterStatus.ERROR, "An unexpected error has occurred!", 0);
    }
}
