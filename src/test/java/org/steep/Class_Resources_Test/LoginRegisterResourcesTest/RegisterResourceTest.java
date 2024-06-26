package org.steep.Class_Resources_Test.LoginRegisterResourcesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.steep.Class_resources.RegisterResource;
import org.steep.Requests.RegisterRequest;
import org.steep.User.Login;
import org.steep.User.Register;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class RegisterResourceTest {
    Response response;

    @Inject
    RegisterResource registerResource;
    RegisterRequest requestConflict = new RegisterRequest("Ciprian", "Ciprian");
    RegisterRequest requestCreated = new RegisterRequest("newUser", "newUser");
    RegisterRequest requestWrongPassOrUsername = new RegisterRequest("", null);

    @Test
    void testRegisterConflict() {
        response = registerResource.register(requestConflict);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    @Test
    void testRegisterSuccess() {
        response = registerResource.register(requestCreated);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        Register.deleteUser(Login.getUserId(requestCreated.getUsername()));
    }

    @Test
    void testRegisterWrongPasswordOrUsername() {
        response = registerResource.register(requestWrongPassOrUsername);
        assertEquals(Response.Status.EXPECTATION_FAILED.getStatusCode(), response.getStatus());
    }

}
