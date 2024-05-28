package org.steep.RecipeTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import org.steep.Ingredients.Ingredients;
import org.steep.Recipe.CrudRecipe;
import org.steep.Stock.CurrentStock;
import org.steep.User.User;

public class CrudRecipeTest {
    private User user() {
        User user = new User();
        user.setCurrentUsername("user");
        user.setId(15);
        return user;
    }

    CrudRecipe crudRecipe = new CrudRecipe();
    @Test
    void createRecipeReturnOne() {
        String recipe = "Erdnussmuss";
        assertEquals(1, crudRecipe.createRecipe(10, recipe, user().getId()));

        assertNotEquals(1, crudRecipe.createRecipe(10, recipe, user().getId()));

        crudRecipe.deleteRecipeGlobally(crudRecipe.recipeId(recipe), user().getId());
    }

    // Tests run at the same time? That's why I can't run the whole test at once
    @Test
    void addRecipeToUserReturnOne() {
        // return one if the ingredient was successfully added to user's list
        String recipe = "Omelett";
        assertEquals(1, crudRecipe.addRecipeToUser(crudRecipe.recipeId(recipe), user().getId()));

        // return zero if the ingredient wasn't added to user's list (because already
        // exist)
        assertEquals(0, crudRecipe.addRecipeToUser(crudRecipe.recipeId(recipe), user().getId()));

        // delete the insertion
        crudRecipe.deleteFromRecipeUserTable(crudRecipe.recipeId(recipe), user().getId());

    }

    @Test
    void addIngredientToCreatedRecipeReturnOne() {
        String ingredient = "Salz";
        String recipe = "Guacamole";

        // return one if ingredient added succcessfully to the recipe's Ingredients list
        assertEquals(1,
                crudRecipe.addIngredientToRecipe(Ingredients.ingredientId(ingredient), 1, crudRecipe.recipeId(recipe)));

        // return zero if ingredient wasn't added to the recipe's Ingredients list
        assertEquals(0,
                crudRecipe.addIngredientToRecipe(Ingredients.ingredientId(ingredient), 1, crudRecipe.recipeId(recipe)));

        // delete insertion
        crudRecipe.deleteOnlyOneIngredientFromRecipeIngredientTable(Ingredients.ingredientId(ingredient),
                crudRecipe.recipeId(recipe));
    }

    @Test
    void readAllRecipesCheckRecipesNames() {
        HashMap<String, CurrentStock> recipeIngredients = crudRecipe.readAllRecipes();
        ArrayList<String> existingRecipes = new ArrayList<>();
        ArrayList<String> existingRecipesCheck = new ArrayList<>();

        existingRecipesCheck.add("Guacamole");
        existingRecipesCheck.add("Cashewmilch");
        existingRecipesCheck.add("Ofenkartoffeln");

        for (String i : recipeIngredients.keySet()) {
            String recipe = i;
            existingRecipes.add(recipe);
        }
        assertTrue(existingRecipes.containsAll(existingRecipesCheck));

    }

    @Test
    void recipesFromUserListMatch() {
        ArrayList<String> checkRecipe = new ArrayList<>();

        checkRecipe.add("Bruschetta");
        checkRecipe.add("Griechischer Joghurt mit Honig und Nüssen");
        // checkRecipe.add("Omelett");
        checkRecipe.add("Ofenkartoffeln");
        checkRecipe.add("Caprese Salat");

        assertTrue(crudRecipe.recipesFromUser(user()).containsAll(checkRecipe));
    }

    @Test
    void updateGlobalRecipeSuccessfully() {
        String oldRecipeName = "Erdnussmusss";
        String newRecipeName = "Erdnussmuss";

        // create recipe
        crudRecipe.createRecipe(10, oldRecipeName, user().getId());

        // modify the name and number of portions. Return one if successfull
        assertEquals(1,
                crudRecipe.updateGlobalRecipe(newRecipeName, crudRecipe.recipeId(oldRecipeName), 2, user().getId()));

        // try to modify again the name and number of portions. Return zero if
        // unsuccessfull
        // recipe of oldRecipeName should not be found anymore
        assertEquals(0,
                crudRecipe.updateGlobalRecipe(newRecipeName, crudRecipe.recipeId(oldRecipeName), 2, user().getId()));

        // delete recipe
        crudRecipe.deleteRecipeGlobally(crudRecipe.recipeId(newRecipeName), user().getId());
    }

    @Test
    void updateRecipeIngredientTableSuccessfully() {
        String recipe = "Cashewmilch";
        String ingredient = "Cashewkerne";

        // update the quantity of ingredient. Return one if successfull
        assertEquals(1, crudRecipe.updateRecipeIngredientTable(Ingredients.ingredientId(ingredient), 120,
                crudRecipe.recipeId(recipe), user().getId()));

        // try to update the quantity of ingredient. Return null because the inserted
        // userId is not the user that added the recipe.
        assertEquals(0, crudRecipe.updateRecipeIngredientTable(Ingredients.ingredientId(ingredient), 120,
                crudRecipe.recipeId(recipe), 20));

        // restore to initial quantity
        crudRecipe.updateRecipeIngredientTable(Ingredients.ingredientId(ingredient), 100, crudRecipe.recipeId(recipe),
                user().getId());
    }

    @Test
    void deleteFromRecipeUserTableSuccessfully() {
        String recipe = "Cashewmilch";

        // add recipe to user
        crudRecipe.addRecipeToUser(crudRecipe.recipeId(recipe), user().getId());

        // delete recipe from user. Return 1 if successfully
        assertEquals(1, crudRecipe.deleteFromRecipeUserTable(crudRecipe.recipeId(recipe), user().getId()));

        // delete recipe from user again (Recipe now not in the recipe list of user).
        // Return 0 if successfully
        assertEquals(0, crudRecipe.deleteFromRecipeUserTable(crudRecipe.recipeId(recipe), user().getId()));
    }

    // This test contains already deleteRecipeFromAllUser() and
    // deleteFromRecipeIngredientTable()
    @Test
    void deleteRecipeGloballySuccessfully() {
        String recipe = "Erdnussmuss";
        String ingredient = "Erdnüsse";

        // create new recipe, add it to an user's list and add an ingredient to the
        // recipe
        crudRecipe.createRecipe(10, recipe, user().getId());
        crudRecipe.addRecipeToUser(crudRecipe.recipeId(recipe), user().getId());
        crudRecipe.addIngredientToRecipe(Ingredients.ingredientId(ingredient), 200, crudRecipe.recipeId(recipe));

        // return 1 if the recipe was deleted from everywhere successfully
        assertEquals(1, crudRecipe.deleteRecipeGlobally(crudRecipe.recipeId(recipe), user().getId()));

        // return 0 if the recipe couldn't be deleted
        assertEquals(0, crudRecipe.deleteRecipeGlobally(crudRecipe.recipeId(recipe), user().getId()));
    }

    @Test
    void recipesCreatedByUserMatch() {
        // create an ArrayList to compare the values
        ArrayList<Integer> recipeId = new ArrayList<>();
        recipeId.add(2);
        recipeId.add(30);

        // return true if the recipes were added by this user
        assertTrue(crudRecipe.recipesCreatedByUser(user()).containsAll(recipeId));

        // return false if the recipe wasn't added by this user
        assertFalse(crudRecipe.recipesCreatedByUser(user()).contains(9));
    }

    @Test
    void recipeIdMatch() {
        int recipeId = 10;

        // return true if the recipeId of the recipe match
        assertEquals(recipeId, crudRecipe.recipeId("Ofenkartoffeln"));

        // return false if the recipeId of the recipe doesn't match
        assertNotEquals(recipeId, crudRecipe.recipeId("Omelett"));
    }

    @Test
    void ingredientFoundInRecipeTrue() {
        int ingredient = 24;

        // return true if the ingredient was found in the recipe
        assertTrue(crudRecipe.ingredientFoundInRecipe(ingredient, 1));

        // return false if the ingredient wasn't found in the recipe
        assertFalse(crudRecipe.ingredientFoundInRecipe(ingredient, 10));
    }

    @Test
    void recipeExistsInUsersListTrue() {
        int bruschetta = 4;

        // return true if the recipe was found in the user list
        assertTrue(crudRecipe.recipeExistsInUsersList(bruschetta, user().getId()));

        // return false if the recipe wasn't found in the user list
        assertFalse(crudRecipe.recipeExistsInUsersList(bruschetta, 10));
    }

    @Test
    void existingGlobalRecipeTrue() {
        String recipe = "Bruschetta";
        String recipeNotFound = "Pizza";

        // return true if the recipe exist in the db
        assertTrue(crudRecipe.existingGlobalRecipe(crudRecipe.recipeId(recipe)));

        // return false if the recipe doesn't exist in the db
        assertFalse(crudRecipe.existingGlobalRecipe(crudRecipe.recipeId(recipeNotFound)));
    }
}
