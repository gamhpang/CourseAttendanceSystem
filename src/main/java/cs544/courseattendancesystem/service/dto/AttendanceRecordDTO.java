package cs544.courseattendancesystem.service.dto;

import cs544.courseattendancesystem.domain.Location;
import cs544.courseattendancesystem.domain.Student;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceRecordDTO {
    private long id;
    private LocalDateTime scanDateTime;
    private Long studentId;
    private Long locationId;
    private LocationDTO location;
    private Long sessionId;
    private SessionDTO session;

    public AttendanceRecordDTO(){}
}
