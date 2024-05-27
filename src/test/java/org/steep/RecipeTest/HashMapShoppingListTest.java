package org.steep.RecipeTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import org.steep.Recipe.HashMapShoppingList;
import org.steep.Stock.CurrentStock;

public class HashMapShoppingListTest {

    HashMapShoppingList hashMapShoppingList = new HashMapShoppingList();

    @Test
    void userIngredientsHashMapWorks() {
        HashMap<String, Double> userIngredients = new HashMap<>();
        userIngredients.put("Papaya", 1.0);

        hashMapShoppingList.setUserIngredients("Papaya", 1);

        assertTrue(hashMapShoppingList.getUserIngredients().containsKey("Papaya"));
        assertEquals(1, hashMapShoppingList.getUserIngredients().get("Papaya"));
    }

    @Test
    void recipeIngredientsHashMapWorks() {
        HashMap<String, Double> recipeIngredients = new HashMap<>();
        recipeIngredients.put("Papaya", 1.0);

        hashMapShoppingList.setRecipeIngredients("Papaya", 1);

        assertTrue(hashMapShoppingList.getRecipeIngredients().containsKey("Papaya"));
        assertEquals(1, hashMapShoppingList.getRecipeIngredients().get("Papaya"));
    }

    @Test
    void setRecipeDataHashMapWorks() {
        CurrentStock currentStock = new CurrentStock();
        currentStock.addIngredientAndQuantity("Papaya", 1.0);

        hashMapShoppingList.setRecipeData("Obstsalat", currentStock);

        assertTrue(hashMapShoppingList.getRecipeData().containsKey("Obstsalat"));
        System.out.println(hashMapShoppingList.getRecipeData().keySet());

        CurrentStock ingredientQuantity = hashMapShoppingList.getRecipeData().get("Obstsalat");
        System.out.println(ingredientQuantity.getIngredientAndQuantity());
    }
}
