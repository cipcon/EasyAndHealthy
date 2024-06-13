package org.steep.Class_resources;

import java.util.ArrayList;

import org.steep.User.User;


import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResources {
    @GET
    @Path("/user/{userId}")
    public Response getUser(int userId) {
        try {
            User user = new User();
            ArrayList<String> userData = new ArrayList<>();
            userData.add(user.getId(), user.getCurrentUsername());
            ObjectMapper mapper = new ObjectMapper(); // Create an ObjectMapper instance
            String json = mapper.writeValueAsString(userData); // Convert list to JSON string
            return Response.ok(json).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving ingredients: " + e.getMessage()).build();
        }
    }
}
