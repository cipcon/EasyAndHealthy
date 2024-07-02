package org.steep.RecipeTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import org.steep.Ingredients.Ingredients;
import org.steep.Recipe.CrudRecipe;
import org.steep.Requests.RecipeIngredients.RecipeRequest;
import org.steep.Requests.User.UserRequest;

public class CrudRecipeTest {
    private UserRequest user() {
        UserRequest user = new UserRequest();
        user.setCurrentUsername("Ciprian");
        user.setId(15);
        return user;
    }

    @Test
    void createRecipeReturnOne() {
        String recipe = "Erdnussmuss";
        assertEquals(1, CrudRecipe.createRecipe(10, recipe, user().getId()));

        assertNotEquals(1, CrudRecipe.createRecipe(10, recipe, user().getId()));

        CrudRecipe.deleteRecipeGlobally(CrudRecipe.recipeId(recipe), user().getId());
    }

    // Tests run at the same time? That's why I can't run the whole test at once
    @Test
    void addRecipeToUserReturnOne() {
        // return one if the ingredient was successfully added to user's list
        String recipe = "Omelett";
        assertEquals(1, CrudRecipe.addRecipeToUser(CrudRecipe.recipeId(recipe), user().getId()));

        // return zero if the ingredient wasn't added to user's list (because already
        // exist)
        assertEquals(0, CrudRecipe.addRecipeToUser(CrudRecipe.recipeId(recipe), user().getId()));

        // delete the insertion
        CrudRecipe.deleteFromRecipeUserTable(CrudRecipe.recipeId(recipe), user().getId());

    }

    @Test
    void addIngredientToCreatedRecipeReturnOne() {
        String ingredient = "Salz";
        String recipe = "Guacamole";

        // return one if ingredient added succcessfully to the recipe's Ingredients list
        assertEquals(1,
                CrudRecipe.addIngredientToRecipe(Ingredients.ingredientId(ingredient), 1, CrudRecipe.recipeId(recipe)));

        // return zero if ingredient wasn't added to the recipe's Ingredients list
        assertEquals(0,
                CrudRecipe.addIngredientToRecipe(Ingredients.ingredientId(ingredient), 1, CrudRecipe.recipeId(recipe)));

        // delete insertion
        CrudRecipe.deleteOnlyOneIngredientFromRecipeIngredientTable(Ingredients.ingredientId(ingredient),
                CrudRecipe.recipeId(recipe));
    }

    @Test
    void readAllRecipesCheckRecipesNames() {
        ArrayList<RecipeRequest> recipeIngredients = CrudRecipe.readAllRecipes();
        ArrayList<String> existingRecipes = new ArrayList<>();
        ArrayList<String> existingRecipesCheck = new ArrayList<>();

        existingRecipesCheck.add("Guacamole");
        existingRecipesCheck.add("Cashewmilch");
        existingRecipesCheck.add("Ofenkartoffeln");

        for (RecipeRequest i : recipeIngredients) {
            existingRecipes.add(i.getRecipeName());
        }
        assertTrue(existingRecipes.containsAll(existingRecipesCheck));

    }

    @Test
    void recipesFromUserListMatch() {
        ArrayList<RecipeRequest> checkRecipe = new ArrayList<>();

        RecipeRequest recipe = new RecipeRequest("Bruschetta", CrudRecipe.recipeId("Bruschetta"));
        checkRecipe.add(recipe);

        recipe = new RecipeRequest("Griechischer Joghurt mit Honig und Nüssen",
                CrudRecipe.recipeId("Griechischer Joghurt mit Honig und Nüssen"));
        checkRecipe.add(recipe);

        recipe = new RecipeRequest("Ofenkartoffeln", CrudRecipe.recipeId("Ofenkartoffeln"));
        checkRecipe.add(recipe);

        recipe = new RecipeRequest("Caprese Salat", CrudRecipe.recipeId("Caprese Salat"));
        checkRecipe.add(recipe);

        assertTrue(CrudRecipe.recipesFromUser(user().getId()).containsAll(checkRecipe));
    }

    @Test
    void updateGlobalRecipeSuccessfully() {
        String oldRecipeName = "Erdnussmusss";
        String newRecipeName = "Erdnussmuss";

        // create recipe
        CrudRecipe.createRecipe(10, oldRecipeName, user().getId());

        // modify the name and number of portions. Return one if successfull
        assertEquals(1,
                CrudRecipe.updateGlobalRecipe(newRecipeName, CrudRecipe.recipeId(oldRecipeName), 2, user().getId()));

        // try to modify again the name and number of portions. Return zero if
        // unsuccessfull
        // recipe of oldRecipeName should not be found anymore
        assertEquals(0,
                CrudRecipe.updateGlobalRecipe(newRecipeName, CrudRecipe.recipeId(oldRecipeName), 2, user().getId()));

        // delete recipe
        CrudRecipe.deleteRecipeGlobally(CrudRecipe.recipeId(newRecipeName), user().getId());
    }

    @Test
    void updateRecipeIngredientTableSuccessfully() {
        String recipe = "Cashewmilch";
        String ingredient = "Cashewkerne";

        // update the quantity of ingredient. Return one if successfull
        assertEquals(1, CrudRecipe.updateIngredientQuantity(Ingredients.ingredientId(ingredient), 120,
                CrudRecipe.recipeId(recipe), user().getId()));

        // try to update the quantity of ingredient. Return null because the inserted
        // userId is not the user that added the recipe.
        assertEquals(0, CrudRecipe.updateIngredientQuantity(Ingredients.ingredientId(ingredient), 120,
                CrudRecipe.recipeId(recipe), 20));

        // restore to initial quantity
        CrudRecipe.updateIngredientQuantity(Ingredients.ingredientId(ingredient), 100, CrudRecipe.recipeId(recipe),
                user().getId());
    }

    @Test
    void deleteFromRecipeUserTableSuccessfully() {
        String recipe = "Cashewmilch";

        // add recipe to user
        CrudRecipe.addRecipeToUser(CrudRecipe.recipeId(recipe), user().getId());

        // delete recipe from user. Return 1 if successfully
        assertEquals(1, CrudRecipe.deleteFromRecipeUserTable(CrudRecipe.recipeId(recipe), user().getId()));

        // delete recipe from user again (Recipe now not in the recipe list of user).
        // Return 0 if successfully
        assertEquals(0, CrudRecipe.deleteFromRecipeUserTable(CrudRecipe.recipeId(recipe), user().getId()));
    }

    // This test contains already deleteRecipeFromAllUser() and
    // deleteFromRecipeIngredientTable()
    @Test
    void deleteRecipeGloballySuccessfully() {
        String recipe = "Erdnussmuss";
        String ingredient = "Erdnüsse";

        // create new recipe, add it to an user's list and add an ingredient to the
        // recipe
        CrudRecipe.createRecipe(10, recipe, user().getId());
        CrudRecipe.addRecipeToUser(CrudRecipe.recipeId(recipe), user().getId());
        CrudRecipe.addIngredientToRecipe(Ingredients.ingredientId(ingredient), 200, CrudRecipe.recipeId(recipe));

        // return 1 if the recipe was deleted from everywhere successfully
        assertEquals(1, CrudRecipe.deleteRecipeGlobally(CrudRecipe.recipeId(recipe), user().getId()));

        // return 0 if the recipe couldn't be deleted
        assertEquals(0, CrudRecipe.deleteRecipeGlobally(CrudRecipe.recipeId(recipe), user().getId()));
    }

    @Test
    void recipesCreatedByUserMatch() {
        // create an ArrayList to compare the values
        ArrayList<Integer> recipeId = new ArrayList<>();
        recipeId.add(2);
        recipeId.add(30);

        // return true if the recipes were added by this user
        assertTrue(CrudRecipe.recipesCreatedByUser(user().getId()).containsAll(recipeId));

        // return false if the recipe wasn't added by this user
        assertFalse(CrudRecipe.recipesCreatedByUser(user().getId()).contains(9));
    }

    @Test
    void recipeIdMatch() {
        int recipeId = 10;

        // return true if the recipeId of the recipe match
        assertEquals(recipeId, CrudRecipe.recipeId("Ofenkartoffeln"));

        // return false if the recipeId of the recipe doesn't match
        assertNotEquals(recipeId, CrudRecipe.recipeId("Omelett"));
    }

    @Test
    void ingredientFoundInRecipeTrue() {
        int ingredient = 24;

        // return true if the ingredient was found in the recipe
        assertTrue(CrudRecipe.ingredientFoundInRecipe(ingredient, 1));

        // return false if the ingredient wasn't found in the recipe
        assertFalse(CrudRecipe.ingredientFoundInRecipe(ingredient, 10));
    }

    @Test
    void recipeExistsInUsersListTrue() {
        int bruschetta = 4;

        // return true if the recipe was found in the user list
        assertTrue(CrudRecipe.recipeExistsInUsersList(bruschetta, user().getId()));

        // return false if the recipe wasn't found in the user list
        assertFalse(CrudRecipe.recipeExistsInUsersList(bruschetta, 10));
    }

    @Test
    void existingGlobalRecipeTrue() {
        String recipe = "Bruschetta";
        String recipeNotFound = "Pizza";

        // return true if the recipe exist in the db
        assertTrue(CrudRecipe.existingGlobalRecipe(CrudRecipe.recipeId(recipe)));

        // return false if the recipe doesn't exist in the db
        assertFalse(CrudRecipe.existingGlobalRecipe(CrudRecipe.recipeId(recipeNotFound)));
    }
}
