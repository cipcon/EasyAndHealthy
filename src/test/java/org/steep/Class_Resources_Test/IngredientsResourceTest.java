package org.steep.Class_Resources_Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.steep.Class_resources.IngredientsResource;
import org.steep.Ingredients.UnitEnum;
import org.steep.Requests.IngredientRequest;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class IngredientsResourceTest {
    
    @Test
    void testCreateIngredientSuccess() {
        String ingredient = "Gin";
        String unit = UnitEnum.ML.toString();

        IngredientRequest request = new IngredientRequest(ingredient, unit);

        IngredientsResource resource = new IngredientsResource();
        Response response = resource.createIngredient(request);

        assertEquals(Response.Status.ACCEPTED.getStatusCode(), response.getStatus());
    }

    @Test
    void testCreateIngredientError() throws Exception {
        String ingredient = "Butter";
        String unit = "EL";
        IngredientRequest request = new IngredientRequest(ingredient, unit);

        IngredientsResource resource = new IngredientsResource();
        Response response = resource.createIngredient(request);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertTrue(response.getEntity().toString().contains("Fehler beim Hinzufügen"));
    }
}
