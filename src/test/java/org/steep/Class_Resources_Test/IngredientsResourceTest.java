package org.steep.Class_Resources_Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        int ingredientId = Ingredients.ingredientId(ingredient);

        // Clean up if the ingredient already exists
        if (ingredientId != 0) {
            Ingredients.deleteGlobalIngredient(ingredientId);
        }

        // Verify that the ingredient does not exist before the test
        assertEquals(0, ingredientId);

        IngredientRequest request = new IngredientRequest(ingredient, unit);

        try {
            Response response = resource.createIngredient(request);
            assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
            if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {

                ingredientId = Ingredients.ingredientId(ingredient);
                Ingredients.deleteGlobalIngredient(ingredientId);
            }
        } catch (Exception e) {
            fail("Unexpected exception during test: " + e.getMessage());
        }
    }

    @Test
    void testGetAllIngredients() {
        assertEquals(Response.Status.OK.getStatusCode(), resource.getAllIngredients().getStatus());
    }

    @Test
    void testSearchIngredient() {
        String ingredient = "kar";

        // check if OK
        assertEquals(Response.Status.OK.getStatusCode(), resource.searchIngredient(ingredient).getStatus());
    }


}
