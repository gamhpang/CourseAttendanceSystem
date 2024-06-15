package cs544.courseattendancesystem.service.dto;

public class CustomerErrorType {
    private String errorMessage;
    public CustomerErrorType(String errorMessage){
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage(){
        return errorMessage;
    }
}
