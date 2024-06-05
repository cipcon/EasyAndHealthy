package org.steep.Requests;

public class CrudRecipeRequest {
    private String recipeName;
    private int recipeId;
    private int userId;
    private int portions;
    private int quantity;
    private int ingredientId;

    // Konstruktor

    public CrudRecipeRequest() {

    }

    // set methods

    public void setRecipeName(String recipe) {
        this.recipeName = recipe;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    // get methods

    public String getRecipeName() {
        return recipeName;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public int getUserId() {
        return userId;
    }

    public int getPortions() {
        return portions;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getIngredientId() {
        return ingredientId;
    }
}
