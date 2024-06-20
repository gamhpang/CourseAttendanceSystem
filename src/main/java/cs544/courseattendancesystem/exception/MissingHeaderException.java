package cs544.courseattendancesystem.exception;

public class MissingHeaderException extends RuntimeException {
    public MissingHeaderException(String headerName) {
        super("Required header '" + headerName + "' is missing");
    }
}
