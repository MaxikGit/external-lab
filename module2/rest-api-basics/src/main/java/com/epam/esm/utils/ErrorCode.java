package com.epam.esm.utils;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.models.GiftCertificate;
import com.epam.esm.models.Tag;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.epam.esm.utils.ErrorCode.ErrorObject.*;

@Getter
public enum ErrorCode {

    BAD_REQUEST_TAG(4001, HttpStatus.BAD_REQUEST, TAG, "Tag field validation error"),
    BAD_REQUEST_CERTIFICATE(4002, HttpStatus.BAD_REQUEST, CERTIFICATE, "Certificate field validation error"),
    NOT_FOUND_TAG(4041, HttpStatus.NOT_FOUND, TAG, "Tag not found"),
    NOT_FOUND_CERTIFICATE(4042, HttpStatus.NOT_FOUND, CERTIFICATE, "Certificate not found"),
    ALREADY_EXIST_TAG(409, HttpStatus.CONFLICT, TAG, "Tag already exist"),
    SERVER_ERROR(5000, HttpStatus.INTERNAL_SERVER_ERROR, UNKNOWN,
            "Unknown error");

    private static final ErrorCode[] VALUES;

    static {
        VALUES = values();
    }

    private final int value;
    private final HttpStatus httpStatus;
    private final ErrorObject errorObject;
    private final String description;

    ErrorCode(int value, HttpStatus httpStatus, ErrorObject errorObject, String description) {
        this.value = value;
        this.httpStatus = httpStatus;
        this.errorObject = errorObject;
        this.description = description;
    }

    public static ErrorCode resolve(HttpStatus statusCode, Class<?> clazz) {
        // Use cached VALUES instead of values() to prevent array allocation.
        ErrorObject object = ErrorObject.getObjectByClass(clazz);
        for (ErrorCode errorCode : VALUES) {
            if (errorCode.httpStatus == statusCode && errorCode.errorObject == object) {
                return errorCode;
            }
        }
        return SERVER_ERROR;
    }

    enum ErrorObject {
        TAG, CERTIFICATE, UNKNOWN;

        private static ErrorObject getObjectByClass(Class<?> clazz) {
            if (TagDto.class == clazz || Tag.class == clazz) {
                return TAG;
            } else if (GiftCertificateDto.class == clazz || GiftCertificate.class == clazz) {
                return CERTIFICATE;
            } else return UNKNOWN;
        }
    }
}
