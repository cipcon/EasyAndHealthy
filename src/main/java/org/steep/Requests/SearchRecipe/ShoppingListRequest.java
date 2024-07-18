package org.steep.Requests.SearchRecipe;

public class ShoppingListRequest {
    private int portions;
    private int recipeId;
    private int userId;

    public int getPortions() {
        return this.portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public int getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
