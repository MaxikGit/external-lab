package com.epam.esm.exceptions;

public class CertificateNotFoundException extends RuntimeException{

    public CertificateNotFoundException(int id) {
        super(String.format("Certificate not found (id = %d)", id));
    }
}
