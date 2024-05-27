package org.steep.Recipe;

import java.util.HashMap;
import org.steep.Stock.CurrentStock;

public class HashMapShoppingList {
    // HashMap to store user's ingredients
    private HashMap<String, Double> userIngredients = new HashMap<>();

    // HashMap to store ingredient requirements for recipes
    private HashMap<String, Double> recipeIngredients = new HashMap<>();

    // HashMap to store recipe name, ingredient and quantity
    private HashMap<String, CurrentStock> recipeData = new HashMap<>();

    public void setUserIngredients(String ingredientName, double quantity) {
        userIngredients.put(ingredientName, quantity);
    }

    public void setRecipeIngredients(String ingredientName, double quantity) {
        recipeIngredients.put(ingredientName, quantity);
    }

    public void setRecipeData(String recipeName, CurrentStock currentStock) {
        recipeData.put(recipeName, currentStock);
    }

    public HashMap<String, Double> getUserIngredients() {
        return userIngredients;
    }

    public HashMap<String, Double> getRecipeIngredients() {
        return recipeIngredients;
    }

    public HashMap<String, CurrentStock> getRecipeData() {
        return recipeData;
    }
   
}