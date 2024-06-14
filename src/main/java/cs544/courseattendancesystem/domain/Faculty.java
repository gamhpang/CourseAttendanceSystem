package cs544.courseattendancesystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Faculty extends Person{
    private String salutation;
    @ElementCollection
    @CollectionTable(name = "FacultyHobby")
    private List<String> hobbies;
    @Override
    public String getRole() {
        return "Faculty";
    }

    protected Faculty() {
    }

    public Faculty(LocalDate birthDate, String emailAddress, String firstName, String lastName, String userName, String password, String salutation) {
        super(birthDate, emailAddress, firstName, lastName, userName, password);
        this.salutation = salutation;
    }
}

