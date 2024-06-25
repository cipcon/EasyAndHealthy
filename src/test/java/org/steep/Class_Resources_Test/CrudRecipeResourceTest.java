package org.steep.Class_Resources_Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.steep.Class_resources.CrudRecipeResource;
import org.steep.Ingredients.Ingredients;
import org.steep.Recipe.CrudRecipe;
import org.steep.Requests.CrudRecipeRequest;
import org.steep.Requests.UserRequest;

import jakarta.inject.Inject;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class CrudRecipeResourceTest {

    Response response;

    final private int OK = Response.Status.OK.getStatusCode();
    final private int CREATED = Response.Status.CREATED.getStatusCode();

    private UserRequest user() {
        UserRequest user = new UserRequest();
        user.setCurrentUsername("Ciprian");
        user.setId(15);
        return user;
    }

    final private String recipe = "Cashewmus";

    private CrudRecipeRequest request() {
        CrudRecipeRequest request = new CrudRecipeRequest();

        request.setPortions(10);
        request.setQuantity(200);
        request.setRecipeName(recipe);
        request.setRecipeId(CrudRecipe.recipeId(recipe));
        request.setUserId(user().getId());
        request.setIngredientId(Ingredients.ingredientId("Cashewkerne"));
        return request;
    }

    @BeforeEach
    void beforeEach() {
        CrudRecipe.createRecipe(request().getPortions(), request().getRecipeName(), request().getUserId());
    }

    @AfterEach
    void afterEach() {
        CrudRecipe.deleteRecipeGlobally(request().getRecipeId(), user().getId());
    }

    @Inject
    CrudRecipeResource resource;

    @Test
    void testCreateIngredientSuccess() {
        CrudRecipe.deleteRecipeGlobally(request().getRecipeId(), user().getId());

        response = resource.createRecipe(request());
        assertEquals(CREATED, response.getStatus());
    }

    @Test
    void addRecipeToUserSuccess() {
        response = resource.addRecipeToUser(request());
        assertEquals(OK, response.getStatus());
    }

    @Test
    void addIngredientToRecipeSuccess() {
        response = resource.addIngredientToRecipe(request());
        assertEquals(OK, response.getStatus());
    }

    @Test
    void readAllRecipesSuccess() {
        response = resource.readAllRecipes();
        assertEquals(OK, response.getStatus());
    }

    @Test
    void recipesFromUserSuccess() {
        response = resource.recipesFromUser(request().getUserId());
        assertEquals(OK, response.getStatus());
        System.out.println(resource.readAllRecipes());
    }

    @Test
    void updateGlobalRecipeSuccess() {
        String recipeNewName = "Cashewmuss";

        response = resource.updateGlobalRecipe(recipeNewName, request());
        assertEquals(OK, response.getStatus());

        CrudRecipe.deleteRecipeGlobally(CrudRecipe.recipeId(recipeNewName), user().getId());
    }

    @Test
    void updateIngredientQuantitySuccess() {
        response = resource.updateIngredientQuantity(request());
        assertEquals(OK, response.getStatus());
    }

    @Test
    void deleteFromRecipeUserTableSuccess() {
        // add recipe to user
        CrudRecipe.addRecipeToUser(request().getRecipeId(), request().getUserId());

        response = resource.deleteFromRecipeUserTable(request());
        assertEquals(OK, response.getStatus());
    }

    @Test
    void deleteRecipeFromAllUsersListsSuccess() {
        // add recipe to users
        CrudRecipe.addRecipeToUser(request().getRecipeId(), request().getUserId());
        CrudRecipe.addRecipeToUser(request().getRecipeId(), 6714);
        CrudRecipe.addRecipeToUser(request().getRecipeId(), 6731);

        response = resource.deleteRecipeFromAllUsersLists(request().getRecipeId());
        assertEquals(OK, response.getStatus());
    }

    @Test
    void deleteFromRecipeIngredientTableSuccess() {
        // add ingredient to recipe
        CrudRecipe.addIngredientToRecipe(request().getIngredientId(), request().getQuantity(), request().getRecipeId());

        response = resource.deleteFromRecipeIngredientTable(request().getRecipeId());
        assertEquals(OK, response.getStatus());
    }

    @Test
    void deleteOnlyOneIngredientFromRecipeIngredientTableSuccess() {
        // add ingredient to recipe
        CrudRecipe.addIngredientToRecipe(request().getIngredientId(), request().getQuantity(), request().getRecipeId());

        response = resource.deleteOnlyOneIngredientFromRecipeIngredientTable(request());
        assertEquals(OK, response.getStatus());
    }

    @Test
    void deleteRecipeGloballySuccess() {
        response = resource.deleteRecipeGlobally(request());
        assertEquals(OK, response.getStatus());
    }

    @Test
    void recipesCreatedByUserSuccess() {
        response = resource.recipesCreatedByUser(request().getUserId());
        assertEquals(OK, response.getStatus());
    }

    @Test
    void recipeNameSuccess() {
        response = resource.recipeName(request().getRecipeId());
        assertEquals(OK, response.getStatus());
    }
}
