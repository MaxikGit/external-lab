package com.epam.esm.exceptions;

public class TagAlreadyExistsException extends RuntimeException {

    public TagAlreadyExistsException(String tagName) {
        super(String.format("Tag already exists (name = %s)", tagName));
    }
}
