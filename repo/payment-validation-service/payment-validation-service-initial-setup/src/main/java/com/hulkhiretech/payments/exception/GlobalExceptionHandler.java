package com.hulkhiretech.payments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import com.hulkhiretech.payments.constant.ErrorEnum;
import com.hulkhiretech.payments.pojo.res.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
    		ValidationException ex) {
    	log.error("Validation error occurred: {}", ex.getMessage(), ex);
    	
    	ErrorResponse errorResponse = new ErrorResponse(
    			ex.getErrorCode(), ex.getMessage());
        log.info("Returning error response: {}", errorResponse);
    	
    	return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); 
    	
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    	log.error("Validation error occurred: {}", ex.getMessage(), ex);
    	
    	ErrorResponse errorResponse = new ErrorResponse(
    			ErrorEnum.GENERIC_ERROR.getErrorCode(), 
    			ErrorEnum.GENERIC_ERROR.getErrorMessage());
        log.info("Returning error response: {}", errorResponse);
    	
    	return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); 
    	// or any suitable status
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {

        String errorMessage =
                ex.getBindingResult()
                  .getFieldError()
                  .getDefaultMessage();

        ErrorResponse errorResponse =
                new ErrorResponse(
                        "400",
                        errorMessage);

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST);
    }
}

