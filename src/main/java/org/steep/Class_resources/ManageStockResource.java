package org.steep.Class_resources;

import jakarta.ws.rs.Produces;

import java.util.ArrayList;

import org.steep.Requests.ManageStock.IngredientUserIdRequest;
import org.steep.Requests.ManageStock.IngredientUserQuantityRequest;
import org.steep.Requests.RecipeIngredients.AddToUserRequest;
import org.steep.Requests.RecipeIngredients.IngredientRequest;
import org.steep.Stock.ManageStock;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/manageStock")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ManageStockResource {

    @POST
    @Path("/addIngredientToUser")
    public Response addIngredientToUserList(IngredientUserQuantityRequest request) {
        int ingredientId = request.getIngredientId();
        int quantity = request.getQuantity();
        int userId = request.getUserId();
        try {
            ManageStock manageStock = new ManageStock();
            AddToUserRequest addToUserRequest = manageStock.addIngredientToUserList(ingredientId,
                    quantity, userId);
            return Response.status(Response.Status.OK)
                    .entity(addToUserRequest).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new AddToUserRequest(false, e.getMessage())).build();
        }
    }

    @POST
    @Path("/listUserIngredients")
    public Response readUserStock(int userId) {
        try {
            ManageStock manageStock = new ManageStock();
            ArrayList<IngredientRequest> ingredientRequest = manageStock.readUserStock(userId);
            return Response.status(Response.Status.OK).entity(ingredientRequest).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new IngredientRequest("", 0, 0, ""))
                    .build();
        }
    }

    @POST
    @Path("/removeIngredientFromUserList")
    public Response removeIngredientFromUserList(IngredientUserIdRequest request) {
        boolean ingredientRemoved = false;
        int ingredientId = request.getIngredientId();
        int userId = request.getUserId();
        try {
            ManageStock manageStock = new ManageStock();
            ingredientRemoved = manageStock.removeIngredientFromUserList(ingredientId,
                    userId);
            return Response.status(Response.Status.OK).entity(ingredientRemoved).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ingredientRemoved).build();
        }
    }

    @POST
    @Path("/updateUserStock")
    public Response updateUserStock(IngredientUserQuantityRequest request) {
        boolean ingredientUpdated = false;
        int ingredientId = request.getIngredientId();
        int quantity = request.getQuantity();
        int userId = request.getUserId();
        try {
            ManageStock manageStock = new ManageStock();
            ingredientUpdated = manageStock.updateUserStock(ingredientId, quantity, userId);
            return Response.status(Response.Status.OK).entity(ingredientUpdated).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ingredientUpdated).build();
        }
    }
}
