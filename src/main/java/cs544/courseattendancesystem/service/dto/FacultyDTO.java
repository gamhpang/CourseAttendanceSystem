package cs544.courseattendancesystem.service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FacultyDTO extends PersonDTO {
    private long id;
    private String salutation;
    private List<String> hobbies;
}
