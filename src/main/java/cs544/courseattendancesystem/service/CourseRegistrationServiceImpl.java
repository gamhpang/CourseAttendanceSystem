package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.CourseOffering;
import cs544.courseattendancesystem.domain.CourseRegistration;
import cs544.courseattendancesystem.domain.Student;
import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.repository.CourseOfferingRepository;
import cs544.courseattendancesystem.repository.CourseRegistrationRepository;
import cs544.courseattendancesystem.repository.StudentRepository;

import cs544.courseattendancesystem.service.adapter.StudentAdapter;

import cs544.courseattendancesystem.service.adapter.CourseRegistrationAdapter;

import cs544.courseattendancesystem.service.dto.CourseOfferingWithDetailsDTO;
import cs544.courseattendancesystem.service.dto.CourseWithGradeDTO;
import cs544.courseattendancesystem.service.dto.CourseRegistrationDTO;
import cs544.courseattendancesystem.service.dto.StudentWithRegisterCourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseRegistrationServiceImpl implements CourseRegistrationService{
    @Autowired
    CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Override
    public List<CourseWithGradeDTO> getCourseOfferingWithGradeDTO(long studentId) {
        List<CourseRegistration> courseRegistrations = courseRegistrationRepository.findByStudentId(studentId);

        if(courseRegistrations.isEmpty()){
            throw new ResourceNotFoundException("CourseOffering not found of Student Id: " + studentId);
        }

        return courseRegistrations.stream().map(courseRegistration -> {
            CourseWithGradeDTO courseWithGradeDTO = new CourseWithGradeDTO();
            if(courseRegistration.getCourseOffering().getFaculty() != null){
                courseWithGradeDTO.setFacultyId(courseRegistration.getCourseOffering().getFaculty().getId());
                courseWithGradeDTO.setFacultyName(courseRegistration.getCourseOffering().getFaculty().getFirstName() + " " + courseRegistration.getCourseOffering().getFaculty().getLastName());
            }
            else{
                courseWithGradeDTO.setFacultyId(0);
            }
            courseWithGradeDTO.setCourseOfferingId(courseRegistration.getCourseOffering().getId());
            courseWithGradeDTO.setCourseCode(courseRegistration.getCourseOffering().getCourse().getCode());
            courseWithGradeDTO.setCourseDepartment(courseRegistration.getCourseOffering().getCourse().getDepartment());
            courseWithGradeDTO.setStartDate(courseRegistration.getCourseOffering().getStartDate());
            courseWithGradeDTO.setEndDate(courseRegistration.getCourseOffering().getEndDate());
            courseWithGradeDTO.setCourseId(courseRegistration.getCourseOffering().getCourse().getId());
            courseWithGradeDTO.setCourseName(courseRegistration.getCourseOffering().getCourse().getName());
            courseWithGradeDTO.setCredits(courseRegistration.getCourseOffering().getCredits());
            courseWithGradeDTO.setCapacity(courseRegistration.getCourseOffering().getCapacity());
            courseWithGradeDTO.setRoom(courseRegistration.getCourseOffering().getRoom());
            courseWithGradeDTO.setGrade(courseRegistration.getGrade());
            courseWithGradeDTO.setCourseOfferingType(courseRegistration.getCourseOffering().getCourseOfferingType());
            return courseWithGradeDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public CourseRegistrationDTO createCourseRegistration(CourseRegistrationDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        CourseOffering courseOffering = courseOfferingRepository.findById(dto.getCourseOfferingId())
                .orElseThrow(() -> new ResourceNotFoundException("Course offering not found"));

        CourseRegistration courseRegistration = new CourseRegistration(dto.getGrade());
        courseRegistration.setStudent(student);
        courseRegistration.setCourseOffering(courseOffering);
        courseRegistrationRepository.save(courseRegistration);
        return CourseRegistrationAdapter.toDTO(courseRegistration);
    }

    @Override
    public CourseOfferingWithDetailsDTO getCourseOfferingDetailsWithId(long offeringId) {
        CourseOffering courseOffering = courseOfferingRepository.findById(offeringId)
                .orElseThrow(() -> new ResourceNotFoundException("Course offering not found"));

        List<CourseRegistrationDTO> registrations = courseRegistrationRepository.findByCourseOfferingId(offeringId).stream()
                .map(registration -> {
                    Student student = studentRepository.findById(registration.getStudent().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
                    CourseRegistrationDTO dto = new CourseRegistrationDTO();
                    dto.setId(registration.getId());
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstName() + " " + student.getLastName());
                    dto.setGrade(registration.getGrade());
                    dto.setCourseOfferingId(registration.getCourseOffering().getId());
                    return dto;
                })
                .collect(Collectors.toList());

        CourseOfferingWithDetailsDTO courseOfferingWithDetailsDTO = new CourseOfferingWithDetailsDTO();
        if(courseOffering.getFaculty() != null){
            courseOfferingWithDetailsDTO.setFacultyId(courseOffering.getFaculty().getId());
            courseOfferingWithDetailsDTO.setFacultyName(courseOffering.getFaculty().getFirstName() + " " + courseOffering.getFaculty().getLastName());

        }
        else{
            courseOfferingWithDetailsDTO.setFacultyId(0);
        }
        courseOfferingWithDetailsDTO.setCourseOfferingId(courseOffering.getId());
        courseOfferingWithDetailsDTO.setCredits(courseOffering.getCredits());
        courseOfferingWithDetailsDTO.setRoom(courseOffering.getRoom());
        courseOfferingWithDetailsDTO.setStartDate(courseOffering.getStartDate());
        courseOfferingWithDetailsDTO.setEndDate(courseOffering.getEndDate());
        courseOfferingWithDetailsDTO.setCapacity(courseOffering.getCapacity());
        courseOfferingWithDetailsDTO.setCourseOfferingType(courseOffering.getCourseOfferingType());
        courseOfferingWithDetailsDTO.setCourseId(courseOffering.getCourse().getId());
        courseOfferingWithDetailsDTO.setCourseName(courseOffering.getCourse().getName());
        courseOfferingWithDetailsDTO.setCourseCode(courseOffering.getCourse().getCode());
        courseOfferingWithDetailsDTO.setCourseDepartment(courseOffering.getCourse().getDepartment());
        courseOfferingWithDetailsDTO.setRegistrations(registrations);
        return courseOfferingWithDetailsDTO;
    }

    @Override
    public Collection<CourseOfferingWithDetailsDTO> getCourseOfferingDetails() {
        List<CourseOffering> courseOfferings = courseOfferingRepository.findAll();

        if(courseOfferings.isEmpty()){
            throw new ResourceNotFoundException("CourseOffering not found.");
        }

        return courseOfferings.stream().map(courseOffering -> {
            List<CourseRegistrationDTO> registrations = courseRegistrationRepository.findByCourseOfferingId(courseOffering.getId()).stream()
                    .map(registration -> {
                        Student student = studentRepository.findById(registration.getStudent().getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
                        CourseRegistrationDTO dto = new CourseRegistrationDTO();
                        dto.setId(registration.getId());
                        dto.setStudentId(student.getId());
                        dto.setStudentName(student.getFirstName() + " " + student.getLastName());
                        dto.setGrade(registration.getGrade());
                        dto.setCourseOfferingId(registration.getCourseOffering().getId());
                        return dto;
                    })
                    .collect(Collectors.toList());

            CourseOfferingWithDetailsDTO courseOfferingWithDetailsDTO = new CourseOfferingWithDetailsDTO();
            if(courseOffering.getFaculty() != null){
                courseOfferingWithDetailsDTO.setFacultyId(courseOffering.getFaculty().getId());
                courseOfferingWithDetailsDTO.setFacultyName(courseOffering.getFaculty().getFirstName() + " " + courseOffering.getFaculty().getLastName());

            }
            else{
                courseOfferingWithDetailsDTO.setFacultyId(0);
            }
            courseOfferingWithDetailsDTO.setCourseOfferingId(courseOffering.getId());
            courseOfferingWithDetailsDTO.setCredits(courseOffering.getCredits());
            courseOfferingWithDetailsDTO.setRoom(courseOffering.getRoom());
            courseOfferingWithDetailsDTO.setStartDate(courseOffering.getStartDate());
            courseOfferingWithDetailsDTO.setEndDate(courseOffering.getEndDate());
            courseOfferingWithDetailsDTO.setCapacity(courseOffering.getCapacity());
            courseOfferingWithDetailsDTO.setCourseOfferingType(courseOffering.getCourseOfferingType());
            courseOfferingWithDetailsDTO.setCourseId(courseOffering.getCourse().getId());
            courseOfferingWithDetailsDTO.setCourseName(courseOffering.getCourse().getName());
            courseOfferingWithDetailsDTO.setCourseCode(courseOffering.getCourse().getCode());
            courseOfferingWithDetailsDTO.setCourseDepartment(courseOffering.getCourse().getDepartment());
            courseOfferingWithDetailsDTO.setRegistrations(registrations);
            return courseOfferingWithDetailsDTO;
        }).collect(Collectors.toList());
    }

    public StudentWithRegisterCourseDTO getCourseOfferingByStudent(long studentId) {
        StudentWithRegisterCourseDTO studentWithRegisterCourseDTO = new StudentWithRegisterCourseDTO();
        studentWithRegisterCourseDTO.setCourseWithGradeDTOCollection(getCourseOfferingWithGradeDTO(studentId));
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        studentWithRegisterCourseDTO.setStudent(StudentAdapter.getStudentDTOFromStudent(optionalStudent.get()));
        return studentWithRegisterCourseDTO;
    }
}
