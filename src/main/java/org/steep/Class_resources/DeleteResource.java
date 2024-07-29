package org.steep.Class_resources;

import org.steep.User.DeleteAccount;
import org.steep.User.DeleteAccount.DeleteResponse;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/delete")
public class DeleteResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(int userId) {
        DeleteAccount deleteAccount = new DeleteAccount();
        try {
            DeleteResponse deleteResponse = deleteAccount.deleteUser(userId);
            if (deleteResponse.isDeleted()) {
                return Response.ok(deleteResponse).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(deleteResponse).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            DeleteResponse errorResponse = new DeleteAccount.DeleteResponse(false,
                    "Server error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }

}