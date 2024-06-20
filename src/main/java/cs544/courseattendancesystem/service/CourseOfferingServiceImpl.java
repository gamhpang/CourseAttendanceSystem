package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.*;
import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.repository.CourseOfferingRepository;
import cs544.courseattendancesystem.repository.SessionRepository;
import cs544.courseattendancesystem.service.adapter.CourseAdapter;
import cs544.courseattendancesystem.service.adapter.CourseOfferingAdapter;
import cs544.courseattendancesystem.service.dto.CourseDTO;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import cs544.courseattendancesystem.service.dto.CourseOfferingWithDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@Service
@Transactional
public class CourseOfferingServiceImpl implements CourseOfferingService {
    @Autowired
    private CourseService courseService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    private CourseAdapter courseAdapter;
    @Autowired
    private CourseOfferingAdapter courseOfferingAdapter;

    @Override
    public CourseOfferingDTO createCourseOffering(CourseOfferingDTO courseOfferingDTO) {
        CourseOffering courseOffering = new CourseOffering(
                courseOfferingDTO.getCredits(),
                courseOfferingDTO.getRoom(),
                courseOfferingDTO.getStartDate(),
                courseOfferingDTO.getEndDate(),
                courseOfferingDTO.getCapacity(),
                courseOfferingDTO.getCourseOfferingType());

        //Checking course existence
        if (courseOfferingDTO.getCourseId() > 0) {
            CourseDTO courseDTO = courseService.getCourse(courseOfferingDTO.getCourseId()).orElse(null);
            if (courseDTO == null) {
                throw new ResourceNotFoundException("Course not found with Id: " + courseOfferingDTO.getCourseId());
            } else {
                Course course = courseAdapter.getCourseFromCourseDTO(courseService.getCourse(courseOfferingDTO.getCourseId()).orElse(null));
                courseOffering.setCourse(course);
            }
        } else {
            throw new ResourceNotFoundException("Course not found!");
        }

        //Checking Faculty existence, if not, just leave null
        if (courseOfferingDTO.getFacultyId() > 0) {
            Faculty faculty = facultyService.getFaculty(courseOfferingDTO.getFacultyId());
            if (faculty == null) {
                throw new ResourceNotFoundException("Faculty Not Found with Id: " + courseOfferingDTO.getFacultyId());
            } else {
                courseOffering.setFaculty(faculty);
            }
        } else {
            courseOffering.setFaculty(null);
        }

        //Generate Sessions for the course Offering
        List<Session> sessions = sessionService.generateSessions(courseOfferingDTO.getStartDate(), courseOfferingDTO.getEndDate());
        courseOffering.setSessionList(sessions);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            AuditData auditData = new AuditData();
            auditData.setCreatedBy(authentication.getName());
            auditData.setCreatedOn(LocalDateTime.now());
            courseOffering.setAuditData(auditData);
        }

        //Save Data to repository
        CourseOffering result = courseOfferingRepository.save(courseOffering);
        return courseOfferingAdapter.getCourseOfferingDTOFromCourseOffering(result);
    }

    @Override
    public CourseOfferingDTO getCourseOffering(long courseOfferingId) {
        Optional<CourseOffering> courseOffering = courseOfferingRepository.findById(courseOfferingId);
        return courseOffering.map(courseOfferingAdapter::getCourseOfferingDTOFromCourseOffering).orElse(null);
    }

    @Override
    public Collection<CourseOfferingDTO> getAllCourseOfferings() {
        return courseOfferingRepository.findAll()
                .stream()
                .map(courseOfferingAdapter::getCourseOfferingDTOFromCourseOffering)
                .toList();
    }

    @Override
    public Collection<CourseOfferingDTO> getAllCourseOfferingsByDate(LocalDate reqDate) {

        Collection<CourseOfferingDTO> courseOfferings = getAllCourseOfferings();
        return courseOfferings.stream().filter(co -> !co.getStartDate().isAfter(reqDate)
                && !co.getEndDate().isBefore(reqDate)).toList();

    }

    @Override
    public CourseOfferingDTO updateCourseOffering(long courseOfferingId, CourseOfferingDTO courseOfferingDTO) {
        CourseOffering courseOffering = courseOfferingRepository.findById(courseOfferingId).orElse(null);
        if (courseOffering != null) {

            //Update the courseOffering Data
            courseOffering.setCapacity(courseOfferingDTO.getCapacity());
            courseOffering.setCredits(courseOfferingDTO.getCredits());
            courseOffering.setRoom(courseOfferingDTO.getRoom());
            courseOffering.setStartDate(courseOfferingDTO.getStartDate());
            courseOffering.setEndDate(courseOfferingDTO.getEndDate());
            courseOffering.setCourseOfferingType(courseOfferingDTO.getCourseOfferingType());

            Course course = courseAdapter.getCourseFromCourseDTO(courseService.getCourse(courseOfferingDTO.getCourseId()).orElse(null));
            courseOffering.setCourse(course);

            Faculty faculty = facultyService.getFaculty(courseOfferingDTO.getFacultyId());
            courseOffering.setFaculty(faculty);
            //Update the session List based on the provided DTO
            List<Session> sessionList = new ArrayList<>();
            courseOfferingDTO.getSessionList().forEach(session -> {
                sessionList.add(sessionService.getSession(session));
            });
            courseOffering.setSessionList(sessionList);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.isAuthenticated()) {
                AuditData auditData = courseOffering.getAuditData();
                if(auditData!=null){
                    auditData.setUpdatedBy(authentication.getName());
                    auditData.setUpdatedOn(LocalDateTime.now());
                    courseOffering.setAuditData(auditData);
                }

            }

            courseOfferingRepository.save(courseOffering);
            return courseOfferingAdapter.getCourseOfferingDTOFromCourseOffering(courseOffering);
        }
        throw new ResourceNotFoundException("Course offering with Id:" + courseOfferingId + " not found!");
    }

    @Override
    public void deleteCourseOffering(long courseOfferingId) {
        courseOfferingRepository.deleteById(courseOfferingId);
    }
}
