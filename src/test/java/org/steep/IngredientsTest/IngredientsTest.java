package org.steep.IngredientsTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.steep.Ingredients.Ingredients;
import org.steep.Ingredients.UnitEnum;
import org.steep.Recipe.CrudRecipe;

public class IngredientsTest {

    public Ingredients ingredients = new Ingredients();

    @Test
    void globalIngredientAddedSuccessfully() {
        String eier = "Eier";
        int eierId = ingredients.ingredientId(eier);
        // add the ingredient global
        assertEquals(1, ingredients.createIngredient(eier, "stück"));

        // try to insert it again. Return value must be false
        assertNotEquals(1, ingredients.createIngredient(eier, "stück"));

        // delete insertion
        ingredients.deleteGlobalIngredient(eierId);
    }

    @Test
    void readAllIngredientsCheckIngredients() {
        ArrayList<String> allIngredients = ingredients.readAllIngredients();
        ArrayList<String> coupleOfIngredients = new ArrayList<>();

        coupleOfIngredients.add("Ananas");
        coupleOfIngredients.add("Butter");
        coupleOfIngredients.add("Erbsen");
        coupleOfIngredients.add("Kokosöl");

        // return true if the ingredients match
        assertTrue(allIngredients.containsAll(coupleOfIngredients));

        // return false if the ingredients doesn't match
        assertFalse(allIngredients.contains("Laptop"));
    }

    @Test
    void searchIngredientsAfterName() {
        ArrayList<String> ingredientsList = ingredients.searchIngredient("mehl");
        ArrayList<String> emptyList = ingredients.searchIngredient("Pistazien");
        ArrayList<String> mehl = new ArrayList<>();

        mehl.add("Dinkel Vollkornmehl");
        mehl.add("Dinkelmehl Typ 1050");

        // return true if the ingredientList ArrayList contains the ingredients should
        // contain
        assertTrue(ingredientsList.containsAll(mehl));

        // return true if searching for an ingredient that doesn't exist in the db
        // return an empty ArrayList
        assertTrue(emptyList.isEmpty());

    }

    @Test
    void readRecipeIngredientsCheckIfMatch() {
        String recipe = "Cashewmilch";
        CrudRecipe crudRecipe = new CrudRecipe();
        int recipeId = crudRecipe.recipeId(recipe);
        HashMap<String, Double> recipeIngredientsHashMap = ingredients.readRecipeIngredients(recipeId);
        ArrayList<String> cashewmilchIngredientsCheck = new ArrayList<>();
        ArrayList<String> cashewmilchIngredients = new ArrayList<>();

        cashewmilchIngredientsCheck.add("Cashewkerne");
        cashewmilchIngredientsCheck.add("Wasser");

        for (String i : recipeIngredientsHashMap.keySet()) {
            String ingredient = i;
            cashewmilchIngredients.add(ingredient);
        }

        // return true if the ingredients match
        assertTrue(cashewmilchIngredients.containsAll(cashewmilchIngredientsCheck));

        // the recipe doesn't contain Zucker, return false
        assertFalse(cashewmilchIngredients.contains("Zucker"));

    }

    @Test
    void ingredientIdFounded() {
        int id = ingredients.ingredientId("Brot");
        int idButter = 9457;

        // return true if the correct inserted ingredietId match
        assertEquals(40, id);

        // the id of Butter is false, must return false
        assertNotEquals(20, idButter);
    }

    @Test
    void ingredientUnitFounded() {
        String brot = "Brot";
        String unitBrot = ingredients.ingredientUnit(ingredients.ingredientId(brot));
        String unitErbsen = "G";
        String scheibe = UnitEnum.SCHEIBE.toString();

        // return true if the correct inserted unit match
        assertEquals(scheibe, unitBrot);

        // the unit of Erbsen is false, must return false
        assertNotEquals(scheibe, unitErbsen);
    }

    @Test
    void updateGlobalIngredientSuccessfully() {


        // add the ingredient
        ingredients.createIngredient("Eier", "stück");

        String firstIngredient = "Eier";
        int firstIngredientId = ingredients.ingredientId(firstIngredient);
        String secondIngredient = "Gemüs";

        // update it, check if successfull
        boolean ingredientUpdatedSuccessfully = ingredients
                .updateGlobalIngredient(ingredients.ingredientId(firstIngredient), "Ei", "stück");

        // check if by trying to modify an ingredient doesn't exist, return false
        boolean ingredientUpdatedFalse = ingredients.updateGlobalIngredient(ingredients.ingredientId(secondIngredient),
                "Gemüse", "stück");

        // return true if the ingredient was updated successfully
        assertTrue(ingredientUpdatedSuccessfully);

        // return false if the ingredient wasn't updated because the name is false
        assertFalse(ingredientUpdatedFalse);

        // delete the updated ingredient
        ingredients.deleteGlobalIngredient(firstIngredientId);
    }

    // even though I ordered the tests and added Thread.sleep(1000), the update and
    // delete Tests still give back errors if i try to run all the tests at once
    @Test
    void deleteGlobalIngredientSuccessfully() {
        // insert the ingredient before delete it
        String ei = "Ei";
        ingredients.createIngredient(ei, "stuck");
        int eiId = ingredients.ingredientId(ei);


        // try if successfully delete
        boolean globalIngredientDeleted = ingredients.deleteGlobalIngredient(eiId);

        // try to delete an ingredient that doesn't exist, return false
        boolean globalIngredientDeleteFalse = ingredients.deleteGlobalIngredient(eiId);

        assertTrue(globalIngredientDeleted);

        assertFalse(globalIngredientDeleteFalse);
    }

    @Test
    void existingIngredientReturnOne() {
        String oliven = "Oliven";
        int olivenId = ingredients.ingredientId(oliven);

        // return true if the ingredient oliven was found
        assertTrue(ingredients.existingIngredient(olivenId));

        // return false if the ingredient Brombeeren wasn't found
        String brombeeren = "Brombeeren";
        int brombeerenId = ingredients.ingredientId(brombeeren);
        assertFalse(ingredients.existingIngredient(brombeerenId));
    }
}
