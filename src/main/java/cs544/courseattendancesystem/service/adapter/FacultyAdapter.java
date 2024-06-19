package cs544.courseattendancesystem.service.adapter;

import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.service.dto.FacultyDTO;
import org.springframework.stereotype.Service;

@Service
public class FacultyAdapter {
    public Faculty getFacultyFromFacultyDTO(FacultyDTO facultyDTO){
        Faculty faculty = new Faculty(facultyDTO.getBirthDate(),facultyDTO.getEmailAddress(), facultyDTO.getFirstName(),facultyDTO.getLastName(),facultyDTO.getUserName(),facultyDTO.getPassword(), facultyDTO.getSalutation(),facultyDTO.getHobbies());
        return faculty;
    }

    public static FacultyDTO getFacultyDTOFromFaculty(Faculty faculty){
        FacultyDTO facultyDTO = new FacultyDTO(faculty.getId(),faculty.getBirthDate(),faculty.getEmailAddress(), faculty.getFirstName(),faculty.getLastName(),faculty.getUserName(),faculty.getPassword(),faculty.getSalutation(),faculty.getHobbies());
        facultyDTO.setGenderType(faculty.getGenderType());
        facultyDTO.setHobbies(faculty.getHobbies());
        return facultyDTO;
    }
}
