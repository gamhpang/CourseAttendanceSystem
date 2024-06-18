package cs544.courseattendancesystem.service.adapter;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import cs544.courseattendancesystem.domain.Student;
import cs544.courseattendancesystem.service.LocationService;
import cs544.courseattendancesystem.service.SessionService;
import cs544.courseattendancesystem.service.SessionServiceImpl;
import cs544.courseattendancesystem.service.StudentService;
import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AttendanceRecordAdapter {
    @Autowired
    private StudentService studentService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private SessionService sessionService;
    public AttendanceRecordDTO getAttendanceRecordDTOFromAttendanceRecord(AttendanceRecord attendanceRecord) {
        if (attendanceRecord == null) {
            return null;
        }

        AttendanceRecordDTO dto = new AttendanceRecordDTO();
        dto.setId(attendanceRecord.getId());
        dto.setScanDateTime(attendanceRecord.getScanDateTime());
        dto.setStudentId(attendanceRecord.getStudent() != null ? attendanceRecord.getStudent().getId() : null);
        dto.setLocationId(attendanceRecord.getLocation() != null ? attendanceRecord.getLocation().getId() : null);
        dto.setSessionId(attendanceRecord.getSession() != null ? attendanceRecord.getSession().getId() : null);
        return dto;
    }

    public AttendanceRecord getAttendanceRecordFromAttendanceDTO(AttendanceRecordDTO dto) {
        if (dto == null) {
            return null;
        }

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.setId(dto.getId());
        attendanceRecord.setScanDateTime(dto.getScanDateTime());

        // For setting entity references, handle null checks
        if (dto.getStudentId() != 0) {
            attendanceRecord.setStudent(studentService.getStudentById(dto.getStudentId()));
        }
        if (dto.getLocationId() != 0) {
            attendanceRecord.setLocation(locationService.getLocationById(dto.getLocationId()));
        }
        if (dto.getSessionId() != 0) {
             attendanceRecord.setSession(sessionService.getSession(dto.getSessionId()));
        }

        return attendanceRecord;
    }

    public static AttendanceRecordDTO getAttendanceRecordDTOIncludeAllRecordFromAttendanceRecord(AttendanceRecord attendanceRecord) {
        if (attendanceRecord == null) {
            return null;
        }

        AttendanceRecordDTO dto = new AttendanceRecordDTO();
        dto.setId(attendanceRecord.getId());
        dto.setScanDateTime(attendanceRecord.getScanDateTime());
        dto.setStudentId(attendanceRecord.getStudent() != null ? attendanceRecord.getStudent().getId() : null);
        dto.setLocationId(attendanceRecord.getLocation() != null ? attendanceRecord.getLocation().getId() : null);
//        dto.setLocation(LocationAdapter.getLocationDTOFromLocation(attendanceRecord.getLocation()));
        dto.setSessionId(attendanceRecord.getSession() != null ? attendanceRecord.getSession().getId() : null);
//        dto.setSession(SessionAdapter.getSessionDTOFromSession(attendanceRecord.getSession()));
        return dto;
    }

    public Collection<AttendanceRecordDTO> getAllAttendanceRecord(Collection<AttendanceRecord> attendanceRecords){
        if(attendanceRecords.isEmpty()){
            return null;
        }
        Collection<AttendanceRecordDTO> attendanceRecordDTOS = new ArrayList<>();
        for(AttendanceRecord att : attendanceRecords){
            System.out.println("Each att......" + att);
            attendanceRecordDTOS.add(getAttendanceRecordDTOIncludeAllRecordFromAttendanceRecord(att));
        }
        return attendanceRecordDTOS;
    }
}

