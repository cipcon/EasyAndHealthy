package org.steep.RecipeTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import org.steep.Recipe.CrudRecipe;
import org.steep.Recipe.SearchRecipe;
import org.steep.Requests.User.UserRequest;

public class SearchRecipeTest {
    SearchRecipe searchRecipe = new SearchRecipe();

    private UserRequest user() {
        UserRequest user = new UserRequest();
        user.setCurrentUsername("Ciprian");
        user.setId(15);
        return user;
    }

    @Test
    void recipeSearchMatchListsValue() {
        // create an ArrayList to compare with the Arraylist the function return
        ArrayList<String> recipesCheck = new ArrayList<>();

        recipesCheck.add("Gemüsesuppe");
        recipesCheck.add("Ofenkartoffeln");

        // return true if the Arrays match
        assertTrue(searchRecipe.recipeSearch("Kartoffeln").containsAll(recipesCheck));

        // return false if the recipe Omelett contains Kartoffeln neither in it's name
        // or ingredients
        assertFalse(searchRecipe.recipeSearch("Kartoffeln").contains("Omelett"));
    }

    @Test
    void cookingPlanCheckValues() {
        HashMap<String, Double> cookingPlanCheck = new HashMap<>();
        String recipe = "Ofenkartoffeln";

        cookingPlanCheck.put("Kartoffeln", 1000.0);
        cookingPlanCheck.put("Butter", 2.0);
        cookingPlanCheck.put("Salz", 2.0);
        cookingPlanCheck.put("Käse", 200.0);

        // return true if the name of the ingredients added in the list above match the
        // list returned from the function
        assertTrue(searchRecipe.cookingPlan(4, CrudRecipe.recipeId(recipe)).entrySet()
                .equals(cookingPlanCheck.entrySet()));

        // return false if the ingredient Karotten doesn't belong to recipe's
        // ingredients
        assertFalse(searchRecipe.cookingPlan(4, CrudRecipe.recipeId(recipe)).keySet().contains("Karotten"));
    }

    @Test
    void shoppingListCheckValues() {
        HashMap<String, Double> shoppingListCheck = new HashMap<>();
        String recipe = "Ofenkartoffeln";

        shoppingListCheck.put("Kartoffeln", 500.0);
        shoppingListCheck.put("Butter", 1.0);
        shoppingListCheck.put("Käse", 100.0);

        // return true if the ingredients in the list received from function match the
        // list i created
        assertTrue(searchRecipe.shoppingList(4, CrudRecipe.recipeId(recipe), user().getId()).entrySet()
                .equals(shoppingListCheck.entrySet()));

        // return false if the ingredient doesn't match
        assertFalse(searchRecipe.shoppingList(4, CrudRecipe.recipeId(recipe), user().getId()).keySet()
                .contains("Karotten"));
    }

    @Test
    void noIdeaModeRecipesMatch() {
        ArrayList<String> recipeCheck = new ArrayList<>();

        recipeCheck.add("Ofenkartoffeln");
        recipeCheck.add("Gemüsesuppe");
        recipeCheck.add("Griechischer Joghurt mit Honig und Nüssen");
        recipeCheck.add("Cashewmilch");

        // return true if the list of ingredients i created match with the list of
        // ingredient returned from the function
        assertTrue(searchRecipe.noIdeaMode(user().getId()).containsAll(recipeCheck));

        // return false if the ingredient Karotten isn't found in the list returned from
        // the function
        assertFalse(searchRecipe.noIdeaMode(user().getId()).contains("Karotten"));
    }
}
