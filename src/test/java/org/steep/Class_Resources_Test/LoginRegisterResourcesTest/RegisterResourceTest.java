package org.steep.Class_Resources_Test.LoginRegisterResourcesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.steep.Class_resources.RegisterResource;
import org.steep.Requests.CredentialsRequest;
import org.steep.User.DeleteAccount;
import org.steep.User.Login;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class RegisterResourceTest {
    Response response;

    @Inject
    RegisterResource registerResource;

    CredentialsRequest requestConflict = new CredentialsRequest("Ciprian", "Ciprian");
    CredentialsRequest requestSuccess = new CredentialsRequest("newUser", "newUser");
    CredentialsRequest requestWrongPassOrUsername = new CredentialsRequest("", "");

    @Test
    void testRegisterConflict() {
        response = registerResource.register(requestConflict);
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    @Test
    void testRegisterSuccess() {
        response = registerResource.register(requestSuccess);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        DeleteAccount.deleteUser(Login.getUserId(requestSuccess.getUsername()));
    }

    @Test
    void testRegisterWrongPasswordOrUsername() {
        response = registerResource.register(requestWrongPassOrUsername);
        assertEquals(Response.Status.EXPECTATION_FAILED.getStatusCode(), response.getStatus());
    }

}
