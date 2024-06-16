package cs544.courseattendancesystem.service.adapter;

import cs544.courseattendancesystem.domain.Course;
import cs544.courseattendancesystem.domain.CourseOffering;
import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.domain.Session;
import cs544.courseattendancesystem.service.*;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import cs544.courseattendancesystem.service.dto.SessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseOfferingAdapter {

    @Autowired
    private static FacultyService facultyService;

    @Autowired
    private static SessionService sessionService;

    @Autowired
    private static CourseService courseService;

    public static CourseOffering getCourseOfferingFromCourseOfferingDTO(CourseOfferingDTO courseOfferingDTO){
        CourseOffering courseOffering = new CourseOffering(courseOfferingDTO.getCredits(),courseOfferingDTO.getRoom(),courseOfferingDTO.getStartDate(),courseOfferingDTO.getEndDate(),courseOfferingDTO.getCapacity(),courseOfferingDTO.getCourseOfferingType());
        Faculty faculty = facultyService.getFaculty(courseOfferingDTO.getFacultyId());
        courseOffering.setFaculty(faculty);
        List<Session> sessionList = new ArrayList<>();
        courseOfferingDTO.getSessionList().forEach(sess->{
           sessionList.add( sessionService.getSession(sess));
        });
        courseOffering.setSessionList(sessionList);
        Course course = CourseAdapter.getCourseFromCourseDTO(courseService.getCourse(courseOfferingDTO.getCourseId()).orElse(null));
        courseOffering.setCourse(course);
        return courseOffering;
    }


    public static CourseOfferingDTO getCourseOfferingDTOFromCourseOffering(CourseOffering courseOffering){
        long courseId = courseOffering.getCourse().getId();
        long facultyId = 0;
        if(courseOffering.getFaculty()!=null){
            facultyId = courseOffering.getFaculty().getId();
        }

        List<Long> sessionIDList = courseOffering.getSessionList().stream().map(Session::getId).toList();
        CourseOfferingDTO courseOfferingDTO = new CourseOfferingDTO(courseOffering.getId(),courseOffering.getCredits(),courseOffering.getRoom(),courseOffering.getStartDate(),courseOffering.getEndDate(),courseOffering.getCapacity(),courseOffering.getCourseOfferingType(),courseId,facultyId,sessionIDList);
        return courseOfferingDTO;
    }

    public static List<SessionDTO> getSessionDTOsFromSession(List<Session> sessionList){
        List<SessionDTO> sessionDTOList = new ArrayList<>();
        for(Session session: sessionList){
            sessionDTOList.add(new SessionDTO(session.getId(),session.getSessionDate(),session.getStartTime(),session.getEndTime()));
        }
        return sessionDTOList;
    }

}
