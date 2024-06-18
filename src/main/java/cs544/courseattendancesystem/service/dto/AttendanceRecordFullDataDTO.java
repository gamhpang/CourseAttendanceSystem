package cs544.courseattendancesystem.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceRecordFullDataDTO {
    private long id;
    private LocalDateTime scanDateTime;
    private long studentId;
    private long locationId;
    private LocationDTO location;
    private long sessionId;
    private SessionDTO session;
}
