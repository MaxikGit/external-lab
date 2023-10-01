package com.epam.esm.authorizationserver.common.exception;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "resource already exists (%s)";
    private final String name;

    public ResourceAlreadyExistsException(String name) {
        this.name = name;
    }

    public String getDetailedMessage() {
        return String.format(MESSAGE, "name = " + name)
                .toLowerCase();
    }
}
