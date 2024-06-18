package cs544.courseattendancesystem.controller;

import cs544.courseattendancesystem.domain.AttendanceRecord;
import cs544.courseattendancesystem.service.AttendanceRecordService;
import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;
import cs544.courseattendancesystem.service.dto.CustomerErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys-admin/")
public class AttendanceController {
    @Autowired
    private AttendanceRecordService attendanceRecordService;

    @PostMapping("/attendance-record")
    public ResponseEntity<?> createAttendanceRecord(@RequestBody AttendanceRecordDTO attendanceRecordDTO) {
        AttendanceRecordDTO createdRecord = attendanceRecordService.createAttendance(attendanceRecordDTO);
        if (createdRecord != null) {
            return new ResponseEntity<>(createdRecord, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new CustomerErrorType("Unable to create attendance record"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/attendance-record/{recordId}")
    public ResponseEntity<?> getAttendanceRecord(@PathVariable long recordId) {
        AttendanceRecordDTO attendanceRecordDTO = attendanceRecordService.getAttendanceRecordDTO(recordId);
        if (attendanceRecordDTO == null) {
            return new ResponseEntity<>(new CustomerErrorType("Attendance record with id= " + recordId + " is not available"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attendanceRecordDTO, HttpStatus.OK);
    }
    @GetMapping("/attendance-record")
    public ResponseEntity<?> getAllAttendanceRecord() {
        List<AttendanceRecordDTO> attendanceRecordDTO = attendanceRecordService.getAllAttendanceRecordDTO();
        if (attendanceRecordDTO.size() == 0) {
            return new ResponseEntity<>(new CustomerErrorType("Attendance record is empty"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(attendanceRecordDTO, HttpStatus.OK);
    }
}
