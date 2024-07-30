package org.steep.Requests.SearchRecipe;

public class PrepareRecipeRequest {

    private boolean prepared;
    private String message;

    public PrepareRecipeRequest(boolean prepared, String message) {
        this.prepared = prepared;
        this.message = message;
    }

    public boolean isPrepared() {
        return this.prepared;
    }

    public void setPrepared(boolean prepared) {
        this.prepared = prepared;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
