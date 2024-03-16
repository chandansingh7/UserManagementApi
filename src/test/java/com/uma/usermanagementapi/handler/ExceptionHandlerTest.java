package com.uma.usermanagementapi.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionHandlerTest {

    @Test
    void exceptionMessageIsCorrectlyStored() {
        String expectedMessage = "Custom error message";
        ExceptionHandler exception = new ExceptionHandler(expectedMessage);

        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage, "The exception message should match the provided message.");
    }
}
