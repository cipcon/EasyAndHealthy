package org.steep.Requests.RecipeIngredients;

import java.util.List;

public class RecipeRequest {
    private String recipeName;
    private int recipeId;
    private int servings;
    private List<IngredientRequest> ingredients;

    public RecipeRequest(String recipeName, int recipeId, int servings, List<IngredientRequest> ingredients) {
        this.recipeName = recipeName;
        this.recipeId = recipeId;
        this.ingredients = ingredients;
        this.servings = servings;
    }

    public RecipeRequest(String recipeName, int recipeId, List<IngredientRequest> ingredients) {
        this.recipeName = recipeName;
        this.recipeId = recipeId;
        this.ingredients = ingredients;
    }

    public RecipeRequest(String recipeName, int recipeId) {
        this.recipeName = recipeName;
        this.recipeId = recipeId;
    }

    public RecipeRequest(String recipeName, int recipeId, int servings) {
        this.recipeName = recipeName;
        this.recipeId = recipeId;
        this.servings = servings;
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

    public int getServings() {
        return this.servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }
}
