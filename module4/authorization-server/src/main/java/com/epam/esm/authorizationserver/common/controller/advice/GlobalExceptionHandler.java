package com.epam.esm.authorizationserver.common.controller.advice;

import com.epam.esm.authorizationserver.common.dto.ErrorResponseDto;
import com.epam.esm.authorizationserver.common.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    private ErrorResponseDto handleAlreadyExistException(ResourceAlreadyExistsException ex) {
        log.warn(ex.getDetailedMessage(), ex);
        return ErrorResponseDto.builder()
                .errorMessage(ex.getDetailedMessage())
                .timestamp(LocalDateTime.now().toString())
                .errorCode(ErrorCode.ALREADY_EXIST.getValue())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    private ErrorResponseDto handleNotKnownException(Exception ex) {
        log.error(ErrorCode.UNKNOWN_ERROR.getDescription(), ex);
        return ErrorResponseDto.builder()
                .errorMessage(ErrorCode.UNKNOWN_ERROR.getDescription())
                .timestamp(LocalDateTime.now().toString())
                .errorCode(ErrorCode.UNKNOWN_ERROR.getValue())
                .build();
    }
}