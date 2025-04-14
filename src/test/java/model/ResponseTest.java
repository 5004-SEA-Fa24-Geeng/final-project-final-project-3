package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResponseTest {

    @Test
    void testConstructorAndGetters() {
        Response response = new Response(201, "Created");
        assertEquals(201, response.getStatus());
        assertEquals("Created", response.getMessage());
    }

    @Test
    void testSuccessFactoryMethod() {
        Response response = Response.success("Operation succeeded");
        assertEquals(200, response.getStatus());
        assertEquals("Operation succeeded", response.getMessage());
    }

    @Test
    void testFailureFactoryMethod() {
        Response response = Response.failure("Operation failed");
        assertEquals(400, response.getStatus());
        assertEquals("Operation failed", response.getMessage());
    }
}
