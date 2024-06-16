package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService{

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public Faculty getFaculty(long facultyId) {
        return facultyRepository.findById(facultyId).orElse(null);
    }

    @Override
    public Faculty createFaculty(LocalDate birthDate, String emailAddress, String firstName, String lastName, String userName, String password, String salutation, List<String> hobbies){
        Faculty faculty = new Faculty(birthDate,emailAddress,firstName,lastName,userName,password,salutation,hobbies);
        facultyRepository.save(faculty);
        return faculty;
    }

}
