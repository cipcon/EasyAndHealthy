package org.steep.Class_Resources_Test.LoginRegisterResourcesTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.steep.Class_resources.LoginResource;
import org.steep.Requests.CredentialsRequest;

import jakarta.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class LoginResourceTest {
    Response response;

    @Inject
    LoginResource loginResource;

    CredentialsRequest requestSuccess = new CredentialsRequest("Ciprian", "Ciprian");
    CredentialsRequest requestUnauthorized = new CredentialsRequest("Ciprian", "ciprian");

    @Test
    void testLoginSuccess() {
        response = loginResource.login(requestSuccess);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    void testLoginUnauthorized() {
        response = loginResource.login(requestUnauthorized);
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}
