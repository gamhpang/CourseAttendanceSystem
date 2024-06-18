package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
public class FacultyServiceImpl implements FacultyService{

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public Faculty getFaculty(long facultyId) {
        return facultyRepository.findById(facultyId).orElse(null);
    }

}
