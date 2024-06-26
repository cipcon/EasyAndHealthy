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
        ERROR,
        EXPECTATION_FAILED
    }

    public static class RegisterResponse {
        private RegisterStatus status;
        private String message;
        private int userId;
        private String username;

        public RegisterResponse(RegisterStatus status, String message, int userId, String username) {
            this.status = status;
            this.message = message;
            this.userId = userId;
            this.username = username;
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

        public String getUsername() {
            return username;
        }
    }

    public RegisterResponse registerMethod(String user, String pass) {
        String hashedPassword = "";
        boolean existingUser = true;
        String username = user;
        String password = pass;

        if (pass == null || pass.isEmpty()) {
            System.out.println("Password equal null or empty");
            return new RegisterResponse(RegisterStatus.EXPECTATION_FAILED, "Password empty or null", 0, username);
        }

        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT benutzer_name FROM benutzer WHERE benutzer_name = ?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new RegisterResponse(RegisterStatus.USERNAME_EXISTS,
                            "Username already exists, please choose another one", 0, username);
                } else {
                    existingUser = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new RegisterResponse(RegisterStatus.ERROR, "An unexpected error has occurred!", 0, username);
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
                        Login.getUserId(username), username);

            } catch (SQLException e) {
                e.printStackTrace();
                return new RegisterResponse(RegisterStatus.ERROR, "An unexpected error has occurred!", 0, username);
            }
        }

        return new RegisterResponse(RegisterStatus.ERROR, "An unexpected error has occurred!", 0, username);
    }

    public static int deleteUser(int userId) {
        int rowsDeleted = 0;

        if (userId == 0) {
            System.out.println("UserId = 0");
            return rowsDeleted;
        }

        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection
                        .prepareStatement("DELETE FROM benutzer WHERE benutzer_id = ?")) {
            statement.setInt(1, userId);
            rowsDeleted = statement.executeUpdate();
            return rowsDeleted;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsDeleted;
    }
}
