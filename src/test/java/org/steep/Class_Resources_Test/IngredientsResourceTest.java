package org.steep.Class_Resources_Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.steep.Class_resources.IngredientsResource;
import org.steep.Ingredients.Ingredients;
import org.steep.Ingredients.UnitEnum;
import org.steep.Requests.IngredientRequest;

import jakarta.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class IngredientsResourceTest {

    @Inject
    private IngredientsResource resource;

    @Test
    void testCreateIngredientSuccess() {
        String ingredient = "Gin";
        String unit = UnitEnum.ML.toString();
        int ingredientId = 0;

        IngredientRequest request = new IngredientRequest();
        request.setIngredient(ingredient);
        request.setUnit(unit);
        try {
            Response response = resource.createIngredient(request);
            ingredientId = Ingredients.ingredientId(ingredient);
            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
                Ingredients.deleteGlobalIngredient(ingredientId);
            } 
        } catch (Exception e) {
            fail("Unexpected exception during test: " + e.getMessage());
        }
    }

    @Test
    void testCreateIngredientError() throws Exception {
        String ingredient = "Butter";
        String unit = UnitEnum.EL.toString();
        IngredientRequest request = new IngredientRequest(ingredient, unit);

        IngredientsResource resource = new IngredientsResource();
        Response response = resource.createIngredient(request);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("Fehler beim Hinzufügen"));
    }
}
