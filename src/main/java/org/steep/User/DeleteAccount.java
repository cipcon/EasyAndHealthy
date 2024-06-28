package org.steep.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.steep.Database.DatabaseManagement;

public class DeleteAccount {
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
