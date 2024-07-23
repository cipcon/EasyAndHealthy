package org.steep.Requests.RecipeIngredients;

import java.util.ArrayList;

public class CreateRecipeRequest {
    public class InnerCreateRecipeRequest {
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

    private int portions;
    private String recipeName;
    private int userId;
    private ArrayList<InnerCreateRecipeRequest> ingredient;

    public int getPortions() {
        return this.portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public String getRecipeName() {
        return this.recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<InnerCreateRecipeRequest> getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(ArrayList<InnerCreateRecipeRequest> ingredient) {
        this.ingredient = ingredient;
    }

}
