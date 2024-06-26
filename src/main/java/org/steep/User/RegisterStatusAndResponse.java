package org.steep.User;

public class RegisterStatusAndResponse {

    public enum RegisterStatus {
        SUCCESS,
        USERNAME_EXISTS,
        ERROR,
        EXPECTATION_FAILED
    }

    public static class RegisterResponse {
        private boolean isAuthenticated;
        private String message;
        private RegisterStatus status;
        private int userId;
        private String username;

        public RegisterResponse(boolean isAuthenticated, String message, RegisterStatus status, int userId,
                String username) {
            this.isAuthenticated = isAuthenticated;
            this.message = message;
            this.status = status;
            this.userId = userId;
            this.username = username;

        }

        public RegisterStatus getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public int getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        public boolean getIsAuthenticated() {
            return isAuthenticated;
        }
    }
}