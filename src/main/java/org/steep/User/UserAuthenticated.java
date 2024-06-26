package org.steep.User;

public class UserAuthenticated {
    private String username;
    private int userId;
    private boolean isAuthenticated;

    public UserAuthenticated(String username, int userId, boolean isAuthenticated) {
        this.username = username;
        this.userId = userId;
        this.isAuthenticated = isAuthenticated;

    }

    // Getter
    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }

    public boolean getIsAuthenticated() {
        return isAuthenticated;
    }

    // Setter
    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setisAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    // Override toString() method for better representation
    @Override
    public String toString() {
        return "UserAuthenticated{" +
                "userId=" + userId +
                ", isAuthenticated=" + isAuthenticated +
                '}';
    }

    public boolean isEmpty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }

}
