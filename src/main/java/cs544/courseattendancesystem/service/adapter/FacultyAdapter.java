package cs544.courseattendancesystem.service.adapter;

import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.service.dto.FacultyDTO;
import org.springframework.stereotype.Service;

@Service
public class FacultyAdapter {

    public static Faculty getFacultyFromFacultyDTO(FacultyDTO facultyDTO){
        Faculty faculty = new Faculty();
        faculty.setId(facultyDTO.getId());
        faculty.setBirthDate(facultyDTO.getBirthDate());
        faculty.setSalutation(facultyDTO.getSalutation());
        faculty.setFirstName(facultyDTO.getFirstName());
        faculty.setLastName(facultyDTO.getLastName());
        faculty.setEmailAddress(facultyDTO.getEmailAddress());
        faculty.setHobbies(facultyDTO.getHobbies());
        faculty.setGenderType(facultyDTO.getGenderType());
        faculty.setUserName(facultyDTO.getUserName());
//        facultyDTO.getBirthDate(),facultyDTO.getEmailAddress(), facultyDTO.getFirstName(),facultyDTO.getLastName(),facultyDTO.getUserName(),facultyDTO.getSalutation(),facultyDTO.getHobbies());

        return faculty;
    }

    public static FacultyDTO getFacultyDTOFromFaculty(Faculty faculty){
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setId(faculty.getId());
        facultyDTO.setBirthDate(faculty.getBirthDate());
        facultyDTO.setSalutation(faculty.getSalutation());
        facultyDTO.setFirstName(faculty.getFirstName());
        facultyDTO.setLastName(faculty.getLastName());
        facultyDTO.setEmailAddress(faculty.getEmailAddress());
        facultyDTO.setHobbies(faculty.getHobbies());
        facultyDTO.setUserName(faculty.getUserName());
        facultyDTO.setGenderType(faculty.getGenderType());

//        facultyDTO.setGenderType(faculty.getGenderType());
//        FacultyDTO facultyDTO = new FacultyDTO(faculty.getId(),
//        faculty.getBirthDate(),faculty.getEmailAddress(), faculty.getFirstName(),
//        faculty.getLastName(),faculty.getUserName(),faculty.getPassword(),faculty.getSalutation(),faculty.getHobbies());
        return facultyDTO;
    }
}
