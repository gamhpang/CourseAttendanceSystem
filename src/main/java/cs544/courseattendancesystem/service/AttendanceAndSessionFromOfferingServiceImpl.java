package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import cs544.courseattendancesystem.repository.AttendanceRecordRepository;
import cs544.courseattendancesystem.service.adapter.AttendanceRecordAdapter;
import cs544.courseattendancesystem.service.adapter.SessionAdapter;
import cs544.courseattendancesystem.service.dto.AttendanceAndSessionFromOfferingDTO;
import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;
import cs544.courseattendancesystem.service.dto.SessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AttendanceAndSessionFromOfferingServiceImpl implements AttendanceAndSessionFromOfferingService{
    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;
    @Autowired
    private AttendanceRecordAdapter attendanceRecordAdapter;
    @Autowired
    private SessionService sessionService;

    public AttendanceAndSessionFromOfferingDTO getAttendanceAndSession(List<Long> sessionList, Long studentId){
        List<AttendanceRecordDTO> attendanceRecords = new ArrayList<>();
        List<SessionDTO> sessionDTOS = new ArrayList<>();
        sessionList.forEach(session->{
            List<AttendanceRecord> att = attendanceRecordRepository.getAttendanceRecordBySessionIdAndStudentId(studentId,session);
            att.forEach(attendance->attendanceRecords.add(attendanceRecordAdapter.getAttendanceRecordDTOFromAttendanceRecord(attendance)));
            sessionDTOS.add(SessionAdapter.getSessionDTOFromSession(sessionService.getSession(session)));
        });

        AttendanceAndSessionFromOfferingDTO resultDTO= new AttendanceAndSessionFromOfferingDTO();
        resultDTO.setAttendances(attendanceRecords);
        resultDTO.setSessions(sessionDTOS);
        return resultDTO;

    }
}
