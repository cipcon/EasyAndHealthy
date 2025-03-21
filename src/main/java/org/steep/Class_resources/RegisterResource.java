package org.steep.Class_resources;

import org.steep.Requests.CredentialsRequest;
import org.steep.User.Register;
import org.steep.User.RegisterStatusAndResponse.RegisterStatus;
import org.steep.User.RegisterStatusAndResponse.UserResponse;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/register")
public class RegisterResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(CredentialsRequest request) {
        Register registerClass = new Register();
        UserResponse registerResponse = registerClass.registerMethod(request.getUsername(), request.getPassword());

        if (registerResponse.getStatus() == RegisterStatus.SUCCESS) {
            return Response.status(Response.Status.CREATED).entity(registerResponse).build();
        } else if (registerResponse.getStatus() == RegisterStatus.USERNAME_EXISTS) {
            return Response.status(Response.Status.CONFLICT).entity(registerResponse).build();
        } else if (registerResponse.getStatus() == RegisterStatus.EXPECTATION_FAILED) {
            return Response.status(Response.Status.EXPECTATION_FAILED).entity(registerResponse).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(registerResponse).build();
        }
    }
}
