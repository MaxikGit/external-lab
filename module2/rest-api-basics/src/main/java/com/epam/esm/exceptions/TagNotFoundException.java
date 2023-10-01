package com.epam.esm.exceptions;

public class TagNotFoundException extends RuntimeException{

    public TagNotFoundException(int id) {
        super(String.format("Tag not found (id = %d)", id));
    }
    public TagNotFoundException(String name) {
        super(String.format("Tag not found (name = %s)", name));
    }
}
