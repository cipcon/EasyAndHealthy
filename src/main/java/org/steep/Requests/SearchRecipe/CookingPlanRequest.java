package org.steep.Requests.SearchRecipe;

public class CookingPlanRequest {
    private int portions;
    private int recipeId;

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

}
