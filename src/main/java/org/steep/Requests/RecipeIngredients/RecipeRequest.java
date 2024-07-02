package org.steep.Requests.RecipeIngredients;

import java.util.List;

public class RecipeRequest {
    private String recipeName;
    private int recipeId;
    private List<IngredientRequest> ingredients;

    public RecipeRequest(String recipeName, int recipeId, List<IngredientRequest> ingredients) {
        this.recipeName = recipeName;
        this.recipeId = recipeId;
        this.ingredients = ingredients;
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<IngredientRequest> getIngredients() {
        return ingredients;
    }

    public void addIngredient(IngredientRequest ingredient) {
        this.ingredients.add(ingredient);
    }

    public int getRecipeId() {
        return this.recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
}
