package org.steep.Requests.RecipeIngredients;

public class IngredientRequest {
    private int ingredientId;
    private String ingredientName;
    private int quantity;
    private String unit;

    public IngredientRequest(String ingredientName, int ingredientId, int quantity, String unit) {
        this.ingredientName = ingredientName;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.unit = unit;
    }

    public IngredientRequest(String ingredientName, int ingredientId, String unit) {
        this.ingredientName = ingredientName;
        this.ingredientId = ingredientId;
        this.unit = unit;
    }

    public String getIngredientName() {
        return this.ingredientName;
    }

    public void setIngredient(String ingredientName) {
        this.ingredientName = ingredientName;
    }

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

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
