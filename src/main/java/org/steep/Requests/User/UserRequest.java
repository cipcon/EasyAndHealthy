package org.steep.Requests.User;

public class UserRequest {
    // java will automatically create a default constructor based on the attributes
    // i saved here
    private String currentUsername;
    private int id;

    public UserRequest() {
    }

    public UserRequest(String currentUsername, int id) {
        this.currentUsername = currentUsername;
        this.id = id;
    }

    public UserRequest(int id) {
        this.id = id;
    }

    // Getter
    public String getCurrentUsername() {
        return currentUsername;
    }

    public int getId() {
        return id;
    }

    // Setter
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    public void setId(int id) {
        this.id = id;
    }
}
