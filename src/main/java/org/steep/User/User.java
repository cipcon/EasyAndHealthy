package org.steep.User;

public class User {

    // java will automatically create a default constructor based on the attributes i saved here
    private String currentUsername;
    private int id;

    public User() {
 
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
