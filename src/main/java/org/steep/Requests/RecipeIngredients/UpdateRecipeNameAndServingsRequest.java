package org.steep.Requests.RecipeIngredients;

public class UpdateRecipeNameAndServingsRequest {
    private String newRecipeName;
    private int recipeId;
    private int servings;

    public String getNewRecipeName() {
        return this.newRecipeName;
    }

    public void setNewRecipeName(String newRecipeName) {
        this.newRecipeName = newRecipeName;
    }

    public int getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getServings() {
        return this.servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

}
