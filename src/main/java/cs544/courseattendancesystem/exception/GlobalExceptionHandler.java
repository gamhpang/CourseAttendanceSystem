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
        logger.error(ex.getMessage());
        if(ex.getMessage().contains("is not supported")){
            return new ResponseEntity<>(new CustomerErrorType(ex.getMessage()),HttpStatus.METHOD_NOT_ALLOWED);
        }else if(ex.getMessage().contains("No static resource")){
            return new ResponseEntity<>(new CustomerErrorType("Not found"),HttpStatus.NOT_FOUND);
        }
        CustomerErrorType error = new CustomerErrorType("An unexpected error occurred");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}