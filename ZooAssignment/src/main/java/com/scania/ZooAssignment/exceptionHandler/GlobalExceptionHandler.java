package com.scania.ZooAssignment.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.opencsv.exceptions.CsvException;

import jakarta.xml.bind.JAXBException;

import java.io.IOException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorMessageResponse> handleIOException(IOException ex) {
        return displayErrorMessage(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), "api/zoo/cost", ex.getMessage());
    }


    @ExceptionHandler({CsvException.class, NumberFormatException.class, JAXBException.class})
    public ResponseEntity<ErrorMessageResponse> handleCSVException(Exception ex){
        return displayErrorMessage(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "api/zoo/cost", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageResponse> handleGenericException(Exception ex){
        return displayErrorMessage(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "api/zoo/cost", ex.getMessage());
    }

    private ResponseEntity<ErrorMessageResponse> displayErrorMessage(LocalDateTime time, int status, String path, String errorMessage) {
        ErrorMessageResponse errorResponse = new ErrorMessageResponse(
                time,
                status,
                path,
                errorMessage
        );
        return ResponseEntity.status(status).body(errorResponse);
    }
}

