package org.steep.Requests.ManageStock;

public class IngredientUserQuantityRequest {
    private int ingredientId;
    private int userId;
    private int quantity;

    IngredientUserQuantityRequest(int ingredientId, int userId, int quantity) {
        this.ingredientId = ingredientId;
        this.userId = userId;
        this.quantity = quantity;
    }

    public int getIngredientId() {
        return this.ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
