package org.steep.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.steep.Database.DatabaseManagement;
import org.steep.Stock.ManageStock;

public class SearchRecipe {

    // This function searches the database for recipes that contain the provided
    // recipeIngredientName. It searches both recipe names and ingredient names
    // within the recipe.
    // Returns an ArrayList<String> containing the names of recipes that include the
    // ingredient name (case-insensitive search).
    // Returns and empty ArrayList if the parameter is empty or no recipe found
    public ArrayList<String> recipeSearch(String recipeIngredientName) {
        ArrayList<String> recipes = new ArrayList<>();

        if (recipeIngredientName.isEmpty()) {
            System.out.println("String is empty");
            return recipes;
        }

        try (Connection connection = DatabaseManagement.connectToDB()) {
            String sqlRecipeSearch = "SELECT DISTINCT r.rezept_name " +
                    "FROM rezept r " +
                    "INNER JOIN rezept_zutat rz ON r.rezept_id = rz.rezept_id " +
                    "INNER JOIN zutaten z ON rz.zutat_id = z.zutat_id " +
                    "WHERE r.rezept_name LIKE ? " +
                    "OR z.zutat_name LIKE ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlRecipeSearch)) {
                statement.setString(1, "%" + recipeIngredientName + "%");
                statement.setString(2, "%" + recipeIngredientName + "%");

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) { // Check for results
                        do {
                            String recipe = resultSet.getString("r.rezept_name");
                            recipes.add(recipe);
                        } while (resultSet.next());
                        return recipes;
                    } else {
                        System.out.println("No recipes found for " + recipeIngredientName);
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
        return recipes;
    }

    // Based on the parameters and the ingredients of a recipe
    // return an empty HashMap if sth went wrong
    // or a HashMap that contains the ingredients and quantity needed for the recipe
    public HashMap<String, Double> cookingPlan(int portions, int recipeId) {
        HashMap<String, Double> cookingPlan = new HashMap<>();

        if (recipeId == 0) {
            System.out.println("Recipe parameter zero");
            return cookingPlan;
        }

        try (Connection connection = DatabaseManagement.connectToDB()) {
            double xPortions = 0;
            double onePortion = 0;

            String sqlCookingPlan = "SELECT z.zutat_name, rz.menge, r.portionen " +
                    "FROM zutaten z " +
                    "INNER JOIN rezept_zutat rz ON z.zutat_id = rz.zutat_id " +
                    "INNER JOIN rezept r ON r.rezept_id = rz.rezept_id " +
                    "WHERE r.rezept_id = ? " +
                    "ORDER BY z.zutat_name";

            try (PreparedStatement statement = connection.prepareStatement(sqlCookingPlan)) {
                statement.setInt(1, recipeId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        do {
                            String ingredient = resultSet.getString("z.zutat_name");
                            xPortions = resultSet.getInt("r.portionen");
                            onePortion = portions / xPortions;
                            double quantity = resultSet.getInt("rz.menge") * onePortion;
                            cookingPlan.put(ingredient, quantity);
                        } while (resultSet.next());
                        return cookingPlan;
                    } else {
                        System.out.println("No ingredients found for recipe: " + recipeId);
                        return cookingPlan;
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred in ResultSet block.");
                    e.printStackTrace();
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
    // return a HashMap filled with what still needed for the recipe
    // or an empty hashMap if sth went wrong
    public HashMap<String, Double> shoppingList(int portions, int recipeId, int userId) {
        ManageStock manageStock = new ManageStock();
        SearchRecipe searchRecipe = new SearchRecipe();

        HashMap<String, Double> recipeIngredients = searchRecipe.cookingPlan(portions, recipeId);
        HashMap<String, Double> userIngredients = manageStock.readUserStock(userId);

        HashMap<String, Double> shoppingList = new HashMap<>();

        if (recipeIngredients.isEmpty() || userIngredients.isEmpty()
                || recipeId == 0) {
            System.out.println(
                    "Sth went wrong with the recipeIngredients HashMaps, userIngredients HashMaps, user object or recipe variable is empty");
            return shoppingList;
        }

        for (String ingredient : recipeIngredients.keySet()) {
            double recipeQuantity = recipeIngredients.get(ingredient);

            // handle salt, pepper and water exceptions
            if (ingredient.equals("Salz") || ingredient.equals("Pfeffer") || ingredient.equals("Wasser")) {

            } else {
                if (userIngredients.containsKey(ingredient)) {
                    double userQuantity = userIngredients.get(ingredient);
                    double missingQuantity = recipeQuantity - userQuantity;

                    if (missingQuantity > 0) {
                        shoppingList.put(ingredient, missingQuantity);
                    }
                } else {
                    shoppingList.put(ingredient, recipeQuantity);
                }
            }
        }
        return shoppingList;
    }

    // Based on the ingredients and their quantities an user already has
    // this function return a list with recommended recipes
    // return an empty list, if sth went wrong
    public List<String> noIdeaMode(int userId) {
        ArrayList<String> savedRecipes = new ArrayList<>();

        try (Connection connection = DatabaseManagement.connectToDB()) {
            String sqlNoIdeaMode = "SELECT r.rezept_name, COUNT(rz.zutat_id) " +
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
                        String recipeName = resultSet.getString("r.rezept_name");
                        savedRecipes.add(recipeName);
                    }
                    return savedRecipes;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return savedRecipes;
    }
}
