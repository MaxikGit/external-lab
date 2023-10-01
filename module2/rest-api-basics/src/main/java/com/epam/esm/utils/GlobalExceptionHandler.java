package com.epam.esm.utils;

import com.epam.esm.dto.ErrorResponseDto;
import com.epam.esm.exceptions.CertificateNotFoundException;
import com.epam.esm.exceptions.TagAlreadyExistsException;
import com.epam.esm.exceptions.TagNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.esm.utils.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({CertificateNotFoundException.class, TagNotFoundException.class})
    private ErrorResponseDto handleNotFoundException(Exception ex) {
        ErrorCode errorCode;
        if (ex instanceof CertificateNotFoundException){
            errorCode = NOT_FOUND_CERTIFICATE;
        }
        else errorCode = NOT_FOUND_TAG;
        log.warn(errorCode.getDescription(), ex);
        return ErrorResponseDto.builder()
                .errorMessage(ex.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .errorCode(errorCode.getValue())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(TagAlreadyExistsException.class)
    private ErrorResponseDto handleAlreadyExistException(Exception ex) {
        ErrorCode errorCode = ALREADY_EXIST_TAG;
        log.warn(errorCode.getDescription(), ex);
        return ErrorResponseDto.builder()
                .errorMessage(ex.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .errorCode(errorCode.getValue())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ErrorResponseDto handleValidationErrors(MethodArgumentNotValidException ex) {
        ErrorCode errorCode = ErrorCode.resolve(HttpStatus.BAD_REQUEST, ex.getTarget().getClass());
        log.warn(errorCode.getDescription(), ex);
        Map<String, String> mapFieldMessage = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors
                        .groupingBy(FieldError::getField, Collectors
                                .mapping(FieldError::getDefaultMessage, Collectors
                                        .joining(". "))));
        return ErrorResponseDto.builder()
                .errorMessage(mapFieldMessage)
                .timestamp(LocalDateTime.now().toString())
                .errorCode(errorCode.getValue())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    private ErrorResponseDto handleNotKnownException(Exception ex) {
        ErrorCode errorCode = SERVER_ERROR;
        log.error(errorCode.getDescription(), ex);
        String errorMessage = (ex.getMessage() == null) ? errorCode.getDescription() : ex.getMessage();
        return ErrorResponseDto.builder()
                .errorMessage(errorMessage)
                .timestamp(LocalDateTime.now().toString())
                .errorCode(errorCode.getValue())
                .build();
    }
}