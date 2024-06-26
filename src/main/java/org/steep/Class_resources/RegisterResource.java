package org.steep.Class_resources;

import org.steep.Requests.RegisterRequest;
import org.steep.User.Register;
import org.steep.User.Register.RegisterResponse;
import org.steep.User.Register.RegisterStatus;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/register")
public class RegisterResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegisterRequest request) {
        Register registerClass = new Register();
        RegisterResponse registerResponse = registerClass.registerMethod(request.getUsername(), request.getPassword());

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
