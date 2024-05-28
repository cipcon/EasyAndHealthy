package org.steep.Ingredients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.steep.Database.DatabaseManagement;
import org.steep.Recipe.CrudRecipe;
import org.steep.Recipe.HashMapShoppingList;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Ingredients {
    // Add ingredient global
    // return one if successfull, 0 if sth went wrong or ingredient already exist
    public int createIngredient(String ingredient, String unit) {
        int rowsAffected = 0;
        int ingredientId = ingredientId(ingredient);

        if (ingredientId == 0) {
            try (Connection connection = DatabaseManagement.connectToDB()) {
                String addGlobalIngredient = "INSERT INTO zutaten(zutat_name, einheit) VALUES(?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(addGlobalIngredient)) {
                    statement.setString(1, ingredient);
                    statement.setString(2, unit);
                    rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println(ingredient + " was added Successfully");
                        return rowsAffected;
                    }
                } catch (SQLException e) {
                    System.out.println("Ingredient '" + ingredient + "' already exists! Please insert another one");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Ingredient already exist");
            return rowsAffected;
        }
        return rowsAffected;
    }

    // read all ingredients ant their unit
    // return a list filled with the ingredients if works, an empty list if doesn't
    public ArrayList<String> readAllIngredients() {
        ArrayList<String> allIngredients = new ArrayList<>();
        String sqlReadAllIngredients = "SELECT zutat_name FROM zutaten";
        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection.prepareStatement(sqlReadAllIngredients);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String ingredient = resultSet.getString("zutat_name");
                allIngredients.add(ingredient);
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
    public ArrayList<String> searchIngredient(String ingredientName) {
        ArrayList<String> ingredients = new ArrayList<>();
        String sqlReadIngredients = "SELECT zutat_name FROM zutaten WHERE zutat_name LIKE ?";
        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection.prepareStatement(sqlReadIngredients)) {
            statement.setString(1, "%" + ingredientName + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String ingredient = resultSet.getString("zutat_name");
                    ingredients.add(ingredient);
                }
            }
            return ingredients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    // Read the ingredients of a recipe, save them in a HashMap and return them
    public HashMap<String, Double> readRecipeIngredients(int recipeId) {
        HashMapShoppingList hashMapShoppingList = new HashMapShoppingList();

        CrudRecipe crudRecipe = new CrudRecipe();
        boolean recipeIdExist = crudRecipe.existingGlobalRecipe(recipeId);

        if (recipeIdExist == true) {
            String readRecipesIngredients = "SELECT z.zutat_name, rz.menge " +
                    "FROM zutaten z " +
                    "INNER JOIN rezept_zutat rz ON z.zutat_id = rz.zutat_id " +
                    "WHERE rezept_id = ? " +
                    "ORDER BY z.zutat_name";

            try (Connection connection = DatabaseManagement.connectToDB();
                    PreparedStatement statement = connection.prepareStatement(readRecipesIngredients)) {
                statement.setInt(1, recipeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        do {
                            String ingredient = resultSet.getString("z.zutat_name");
                            int quantity = resultSet.getInt("rz.menge");
                            hashMapShoppingList.setRecipeIngredients(ingredient, quantity);
                        } while (resultSet.next());
                    } else {
                        System.out.println("No ingredients found for recipeId: " + recipeId);
                    }
                }
                return hashMapShoppingList.getRecipeIngredients();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Recipe doesn't exist");
        }

        return hashMapShoppingList.getRecipeIngredients();
    }

    // get the id of an ingredient
    // return the ingredient id if it was found or 0 if it wasn't or sth went wrong
    public int ingredientId(String ingredient) {
        int ingredientId = 0;
        String sqlIngredientId = "SELECT zutat_id FROM zutaten WHERE zutat_name = ?";
        try (Connection connection = DatabaseManagement.connectToDB();
                PreparedStatement statement = connection.prepareStatement(sqlIngredientId)) {
            statement.setString(1, ingredient);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    ingredientId = resultSet.getInt("zutat_id");
                    return ingredientId;
                } else {
                    System.out.println("Ingredient doesn't exist");
                    return ingredientId;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredientId;
    }

    // get the unit of an ingredient
    // return the ingredient unit if it was found or an empty String if it wasn't or
    // sth went wrong
    public String ingredientUnit(int ingredientId) {
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
    public boolean updateGlobalIngredient(int ingredientId, String newIngredient, String unit) {
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
    public boolean deleteGlobalIngredient(int ingredientId) {
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
    public boolean existingIngredient(int ingredientId) {
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

    public static void main(String[] args) {
        Ingredients ingredients = new Ingredients();
        String unit = UnitEnum.ML.toString().toLowerCase();
        System.out.println(ingredients.createIngredient("Gin", unit));
    }
}
