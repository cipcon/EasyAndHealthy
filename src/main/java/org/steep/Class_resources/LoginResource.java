package org.steep.Class_resources;

import org.steep.Requests.User.CredentialsRequest;
import org.steep.User.Login;
import org.steep.User.RegisterStatusAndResponse.UserResponse;
import org.steep.User.RegisterStatusAndResponse.RegisterStatus;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
public class LoginResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(CredentialsRequest request) {
        Login loginClass = new Login();
        UserResponse loginResponse = loginClass.loginMethod(request.getUsername(), request.getPassword());

        if (loginResponse.getStatus() == RegisterStatus.SUCCESS) {
            return Response.status(Response.Status.OK).entity(loginResponse).build();
        } else if (loginResponse.getStatus() == RegisterStatus.EXPECTATION_FAILED) {
            return Response.status(Response.Status.BAD_REQUEST).entity(loginResponse).build();
        } else if (loginResponse.getStatus() == RegisterStatus.ERROR) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(loginResponse).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(loginResponse).build();
        }
    }
}