package cs544.courseattendancesystem.service.dto;

import cs544.courseattendancesystem.domain.Course;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class CourseDTO {
    private long id;
    private double credits;
    private String description;
    private String code;
    private String name;
    private String department;
    private List<Long> prerequisites = new ArrayList<>();

    public CourseDTO(long id,double credits, String description, String code, String name, String department) {
        this.id = id;
        this.credits = credits;
        this.description = description;
        this.code = code;
        this.name = name;
        this.department = department;
    }
}
