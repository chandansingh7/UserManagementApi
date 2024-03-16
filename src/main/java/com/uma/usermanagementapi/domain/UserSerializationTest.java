package com.uma.usermanagementapi.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserSerializationTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSerialized_User() throws JsonProcessingException {
        User user = new User(1L, "John", "Doe", "john.doe@example.com", "1234567890");
        String json = objectMapper.writeValueAsString(user);

        assertNotNull(json);
        // Checking for presence of a few key fields in the serialized JSON
        assert(json.contains("\"firstName\":\"John\""));
        assert(json.contains("\"lastName\":\"Doe\""));
        assert(json.contains("\"email\":\"john.doe@example.com\""));
    }

    @Test
    public void testDeserialized_User() throws JsonMappingException, JsonProcessingException {
        String json = "{\"id\":1,\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"phone\":\"1234567890\"}";
        User user = objectMapper.readValue(json, User.class);

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("1234567890", user.getPhone());
    }
}
