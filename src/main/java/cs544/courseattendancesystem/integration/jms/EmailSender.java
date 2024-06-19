package cs544.courseattendancesystem.integration.jms;

import cs544.courseattendancesystem.domain.AttendanceRecord;

import java.util.List;

public interface EmailSender {

    public void sendConfirmationMessage(String to, String subject, String body);

    public void sendAttendances(String to, List<AttendanceRecord> attendanceRecordList);
}
