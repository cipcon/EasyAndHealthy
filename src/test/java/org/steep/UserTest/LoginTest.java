package org.steep.UserTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.steep.User.Login;
import org.steep.User.RegisterStatusAndResponse.RegisterResponse;
import org.steep.User.UserAuthenticated;

public class LoginTest {

    String existingUsername = "Ciprian";
    String falseUsername = "Jon";
    String correctPassword = "Ciprian";
    String wrongPassword = "BadBoys";
    UserAuthenticated compare = new UserAuthenticated(existingUsername, Login.getUserId(existingUsername), true, "");

    @Test
    void loginSuccess() {
        RegisterResponse authenticatedUser = new Login().loginMethod(existingUsername, correctPassword);
        assertEquals(authenticatedUser.getIsAuthenticated(), compare.getIsAuthenticated());
        assertEquals(authenticatedUser.getUserId(), compare.getUserId());
        assertEquals(authenticatedUser.getUsername(), compare.getUsername());
    }

    @Test
    void wrongPassword() {
        RegisterResponse authenticatedUser = new Login().loginMethod(existingUsername, wrongPassword);
        assertEquals(authenticatedUser.getUserId(), 0);
        assertFalse(authenticatedUser.getIsAuthenticated());
        assertEquals(authenticatedUser.getUsername(), existingUsername);
    }

    @Test
    void wrongUsername() {
        RegisterResponse authenticatedUser = new Login().loginMethod(falseUsername, wrongPassword);
        assertEquals(authenticatedUser.getUserId(), 0);
        assertFalse(authenticatedUser.getIsAuthenticated());
        assertEquals(authenticatedUser.getUsername(), falseUsername);
    }

    @Test
    void emptyCredentials() {
        RegisterResponse authenticatedUser = new Login().loginMethod("", "");
        assertEquals(authenticatedUser.getUserId(), 0);
        assertFalse(authenticatedUser.getIsAuthenticated());
        assertEquals(authenticatedUser.getUsername(), "");
    }

}
