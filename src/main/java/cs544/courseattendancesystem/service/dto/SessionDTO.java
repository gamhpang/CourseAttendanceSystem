package cs544.courseattendancesystem.service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SessionDTO {
    private long id;
    private LocalDate sessionDate;
    private LocalTime startTime;
    private LocalTime endTime;
    public SessionDTO(long id,LocalDate sessionDate, LocalTime startTime, LocalTime endTime) {
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = id;
    }
}
