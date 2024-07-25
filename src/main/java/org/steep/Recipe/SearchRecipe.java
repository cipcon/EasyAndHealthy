package org.steep.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.steep.Database.DatabaseManagement;
import org.steep.Requests.RecipeIngredients.IngredientRequest;
import org.steep.Requests.RecipeIngredients.RecipeRequest;
import org.steep.Stock.ManageStock;

public class SearchRecipe {

    // This function searches the database for recipes that contain the provided
    // request. It searches both recipe names and ingredient names
    // within the recipe.
    // Returns an ArrayList<String> containing the names of recipes that include the
    // ingredient name (case-insensitive search).
    // Returns and empty ArrayList if the parameter is empty or no recipe found
    public ArrayList<RecipeRequest> recipeSearch(String request) {
        ArrayList<RecipeRequest> recipes = new ArrayList<>();

        if (request.isEmpty()) {
            System.out.println("String is empty");
            return recipes;
        }

        try (Connection connection = DatabaseManagement.connectToDB()) {
            String sqlRecipeSearch = "SELECT DISTINCT r.rezept_name, r.rezept_id, r.portionen " +
                    "FROM rezept r " +
                    "INNER JOIN rezept_zutat rz ON r.rezept_id = rz.rezept_id " +
                    "INNER JOIN zutaten z ON rz.zutat_id = z.zutat_id " +
                    "WHERE r.rezept_name LIKE ? " +
                    "OR z.zutat_name LIKE ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlRecipeSearch)) {
                statement.setString(1, "%" + request.substring(1, request.length() - 1) + "%");
                statement.setString(2, "%" + request.substring(1, request.length() - 1) + "%");

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) { // Check for results
                        do {
                            String recipeName = resultSet.getString("r.rezept_name");
                            int recipeId = resultSet.getInt("rezept_id");
                            int servings = resultSet.getInt("portionen");
                            RecipeRequest recipeRequest = new RecipeRequest(recipeName, recipeId, servings);
                            recipes.add(recipeRequest);
                        } while (resultSet.next());
                    } else {
                        System.out.println("No recipes found for " + request);
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred in ResultSet block.");
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.out.println("An error occurred in PreparedStatement block.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while executing the SQL query.");
            e.printStackTrace();
        }
        for (RecipeRequest recipeRequest : recipes) {
            System.out.println(recipeRequest.toString());
        }
        return recipes;
    }

    // Based on the portion and recipeId parameters
    // return an empty List if sth went wrong
    // or a List that contains the ingredients and quantities needed for the recipe
    public ArrayList<IngredientRequest> cookingPlan(int portions, int recipeId) {
        ArrayList<IngredientRequest> cookingPlan = new ArrayList<>();

        if (recipeId == 0 || portions == 0) {
            System.out.println("Recipe parameters are zero");
            return cookingPlan;
        }

        try (Connection connection = DatabaseManagement.connectToDB()) {
            double standardPortions;
            double onePortion;

            String sqlCookingPlan = "SELECT z.zutat_name, z.einheit, z.zutat_id, rz.menge, r.portionen " +
                    "FROM zutaten z " +
                    "INNER JOIN rezept_zutat rz ON z.zutat_id = rz.zutat_id " +
                    "INNER JOIN rezept r ON r.rezept_id = rz.rezept_id " +
                    "WHERE r.rezept_id = ? " +
                    "ORDER BY z.zutat_name";

            try (PreparedStatement statement = connection.prepareStatement(sqlCookingPlan)) {
                statement.setInt(1, recipeId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    do {
                        String ingredientName = resultSet.getString("zutat_name");
                        int ingredientId = resultSet.getInt("zutat_id");
                        String unit = resultSet.getString("einheit");
                        standardPortions = resultSet.getInt("portionen");
                        onePortion = portions / standardPortions;
                        double quantity = resultSet.getInt("menge") * onePortion;
                        IngredientRequest ingredientRequest = new IngredientRequest(ingredientName, ingredientId,
                                quantity, unit);
                        cookingPlan.add(ingredientRequest);
                    } while (resultSet.next());
                } else {
                    System.out.println("No ingredients found for recipe: " + recipeId);
                }
            } catch (Exception e) {
                System.out.println("An error occurred in PreparedStatement block.");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cookingPlan;
    }

    // Based on the ingredients and quantity a user has in his list
    // return an ArrayList filled with objects containing what still needed for the
    // recipe(ingredient, quantity and unit)
    // or an empty List if sth went wrong
    public ArrayList<IngredientRequest> shoppingList(int portions, int recipeId, int userId) {
        ManageStock manageStock = new ManageStock();
        SearchRecipe searchRecipe = new SearchRecipe();

        ArrayList<IngredientRequest> recipeIngredients = searchRecipe.cookingPlan(portions, recipeId);
        ArrayList<IngredientRequest> userIngredients = manageStock.readUserStock(userId);

        ArrayList<IngredientRequest> shoppingList = new ArrayList<>();

        if (recipeIngredients.isEmpty() || userIngredients.isEmpty()
                || recipeId == 0 || userId == 0) {
            System.out.println(
                    "Sth went wrong with the recipeIngredients, userIngredients or user object and recipe parameters are empty");
            return shoppingList;
        }

        Set<String> specialIngredients = new HashSet<>(
                Set.of("Salz", "Pfeffer", "Wasser", "Olivenöl", "Balsamico-Essig"));

        for (IngredientRequest ingredientFromRecipe : recipeIngredients) {
            // handle salt, pepper and water exceptions
            if (specialIngredients.contains(ingredientFromRecipe.getIngredientName())) {
                continue;
            }

            boolean foundInUserStock = false;

            for (IngredientRequest ingredientFromUser : userIngredients) {
                if (ingredientFromRecipe.getIngredientName().equals(ingredientFromUser.getIngredientName())) {
                    foundInUserStock = true;
                    double missingQuantity = ingredientFromRecipe.getQuantity() - ingredientFromUser.getQuantity();
                    if (missingQuantity > 0) {
                        IngredientRequest ingredientRequest = new IngredientRequest(
                                ingredientFromRecipe.getIngredientName(),
                                ingredientFromRecipe.getIngredientId(),
                                missingQuantity,
                                ingredientFromRecipe.getUnit());
                        shoppingList.add(ingredientRequest);
                    }
                    break; // Break inner loop since the ingredient is found in user stock
                }
            }
            if (!foundInUserStock) {
                IngredientRequest ingredientRequest = new IngredientRequest(
                        ingredientFromRecipe.getIngredientName(),
                        ingredientFromRecipe.getIngredientId(),
                        ingredientFromRecipe.getQuantity(),
                        ingredientFromRecipe.getUnit());
                shoppingList.add(ingredientRequest);
            }
        }
        return shoppingList;
    }

    // Based on the ingredients and their quantities an user already has
    // this function return a list with recommended recipes as object
    // return an empty list, if sth went wrong
    public ArrayList<RecipeRequest> noIdeaMode(int userId) {
        ArrayList<RecipeRequest> recipes = new ArrayList<>();

        try (Connection connection = DatabaseManagement.connectToDB()) {
            String sqlNoIdeaMode = "SELECT r.rezept_name, r.rezept_id, r.portionen, COUNT(rz.zutat_id) " +
                    "FROM rezept r " +
                    "INNER JOIN rezept_zutat rz ON r.rezept_id = rz.rezept_id " +
                    "INNER JOIN zutaten z ON z.zutat_id = rz.zutat_id " +
                    "INNER JOIN vorrat v ON z.zutat_id = v.zutat_id " +
                    "INNER JOIN benutzer b ON b.benutzer_id = v.benutzer_id " +
                    "WHERE b.benutzer_id = ? AND v.menge >= rz.menge " +
                    "GROUP BY r.rezept_name " +
                    "HAVING COUNT(rz.zutat_id) = COUNT(DISTINCT rz.zutat_id) " +
                    "ORDER BY COUNT(rz.zutat_id) DESC";

            try (PreparedStatement statement = connection.prepareStatement(sqlNoIdeaMode)) {
                statement.setInt(1, userId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String recipeName = resultSet.getString("rezept_name");
                        int recipeId = resultSet.getInt("rezept_id");
                        int servings = resultSet.getInt("portionen");
                        RecipeRequest recipeRequest = new RecipeRequest(recipeName, recipeId, servings);
                        recipes.add(recipeRequest);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }
}
