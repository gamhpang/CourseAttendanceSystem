package cs544.courseattendancesystem.exception;

import cs544.courseattendancesystem.service.dto.CustomerErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomerErrorType> handleCustomException(ResourceNotFoundException ex) {
        CustomerErrorType error = new CustomerErrorType(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomerErrorType> handleGeneralException(Exception ex) {
        CustomerErrorType error = new CustomerErrorType("An unexpected error occurred");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}