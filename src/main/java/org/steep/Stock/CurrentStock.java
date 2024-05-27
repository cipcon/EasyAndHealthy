package org.steep.Stock;

import java.util.HashMap;

// this class is only usefull for the readAllRecipes(Ingredients ingredients) method in CrudRecipe.java class, which method i don't think is necessary
public class CurrentStock {
    // HashMap to store ingredients and their quantities
    private HashMap<String, Double> ingredients;

    public CurrentStock() {
        this.ingredients = new HashMap<String, Double>();
    }

      // Add ingredient with quantity
    public void addIngredientAndQuantity(String ingredient, double quantity) {
        ingredients.put(ingredient, quantity);
    }

    public HashMap<String, Double> getIngredientAndQuantity(){
        return ingredients;
    }
    
}
