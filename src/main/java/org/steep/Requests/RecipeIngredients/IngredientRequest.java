package org.steep.Requests.RecipeIngredients;

public class IngredientRequest {
    private int ingredientId;
    private String ingredientName;
    private double quantity;
    private String unit;
    private int userId;
    private String message;
    private boolean added;

    public IngredientRequest(String ingredientName, int ingredientId, double quantity, String unit) {
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

    public IngredientRequest(String message, boolean added) {
        this.message = message;
        this.added = added;
    }

    // ingredientName
    public String getIngredientName() {
        return this.ingredientName;
    }

    public void setIngredient(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    // ingredientId
    public int getIngredientId() {
        return this.ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    // quantity
    public double getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // unit
    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // userId
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // message
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // added
    public boolean isAdded() {
        return this.added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }
}
