package cs544.courseattendancesystem.service.adapter;

import cs544.courseattendancesystem.domain.CourseOffering;
import cs544.courseattendancesystem.domain.Session;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import cs544.courseattendancesystem.service.dto.FacultyDTO;
import cs544.courseattendancesystem.service.dto.SessionDTO;

import java.util.ArrayList;
import java.util.List;

public class CourseOfferingAdapter {

    public static CourseOffering getCourseOfferingFromCourseOfferingDTO(CourseOfferingDTO courseOfferingDTO){
        CourseOffering courseOffering = new CourseOffering(courseOfferingDTO.getCredits(),courseOfferingDTO.getRoom(),courseOfferingDTO.getStartDate(),courseOfferingDTO.getEndDate(),courseOfferingDTO.getCapacity(),courseOfferingDTO.getCourseOfferingType());
        courseOffering.setFaculty(FacultyAdapter.getFacultyFromFacultyDTO(courseOfferingDTO.getFaculty()));
        return courseOffering;
    }

    public static List<Session> getSessionsFromSessionDTO(List<SessionDTO> sessionDTOList){
        List<Session> sessionList = new ArrayList<>();
        for(SessionDTO session: sessionDTOList){
            sessionList.add(new Session(session.getSessionDate(),session.getStartTime(),session.getEndTime()));
        }
        return sessionList;
    }

    public static CourseOfferingDTO getCourseOfferingDTOFromCourseOffering(CourseOffering courseOffering){
        //CourseOfferingDTO courseOfferingDTO = new CourseOfferingDTO(courseOffering.getId(),courseOffering.getCredits(),courseOffering.getRoom(),courseOffering.getStartDate(),courseOffering.getEndDate(),courseOffering.getCapacity(),courseOffering.getCourseOfferingType(),);
        return null;
    }
}
