package org.steep.Requests.RecipeIngredients;

public class DeleteRecipeRequest {
    private int recipeId;
    private int userId;

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
