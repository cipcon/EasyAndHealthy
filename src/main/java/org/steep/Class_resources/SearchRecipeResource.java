package org.steep.Class_resources;

import java.util.ArrayList;

import org.steep.Recipe.SearchRecipe;
import org.steep.Requests.RecipeIngredients.RecipeRequest;

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
    @Path("/noIdeaMode")
    public Response noIdeaMode(int userId) {
        try {
            SearchRecipe searchRecipe = new SearchRecipe();
            ArrayList<RecipeRequest> request = searchRecipe.noIdeaMode(userId);
            return Response.status(Response.Status.OK).entity(request).build();
        } catch (Exception e) {
            ArrayList<RecipeRequest> request = new ArrayList<>();
            return Response.status(Response.Status.OK).entity(request).build();
        }
    }
}
