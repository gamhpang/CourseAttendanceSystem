package cs544.courseattendancesystem.service.dto;

import lombok.Data;

import java.util.List;
@Data
public class AttendanceAndSessionFromOfferingDTO {
    List<SessionDTO> sessions;
    List<AttendanceRecordDTO> attendances;
    public AttendanceAndSessionFromOfferingDTO(){}
}
