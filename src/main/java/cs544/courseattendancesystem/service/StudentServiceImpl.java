package cs544.courseattendancesystem.service;


import cs544.courseattendancesystem.domain.Faculty;
import cs544.courseattendancesystem.domain.Student;
import cs544.courseattendancesystem.exception.ResourceNotFoundException;
import cs544.courseattendancesystem.repository.CourseRegistrationRepository;
import cs544.courseattendancesystem.repository.FacultyRepository;
import cs544.courseattendancesystem.repository.StudentRepository;
import cs544.courseattendancesystem.service.adapter.StudentAdapter;
import cs544.courseattendancesystem.service.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;

    @Override
    public StudentDTO createStudentByDTO(StudentDTO studentDTO) {
        Optional<Faculty> faculty = facultyRepository.findById(studentDTO.getFacultyId());
        System.out.println("This is Faculty => " + faculty);
        if(faculty.isEmpty()){
            throw new ResourceNotFoundException("There is no Faculty.");
        }
        Student student = StudentAdapter.getStudentFromStudentDTO(studentDTO);
        student.setFaculty(faculty.get());
        studentRepository.save(student);
        System.out.println("Save successfully...");
        return StudentAdapter.getStudentDTOFromStudent(student);
    }

    @Override
    public StudentDTO getStudent(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if(student.isEmpty()){
            throw new ResourceNotFoundException("There is no Student.");
        }
        return StudentAdapter.getStudentDTOFromStudent(student.get());
    }

    @Override
    public Student getStudentById(long id){
        Optional<Student> student = studentRepository.findById(id);
        if(student.isEmpty()){
            throw new ResourceNotFoundException("There is no Student.");
        }
        return student.get();
    }

    @Override
    public StudentDTO updateStudent(long studentId, StudentDTO studentDTO){
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Faculty> faculty = facultyRepository.findById(studentDTO.getFacultyId());
        System.out.println("Optional Student => " + optionalStudent);
        if(optionalStudent.isEmpty()){
            throw new ResourceNotFoundException("There is no Student.");
        }
        if(faculty.isEmpty()){
            throw new ResourceNotFoundException("There is no Faculty.");
        }
        Student student = optionalStudent.get();
        student.setEntry(studentDTO.getEntry());
        student.setApplicantId(studentDTO.getAlternateId());
        student.setBarCode(studentDTO.getBarCode());
        student.setStudentId(studentDTO.getStudentId());
        student.setApplicantId(studentDTO.getApplicantId());
        student.setFaculty(faculty.get());
        student.setBirthDate(studentDTO.getBirthDate());
        student.setEmailAddress(studentDTO.getEmailAddress());
        student.setGenderType(studentDTO.getGenderType());
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setPassword(studentDTO.getPassword());
        student.setUserName(studentDTO.getUserName());
        studentRepository.save(student);
        System.out.println("Save Successfully.....");
        return StudentAdapter.getStudentDTOFromStudent(student);
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
        System.out.println("Delete Successfully....");
    }

    @Override
    public Collection<StudentDTO> getAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        return StudentAdapter.getStudentListFromStudentDTO(studentList);
    }

}
