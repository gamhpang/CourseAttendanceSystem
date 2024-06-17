package cs544.courseattendancesystem.service.dto;

import cs544.courseattendancesystem.domain.Faculty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentDTO extends PersonDTO{
    private long id;
    private String entry;
    private FacultyDTO faculty;
    private Long facultyId;
    private long alternateId;
    private long applicantId;
    private long studentId;
    private String barCode;
}
