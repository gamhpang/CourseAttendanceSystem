package cs544.courseattendancesystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class CourseOffering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double credits;
    private String room;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated
    private CourseOfferingType courseOfferingType;
    private int capacity;
    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;
    @ManyToOne
    @JoinColumn(name = "facultyId")
    private Faculty faculty;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sessionId")
    private List<Session> sessionList;

    @Embedded
    private AuditData auditData;

    protected CourseOffering() {
    }

    public CourseOffering(double credits, String room, LocalDate startDate, LocalDate endDate, int capacity, CourseOfferingType courseOfferingType) {
        this.credits = credits;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.courseOfferingType = courseOfferingType;
        this.sessionList = new ArrayList<>();
    }

    public void addSession(Session session) {
        sessionList.add(session);
    }
}
