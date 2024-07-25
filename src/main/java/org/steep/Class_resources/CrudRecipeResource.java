package org.steep.Class_resources;

import java.util.ArrayList;
import org.steep.Recipe.CrudRecipe;
import org.steep.Requests.CrudRecipeRequest;
import org.steep.Requests.RecipeIngredients.AddToUserRequest;
import org.steep.Requests.RecipeIngredients.CreateRecipeRequest;
import org.steep.Requests.RecipeIngredients.DeleteRecipeRequest;
import org.steep.Requests.RecipeIngredients.RecipeIngredientQuantityRequest;
import org.steep.Requests.RecipeIngredients.RecipeRequest;
import org.steep.Requests.RecipeIngredients.UpdateRecipeNameAndServingsRequest;
import org.steep.Requests.RecipeIngredients.deleteIngredientFromRecipeRequest;

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
    public Response createRecipe(CreateRecipeRequest request) {
        System.out.println(request);
        AddToUserRequest response = new AddToUserRequest(false, "");
        try {
            response = CrudRecipe.createRecipe(request);
            return Response.ok(response).build();
        } catch (Exception e) {
            response.setMessage(e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(response).build();
        }
    }

    @POST
    @Path("/addRecipeToUser")
    public Response addRecipeToUser(CrudRecipeRequest request) {
        try {
            AddToUserRequest addToUserRequest = CrudRecipe.addRecipeToUser(request.getRecipeId(),
                    request.getUserId());
            return Response.status(Response.Status.OK).entity(addToUserRequest).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new AddToUserRequest(false, e.getMessage())).build();
        }
    }

    @POST
    @Path("/addIngredientToRecipe")
    public Response addIngredientToRecipe(RecipeIngredientQuantityRequest request) {
        boolean recipeAdded = false;
        try {
            recipeAdded = CrudRecipe.addIngredientToRecipe(request.getIngredientId(), request.getQuantity(),
                    request.getRecipeId());
            return Response.ok(recipeAdded).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(recipeAdded).build();
        }
    }

    @GET
    @Path("/readAllRecipes")
    public Response readAllRecipes() {
        ArrayList<RecipeRequest> allRecipes = new ArrayList<>();
        try {
            allRecipes = CrudRecipe.readAllRecipes();
            return Response.ok(allRecipes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving ingredients: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/recipesFromUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response recipesFromUser(int userId) {
        try {
            ArrayList<RecipeRequest> recipes = CrudRecipe.recipesFromUser(userId);
            return Response.ok(recipes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving recipes: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/updateGlobalRecipe")
    public Response updateGlobalRecipe(UpdateRecipeNameAndServingsRequest request) {
        boolean recipeUpdated = false;
        try {
            recipeUpdated = CrudRecipe.updateGlobalRecipe(request.getNewRecipeName(), request.getRecipeId(),
                    request.getServings());
            return Response.ok(recipeUpdated).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(recipeUpdated).build();
        }
    }

    @POST
    @Path("/updateIngredientQuantity")
    public Response updateIngredientQuantity(RecipeIngredientQuantityRequest request) {
        boolean recipeUpdated = false;
        try {
            recipeUpdated = CrudRecipe.updateIngredientQuantity(request.getIngredientId(), request.getQuantity(),
                    request.getRecipeId());
            return Response.ok(recipeUpdated).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(recipeUpdated).build();
        }
    }

    @POST
    @Path("/deleteFromRecipeUserTable")
    @Consumes(MediaType.APPLICATION_JSON)
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
    @Path("/deleteIngredientFromRecipe")
    public Response deleteIngredientFromRecipe(deleteIngredientFromRecipeRequest request) {
        boolean ingredientDeleted = false;
        try {
            ingredientDeleted = CrudRecipe.deleteIngredientFromRecipe(request.getIngredientId(),
                    request.getRecipeId());
            return Response.ok(ingredientDeleted).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ingredientDeleted).build();
        }
    }

    @POST
    @Path("/deleteRecipeGlobally")
    public Response deleteRecipeGlobally(DeleteRecipeRequest request) {
        boolean deleted = CrudRecipe.deleteRecipeGlobally(request.getRecipeId(), request.getUserId());
        if (deleted) {
            return Response.ok(deleted).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(deleted).build();
        }
    }

    @POST
    @Path("/recipesCreatedByUser")
    public Response recipesCreatedByUser(int userId) {
        ArrayList<RecipeRequest> recipes = new ArrayList<>();
        try {
            recipes = CrudRecipe.recipesCreatedByUser(userId);
            return Response.ok(recipes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(recipes).build();
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

    public static void main(String[] args) {
        CrudRecipeResource resource = new CrudRecipeResource();
        DeleteRecipeRequest request = new DeleteRecipeRequest();
        request.setRecipeId(2127);
        request.setUserId(15);
        Response response = resource.deleteRecipeGlobally(request);
        System.out.println(response.getStatus());
    }
}
