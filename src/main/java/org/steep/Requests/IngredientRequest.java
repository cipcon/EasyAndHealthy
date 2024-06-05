package org.steep.Requests;

public class IngredientRequest {
    private String name;
    private String unit;

    public IngredientRequest() {
    
    }

    public IngredientRequest(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public void setIngredient(String name) {
        this.name = name;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getIngredient() {
        return this.name;
    }

    public String getUnit() {
        return this.unit;
    }
}
