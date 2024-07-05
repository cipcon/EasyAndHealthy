package org.steep.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.steep.Database.DatabaseManagement;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteAccount {
    public DeleteRequest deleteUser(int userId) {
        if (userId == 0) {
            System.out.println("UserId = 0");
            return new DeleteRequest(false, "Invalid user ID");
        }

        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection
                        .prepareStatement("DELETE FROM benutzer WHERE benutzer_id = ?")) {
            statement.setInt(1, userId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 1) {
                return new DeleteRequest(true, "Successfully deleted");
            } else {
                return new DeleteRequest(false, "User not found or could not be deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DeleteRequest(false, "Error occurred while deleting user: " + e.getMessage());
        }
    }

    public static class DeleteRequest {
        private boolean deleted;
        private String message;

        public boolean isDeleted() {
            return this.deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public DeleteRequest(boolean deleted, String message) {
            this.deleted = deleted;
            this.message = message;
        }

    }

    public static void main(String[] args) {

        DeleteAccount deleteAccount = new DeleteAccount();
        DeleteAccount.DeleteRequest deleteRequest = deleteAccount.deleteUser(6894);
        System.out.println(deleteRequest.deleted + " " + deleteRequest.getMessage());
    }
}
