# CourseAttendanceSystem
Course Attendance System
Welcome to the Course Attendance System project! This system aims to automate the process of attendance taking for Compro students using a collection of RESTful web services. The core functionality revolves around badge scanners installed in classrooms that record student attendance via badge scans.

Project Overview
The Course Attendance System is designed to streamline attendance tracking for courses offered to Compro students. The system leverages badge scanners to create attendance records and offers various endpoints to manage and retrieve attendance data. The attendance data is then used to generate attendance lists, which can be exported as Excel spreadsheets.

Key Features
Badge Scanning:

Each classroom is equipped with a badge scanner.
Students scan their badges upon entering the classroom.
The scanner records the student's barcode number, location ID, date, and time of the scan.
Course and Session Management:

Students are registered for multiple course offerings.
Each course offering has a unique identifier and is associated with a particular faculty and classroom.
Courses have start and end dates, and a list of sessions is either computed in-memory or stored in the database.
Standard course sessions are scheduled as follows:
Monday to Saturday: 10:00 AM – 12:30 PM (Morning session) and 1:30 PM – 3:30 PM (Afternoon session).
The last day of the course is a half-day with only the morning session.
Attendance List Generation:

A GET endpoint to retrieve the attendance list for a specific course offering.
The attendance list is provided as a Microsoft Excel spreadsheet.
The spreadsheet contains columns for each course session and rows for each student, with attendance marked as 1 (present) or 0 (absent).
Use Cases
Student Use Cases
View Registered Courses:

Endpoint: GET /student-view/course-offerings
Description: Retrieve the list of courses the student is registered in and their grades if published.
View Attendance for a Course Offering:

Endpoint: GET /student-view/course-offerings/{offeringId}/attendance
Description: Retrieve the list of sessions for a specific course offering and the student's attendance.
View All Attendance Records:

Endpoint: GET /student-view/attendance-records
Description: Retrieve all attendance records for the student.
View Course Information:

Endpoint: GET /student-view/course-offerings/{offeringId}
Description: Retrieve information about a specific course offering, such as the instructor, start date, and classroom details.
Admin Use Cases
Download Attendance Records for a Course Offering:

Endpoint: GET /admin-view/course-offerings/{offeringId}/attendance
Description: Download an Excel sheet of the attendance records for a specific course offering.
View Courses in Session on a Specific Date:

Endpoint: GET /admin-view/course-offerings?date=YYYY-MM-DD
Description: Retrieve all courses that are in session on a specific date.
View Course Offering Details:

Endpoint: GET /admin-view/course-offerings/{offeringId}
Description: Retrieve all details of a specific course offering, including the start and end dates, instructor, and class roster.
View Student Details and Courses:

Endpoint: GET /admin-view/students/{studentID}
Description: Retrieve details of a specific student and the courses they are registered for.
Sys-Admin Use Cases
Manage Students:

Endpoint: CRUD /sys-admin/students
Description: Create, read, update, and delete student records.
Manage Course Offerings:

Endpoint: CRUD /sys-admin/course-offerings
Description: Create, read, update, and delete course offerings.
Manage Courses:

Endpoint: CRUD /sys-admin/courses
Description: Create, read, update, and delete courses.
Manage Locations:

Endpoint: CRUD /sys-admin/locations
Description: Create, read, update, and delete locations.
API Endpoints
1. Record Attendance
Endpoint: POST /attendance-records
Description: Records attendance based on badge scans.
Payload:
json
Copy code
{
  "studentBarcode": "string",
  "locationId": "string",
  "scanDateTime": "ISO 8601 datetime"
}
2. Get Course Offering Attendance
Endpoint: GET /course-offerings/{courseOfferingId}/attendance
Description: Retrieves the attendance list for a specified course offering as an Excel spreadsheet.
Response: An Excel file with columns representing course sessions and rows representing student attendance.
Roles and Permissions
Student: Can view their own attendance records and course information.
Faculty: Can view attendance records for their course offerings.
Staff: Can manage course offerings and sessions.
Sys-Admin: Has full access to all system functionalities.
Technology Stack
Java: Backend language
Spring Boot: Framework for building RESTful services
Apache POI: Library for creating and manipulating Excel files
Hibernate: ORM for database interactions
PostgreSQL: Database for storing application data
Getting Started
Prerequisites
Java 11 or higher
Maven
PostgreSQL
Installation
Clone the repository:

bash
Copy code
git clone https://github.com/gamhpang/CourseAttendanceSystem.git
cd course-attendance-system
Configure the database:

Update application.properties with your PostgreSQL database credentials.
Build and run the application:

bash
Copy code
mvn clean install
mvn spring-boot:run
API Documentation
Access the API documentation at http://localhost:8080/swagger-ui.html for detailed information on available endpoints and their usage.
Contribution
We welcome contributions from the community. To contribute:

Fork the repository.
Create a new branch (git checkout -b feature/your-feature).
Commit your changes (git commit -am 'Add new feature').
Push to the branch (git push origin feature/your-feature).
Create a new Pull Request.
License
This project is licensed under the MIT License. See the LICENSE file for details.

We hope this Course Attendance System project helps in efficiently managing and automating the attendance process for Compro students. For any questions or support, please open an issue on GitHub. Happy coding!








