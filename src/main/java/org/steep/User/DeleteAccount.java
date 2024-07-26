package org.steep.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.steep.Database.DatabaseManagement;
import org.steep.Recipe.CrudRecipe;
import org.steep.Requests.RecipeIngredients.IngredientRequest;
import org.steep.Requests.RecipeIngredients.RecipeRequest;
import org.steep.Stock.ManageStock;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteAccount {
    public DeleteResponse deleteUser(int userId) {
        if (userId == 0) {
            System.out.println("UserId = 0");
            return new DeleteResponse(false, "Invalid user ID");
        }
        boolean deleteRecipes = deleteSavedRecipes(userId);
        boolean deleteIngredients = removeAllIngredientsFromUserList(userId);
        boolean changeRecipeOwner = changeRecipeOwner(userId);
        if (!deleteRecipes || !deleteIngredients
                || !changeRecipeOwner) {
            System.out.println("Something went wrong while deleting database dependencies");
            System.out.println(deleteRecipes);
            System.out.println(deleteIngredients);
            System.out.println(changeRecipeOwner);
            /* Something went wrong while deleting database dependencies */
            return new DeleteResponse(false, "Something went wrong, please try again later or contact support");
        }

        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection
                        .prepareStatement("DELETE FROM benutzer WHERE benutzer_id = ?")) {
            statement.setInt(1, userId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 1) {
                return new DeleteResponse(true, "Successfully deleted");
            } else {
                return new DeleteResponse(false, "User not found or could not be deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new DeleteResponse(false, "Error occurred while deleting user: " + e.getMessage());
        }
    }

    // delete all saved recipes
    public static boolean deleteSavedRecipes(int userId) {
        boolean deleted = false;

        // first proof if the user saved any racipes in his list
        ArrayList<RecipeRequest> userStock = CrudRecipe.recipesFromUser(userId);
        if (userStock.isEmpty()) {
            System.out.println("User didn't saved any recipe");
            deleted = true;
            return deleted;
        }

        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection
                        .prepareStatement("DELETE FROM rezept_benutzer WHERE benutzer_id = ?")) {
            statement.setInt(1, userId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                deleted = true;
                return deleted;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleted;
    }

    // delete all saved ingredients
    public boolean removeAllIngredientsFromUserList(int userId) {
        boolean removed = false;

        // fist proof if the user saved any ingredients in his list
        ManageStock manageStock = new ManageStock();
        ArrayList<IngredientRequest> userStock = manageStock.readUserStock(userId);
        if (userStock.isEmpty()) {
            System.out.println("User didn't saved any ingredients");
            removed = true;
            return removed;
        }

        try (Connection connection = DatabaseManagement.connectToDB()) {
            try (PreparedStatement statement = connection
                    .prepareStatement("DELETE FROM vorrat WHERE benutzer_id = ?")) {
                statement.setInt(1, userId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Ingredients successfully deleted");
                    removed = true;
                    return removed;
                } else {
                    System.out.println("something went wrong while deleting the ingredients ");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return removed;
    }

    public boolean changeRecipeOwner(int userId) {
        boolean removed = false;
        // first check if the user created any recipe
        ArrayList<RecipeRequest> userStock = CrudRecipe.recipesCreatedByUser(userId);
        if (userStock.isEmpty()) {
            System.out.println("User didn't create any recipe");
            removed = true;
            return removed;
        }

        try (Connection connection = DatabaseManagement.connectToDB()) {
            try (PreparedStatement statement = connection
                    .prepareStatement("UPDATE rezept SET benutzer_id = ? WHERE benutzer_id = ?")) {
                statement.setInt(1, 15);
                statement.setInt(2, userId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Owner successfully changed");
                    removed = true;
                    return removed;
                } else {
                    System.out.println("something went wrong while changing the owner ");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return removed;
    }

    public static class DeleteResponse {
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

        public DeleteResponse(boolean deleted, String message) {
            this.deleted = deleted;
            this.message = message;
        }

    }

}
