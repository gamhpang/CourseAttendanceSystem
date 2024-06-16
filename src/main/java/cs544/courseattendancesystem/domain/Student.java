package cs544.courseattendancesystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Student extends Person{
    private String entry;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="faculty_adviserid")
    private Faculty faculty;
    private long alternateId;
    private long applicantId;
    private long studentId;
    private String barCode;

    @Override
    public String getRole() {
        return "Student";
    }
    public Student(){}

    public Student(LocalDate birthDate, String emailAddress, String firstName, String lastName, String userName, String password, String entry, long alternateId, long applicantId, long studentId, String barCode) {
        super(birthDate, emailAddress, firstName, lastName, userName, password);
        this.entry = entry;
        this.alternateId = alternateId;
        this.applicantId = applicantId;
        this.studentId = studentId;
        this.barCode = barCode;
    }
}
