package com.uma.usermanagementapi.handler;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class ExceptionHandler extends RuntimeException {

    public ExceptionHandler(String message) {
        super(message);
    }
}
