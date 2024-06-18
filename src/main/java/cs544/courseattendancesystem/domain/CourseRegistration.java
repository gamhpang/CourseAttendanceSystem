package cs544.courseattendancesystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class CourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String grade;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "courseOfferingId")
    private CourseOffering courseOffering;

    public CourseRegistration(){}

    public CourseRegistration(String grade) {
        this.grade = grade;
    }
}
