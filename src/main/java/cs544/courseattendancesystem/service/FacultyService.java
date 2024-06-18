package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Faculty;

import java.time.LocalDate;
import java.util.List;

public interface FacultyService {

    public Faculty createFaculty(LocalDate birthDate, String emailAddress, String firstName, String lastName, String userName, String password, String salutation, List<String> hobbies);
    public Faculty getFaculty(long facultyId);


}
