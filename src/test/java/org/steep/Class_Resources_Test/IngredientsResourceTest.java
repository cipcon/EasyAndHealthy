package org.steep.Class_Resources_Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.steep.Class_resources.IngredientsResource;
import org.steep.Ingredients.Ingredients;
import org.steep.Ingredients.UnitEnum;
import org.steep.Requests.RecipeIngredients.IngredientRequest;

import jakarta.inject.Inject;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class IngredientsResourceTest {

    Response response;

    final private int OK = Response.Status.OK.getStatusCode();
    final private int CREATED = Response.Status.CREATED.getStatusCode();

    String ananas = "Ananas";
    int ananasId = Ingredients.ingredientId(ananas);
    String ananasUnit = UnitEnum.STÜCK.toString();

    @BeforeEach
    void beforeEach() {
        Ingredients.createIngredient(ananas, ananasUnit);
    }

    @AfterEach
    void afterEach() {
        Ingredients.deleteGlobalIngredient(ananasId);
    }

    @Inject
    private IngredientsResource resource;

    @Test
    void testCreateIngredientSuccess() {
        IngredientRequest request = new IngredientRequest(ananas, ananasUnit);

        Response response = resource.createIngredient(request);
        assertEquals(CREATED, response.getStatus());

    }

    @Test
    void testGetAllIngredientsSuccess() {
        Response response = resource.getAllIngredients();
        assertEquals(OK, response.getStatus());
    }

    @Test
    void testSearchIngredientSuccess() {
        String ingredient = "kar";

        Response response = resource.searchIngredient(ingredient);
        // check if OK
        assertEquals(OK, response.getStatus());
    }

    @Test
    void getAllUnitsSuccess() {
        Response response = resource.getAllUnits();

        assertEquals(OK, response.getStatus());
    }

    @Test
    void readRecipeIngredientsSuccess() {
        int recipeId = 1; // Caprese Salat

        Response response = resource.readRecipeIngredients(recipeId);
        assertEquals(OK, response.getStatus());
    }

    @Test
    void getIngredientUnitSuccess() {
        Response response = resource.getIngredientUnit(ananasId);

        assertEquals(OK, response.getStatus());
    }

    @Test
    void updateGlobalIngredientSuccess() {
        String newName = "Pineapple";
        Response response = resource.updateGlobalIngredient(ananasId, newName, ananasUnit);

        assertEquals(OK, response.getStatus());
    }

    @Test
    void deleteGlobalIngredientSuccess() {
        Response response = resource.deleteGlobalIngredient(ananasId);

        assertEquals(OK, response.getStatus());
    }
}
