package com.epam.esm.common.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private static final String MESSAGE = "resource not found (%s)";
    private final int id;
    private final String name;

    public ResourceNotFoundException(int id) {
        this.id = id;
        this.name = null;
    }

    public ResourceNotFoundException(String name) {
        this.name = name;
        this.id = 0;
    }

    public String getDetailedMessage() {
        return String.format(MESSAGE,
                name == null ? "id = " + getId() : "name = " + getName()).toLowerCase();
    }
}
