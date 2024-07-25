package org.steep.Requests.RecipeIngredients;

import java.util.ArrayList;

public class CreateRecipeRequest {
    public static class InnerCreateRecipeRequest {
        private int ingredientId;
        private int quantity;

        public int getIngredientId() {
            return this.ingredientId;
        }

        public void setIngredientId(int ingredientId) {
            this.ingredientId = ingredientId;
        }

        public int getQuantity() {
            return this.quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

    }

    private ArrayList<InnerCreateRecipeRequest> ingredient;
    private String recipeName;
    private int servings;
    private int userId;

    public ArrayList<InnerCreateRecipeRequest> getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(ArrayList<InnerCreateRecipeRequest> ingredient) {
        this.ingredient = ingredient;
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getServings() {
        return this.servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}