package org.steep.Class_resources;

import org.steep.Ingredients.Ingredients;
import org.steep.Requests.IngredientRequest;

import jakarta.ws.rs.Consumes;
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
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Fehler beim Hinzufügen: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

}
