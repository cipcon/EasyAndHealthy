package org.steep.Requests.RecipeIngredients;

public class CreateIngredientRequest {
    private String ingredientName;
    private String unit;

    public String getIngredientName() {
        return this.ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
