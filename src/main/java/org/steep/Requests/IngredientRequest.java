package org.steep.Requests;

public class IngredientRequest {
    private String ingredient;
    private String unit;

    public IngredientRequest() {
    
    }

    public IngredientRequest(String ingredient, String unit) {
        this.ingredient = ingredient;
        this.unit = unit;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getUnit() {
        return unit;
    }
}
