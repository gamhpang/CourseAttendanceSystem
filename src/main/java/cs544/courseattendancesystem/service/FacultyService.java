package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.service.dto.FacultyDTO;

public interface FacultyService {

    public Faculty getFaculty(long facultyId);

    public FacultyDTO createFaculty(FacultyDTO facultyDTO);
}
