package cs544.courseattendancesystem.service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FacultyDTO extends PersonDTO {
    private long id;
    private String salutation;
    private List<String> hobbies;

    public FacultyDTO(long id,LocalDate birthDate, String emailAddress, String firstName, String lastName, String userName, String password, String salutation,List<String> hobbies) {
        super(birthDate, emailAddress, firstName, lastName, userName, password);
        this.salutation = salutation;
        this.id = id;
        this.hobbies = hobbies;
    }
}
