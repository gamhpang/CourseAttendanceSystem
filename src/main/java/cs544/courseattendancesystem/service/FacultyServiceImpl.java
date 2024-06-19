package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.repository.FacultyRepository;
import cs544.courseattendancesystem.service.adapter.FacultyAdapter;
import cs544.courseattendancesystem.service.dto.FacultyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class FacultyServiceImpl implements FacultyService{

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private FacultyAdapter facultyAdapter;

    @Override
    public Faculty getFaculty(long facultyId) {
        return facultyRepository.findById(facultyId).orElse(null);
    }

    @Override
    public FacultyDTO createFaculty(FacultyDTO facultyDTO){
        Faculty faculty = new Faculty();
        faculty.setId(facultyDTO.getId());
        faculty.setFirstName(facultyDTO.getFirstName());
        faculty.setLastName(facultyDTO.getLastName());
        faculty.setBirthDate(facultyDTO.getBirthDate());
        faculty.setEmailAddress(facultyDTO.getEmailAddress());
        faculty.setSalutation(facultyDTO.getSalutation());
        faculty.setUserName(facultyDTO.getUserName());
        faculty.setHobbies(facultyDTO.getHobbies());
        faculty.setGenderType(facultyDTO.getGenderType());
        facultyRepository.save(faculty);
        return FacultyAdapter.getFacultyDTOFromFaculty(faculty);
    }
}
