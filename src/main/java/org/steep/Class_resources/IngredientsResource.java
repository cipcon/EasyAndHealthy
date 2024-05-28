package org.steep.Class_resources;

import java.util.ArrayList;

import org.steep.Ingredients.Ingredients;
import org.steep.Requests.IngredientRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
                    .entity("Fehler beim Hinzufügen: " + e.getMessage()).build();
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
    public Response searchIngredient(@PathParam("name") String name) {
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
}
