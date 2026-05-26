package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.entity.Attendance;
import com.project.entity.Marks;
import com.project.entity.Message;
import com.project.entity.Student;

import com.project.repository.AttendanceRepository;
import com.project.repository.MarksRepository;
import com.project.repository.MessageRepository;
import com.project.repository.StudentRepository;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@CrossOrigin(origins = "*")
public class TeacherController {

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private MarksRepository marksRepo;

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentRepo.findAll();
    }

    @GetMapping("/studentsByDivision/{division}")
    public List<Student> getStudentsByDivision(@PathVariable String division) {
        return studentRepo.findByDivision(division);
    }

    @PostMapping("/addAttendance")
    public Attendance addAttendance(@RequestBody Attendance a) {

        Attendance existingAttendance =
                attendanceRepo.findByStudentIdAndDateAndSubjectAndLectureTime(
                        a.getStudentId(),
                        a.getDate(),
                        a.getSubject(),
                        a.getLectureTime()
                );

        if (existingAttendance != null) {
            existingAttendance.setStatus(a.getStatus());
            return attendanceRepo.save(existingAttendance);
        }

        return attendanceRepo.save(a);
    }

    @GetMapping("/attendanceHistory")
    public List<Attendance> attendanceHistory(
            @RequestParam String subject,
            @RequestParam String division) {

        return attendanceRepo.findAll()
                .stream()
                .filter(a -> a.getSubject() != null && a.getSubject().equals(subject))
                .filter(a -> {
                    Student student = studentRepo.findById(a.getStudentId()).orElse(null);

                    if (student == null) {
                        return false;
                    }

                    return student.getDivision() != null &&
                           student.getDivision().equals(division);
                })
                .toList();
    }

    @PostMapping("/addMarks")
    public Marks addMarks(@RequestBody Marks m) {
        return marksRepo.save(m);
    }

    @PostMapping("/sendMessage")
    public Message sendMessage(@RequestBody Message msg) {
        return messageRepo.save(msg);
    }
}