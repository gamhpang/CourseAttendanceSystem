package cs544.courseattendancesystem.service.dto;

import cs544.courseattendancesystem.domain.Student;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceRecordDTO {
    private long id;
    private LocalDateTime scanDateTime;
    private long studentId;
    private long locationId;
    private long sessionId;
    private String locationName;

    public AttendanceRecordDTO(){}
}
