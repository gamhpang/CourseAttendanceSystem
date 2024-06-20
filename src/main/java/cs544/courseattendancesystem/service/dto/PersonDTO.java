package cs544.courseattendancesystem.service.dto;

import cs544.courseattendancesystem.domain.GenderType;
import cs544.courseattendancesystem.domain.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonDTO {
    private long id;
    private LocalDate birthDate;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private User user;
    private GenderType genderType;

    public PersonDTO(){}
    public PersonDTO(LocalDate birthDate, String emailAddress, String firstName, String lastName, String userName) {
        this.birthDate = birthDate;
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }
}
