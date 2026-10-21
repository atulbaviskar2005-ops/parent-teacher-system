# Parent–Teacher Communication System

A full-stack, enterprise-grade **Parent–Teacher Communication System** built with **Java 17**, **Spring Boot 3.2.5**, **Spring Data MongoDB**, and vanilla **HTML5/CSS3/JavaScript**. Designed for institutional deployment with MIT ADT academic structure support.

---

## 🛠 Technology Stack

| Layer | Technology |
|---|---|
| **Backend** | Java 17, Spring Boot 3.2.5, Spring Security, BCrypt, Spring Web (REST) |
| **Database** | MongoDB Community Server (`parent_teacher_db` on `localhost:27017`), Spring Data MongoDB |
| **Frontend** | Responsive HTML5, CSS3, Vanilla JavaScript (Fetch API, Bootstrap Icons) — served directly by Spring Boot |
| **Security** | Role-Based Access Control (`ADMIN`, `TEACHER`, `PARENT`), JWT authentication, BCrypt password hashing, Strict family and class data isolation |
| **Email & Recovery** | Spring Boot Mail (SMTP), time-limited single-use reset tokens, email verification |
| **Testing** | JUnit 5, Mockito, AssertJ, Flapdoodle Embedded MongoDB (`28/28 passing tests`) |

---

## 🚀 Quick Start

### 1. Prerequisites
- **JDK 17+**
- **MongoDB** running on `mongodb://localhost:27017` (MongoDB Community Server / Compass or Docker)
- **Maven** (bundled Maven Wrapper `./mvnw.cmd` included)

### 2. Start Database
```bash
# Option A: Local MongoDB Community Service (Windows Services)
# Option B: Docker Compose
docker compose up -d mongodb
```

### 3. Run Application
```powershell
./mvnw.cmd spring-boot:run
```
The application starts at: **http://localhost:8082**
- Web UI: http://localhost:8082/login.html
- Mongo Express (if running docker compose): http://localhost:8081

### 4. Run Test Suite
```powershell
./mvnw.cmd test
```
*Executes all 28 automated unit and integration tests against isolated embedded MongoDB instances.*

### 5. Build Production JAR
```powershell
./mvnw.cmd package -DskipTests=false
```
*Generates executable artifact at `target/parent-teacher-system-1.0.0.jar`.*

---

## 🔐 Default Demo Accounts

Demo accounts and sample students are opt-in (`SEED_DEMO_DATA=true`). By default the application seeds only the bootstrap administrator and missing academic structure, so a clean student database stays clean after restart. Set `ADMIN_DEFAULT_PASSWORD` to a unique value before the first production start. For a local demo, set `SEED_DEMO_DATA=true` before starting:

| Role | Username | Password | Email | Notes |
|---|---|---|---|---|
| **Administrator** | `admin` | `admin123` | `admin@mitadt.edu` | Full system control, academic structure, user management, audit logs |
| **Teacher** | `teacher1` | `teacher123` | `teacher1@mitadt.edu` | Assigned to CSE, SY-CSE-1, Java Programming & Data Structures |
| **Teacher** | `teacher2` | `teacher123` | `teacher2@mitadt.edu` | Assigned to IT, TY-IT-A, Web Technologies |
| **Teacher** | `teacher3` | `teacher123` | `teacher3@mitadt.edu` | Assigned to ENTC, SY-ENTC-1, Signals and Systems |
| **Parent** | `parent1` | `parent123` | `parent1@gmail.com` | Linked to Aarav Sharma (`MIT2024CSE001`) |
| **Parent** | `parent2` | `parent123` | `parent2@gmail.com` | Linked to Diya Patel (`MIT2024IT002`) |
| **Parent** | `parent3` | `parent123` | `parent3@gmail.com` | Linked to Rohan Kulkarni (`MIT2024ENTC003`) |

## Student data backup and cleanup

The student collection is referenced by `attendance`, `marks`, `messages`, `notifications`, parent `studentIds`, teacher `teacherIds`, and student-linked communication logs. Back up the database before any cleanup:

```powershell
mongodump --uri="mongodb://localhost:27017/parent_teacher_db" --out ".\backup-before-student-cleanup"
```

Do not run a delete until the backup is verified and the intended scope is confirmed. A cleanup should remove selected test students and their test attendance/marks, clear the removed IDs from parent and teacher link arrays, and remove only student-context communication data that is explicitly in scope. Keep users, parent/teacher profiles, departments, subjects, classes, and administrator access. The current application does not perform this cleanup automatically.

---

## ⭐ Core Systems & Workflows

### 1. ⚡ High-Efficiency One-Click Attendance System
- **Mark All Present / Mark All Absent**: One-click bulk state toggling with keyboard shortcuts.
- **Per-Student Toggles**: Immediate status pill switches (`PRESENT`, `ABSENT`, `LATE`, `ON_LEAVE`).
- **Live Summary Counters**: Real-time counter pills showing Total Students, Present, Absent, Late, and On Leave.
- **Filter & Search**: Real-time roll number and student name filter.
- **Batch Save with Conflict Detection**:
  - Endpoint: `POST /api/teacher/attendance/save-batch`
  - Prevents accidental duplicates for same class, date, and subject.
  - Automatically prompts teacher to confirm overwrite if existing records are detected (HTTP 409 Conflict handling).
- **Automated Parent Notification**: When marked `ABSENT`, system records absence alert in MongoDB and dispatches in-app notification to linked parents.
- **Export to CSV**: `GET /api/teacher/attendance/export-csv` downloads filtered attendance sheet with roll numbers, names, status, and timestamps.
- **Unsaved Changes Guard**: Browser `beforeunload` warning prompts teachers if changes are unsaved.

### 2. 🏫 MIT ADT Academic Structure Engine
- **Departments**: Computer Science & Engineering (`CSE`), Information Technology (`IT`), Electronics & Telecommunication (`ENTC`), extensible via Admin UI.
- **Degrees & Programs**: B.Tech (4-Year Undergrad), M.Tech (2-Year Postgrad).
- **Class Divisions**: Year Level (`FY`, `SY`, `TY`, `FINAL`), Division (`1`, `2`, `A`, `B`), Academic Year (`2025-2026`).
- **Course Catalog & Subjects**: Semester-wise subject catalog with lecture credits.
- **Teacher Assignments**: Maps verified teachers to departments, class divisions, and subjects with designated Class Teacher flags.
- **Admin Management Tabs**: Visual CRUD tables in Admin portal for Departments, Courses, Classes, Subjects, and Teacher Assignments.
- **Seeding Endpoint**: `POST /api/academic/seed-mit-adt` populates standard MIT ADT baseline catalog idempotently.

### 3. 📧 Real Email Verification & Account Recovery
- **Forgot Password Workflow**:
  - `POST /api/auth/forgot-password` with registered email.
  - Generates secure, time-limited reset token with hash validation.
  - Dispatches email with reset link (`/login.html?token={token}`).
  - Console fallback logs complete reset URL if SMTP host is not configured.
- **Reset Password Workflow**:
  - `POST /api/auth/reset-password` validates token expiry and hashes new password with BCrypt.
  - Single-use token invalidation upon successful reset.
- **Forgot Username Workflow**:
  - `POST /api/auth/forgot-username` safely emails account username without enumeration risk.
- **Administrator Password Reset**:
  - `POST /api/admin/users/{id}/reset-password` allows authorized admins to reset user credentials directly from Admin portal.
- **Account Invitation & Verification**:
  - `POST /api/auth/verify-email?token=...` confirms user identity before enabling self-registration access.

### 4. 📂 Bulk CSV Import & Export
- **Teacher Bulk Import**:
  - Admin modal with role creation and department assignment validation.
  - Downloadable template: `sample_teachers.csv` (includes Employee ID, Department, Qualification, Phone).
- **Student Bulk Import**:
  - Import `sample_teachers.csv` first so each `teacherEmployeeId` in the student file resolves to a teacher account.
  - Then import `sample_students.csv`. It creates or reuses parent accounts by email and links each student in both the parent and student records.
  - CSV columns: registration number, roll number, name, class, division, parent details, department, and teacher employee ID. Duplicate student identifiers and unresolved teacher IDs are reported row by row.

### 5. 🛡 Security & Audit Logging
- **Strict Role-Based Access Control**:
  - Spring Security JWT filter validates bearer tokens on every request.
  - Method-level `@PreAuthorize("hasRole('ADMIN')")`, `@PreAuthorize("hasRole('TEACHER')")`, `@PreAuthorize("hasRole('PARENT')")`.
- **Parent Isolation**: Parents can only retrieve marks, attendance, and message threads for children explicitly linked to their user account.
- **Teacher Isolation**: Teachers can only grade and submit attendance for classes and subjects assigned to them.
- **Audit Logging System**:
  - Logs critical operations (User login, password resets, student creation, attendance batch saves, PTM booking).
  - Admin Audit Log Viewer (`/api/admin/audit-logs`) with search and category filtering.
- **In-App Notification Dispatcher**:
  - Real-time notification badge and dropdown in UI (`/api/notifications`).

---

## 📁 Project Structure

```
parent-teacher-systems-1/
├── pom.xml                                      # Maven project configuration
├── sample_students.csv                          # Validated Student CSV template
├── sample_teachers.csv                          # Validated Teacher CSV template
├── .env.example                                 # Environment template
├── src/
│   ├── main/
│   │   ├── java/com/project/
│   │   │   ├── controller/                      # REST API Controllers
│   │   │   │   ├── AcademicController.java      # MIT ADT Academic Structure API
│   │   │   │   ├── AdminController.java         # Admin dashboard & management API
│   │   │   │   ├── AttendanceController.java    # Core Attendance API
│   │   │   │   ├── AuditLogController.java      # Admin Audit Log API
│   │   │   │   ├── AuthController.java          # JWT Auth, Register, Recovery API
│   │   │   │   ├── MarksController.java         # Academic marks entry & review API
│   │   │   │   ├── MessageController.java       # Direct messaging API
│   │   │   │   ├── NotificationController.java  # In-app notifications API
│   │   │   │   ├── ParentController.java        # Parent portal API
│   │   │   │   ├── PtmController.java           # Parent-Teacher Meeting API
│   │   │   │   ├── StudentController.java       # Student registration & CSV import API
│   │   │   │   └── TeacherController.java       # Teacher portal & One-Click Attendance API
│   │   │   ├── model/                           # MongoDB Document Models
│   │   │   │   ├── AcademicDepartment.java      # Department entity
│   │   │   │   ├── AcademicProgram.java         # Degree / Program entity
│   │   │   │   ├── ClassDivision.java           # Division entity (compound unique index)
│   │   │   │   ├── Subject.java                 # Subject curriculum entity
│   │   │   │   ├── TeacherAssignment.java       # Teacher-Class-Subject mapping
│   │   │   │   ├── Attendance.java              # Attendance record entity
│   │   │   │   ├── AuditLog.java                # Audit trail entity
│   │   │   │   ├── Notification.java            # Notification entity
│   │   │   │   ├── PasswordResetToken.java      # Secure reset token entity
│   │   │   │   ├── EmailVerificationToken.java  # Verification token entity
│   │   │   │   ├── Student.java                 # Student entity with MIT ADT fields
│   │   │   │   ├── User.java                    # Base user credentials & role
│   │   │   │   ├── Parent.java                  # Parent profile & linked children
│   │   │   │   ├── Teacher.java                 # Teacher profile & qualifications
│   │   │   │   ├── Marks.java                   # Examination marks entity
│   │   │   │   ├── Message.java                 # Parent-teacher message entity
│   │   │   │   └── PtmSchedule.java             # PTM event & slot booking entity
│   │   │   ├── repository/                      # Spring Data MongoDB Repositories
│   │   │   ├── security/                        # Security config & JWT Filter
│   │   │   ├── service/                         # Business logic services
│   │   │   └── DemoDataInitializer.java         # Automated startup seed runner
│   │   └── resources/
│   │       ├── application.properties           # Application configuration
│   │       └── static/                          # Pure Vanilla JS/HTML/CSS Frontend
│   │           ├── index.html                   # Landing page
│   │           ├── login.html                   # Login & Password Recovery UI
│   │           ├── admin.html                   # Admin Dashboard & Academic Manager UI
│   │           ├── teacher.html                 # Teacher Portal & One-Click Attendance UI
│   │           ├── parent.html                  # Parent Portal UI
│   │           ├── js/                          # Modular Frontend Controllers
│   │           │   ├── api.js                   # Unified Fetch API wrapper & auth interceptor
│   │           │   ├── login.js                 # Auth & Password Recovery controller
│   │           │   ├── admin.js                 # Admin & Academic Structure controller
│   │           │   ├── teacher.js               # One-Click Attendance & Marks controller
│   │           │   └── parent.js                # Parent portal controller
│   │           └── css/
│   │               └── style.css                # Polished institutional design system
│   └── test/                                    # Automated JUnit 5 Test Suite (28 Tests)
```

---

## 🧪 Automated Testing Verification

The project includes 28 automated unit and integration tests covering all critical operations:

```
[INFO] Running com.project.ParentTeacherSystems1ApplicationTests
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.project.service.AttendanceServiceTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.project.service.MarksServiceTest
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.project.service.OneClickAttendanceAndRecoveryTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.project.service.StudentServiceTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running com.project.integration.AuthIntegrationTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Results:
[INFO] Tests run: 28, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## 📄 License
MIT License. Developed for institutional Parent-Teacher Communication and Academic Administration.
