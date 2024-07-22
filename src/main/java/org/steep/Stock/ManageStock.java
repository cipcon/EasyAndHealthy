package org.steep.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.steep.Database.DatabaseManagement;
import org.steep.Requests.RecipeIngredients.AddToUserRequest;
import org.steep.Requests.RecipeIngredients.IngredientRequest;

public class ManageStock {

    // CRUD (Instead of Create - Add)
    // Add ingredients to the user's list
    public AddToUserRequest addIngredientToUserList(int ingredientId, int quantity, int userId) {
        boolean ingredientAdded = false;
        String message = "";

        if (ingredientId == 0 || quantity == 0 || userId == 0) {
            message = "Invalid input: ingredientID, quantity or userId is null";
            return new AddToUserRequest(ingredientAdded, message);
        }

        try (Connection connection = DatabaseManagement.connectToDB()) {

            String insertNewIngredient = "INSERT INTO vorrat VALUES (?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(insertNewIngredient)) {
                if (!existingIngredient(ingredientId, userId)) {
                    statement.setInt(1, userId);
                    statement.setInt(2, ingredientId);
                    statement.setInt(3, quantity);
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected == 1) {
                        message = "ingredient added successfully";
                        ingredientAdded = true;
                    } else {
                        message = "failed to add to your list.";
                    }
                } else {
                    message = "Ingredient already exists. Please add another ingredient or update this one";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                message = e.getMessage();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            message = e.getMessage();
        }
        return new AddToUserRequest(ingredientAdded, message);
    }

    // Read ingredients from the user's list
    public ArrayList<IngredientRequest> readUserStock(int userId) {
        ArrayList<IngredientRequest> ingredientRequestList = new ArrayList<>();

        try (Connection connection = DatabaseManagement.connectToDB()) {
            String readStock = "SELECT z.zutat_id, z.zutat_name, z.einheit, v.menge " +
                    "FROM benutzer b " +
                    "INNER JOIN vorrat v ON b.benutzer_id = v.benutzer_id " +
                    "INNER JOIN zutaten z ON z.zutat_id = v.zutat_id " +
                    "WHERE b.benutzer_id = ? " +
                    "ORDER BY z.zutat_name ASC";

            try (PreparedStatement statement = connection.prepareStatement(readStock)) {
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String ingredientName = resultSet.getString("zutat_name");
                    int ingredientId = resultSet.getInt("zutat_id");
                    String unit = resultSet.getString("einheit");
                    double quantity = resultSet.getInt("menge");
                    IngredientRequest ingredientRequest = new IngredientRequest(ingredientName, ingredientId, quantity,
                            unit);
                    ingredientRequestList.add(ingredientRequest);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientRequestList;
    }

    // update the user's stock list
    public boolean updateUserStock(int ingredientId, int quantity, int userId) {
        int rowsUpdated = 0;
        boolean updated = false;
        try (Connection connection = DatabaseManagement.connectToDB()) {

            String updateIngredient = "UPDATE vorrat " +
                    "SET menge = ? " +
                    "WHERE benutzer_id = ? AND " +
                    "zutat_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(updateIngredient)) {
                statement.setInt(1, quantity);
                statement.setInt(2, userId);
                statement.setInt(3, ingredientId);
                rowsUpdated = statement.executeUpdate();
                if (rowsUpdated == 1) {
                    updated = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updated;
    }

    // delete from user's stock list
    public boolean removeIngredientFromUserList(int ingredientId, int userId) {
        boolean removed = false;
        int rowsDeleted = 0;
        try (Connection connection = DatabaseManagement.connectToDB()) {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM vorrat " +
                    "WHERE benutzer_id = ? AND " +
                    "zutat_id = ?")) {
                statement.setInt(1, userId);
                statement.setInt(2, ingredientId);
                rowsDeleted = statement.executeUpdate();
                if (rowsDeleted == 1) {
                    System.out.println("Ingredient successfully deleted");
                    removed = true;
                } else {
                    System.out.println("Ingredient doesn't exist");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return removed;
    }

    // Check if the inserting ingredient already exist in the user's stock
    public boolean existingIngredient(int ingredientId, int userId) {
        boolean ingredientExist = false;

        String ingredientInDB = "SELECT zutat_id FROM vorrat WHERE benutzer_id = ?";
        try (Connection connection = DatabaseManagement.connectToDB()) {
            try (PreparedStatement statement = connection.prepareStatement(ingredientInDB)) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int ingredientIdExist = resultSet.getInt("zutat_id");
                        if (ingredientIdExist == ingredientId) {
                            ingredientExist = true;
                            return ingredientExist;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingredientExist;
    }

}
