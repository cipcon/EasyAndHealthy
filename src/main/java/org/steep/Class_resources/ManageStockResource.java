package org.steep.Class_resources;

import jakarta.ws.rs.Produces;

import java.util.ArrayList;

import org.steep.Requests.RecipeIngredients.AddToUserRequest;
import org.steep.Requests.RecipeIngredients.IngredientRequest;
import org.steep.Stock.ManageStock;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/manageStock")
public class ManageStockResource {

    @POST
    @Path("/addIngredientToUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addIngredientToUserList(IngredientRequest request) {
        try {
            ManageStock manageStock = new ManageStock();
            AddToUserRequest addToUserRequest = manageStock.addIngredientToUserList(request.getIngredientId(),
                    request.getQuantity(), request.getUserId());
            return Response.status(Response.Status.OK)
                    .entity(addToUserRequest).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new AddToUserRequest(false, e.getMessage())).build();
        }
    }

    @POST
    @Path("/listUserIngredients")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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
}
