package org.steep.Class_resources;

import java.util.ArrayList;

import org.steep.User.Login;
import org.steep.User.UserAuthenticated;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{username}/{password}")
    public Response login(@PathParam("username") String username, @PathParam("password") String password) {
        try {
            Login loginClass = new Login();
            ArrayList<UserAuthenticated> authenticatedUsers = loginClass.loginMethod(username, password);
            // check if the Array has no entry and if the user is authenticated, receive true back
            if (!authenticatedUsers.isEmpty() && authenticatedUsers.get(0).getIsAuthenticated()) {
                return Response.status(Response.Status.OK)
                        .entity(authenticatedUsers)
                        .build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Invalid username or password.")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error during login: " + e.getMessage())
                    .build();
        }
    }
}
