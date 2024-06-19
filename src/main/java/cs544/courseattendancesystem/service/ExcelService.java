package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;
import cs544.courseattendancesystem.service.dto.CourseOfferingDTO;
import cs544.courseattendancesystem.service.dto.CourseOfferingWithDetailsDTO;
import cs544.courseattendancesystem.service.dto.StudentDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

    public static byte[] generateAttendanceExcel(List<AttendanceRecordDTO> attendanceRecordDTOs, CourseOfferingWithDetailsDTO courseOfferingDetailDTO) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance");

        // Example: Creating headers
        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("Student Id");
        headerRow.createCell(0).setCellValue("Course Name");
        headerRow.createCell(1).setCellValue("Faculty Name");
        headerRow.createCell(2).setCellValue("Student Id");
        headerRow.createCell(3).setCellValue("Scan Date");


        // Example: Populate attendance data (replace with your actual logic)
        // Assuming courseOfferingDTO has attendance-related data
        int rowNum = 1;

        Row childHeaderRow = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue(attendanceRecordDTOs.get(0).getStudentId());
        childHeaderRow.createCell(0).setCellValue(courseOfferingDetailDTO.getCourseName());
        childHeaderRow.createCell(1).setCellValue(courseOfferingDetailDTO.getFacultyName());
        for(AttendanceRecordDTO attendanceRecordDTO : attendanceRecordDTOs) {
            childHeaderRow.createCell(2).setCellValue(attendanceRecordDTO.getStudentId());
            childHeaderRow.createCell(3).setCellValue(attendanceRecordDTO.getScanDateTime());

        }

//        for (Long sessionId : courseOfferingDetailDTO.getSessionList()) {
//            Row row = sheet.createRow(rowNum++);
//            // Add data to each row
//            row.createCell(0).setCellValue(sessionId);
//            row.createCell(1).setCellValue(courseOfferingDTO.getCourseId());
//
//
//            // Example: row.createCell(0).setCellValue(studentName);
//            // Example: row.createCell(1).setCellValue(attendanceStatus);
//        }

        // Writing workbook to ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

}
