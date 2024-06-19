package cs544.courseattendancesystem.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double credits;
    private String description;
    private String code;
    private String name;
    private String department;
    @Embedded
    private AuditData auditData;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name ="CoursePrerequisite",
        joinColumns = @JoinColumn(name="courseId"),
            inverseJoinColumns = @JoinColumn(name="prerequisiteId")
    )
    private List<Course> prerequisites;

    protected Course(){}

    public Course(double credits, String description, String code, String name, String department) {
        this.credits = credits;
        this.description = description;
        this.code = code;
        this.name = name;
        this.department = department;
        this.prerequisites = new ArrayList<>();
    }
}
