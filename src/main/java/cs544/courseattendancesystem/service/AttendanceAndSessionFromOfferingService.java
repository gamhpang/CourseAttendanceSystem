package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.service.dto.AttendanceAndSessionFromOfferingDTO;

import java.util.List;

public interface AttendanceAndSessionFromOfferingService {
    AttendanceAndSessionFromOfferingDTO getAttendanceAndSession(List<Long> sessionList,Long studentId);
}
