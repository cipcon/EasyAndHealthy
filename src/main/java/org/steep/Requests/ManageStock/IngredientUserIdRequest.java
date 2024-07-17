package org.steep.Requests.ManageStock;

public class IngredientUserIdRequest {
    private int ingredientId;
    private int userId;

    public IngredientUserIdRequest(int ingredientId, int userId) {
        this.ingredientId = ingredientId;
        this.userId = userId;
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

}
