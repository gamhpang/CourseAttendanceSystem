package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.*;
import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.repository.CourseOfferingRepository;
import cs544.courseattendancesystem.service.adapter.CourseAdapter;
import cs544.courseattendancesystem.service.adapter.CourseOfferingAdapter;
import cs544.courseattendancesystem.service.dto.CourseDTO;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import cs544.courseattendancesystem.service.dto.CustomerErrorType;
import cs544.courseattendancesystem.service.dto.FacultyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CourseOfferingServiceImpl implements CourseOfferingService {
    @Autowired
    private CourseService courseService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Override
    public CourseOfferingDTO createCourseOffering(CourseOfferingDTO courseOfferingDTO) {
        CourseOffering courseOffering = new CourseOffering(
                courseOfferingDTO.getCredits(),
                courseOfferingDTO.getRoom(),
                courseOfferingDTO.getStartDate(),
                courseOfferingDTO.getEndDate(),
                courseOfferingDTO.getCapacity(),
                courseOfferingDTO.getCourseOfferingType());

        //Checking course exit or not
        if(courseOfferingDTO.getCourseId() > 0){
            CourseDTO courseDTO = courseService.getCourse(courseOfferingDTO.getCourseId());
            if(courseDTO == null){
                throw new ResourceNotFoundException("Course not found with Id: "+courseOfferingDTO.getCourseId());
            }
            else{
                Course course = CourseAdapter.getCourseFromCourseDTO(courseService.getCourse(courseOfferingDTO.getCourseId()));
                System.out.println(course);
                courseOffering.setCourse(course);
            }
        }else{
            throw new ResourceNotFoundException("Course not found!");
        }

        System.out.println(courseOffering.getCourse()+"------------------------------");

        //Checking Faculty exit or not
        if(courseOfferingDTO.getFacultyId() > 0){
            Faculty faculty = facultyService.getFaculty(courseOfferingDTO.getFacultyId());
            if(faculty == null){
                throw new ResourceNotFoundException("Faculty Not Found with Id: " + courseOfferingDTO.getFacultyId());
            }
            else{
                courseOffering.setFaculty(faculty);
            }
        }
        else{
            courseOffering.setFaculty(null);
            //throw new ResourceNotFoundException("Faculty Not found");
        }

        List<Session> sessionList = new ArrayList<>();
        if(courseOfferingDTO.getSessionList()!=null){
            courseOfferingDTO.getSessionList().forEach(session->{
            sessionList.add(sessionService.getSession(session));
        });}
        courseOffering.setSessionList(sessionList);
        CourseOffering result = courseOfferingRepository.save(courseOffering);
       return CourseOfferingAdapter.getCourseOfferingDTOFromCourseOffering(result);
    }

    @Override
    public CourseOfferingDTO getCourseOffering(long courseOfferingId) {
        Optional<CourseOffering> courseOffering = courseOfferingRepository.findById(courseOfferingId);
        return courseOffering.map(CourseOfferingAdapter::getCourseOfferingDTOFromCourseOffering).orElse(null);
    }

    @Override
    public Collection<CourseOfferingDTO> getAllCourseOfferings() {
        return courseOfferingRepository.findAll()
                .stream()
                .map(CourseOfferingAdapter::getCourseOfferingDTOFromCourseOffering)
                .collect(Collectors.toList());
    }

    @Override
    public CourseOfferingDTO updateCourseOffering(long courseOfferingId,CourseOfferingDTO courseOfferingDTO) {
        CourseOffering courseOffering = courseOfferingRepository.findById(courseOfferingId).orElse(null);
        if(courseOffering != null){
            courseOffering.setCapacity(courseOfferingDTO.getCapacity());
            courseOffering.setCredits(courseOfferingDTO.getCredits());
            courseOffering.setRoom(courseOfferingDTO.getRoom());
            courseOffering.setStartDate(courseOfferingDTO.getStartDate());
            courseOffering.setEndDate(courseOfferingDTO.getEndDate());
            courseOffering.setCourseOfferingType(courseOfferingDTO.getCourseOfferingType());
            Course course = CourseAdapter.getCourseFromCourseDTO(courseService.getCourse(courseOfferingDTO.getCourseId()));
            courseOffering.setCourse(course);
            Faculty faculty = facultyService.getFaculty(courseOfferingDTO.getFacultyId());
            courseOffering.setFaculty(faculty);
            List<Session> sessionList = new ArrayList<>();
            courseOfferingDTO.getSessionList().forEach(session->{
                sessionList.add(sessionService.getSession(session));
            });
            courseOffering.setSessionList(sessionList);
            courseOfferingRepository.save(courseOffering);
            return CourseOfferingAdapter.getCourseOfferingDTOFromCourseOffering(courseOffering);
        }
        return null;
    }

    @Override
    public void deleteCourseOffering(long courseOfferingId) {
        courseOfferingRepository.deleteById(courseOfferingId);
    }
}
