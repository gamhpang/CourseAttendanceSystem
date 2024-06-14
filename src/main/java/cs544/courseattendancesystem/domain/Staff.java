package cs544.courseattendancesystem.domain;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;
@Entity
@Data
public class Staff extends Person{
    private LocalDate hireDate;
    @Override
    public String getRole() {
        return "Staff";
    }

    protected Staff(){}

    public Staff(LocalDate birthDate, String emailAddress, String firstName, String lastName, String userName, String password, LocalDate hireDate) {
        super(birthDate, emailAddress, firstName, lastName, userName, password);
        this.hireDate = hireDate;
    }
}
