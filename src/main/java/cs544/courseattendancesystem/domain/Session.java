package cs544.courseattendancesystem.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate sessionDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public Session(){}
    public Session(LocalDate sessionDate, LocalTime startTime, LocalTime endTime) {
        this.sessionDate = sessionDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
