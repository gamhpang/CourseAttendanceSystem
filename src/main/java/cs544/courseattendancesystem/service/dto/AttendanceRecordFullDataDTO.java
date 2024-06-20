package cs544.courseattendancesystem.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceRecordFullDataDTO {
    private long id;
    private LocalDateTime scanDateTime;
    private long sessionId;
    private LocationDTO location;
}
