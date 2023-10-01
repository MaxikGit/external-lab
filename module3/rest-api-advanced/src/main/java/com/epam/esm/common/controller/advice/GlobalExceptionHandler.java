package com.epam.esm.common.controller.advice;

import com.epam.esm.common.dto.ErrorResponseDto;
import com.epam.esm.common.exception.ResourceAlreadyExistsException;
import com.epam.esm.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    private ErrorResponseDto handleNotFoundException(ResourceNotFoundException ex) {
        log.warn(ex.getDetailedMessage(), ex);
        return ErrorResponseDto.builder()
                .errorMessage(ex.getDetailedMessage())
                .timestamp(LocalDateTime.now().toString())
                .errorCode(ErrorCode.NOT_FOUND.getValue())
                .build();
    }

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ErrorResponseDto handleValidationErrors(MethodArgumentNotValidException ex) {
        log.warn(ErrorCode.VALIDATION_ERROR.getDescription(), ex);
        Map<String, String> mapFieldMessage = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors
                        .groupingBy(FieldError::getField,
                                Collectors.mapping(FieldError::getDefaultMessage,
                                        Collectors.joining(". ")))
                );
        return ErrorResponseDto.builder()
                .errorMessage(mapFieldMessage)
                .timestamp(LocalDateTime.now().toString())
                .errorCode(ErrorCode.VALIDATION_ERROR.getValue())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    private ErrorResponseDto handleMissingParameterException(Exception ex) {
        log.error(ErrorCode.MISSING_PARAMETER.getDescription(), ex);
        return ErrorResponseDto.builder()
                .errorMessage(ErrorCode.MISSING_PARAMETER.getDescription())
                .timestamp(LocalDateTime.now().toString())
                .errorCode(ErrorCode.MISSING_PARAMETER.getValue())
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