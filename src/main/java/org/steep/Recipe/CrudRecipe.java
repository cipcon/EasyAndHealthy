package org.steep.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.steep.Database.DatabaseManagement;
import org.steep.Requests.RecipeIngredients.AddToUserRequest;
import org.steep.Requests.RecipeIngredients.IngredientRequest;
import org.steep.Requests.RecipeIngredients.RecipeRequest;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CrudRecipe {
    // Create a new recipe in the database with the provided details.
    // Returns 1 if the recipe is created successfully, 0 if there's an error.
    public static int createRecipe(int portions, String recipeName, int userId) {
        int rowsAffected = 0;
        int recipeId = recipeId(recipeName);

        boolean recipeExist = existingGlobalRecipe(recipeId);
        if (recipeExist == true) {
            System.out.println("Recipe name already exist");
        }
        try (Connection connection = DatabaseManagement.connectToDB()) {
            String createRecipe = "INSERT INTO rezept(rezept_name, portionen, benutzer_id) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(createRecipe)) {
                statement.setString(1, recipeName);
                statement.setInt(2, portions);
                statement.setInt(3, userId);
                rowsAffected = statement.executeUpdate();
                return rowsAffected;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    // Add an existing recipe to the user's recipe list
    // Returns 1 if the recipe is added successfully, 0 if it already exists in the
    // user's list,
    public static AddToUserRequest addRecipeToUser(int recipeId, int userId) {
        boolean recipeAdded = false;
        String message = "";

        if (recipeId == 0 || userId == 0) {
            message = "Invalid input: recipe or user is null.";
            return new AddToUserRequest(recipeAdded, message);
        }

        try (Connection connection = DatabaseManagement.connectToDB()) {
            Boolean recipeExists = recipeExistsInUsersList(recipeId, userId);

            if (recipeExists == true) {
                message = " already exists in your list.";
                return new AddToUserRequest(recipeAdded, message);
            }

            String addRecipeToUser = "INSERT INTO rezept_benutzer(benutzer_id, rezept_id) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(addRecipeToUser)) {
                statement.setInt(1, userId);
                statement.setInt(2, recipeId);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    message = " added successfully to your list";
                    recipeAdded = true;
                } else {
                    message = "failed to add to your list.";
                }
            } catch (SQLIntegrityConstraintViolationException e) {
                message = "recipe or user doesn't exist";
            } catch (Exception e) {
                message = "An error occurred while executing the SQL query.";
                e.printStackTrace();
            }
        } catch (SQLException e) {
            message = "An error occurred while establishing a database connection.";
            e.printStackTrace();
        }
        return new AddToUserRequest(recipeAdded, message);
    }

    // Adds an ingredient to a recipe in the database.
    // Returns the number of rows affected (typically 1 if successful).
    // Returns 0 if the ingredient already exists in the recipe.
    public static int addIngredientToRecipe(int ingredientId, int quantity,
            int recipeId) {
        int rowsAffected = 0;
        if (ingredientId == 0 || quantity == 0 || recipeId == 0) {
            System.out.println("Invalid input, one of the parameters is null or zero");
            return rowsAffected;
        }
        try (Connection connection = DatabaseManagement.connectToDB()) {

            if (ingredientFoundInRecipe(ingredientId, recipeId) == false) {
                String addToCreatedRecipe = "INSERT INTO rezept_zutat VALUES(?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(addToCreatedRecipe)) {
                    statement.setInt(1, recipeId);
                    statement.setInt(2, ingredientId);
                    statement.setInt(3, quantity);
                    rowsAffected = statement.executeUpdate();
                    return rowsAffected;
                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Ingredient doesn't exist");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Ingredient already exist, please insert another one or edit it");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    // Read all recipes, their ingredients and quantity of the ingredients
    // not sure if i need this function
    // output only the recipes that has ingredients
    // return a HashMap in a HashMap. CurrentStock is a HashMap
    public static ArrayList<RecipeRequest> readAllRecipes() {
        ArrayList<RecipeRequest> recipes = new ArrayList<>();
        HashMap<Integer, RecipeRequest> recipeMap = new HashMap<>();

        try (Connection connection = DatabaseManagement.connectToDB()) {
            String readRecipes = "SELECT r.rezept_name, r.rezept_id, r.portionen, z.zutat_name, z.zutat_id, rz.menge, z.einheit "
                    +
                    "FROM rezept r " +
                    "INNER JOIN rezept_zutat rz ON r.rezept_id = rz.rezept_id " +
                    "INNER JOIN zutaten z ON rz.zutat_id = z.zutat_id " +
                    "ORDER BY r.rezept_name";

            try (PreparedStatement statement = connection.prepareStatement(readRecipes)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String recipeName = resultSet.getString("rezept_name");
                    int recipeId = resultSet.getInt("rezept_id");
                    int servings = resultSet.getInt("portionen");
                    String ingredientName = resultSet.getString("zutat_name");
                    int ingredientId = resultSet.getInt("zutat_id");
                    int quantity = resultSet.getInt("menge");
                    String unit = resultSet.getString("einheit");

                    IngredientRequest ingredient = new IngredientRequest(ingredientName, ingredientId, quantity, unit);

                    if (recipeMap.containsKey(recipeId)) {
                        // If the recipe already exists, add the ingredient to it
                        recipeMap.get(recipeId).addIngredient(ingredient);
                    } else {
                        // If the recipe does not exist, create a new RecipeRequest
                        List<IngredientRequest> ingredients = new ArrayList<>();
                        ingredients.add(ingredient);
                        RecipeRequest recipeRequest = new RecipeRequest(recipeName, recipeId, servings, ingredients);
                        recipeMap.put(recipeId, recipeRequest);
                    }
                }

                // Add all recipes from the map to the list
                recipes.addAll(recipeMap.values());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    // Not sure yet if i need this function
    // Return a ArrayList with the recipes the user saved in his recipe list
    // If the ArrayList is empty, sth went wrong
    public static ArrayList<RecipeRequest> recipesFromUser(int userId) {
        ArrayList<RecipeRequest> recipeList = new ArrayList<>();
        HashMap<Integer, RecipeRequest> recipeMap = new HashMap<>();

        try (Connection connection = DatabaseManagement.connectToDB()) {
            String readUsersRecipes = "SELECT r.rezept_name, r.portionen, r.rezept_id, z.zutat_name, z.zutat_id, rz.menge, z.einheit "
                    +
                    "FROM rezept r " +
                    "INNER JOIN rezept_benutzer rb ON rb.rezept_id = r.rezept_id " +
                    "INNER JOIN rezept_zutat rz ON r.rezept_id = rz.rezept_id " +
                    "INNER JOIN zutaten z ON rz.zutat_id = z.zutat_id " +
                    "WHERE rb.benutzer_id = ? " +
                    "ORDER BY r.rezept_name";

            try (PreparedStatement statement = connection.prepareStatement(readUsersRecipes)) {
                statement.setInt(1, userId);

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String recipeName = resultSet.getString("rezept_name");
                    int recipeId = resultSet.getInt("rezept_id");
                    int servings = resultSet.getInt("portionen");
                    String ingredientName = resultSet.getString("zutat_name");
                    int ingredientId = resultSet.getInt("zutat_id");
                    int quantity = resultSet.getInt("menge");
                    String unit = resultSet.getString("einheit");

                    IngredientRequest ingredient = new IngredientRequest(ingredientName, ingredientId, quantity, unit);

                    if (recipeMap.containsKey(recipeId)) {
                        // If the recipe already exists, add the ingredient to it
                        recipeMap.get(recipeId).addIngredient(ingredient);
                    } else {
                        // If the recipe does not exist, create a new RecipeRequest
                        List<IngredientRequest> ingredients = new ArrayList<>();
                        ingredients.add(ingredient);
                        RecipeRequest recipeRequest = new RecipeRequest(recipeName, recipeId, servings, ingredients);
                        recipeMap.put(recipeId, recipeRequest);
                    }
                }

                // Add all recipes from the map to the list
                recipeList.addAll(recipeMap.values());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipeList;
    }

    // Updates recipe name & portions if it exists and belongs to the user.
    // Returns affected rows (1 on success, 0 otherwise).
    public static int updateGlobalRecipe(String newRecipeName, int recipeId, int portions, int userId) {
        int rowsAffected = 0;
        if (existingGlobalRecipe(recipeId) == false) {
            System.out.println("Recipe doesn't exist");

        }
        try (Connection connection = DatabaseManagement.connectToDB()) {
            String updateGlobalRecipe = "UPDATE rezept SET rezept_name = ?, portionen = ? WHERE benutzer_id = ? and rezept_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(updateGlobalRecipe)) {
                statement.setString(1, newRecipeName);
                statement.setInt(2, portions);
                statement.setInt(3, userId);
                statement.setInt(4, recipeId);
                rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Name and portions of the recipe updated successfully");
                    return rowsAffected;
                } else {
                    System.out.println("You are allowed to update only your recipes");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    // Updates ingredient quantity in a recipe belonging to the user.
    // Returns affected rows (1 on success, 0 otherwise).
    public static int updateIngredientQuantity(int ingredientId, int quantity,
            int recipeId, int userId) {

        int rowsUpdated = 0;

        if (existingGlobalRecipe(recipeId) == true) {

            try (Connection connection = DatabaseManagement.connectToDB()) {
                String updateRecipeUserTable = "UPDATE rezept_zutat rz " +
                        "INNER JOIN rezept r ON rz.rezept_id = r.rezept_id " +
                        "SET menge = ? " +
                        "WHERE rz.rezept_id = ? AND rz.zutat_id = ? " +
                        "AND r.benutzer_id = ?";

                try (PreparedStatement statement = connection.prepareStatement(updateRecipeUserTable)) {
                    statement.setInt(1, quantity);
                    statement.setInt(2, recipeId);
                    statement.setInt(3, ingredientId);
                    statement.setInt(4, userId);
                    rowsUpdated = statement.executeUpdate();
                    if (rowsUpdated > 0) {
                        return rowsUpdated;
                    } else {
                        System.out.println("You are allowed to update only recipes you created");
                    }

                } catch (SQLSyntaxErrorException e) {
                    System.out.println("You are not allowed to modify recipes that do not belongs to you");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Recipe doesn't exist");
        }

        return rowsUpdated;
    }

    // Removes recipe from user's list if it exists. Returns affected rows (1 on
    // success, 0 otherwise).
    public static int deleteFromRecipeUserTable(int recipeId, int userId) {
        int rowsDeleted = 0;

        if (existingGlobalRecipe(recipeId) == true) {
            try (Connection connection = DatabaseManagement.connectToDB()) {
                String sqlDeleteRecipeFromUserList = "DELETE FROM rezept_benutzer WHERE benutzer_id = ? AND rezept_id = ?";
                try (PreparedStatement statement = connection.prepareStatement(sqlDeleteRecipeFromUserList)) {
                    statement.setInt(1, userId);
                    statement.setInt(2, recipeId);
                    rowsDeleted = statement.executeUpdate();
                    return rowsDeleted;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Recipe doesn't exist");
        }
        return rowsDeleted;
    }

    // Removes recipe from all users list if it exists. Returns affected rows
    // (1 on success, 0 otherwise).
    public static int deleteRecipeFromAllUsersLists(int recipeId) {

        int rowsDeleted = 0;

        if (existingGlobalRecipe(recipeId) == false) {
            System.out.println("Recipe doesn't exist");
            return rowsDeleted;
        }

        try (Connection connection = DatabaseManagement.connectToDB()) {
            String sqldeleteRecipeFromUsersLists = "DELETE FROM rezept_benutzer " +
                    "WHERE rezept_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sqldeleteRecipeFromUsersLists)) {
                statement.setInt(1, recipeId);
                rowsDeleted = statement.executeUpdate();
                return rowsDeleted;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsDeleted;
    }

    // Deletes recipe's ingredients (from rezept_zutat) if the recipe exists.
    // Returns affected rows (number of deleted entries).
    public static int deleteFromRecipeIngredientTable(int recipeId) {
        int rowsDeleted = 0;
        boolean recipeExist = existingGlobalRecipe(recipeId);

        if (recipeId == 0 || recipeExist == false) {
            System.out.println("Recipe is null or doesn't exist");
            return rowsDeleted;
        }

        try (Connection connection = DatabaseManagement.connectToDB()) {
            String deleteFromRecipeIngredientTable = "DELETE FROM rezept_zutat " +
                    "WHERE rezept_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(deleteFromRecipeIngredientTable)) {
                statement.setInt(1, recipeId);
                rowsDeleted = statement.executeUpdate();
                return rowsDeleted;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsDeleted;
    }

    // Delete only one ingredient from rezept_zutat table
    // Returns one if successfull or zero if sth went wrong or already exist

    public static int deleteOnlyOneIngredientFromRecipeIngredientTable(int ingredientId, int recipeId) {
        int rowsDeleted = 0;
        boolean ingredientFound = ingredientFoundInRecipe(ingredientId, recipeId);

        if (recipeId == 0 || ingredientId == 0) {
            System.out.println("No recipe or ingredient inserted as parameter");
            return rowsDeleted;
        }

        if (ingredientFound == true) {
            try (Connection connection = DatabaseManagement.connectToDB()) {
                String deleteFromRecipeIngredientTable = "DELETE FROM rezept_zutat " +
                        "WHERE rezept_id = ? " +
                        "AND zutat_id = ?";

                try (PreparedStatement statement = connection.prepareStatement(deleteFromRecipeIngredientTable)) {
                    statement.setInt(1, recipeId);
                    statement.setInt(2, ingredientId);

                    rowsDeleted = statement.executeUpdate();
                    return rowsDeleted;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Ingredient doesn't exist in the recipe's list of ingredients");
        }
        return rowsDeleted;
    }

    // Permanently deletes a recipe from the database, including:
    // - Deleting its associated ingredients from the rezept_zutat table.
    // - Removing it from all user lists (rezept_benutzer).
    // - Deleting the recipe itself (rezept) if it belongs to the current user.
    // Returns 1 if the recipe was successfull deleted.
    // Returns 0 if the recipe doesn't exist or the user is null.
    public static int deleteRecipeGlobally(int recipeId, int userId) {
        int rowsDeleted = 0;
        boolean recipeExist = existingGlobalRecipe(recipeId);

        if (recipeExist == false || userId == 0) {
            System.out.println("Recipe doesn't exist or user is null");
            return rowsDeleted;
        }
        try (Connection connection = DatabaseManagement.connectToDB()) {
            deleteFromRecipeIngredientTable(recipeId);
            deleteRecipeFromAllUsersLists(recipeId);

            String deleteRecipeGlobally = "DELETE FROM rezept " +
                    "WHERE rezept_id = ? AND benutzer_id = ?";

            try (PreparedStatement statement = connection.prepareStatement(deleteRecipeGlobally)) {
                statement.setInt(1, recipeId);
                statement.setInt(2, userId);
                rowsDeleted = statement.executeUpdate();
                return rowsDeleted;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowsDeleted;
    }

    // This function retrieves a list of recipe IDs for recipes created by the
    // current user.
    // If no recipes are found, an empty ArrayList is returned.
    public static ArrayList<RecipeRequest> recipesCreatedByUser(int userId) {
        ArrayList<RecipeRequest> userRecipes = new ArrayList<>();
        try (Connection connection = DatabaseManagement.connectToDB()) {
            String sqlRecipesCreatedByUser = "SELECT rezept_id, rezept_name, portionen FROM rezept " +
                    "WHERE benutzer_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlRecipesCreatedByUser)) {
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    do {
                        int recipeId = resultSet.getInt("rezept_id");
                        String recipeName = resultSet.getString("rezept_name");
                        int servings = resultSet.getInt("portionen");
                        RecipeRequest recipeRequest = new RecipeRequest(recipeName, recipeId, servings);
                        userRecipes.add(recipeRequest);
                    } while (resultSet.next());
                } else {
                    System.out.println("No recipes added by ");
                }
                return userRecipes;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userRecipes;
    }

    // Return recipeId based on recipe name
    // Return 0 if the recipe wasn't found
    public static int recipeId(String recipeName) {
        int recipeID = 0;
        try (Connection connection = DatabaseManagement.connectToDB()) {
            String sqlRecipeId = "SELECT rezept_id FROM rezept " +
                    "WHERE rezept_name = ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlRecipeId)) {
                statement.setString(1, recipeName);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    recipeID = resultSet.getInt("rezept_id");
                    return recipeID;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // Checks if an ingredient with the given ID already exists in the specified
    // recipe
    // Returns true if the ingredient exists in the recipe, False otherwise.
    public static boolean ingredientFoundInRecipe(int ingredientId, int recipeId) {
        boolean ingredientFoundInRecipe = false;
        try (Connection connection = DatabaseManagement.connectToDB()) {
            String ingredientBelongingRecipe = "SELECT zutat_id FROM rezept_zutat WHERE rezept_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(ingredientBelongingRecipe)) {
                statement.setInt(1, recipeId);

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int ingredientIdExist = resultSet.getInt("zutat_id");
                    if (ingredientIdExist == ingredientId) {
                        ingredientFoundInRecipe = true;
                        return ingredientFoundInRecipe;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientFoundInRecipe;
    }

    // Checks if a recipe with the given ID exists in the user's list of recipes.
    // Returns true if the recipe exists in the user's list, False otherwise.
    public static Boolean recipeExistsInUsersList(int recipeId, int userId) {
        try (Connection connection = DatabaseManagement.connectToDB()) {
            Boolean existingRecipeUserDB = false;
            String sqlExistingRecipeUserDB = "SELECT rezept_id " +
                    "FROM rezept_benutzer " +
                    "WHERE benutzer_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlExistingRecipeUserDB)) {
                statement.setInt(1, userId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int recipeIdExist = resultSet.getInt("rezept_id");
                    if (recipeIdExist == recipeId) {
                        existingRecipeUserDB = true;
                        return existingRecipeUserDB;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Checks if a recipe with the given name exists in the database.
    // Returns true if the recipe exists, False otherwise.
    public static boolean existingGlobalRecipe(int recipeId) {
        boolean recipeExist = false;
        String searchRecipes = "SELECT rezept_id FROM rezept WHERE rezept_id = ?";

        try (Connection connection = DatabaseManagement.connectToDB()) {
            try (PreparedStatement statement = connection.prepareStatement(searchRecipes)) {
                statement.setInt(1, recipeId);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    int existingRecipeId = resultSet.getInt("rezept_id");
                    if (recipeId == existingRecipeId) {
                        recipeExist = true;
                        return recipeExist;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipeExist;
    }

    // Return recipeId based on recipe name
    // Return 0 if the recipe wasn't found
    public static String recipeName(int recipeid) {
        String recipeName = "";
        try (Connection connection = DatabaseManagement.connectToDB()) {
            String sqlRecipeId = "SELECT rezept_name FROM rezept " +
                    "WHERE rezept_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sqlRecipeId)) {
                statement.setInt(1, recipeid);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    recipeName = resultSet.getString("rezept_name");
                    return recipeName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recipeName;
    }

}