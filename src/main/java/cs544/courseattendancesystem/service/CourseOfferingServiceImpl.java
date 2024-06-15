package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.*;
import cs544.courseattendancesystem.service.adapter.CourseAdapter;
import cs544.courseattendancesystem.service.adapter.CourseOfferingAdapter;
import cs544.courseattendancesystem.service.adapter.FacultyAdapter;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class CourseOfferingServiceImpl implements CourseOfferingService {
    @Autowired
    private CourseService courseService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private SessionService sessionService;

    @Override
    public CourseOfferingDTO createCourseOffering(CourseOfferingDTO courseOfferingDTO) {
        CourseOffering courseOffering = new CourseOffering(
                courseOfferingDTO.getCredits(),
                courseOfferingDTO.getRoom(),
                courseOfferingDTO.getStartDate(),
                courseOfferingDTO.getEndDate(),
                courseOfferingDTO.getCapacity(),
                courseOfferingDTO.getCourseOfferingType());

       Course course = CourseAdapter.getCourseFromCourseDTO(courseService.getCourse(courseOfferingDTO.getCourseId()));
       courseOffering.setCourse(course);
        Faculty faculty = facultyService.getFaculty(courseOfferingDTO.getFacultyId());
        courseOffering.setFaculty(faculty);
        List<Session> sessionList = new ArrayList<>();
        courseOfferingDTO.getSessionList().stream().forEach(session->{
           sessionList.add(sessionService.getSession(session));
        });
        courseOffering.setSessionList(sessionList);
       return CourseOfferingAdapter.getCourseOfferingDTOFromCourseOffering(courseOffering);
    }

    @Override
    public CourseOfferingDTO getCourseOffering(long courseOfferingId) {
        return null;
    }

    @Override
    public Collection<CourseOfferingDTO> getAllCourseOfferings() {
        return null;
    }

    @Override
    public CourseOfferingDTO updateCourseOffering(CourseOfferingDTO courseOfferingDTO) {
        return null;
    }

    @Override
    public void deleteCourseOffering(long courseOfferingId) {

    }
}
