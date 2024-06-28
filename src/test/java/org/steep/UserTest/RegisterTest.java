package org.steep.UserTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.steep.User.DeleteAccount;
import org.steep.User.Login;
import org.steep.User.Register;
import org.steep.User.RegisterStatusAndResponse;
import org.steep.User.RegisterStatusAndResponse.RegisterStatus;

public class RegisterTest {
    Register register = new Register();
    String username = "newUser";
    String password = "newPassword";

    @Test
    void createNewAccountSuccess() {
        RegisterStatusAndResponse.UserResponse response = register.registerMethod(username, password);

        assertEquals(response.getStatus(), RegisterStatus.SUCCESS);
        assertEquals(response.getMessage(), "User registered successfully");
        assertEquals(response.getUserId(), Login.getUserId(username));

        DeleteAccount.deleteUser(Login.getUserId(username));
    }

    @Test
    void createNewAccountUsernameExist() {
        RegisterStatusAndResponse.UserResponse response = register.registerMethod("Ciprian", "Ciprian");

        assertEquals(response.getStatus(), RegisterStatus.USERNAME_EXISTS);
        assertEquals(response.getMessage(), "Username already exists, please choose another one");
    }
}
