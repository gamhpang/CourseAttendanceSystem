package cs544.courseattendancesystem.service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDTO extends PersonDTO{
    //    private Long id;
    private String entry;
    //    private Faculty faculty;
    private Long facultyId;
    private long alternateId;
    private long applicantId;
    private long studentId;
    private String barCode;

    public StudentDTO(){}

    public StudentDTO(LocalDate birthDate, String emailAddress, String firstName, String lastName, String userName, String password, String entry, Long facultyId, long alternateId, long applicantId, long studentId, String barCode) {
        super(birthDate, emailAddress, firstName, lastName, userName,password);
//        this.id = id;
        this.entry = entry;
        this.facultyId = facultyId;
        this.alternateId = alternateId;
        this.applicantId = applicantId;
        this.studentId = studentId;
        this.barCode = barCode;
    }
}
