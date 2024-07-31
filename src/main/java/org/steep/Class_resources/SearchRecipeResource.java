package org.steep.Class_resources;

import java.util.ArrayList;

import org.steep.Recipe.SearchRecipe;
import org.steep.Requests.RecipeIngredients.IngredientRequest;
import org.steep.Requests.RecipeIngredients.RecipeRequest;
import org.steep.Requests.SearchRecipe.CookingPlanRequest;
import org.steep.Requests.SearchRecipe.ShoppingListRequest;
import org.steep.Requests.SearchRecipe.PrepareRecipeRequest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/searchRecipe")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchRecipeResource {

    @POST
    @Path("/recipeSearch")
    public Response recipeSearch(String request) {
        ArrayList<RecipeRequest> recipeRequest = new ArrayList<>();
        SearchRecipe searchRecipe = new SearchRecipe();
        try {
            recipeRequest = searchRecipe.recipeSearch(request);
            return Response.ok(recipeRequest).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(recipeRequest).build();
        }
    }

    @POST
    @Path("/noIdeaMode")
    public Response noIdeaMode(int userId) {
        ArrayList<RecipeRequest> request = new ArrayList<>();
        SearchRecipe searchRecipe = new SearchRecipe();
        try {
            request = searchRecipe.noIdeaMode(userId);
            return Response.ok(request).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(request).build();
        }
    }

    @POST
    @Path("/cookingPlan")
    public Response cookingPlan(CookingPlanRequest requestLoad) {
        SearchRecipe searchRecipe = new SearchRecipe();
        ArrayList<IngredientRequest> newQuantities = new ArrayList<>();
        try {
            newQuantities = searchRecipe.cookingPlan(requestLoad.getPortions(),
                    requestLoad.getRecipeId());
            return Response.ok(newQuantities).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(newQuantities).build();
        }
    }

    @POST
    @Path("/shoppingList")
    public Response shoppingList(ShoppingListRequest request) {
        SearchRecipe searchRecipe = new SearchRecipe();
        ArrayList<IngredientRequest> list = new ArrayList<>();
        try {
            list = searchRecipe.shoppingList(request.getPortions(), request.getRecipeId(), request.getUserId());
            return Response.ok(list).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(list).build();
        }
    }

    @POST
    @Path("/prepareRecipe")
    public Response prepareRecipe(ShoppingListRequest request) {
        SearchRecipe searchRecipe = new SearchRecipe();
        PrepareRecipeRequest response = new PrepareRecipeRequest(false, null);
        try {
            response = searchRecipe.prepareRecipe(request.getPortions(), request.getRecipeId(), request.getUserId());
            return Response.ok(response).build();
        } catch (Exception e) {
            response.setMessage("Something went wrong, please try again later");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }

}
