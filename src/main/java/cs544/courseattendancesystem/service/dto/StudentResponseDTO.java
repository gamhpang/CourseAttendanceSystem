package cs544.courseattendancesystem.service.dto;

import cs544.courseattendancesystem.domain.GenderType;
import cs544.courseattendancesystem.domain.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentResponseDTO {
    private long id;
    private LocalDate birthDate;
    private String emailAddress;
    private String studentName;
    private String userName;
    private String entry;
    private String adviserName;
    private String gender;
    private long alternateId;
    private long applicantId;
    private long studentId;
    private String barCode;

}
