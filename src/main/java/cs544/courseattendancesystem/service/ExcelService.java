package cs544.courseattendancesystem.service;

import cs544.courseattendancesystem.service.dto.AttendanceRecordDTO;
import cs544.courseattendancesystem.service.dto.CourseOfferingWithDetailsDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelService {

    public static byte[] generateAttendanceExcel(List<AttendanceRecordDTO> attendanceRecordDTOs, CourseOfferingWithDetailsDTO courseOfferingDetailDTO) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance");

        // Example: Creating headers
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Course Name");
        headerRow.createCell(1).setCellValue("Faculty Name");
        headerRow.createCell(2).setCellValue("Student Id");
        headerRow.createCell(3).setCellValue("Attendance Date");
        headerRow.createCell(4).setCellValue("Location");
        headerRow.createCell(5).setCellValue("Room");


        // Example: Populate attendance data (replace with your actual logic)
        // Assuming courseOfferingDTO has attendance-related data
        int rowNum = 1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (AttendanceRecordDTO attendanceRecordDTO : attendanceRecordDTOs) {
            Row childRow = sheet.createRow(rowNum++);

            childRow.createCell(0).setCellValue(courseOfferingDetailDTO.getCourseName());
            childRow.createCell(1).setCellValue(courseOfferingDetailDTO.getFacultyName());
            childRow.createCell(2).setCellValue(attendanceRecordDTO.getStudentId());
            childRow.createCell(3).setCellValue(attendanceRecordDTO.getScanDateTime().format(formatter));
            childRow.createCell(4).setCellValue(attendanceRecordDTO.getLocationName());
            childRow.createCell(5).setCellValue(courseOfferingDetailDTO.getRoom());

        }


        // Writing workbook to ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }

}
