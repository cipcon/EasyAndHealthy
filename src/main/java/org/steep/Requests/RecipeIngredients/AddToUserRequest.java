package org.steep.Requests.RecipeIngredients;

public class AddToUserRequest {
    private boolean added;
    private String message;

    public AddToUserRequest(boolean added, String message) {
        this.added = added;
        this.message = message;
    }

    public boolean isAdded() {
        return this.added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
