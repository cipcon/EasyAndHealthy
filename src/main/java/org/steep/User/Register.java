package org.steep.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;

import org.steep.Database.DatabaseManagement;
import org.steep.Requests.User.RegisterRequest.RegisterStatus;
import org.steep.Requests.User.RegisterRequest.UserResponse;

public class Register {

    public UserResponse registerMethod(String user, String pass) {
        String hashedPassword = "";
        boolean existingUser = true;
        String username = user;
        String password = pass;

        if (pass.isEmpty() || pass.isEmpty()) {
            System.out.println("Password equal null or empty");
            return new UserResponse(false, "Password empty or null", RegisterStatus.EXPECTATION_FAILED, 0,
                    username);
        }

        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection
                        .prepareStatement("SELECT benutzer_name FROM benutzer WHERE benutzer_name = ?")) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new UserResponse(false, "Username already exists, please choose another one",
                            RegisterStatus.USERNAME_EXISTS,
                            0, username);
                } else {
                    existingUser = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new UserResponse(false, "An unexpected error has occurred!", RegisterStatus.ERROR, 0, username);
        }

        if (!existingUser) {
            try (Connection connection = DatabaseManagement.connectToDB();
                    PreparedStatement insertStatement = connection
                            .prepareStatement("INSERT INTO benutzer(benutzer_name, passwort) VALUES (?, ?)")) {
                hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                insertStatement.setString(1, username);
                insertStatement.setString(2, hashedPassword);
                insertStatement.executeUpdate();
                return new UserResponse(true, "User registered successfully",
                        RegisterStatus.SUCCESS, Login.getUserId(username), username);

            } catch (SQLException e) {
                e.printStackTrace();
                return new UserResponse(false, "An unexpected error has occurred!", RegisterStatus.ERROR, 0,
                        username);
            }
        }

        return new UserResponse(false, "An unexpected error has occurred!", RegisterStatus.ERROR, 0, username);
    }

}
