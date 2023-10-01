package com.epam.esm.common.controller.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    VALIDATION_ERROR(40001,"Field validation error"),
    MISSING_PARAMETER(40002,"Missing required parameter"),
    NOT_FOUND(40401, "Resource not found"),
    ALREADY_EXIST(40901, "Resource already exist"),
    UNKNOWN_ERROR(50001, "Unknown error");

    // Use cached VALUES instead of values() to prevent array allocation.
    private static final ErrorCode[] VALUES = values();

    private final int value;
    private final String description;
}
