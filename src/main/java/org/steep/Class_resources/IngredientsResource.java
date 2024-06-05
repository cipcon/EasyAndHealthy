package org.steep.Class_resources;

import java.util.ArrayList;
import java.util.HashMap;

import org.steep.Ingredients.Ingredients;
import org.steep.Ingredients.UnitEnum;
import org.steep.Requests.IngredientRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/ingredients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IngredientsResource {

    @POST
    @Path("/createIngredient")
    public Response createIngredient(IngredientRequest request) {
        try {
            Ingredients.createIngredient(request.getIngredient(), request.getUnit());
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding ingredient: " + e.getMessage()).build();
        }
    }

    /*
     * Use Response.status(Response.Status.CREATED).build(); when your endpoint
     * creates a new resource and doesn't need to return any data in the response
     * body (e.g., creating a new ingredient).
     * 
     * Use Response.ok(json).build(); when your endpoint retrieves or updates
     * existing resources and needs to return data to the client (e.g., retrieving a
     * list of ingredients).
     */

    @GET
    @Path("/ingredients")
    public Response getAllIngredients() {
        try {
            ArrayList<String> allIngredients = Ingredients.getAllIngredients();
            ObjectMapper mapper = new ObjectMapper(); // Create an ObjectMapper instance
            String json = mapper.writeValueAsString(allIngredients); // Convert list to JSON string
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving ingredients: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{name}")
    public Response searchIngredient(String name) {
        try {
            ArrayList<String> ingredientsFound = Ingredients.searchIngredient(name);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(ingredientsFound);
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving ingredients: " + e.getMessage()).build();
        }

    }

    @GET
    @Path("/unit")
    public Response getAllUnits() {
        try {
            ArrayList<String> allUnits = UnitEnum.allUnits();
            ObjectMapper mapper = new ObjectMapper(); // Create an ObjectMapper instance
            String json = mapper.writeValueAsString(allUnits); // Convert list to JSON string
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving ingredients: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/RecipeIngredients/{recipeId:\\d+}")
    public Response readRecipeIngredients(int recipeId) {
        try {
            HashMap<String, Double> ingredientsFound = Ingredients.readRecipeIngredients(recipeId);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(ingredientsFound);
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving ingredients: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/ingredientUnit/{ingredientId:\\d+}")
    public Response getIngredientUnit(int ingredientId) {
        try {
            String json = Ingredients.ingredientUnit(ingredientId);
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving ingredient unit: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/updateGlobalIngredient/{ingredientId:\\d+}/{newIngredientName}/{unit}")
    public Response updateGlobalIngredient(int ingredientId, String newIngredientName, String unit) {
        try {
            Ingredients.updateGlobalIngredient(ingredientId, newIngredientName, unit);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error editing ingredient: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/deleteIngredient/{ingredientId:\\d+}")
    public Response deleteGlobalIngredient(int ingredientId) {
        try {
            Ingredients.deleteGlobalIngredient(ingredientId);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting ingredient: " + e.getMessage()).build();
        }
    }
}
