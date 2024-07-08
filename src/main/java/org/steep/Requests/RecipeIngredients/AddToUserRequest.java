package org.steep.Requests.RecipeIngredients;

public class AddToUserRequest {
    private boolean recipeAdded;
    private String message;

    public AddToUserRequest(boolean recipeAdded, String message) {
        this.recipeAdded = recipeAdded;
        this.message = message;
    }

    public boolean isRecipeAdded() {
        return this.recipeAdded;
    }

    public void setRecipeAdded(boolean recipeAdded) {
        this.recipeAdded = recipeAdded;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
