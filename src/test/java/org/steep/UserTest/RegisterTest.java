package org.steep.UserTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.steep.Requests.User.RegisterRequest;
import org.steep.Requests.User.RegisterRequest.RegisterStatus;
import org.steep.User.DeleteAccount;
import org.steep.User.Login;
import org.steep.User.Register;

public class RegisterTest {
    Register register = new Register();
    String username = "newUser";
    String password = "newPassword";

    @Test
    void createNewAccountSuccess() {
        RegisterRequest.UserResponse response = register.registerMethod(username, password);

        assertEquals(response.getStatus(), RegisterStatus.SUCCESS);
        assertEquals(response.getMessage(), "User registered successfully");
        assertEquals(response.getUserId(), Login.getUserId(username));

        DeleteAccount.deleteUser(Login.getUserId(username));
    }

    @Test
    void createNewAccountUsernameExist() {
        RegisterRequest.UserResponse response = register.registerMethod("Ciprian", "Ciprian");

        assertEquals(response.getStatus(), RegisterStatus.USERNAME_EXISTS);
        assertEquals(response.getMessage(), "Username already exists, please choose another one");
    }
}
