package cs544.courseattendancesystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime scanDateTime;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    public AttendanceRecord(){}
    public AttendanceRecord(Student student, Location location) {
        this.scanDateTime = LocalDateTime.now();
        this.student = student;
        this.location = location;
    }
}
