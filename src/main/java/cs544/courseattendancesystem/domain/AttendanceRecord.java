package cs544.courseattendancesystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class AttendanceRecord {
    @Id
    @GeneratedValue
    private long id;
    private LocalDateTime scanDateTime;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "sessionId")
    private Session session;

    public AttendanceRecord(){}
    public AttendanceRecord(Student student, Location location) {
        this.scanDateTime = LocalDateTime.now();
        this.student = student;
        this.location = location;
    }
}
