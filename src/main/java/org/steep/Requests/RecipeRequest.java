package org.steep.Requests;

public class RecipeRequest {
    private String recipeName;
    private int recipeId;

    public RecipeRequest(String recipeName, int recipeId) {
        this.recipeName = recipeName;
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
