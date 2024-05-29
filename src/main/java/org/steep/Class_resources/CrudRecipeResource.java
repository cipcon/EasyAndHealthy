package org.steep.Class_resources;

import java.util.ArrayList;
import java.util.HashMap;

import org.steep.Recipe.CrudRecipe;
import org.steep.Requests.CrudRecipeRequest;
import org.steep.Stock.CurrentStock;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.codegen.annotations.GenIgnore;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/recipe")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CrudRecipeResource {

    @POST
    @Path("/createRecipe")
    public Response createRecipe(CrudRecipeRequest request) {
        try {
            CrudRecipe.createRecipe(request.getPortions(), request.getRecipeName(), request.getUserId());
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding recipe: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/addRecipeToUser")
    public Response addRecipeToUser(CrudRecipeRequest request) {
        try {
            CrudRecipe.addRecipeToUser(request.getRecipeId(), request.getUserId());
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding recipe: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/addIngredientToRecipe")
    public Response addIngredientToRecipe(CrudRecipeRequest request) {
        try {
            CrudRecipe.addIngredientToRecipe(request.getIngredientId(), request.getQuantity(), request.getRecipeId());
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding recipe: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/readAllRecipes")
    public Response readAllRecipes() {
        try {
            HashMap<String, CurrentStock> allRecipes = CrudRecipe.readAllRecipes();
            ObjectMapper mapper = new ObjectMapper(); // Create an ObjectMapper instance
            String json = mapper.writeValueAsString(allRecipes); // Convert list to JSON string
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving ingredients: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/recipeFromUser")
    public Response recipesFromUser(CrudRecipeRequest request) {
        try {
            ArrayList<String> recipes = CrudRecipe.recipesFromUser(request.getUserId());
            ObjectMapper mapper = new ObjectMapper(); // Create an ObjectMapper instance
            String json = mapper.writeValueAsString(recipes); // Convert list to JSON string
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving ingredients: " + e.getMessage()).build();
        }
    }

}
