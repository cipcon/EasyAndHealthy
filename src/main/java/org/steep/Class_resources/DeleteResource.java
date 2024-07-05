package org.steep.Class_resources;

import org.steep.User.DeleteAccount;

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
    public Response delete(DeleteUserRequest request) {
        System.out.println("Received delete request for user ID: " + request.getUserId());
        DeleteAccount deleteAccount = new DeleteAccount();
        try {
            DeleteAccount.DeleteRequest deleteResponse = deleteAccount.deleteUser(request.getUserId());
            System.out.println("Delete response: " + deleteResponse.isDeleted() + ", " + deleteResponse.getMessage());
            if (deleteResponse.isDeleted()) {
                return Response.status(Response.Status.OK).entity(deleteResponse).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(deleteResponse).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            DeleteAccount.DeleteRequest errorResponse = new DeleteAccount.DeleteRequest(false,
                    "Server error: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
        }
    }

    public static class DeleteUserRequest {
        private int userId;

        public DeleteUserRequest(int userId) {
            this.userId = userId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}