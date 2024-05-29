package org.steep.Class_Resources_Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.steep.Class_resources.CrudRecipeResource;
import org.steep.Ingredients.Ingredients;
import org.steep.Recipe.CrudRecipe;
import org.steep.Requests.CrudRecipeRequest;
import org.steep.User.User;

import jakarta.inject.Inject;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class CrudRecipeResourceTest {

    Response response;

    final private int OK = Response.Status.OK.getStatusCode();
    final private int CREATED = Response.Status.CREATED.getStatusCode();

    private User user() {
        User user = new User();
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
        CrudRecipe.deleteRecipeGlobally(CrudRecipe.recipeId(recipe), user().getId());

    }

    @AfterEach
    void afterEach() {
        CrudRecipe.deleteRecipeGlobally(CrudRecipe.recipeId(recipe), user().getId());
    }

    @Inject
    CrudRecipeResource resource;

    @Test
    void testCreateIngredientSuccess() {
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
        response = resource.recipesFromUser(request());
        assertEquals(OK, response.getStatus());
    }
}
