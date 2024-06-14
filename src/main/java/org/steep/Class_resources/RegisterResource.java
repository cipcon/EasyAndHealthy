package org.steep.Class_resources;


import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/register")

public class RegisterResource {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{userId}/{password}")
    public Response login(@PathParam("userId") String userId, @PathParam("userId") String password) {
        try {
            return Response.status(Response.Status.CREATED).entity("Recipe successfully created").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding recipe: " + e.getMessage()).build();
        }
    }
}
