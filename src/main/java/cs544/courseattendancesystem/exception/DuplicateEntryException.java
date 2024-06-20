package cs544.courseattendancesystem.exception;

public class DuplicateEntryException extends RuntimeException{
    public DuplicateEntryException(String message) {
        super(message);
    }
}
