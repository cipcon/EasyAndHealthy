package org.steep.Requests.RecipeIngredients;

public class deleteIngredientFromRecipeRequest {
    private int ingredientId;
    private int recipeId;

    public int getIngredientId() {
        return this.ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(int userId) {
        this.recipeId = userId;
    }

}
