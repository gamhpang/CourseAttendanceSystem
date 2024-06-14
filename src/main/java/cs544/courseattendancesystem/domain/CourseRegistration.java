package cs544.courseattendancesystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class CourseRegistration {
    @Id
    @GeneratedValue
    private long id;
    private String grade;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "courseOfferingId")
    private CourseOffering courseOffering;

    protected CourseRegistration(){}

    public CourseRegistration(String grade) {
        this.grade = grade;
    }
}
