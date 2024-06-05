package org.steep.Class_resources;

import java.util.ArrayList;
import java.util.HashMap;

import org.steep.Recipe.CrudRecipe;
import org.steep.Requests.CrudRecipeRequest;
import org.steep.Stock.CurrentStock;

import com.fasterxml.jackson.databind.ObjectMapper;

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
            return Response.status(Response.Status.CREATED).entity("Recipe successfully created").build();
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
    @Path("/recipesFromUser/{userId}")
    public Response recipesFromUser(int userId) {
        try {
            ArrayList<String> recipes = CrudRecipe.recipesFromUser(userId);
            ObjectMapper mapper = new ObjectMapper(); // Create an ObjectMapper instance
            String json = mapper.writeValueAsString(recipes); // Convert list to JSON string
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving ingredients: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/updateGlobalRecipe/{recipeNewName}")
    public Response updateGlobalRecipe(String recipeNewName, CrudRecipeRequest request) {
        try {
            CrudRecipe.updateGlobalRecipe(recipeNewName, request.getRecipeId(), request.getPortions(),
                    request.getUserId());
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding recipe: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/updateIngredientQuantity/")
    public Response updateIngredientQuantity(CrudRecipeRequest request) {
        try {
            CrudRecipe.updateIngredientQuantity(request.getIngredientId(), request.getQuantity(), request.getRecipeId(),
                    request.getUserId());
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding recipe: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/deleteFromRecipeUserTable")
    public Response deleteFromRecipeUserTable(CrudRecipeRequest request) {
        try {
            CrudRecipe.deleteFromRecipeUserTable(request.getRecipeId(), request.getUserId());
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding recipe: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/deleteRecipeFromAllUsersLists/{recipeId}")
    public Response deleteRecipeFromAllUsersLists(int recipeId) {
        try {
            CrudRecipe.deleteRecipeFromAllUsersLists(recipeId);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding recipe: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/deleteFromRecipeIngredientTable/{recipeId}")
    public Response deleteFromRecipeIngredientTable(int recipeId) {
        try {
            CrudRecipe.deleteFromRecipeIngredientTable(recipeId);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding recipe: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/deleteFromRecipeIngredientTable")
    public Response deleteOnlyOneIngredientFromRecipeIngredientTable(CrudRecipeRequest request) {
        try {
            CrudRecipe.deleteOnlyOneIngredientFromRecipeIngredientTable(request.getIngredientId(), request.getRecipeId());
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding recipe: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/deleteRecipeGlobally")
    public Response deleteRecipeGlobally(CrudRecipeRequest request) {
        try {
            CrudRecipe.deleteRecipeGlobally(request.getRecipeId(), request.getUserId());
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding recipe: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/recipesCreatedByUser/{userId}")
    public Response recipesCreatedByUser(int userId) {
        try {
            ArrayList<Integer> recipes = CrudRecipe.recipesCreatedByUser(userId);
            ObjectMapper mapper = new ObjectMapper(); // Create an ObjectMapper instance
            String json = mapper.writeValueAsString(recipes); // Convert list to JSON string
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving ingredients: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/recipeName/{recipeId}")
    public Response recipeName(int recipeId) {
        try {
            String recipe = CrudRecipe.recipeName(recipeId);
            return Response.ok(recipe).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving ingredients: " + e.getMessage()).build();
        }
    }
}
