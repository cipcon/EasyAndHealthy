package org.steep.Ingredients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.steep.Database.DatabaseManagement;
import org.steep.Recipe.CrudRecipe;
import org.steep.Requests.RecipeIngredients.IngredientRequest;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Ingredients {
    // Add ingredient global
    // return one if successfull, 0 if sth went wrong or ingredient already exist
    public static IngredientRequest createIngredient(String ingredient, String unit) {
        boolean ingredientExist = ingredientExist(ingredient);
        String message = "";
        boolean added = false;

        if (ingredientExist) {
            return new IngredientRequest("Ingredient already exist", added);
        } else {
            try (Connection connection = DatabaseManagement.connectToDB()) {
                String addGlobalIngredient = "INSERT INTO zutaten(zutat_name, einheit) VALUES(?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(addGlobalIngredient)) {
                    statement.setString(1, ingredient);
                    statement.setString(2, unit);
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        message = ingredient + " was added Successfully";
                        added = true;
                        return new IngredientRequest(message, added);
                    }
                } catch (SQLException e) {
                    message = e.getMessage();
                }
            } catch (SQLException e) {
                message = e.getMessage();
            }
        }

        return new IngredientRequest(message, added);
    }

    // read all ingredients ant their unit
    // return a list filled with the ingredients if works, an empty list if doesn't
    public static ArrayList<IngredientRequest> getAllIngredients() {
        ArrayList<IngredientRequest> allIngredients = new ArrayList<>();
        String sqlReadAllIngredients = "SELECT zutat_name, zutat_id, einheit FROM zutaten";
        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection.prepareStatement(sqlReadAllIngredients);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String ingredientName = resultSet.getString("zutat_name");
                int ingredientId = resultSet.getInt("zutat_id");
                String unit = resultSet.getString("einheit");
                IngredientRequest ingredientRequest = new IngredientRequest(ingredientName, ingredientId, unit);
                allIngredients.add(ingredientRequest);
            }
            return allIngredients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allIngredients;
    }

    // read ingredients based on the letters they contain
    // return a list with ingredients based on the letters they contain
    // return an empty list if wth went wrong or no ingredients founded
    public static ArrayList<IngredientRequest> searchIngredient(String ingredientName) {
        ArrayList<IngredientRequest> ingredients = new ArrayList<>();
        String sqlReadIngredients = "SELECT zutat_name, zutat_id, einheit FROM zutaten WHERE zutat_name LIKE ?";
        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection.prepareStatement(sqlReadIngredients)) {
            statement.setString(1, "%" + ingredientName + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String ingredient = resultSet.getString("zutat_name");
                    int ingredientId = resultSet.getInt("zutat_id");
                    String unit = resultSet.getString("einheit");
                    IngredientRequest ingredientRequest = new IngredientRequest(ingredient, ingredientId, unit);
                    ingredients.add(ingredientRequest);
                }
            }
            return ingredients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    // Read the ingredients of a recipe, save them in a HashMap and return them
    public static ArrayList<IngredientRequest> readRecipeIngredients(int recipeId) {
        boolean recipeIdExist = CrudRecipe.existingGlobalRecipe(recipeId);
        if (recipeIdExist) {
            String readRecipesIngredients = "SELECT z.zutat_name, z.zutat_id, rz.menge, z.einheit " +
                    "FROM zutaten z " +
                    "INNER JOIN rezept_zutat rz ON z.zutat_id = rz.zutat_id " +
                    "WHERE rezept_id = ? " +
                    "ORDER BY z.zutat_name";

            ArrayList<IngredientRequest> ingredients = new ArrayList<>();

            try (Connection connection = DatabaseManagement.connectToDB();
                    PreparedStatement statement = connection.prepareStatement(readRecipesIngredients)) {
                statement.setInt(1, recipeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String ingredientName = resultSet.getString("z.zutat_name");
                        int ingredientId = resultSet.getInt("z.zutat_id");
                        int quantity = resultSet.getInt("rz.menge");
                        String unit = resultSet.getString("z.einheit");
                        IngredientRequest ingredientRequest = new IngredientRequest(ingredientName, ingredientId,
                                quantity,
                                unit);
                        ingredients.add(ingredientRequest);
                    }
                    if (ingredients.isEmpty()) {
                        System.out.println("No ingredients found for recipeId: " + recipeId);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return ingredients;
        } else {
            System.out.println("Recipe doesn't exist");
            return new ArrayList<>(); // Return an empty list if the recipe doesn't exist
        }
    }

    // get the id of an ingredient
    // return the ingredient id if it was found or 0 if it wasn't or sth went wrong
    public static boolean ingredientExist(String ingredient) {
        boolean ingredientExist = false;
        String sqlIngredientId = "SELECT zutat_id FROM zutaten WHERE zutat_name = ?";
        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection.prepareStatement(sqlIngredientId)) {
            statement.setString(1, ingredient);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Ingredient exist");
                    return ingredientExist = true;
                } else {
                    System.out.println("Ingredient doesn't exist");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientExist;
    }

    // get the unit of an ingredient
    // return the ingredient unit if it was found or an empty String if it wasn't or
    // sth went wrong
    public static String ingredientUnit(int ingredientId) {
        String ingredientUnit = "";
        String sqlIngredientUnit = "SELECT einheit FROM zutaten WHERE zutat_id = ?";

        if (existingIngredient(ingredientId) == false) {
            System.out.println("Ingredient doesn't exist");
            return ingredientUnit;
        }
        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection.prepareStatement(sqlIngredientUnit)) {
            statement.setInt(1, ingredientId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ingredientUnit = resultSet.getString("einheit");
                    return ingredientUnit;
                } else {
                    System.out.println("Invalid ingredient");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientUnit;
    }

    // update the name or unit of an ingredient
    // return 1 if the ingredient was successfully updated or 0 if it wasn't
    public static boolean updateGlobalIngredient(int ingredientId, String newIngredient, String unit) {
        String updateIngredient = "UPDATE zutaten SET zutat_name = ?, einheit = ? WHERE zutat_id = ?";
        boolean ingredientUpdated = false;

        if (existingIngredient(ingredientId) == false) {
            System.out.println("Ingredient doesn't exist");
            return ingredientUpdated;
        }

        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection.prepareStatement(updateIngredient)) {
            statement.setString(1, newIngredient);
            statement.setString(2, unit);
            statement.setInt(3, ingredientId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Ingredient updated successfully");
                ingredientUpdated = true;
            } else {
                System.out.println("Sth went wrong, please try again");
            }
            return ingredientUpdated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientUpdated;
    }

    // delete an ingredient globally
    // return 1 if the ingredient was successfully deleted or 0 if it wasn't
    public static boolean deleteGlobalIngredient(int ingredientId) {
        boolean globalIngredientDeleted = false;

        if (existingIngredient(ingredientId) == false) {
            System.out.println("Ingredient doesn't exist");
            return globalIngredientDeleted;
        }

        String sqlDeleteGlobalIngredient = "DELETE FROM zutaten WHERE zutat_id = ?";
        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection.prepareStatement(sqlDeleteGlobalIngredient)) {
            statement.setInt(1, ingredientId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(ingredientId + " deleted successfully!");
                globalIngredientDeleted = true;
            } else {
                System.out.println("Sth went wrong, please try again");
            }
            return globalIngredientDeleted;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return globalIngredientDeleted;
    }

    // check if an ingredient exist
    // return true if exist, false if doesn't
    public static boolean existingIngredient(int ingredientId) {
        boolean ingredientExist = false;
        String searchRecipes = "SELECT zutat_id FROM zutaten WHERE zutat_id = ?";

        try (Connection connection = DatabaseManagement.connectToDB()) {
            try (PreparedStatement statement = connection.prepareStatement(searchRecipes)) {
                statement.setInt(1, ingredientId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int existingIngredientId = resultSet.getInt("zutat_id");
                    if (ingredientId == existingIngredientId) {
                        ingredientExist = true;
                        return ingredientExist;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingredientExist;
    }
}
