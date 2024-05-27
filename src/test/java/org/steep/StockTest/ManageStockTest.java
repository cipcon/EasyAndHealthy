package org.steep.StockTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import org.steep.Ingredients.Ingredients;
import org.steep.Stock.ManageStock;
import org.steep.User.User;

public class ManageStockTest {
    ManageStock manageStock = new ManageStock();
    Ingredients ingredients = new Ingredients();

    private User user() {
        User user = new User();
        user.setCurrentUsername("Marta");
        user.setId(6737);
        return user;
    }

    @Test
    void addIngredientToUserListReturnOne() {
        String ingredient = "Avocado";
        int ingredientId = ingredients.ingredientId(ingredient);
        // return true if the ingredient was added to user
        assertEquals(1, manageStock.addIngredientToUserList(ingredientId, 1, user().getId()));

        // return false if the ingredient wasn't added to user. Cannot be added again because already exist
        assertNotEquals(1, manageStock.addIngredientToUserList(ingredientId, 1, user().getId()));

        manageStock.deleteStock(ingredientId, user().getId());
    }

    @Test
    void readUserStockMatchTrue() {
        HashMap<String, Double> ingredientQuantity = new HashMap<>();
        ingredientQuantity.put("Butter", 4.0);
        ingredientQuantity.put("Brot", 500.0);

        // return true if the ingredients and their quantities in both HashMaps match
        assertTrue(manageStock.readUserStock(user().getId()).entrySet().equals(ingredientQuantity.entrySet()));
        assertTrue(manageStock.readUserStock(user().getId()).get("Butter").equals(ingredientQuantity.get("Butter")));

        // return false if the ingredient Butter cannot be found in the HashMap
        assertFalse(manageStock.readUserStock(user().getId()).keySet().contains("Erbsen"));
    }

    @Test
    void updateUserStockSuccessfully() {
        String ingredient = "Avocado";
        int ingredientId = ingredients.ingredientId(ingredient);

        // add the ingredient in the user stock
        manageStock.addIngredientToUserList(ingredientId, 1, user().getId());

        // return one if the quantity of the ingredient is changed
        assertEquals(1, manageStock.updateUserStock(ingredientId, 2, user().getId()));

        // return zero if the quantity of the ingredient couldn't be changed because the ingredient doesn't exist in the user's list
        assertNotEquals(1, manageStock.updateUserStock(16, 2, user().getId()));

        // return zero if the user doesn't exist
        assertNotEquals(1, manageStock.updateUserStock(16, 2, 1));

        // delete insertion
        manageStock.deleteStock(ingredientId, user().getId());
    }

    @Test
    void deleteStockSuccessfully() {
        String ingredient = "Avocado";
        int ingredientId = ingredients.ingredientId(ingredient);

        // add the ingredient
        manageStock.addIngredientToUserList(ingredientId, 1, user().getId());

        // return 1 if the ingredient was deleted successfully
        assertEquals(1, manageStock.deleteStock(ingredientId, user().getId()));

        // return 0 if the ingredient wasn't deleted, because it doesn't exist
        assertNotEquals(1, manageStock.deleteStock(ingredientId, user().getId()));

        // return 0 if the user doesn't exist
        assertNotEquals(1, manageStock.deleteStock(ingredientId, 1));
    }

    @Test 
    void existingIngredientFound() {
        // return true if the ingredient was found in the user's list of ingredients he has  
        assertTrue(manageStock.existingIngredient(40, user().getId()));

        // return false if the ingredient wasn't found
        assertFalse(manageStock.existingIngredient(16, user().getId()));

        // return false if the user doesn't exist
        assertFalse(manageStock.existingIngredient(16, 1));
    }
}
