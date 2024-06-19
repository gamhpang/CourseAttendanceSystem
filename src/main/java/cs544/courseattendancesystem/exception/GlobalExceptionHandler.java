package cs544.courseattendancesystem.exception;

import cs544.courseattendancesystem.service.dto.CustomerErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomerErrorType> handleCustomException(ResourceNotFoundException ex) {
        CustomerErrorType error = new CustomerErrorType(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingHeaderException.class)
    public ResponseEntity<CustomerErrorType> handleMissingHeaderException(MissingHeaderException ex) {
        CustomerErrorType error = new CustomerErrorType(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomerErrorType> handleGeneralException(Exception ex) {
        CustomerErrorType error = new CustomerErrorType("An unexpected error occurred");
        logger.error(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}