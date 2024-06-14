package org.steep.User;

public class UserAuthenticated {
    private int userId;
    private boolean isAuthenticated;

    public UserAuthenticated(int userId, boolean isAuthenticated) {
        this.userId = userId;
        this.isAuthenticated = isAuthenticated;
    }

    // Getter
    public int getUserId() {
        return userId;
    }

    public boolean getIsAuthenticated() {
        return isAuthenticated;
    }

    // Setter
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

}
