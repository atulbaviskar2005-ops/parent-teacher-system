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
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/parent")
@CrossOrigin(origins = "*")
public class ParentController {

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private MarksRepository marksRepo;

    @Autowired
    private MessageRepository messageRepo;

    @Autowired
    private StudentRepository studentRepo;

    // ✅ Get student by parent username
    @GetMapping("/studentByParent/{parentUsername}")
    public Student getStudentByParent(@PathVariable String parentUsername) {
        return studentRepo.findByParentUsername(parentUsername);
    }

    // ✅ Get attendance
    @GetMapping("/attendance/{studentId}")
    public List<Attendance> getAttendance(@PathVariable int studentId) {
        return attendanceRepo.findByStudentId(studentId);
    }

    // ✅ Get overall attendance percentage
    @GetMapping("/attendancePercent/{studentId}")
    public String getAttendancePercent(@PathVariable int studentId) {

        List<Attendance> attendanceList = attendanceRepo.findByStudentId(studentId);

        if (attendanceList == null || attendanceList.size() == 0) {
            return "0%";
        }

        int presentCount = 0;

        for (Attendance a : attendanceList) {
            if ("Present".equalsIgnoreCase(a.getStatus())) {
                presentCount++;
            }
        }

        double percentage = (presentCount * 100.0) / attendanceList.size();

        return String.format("%.2f%%", percentage);
    }

    // ✅ Subject-wise attendance percentage
    @GetMapping("/subjectAttendancePercent/{studentId}")
    public Map<String, String> getSubjectAttendancePercent(@PathVariable int studentId) {

        List<Attendance> attendanceList = attendanceRepo.findByStudentId(studentId);

        Map<String, Integer> totalMap = new HashMap<>();
        Map<String, Integer> presentMap = new HashMap<>();
        Map<String, String> result = new HashMap<>();

        for (Attendance a : attendanceList) {
            String subject = a.getSubject();

            if (subject == null || subject.isEmpty()) {
                subject = "General";
            }

            totalMap.put(subject, totalMap.getOrDefault(subject, 0) + 1);

            if ("Present".equalsIgnoreCase(a.getStatus())) {
                presentMap.put(subject, presentMap.getOrDefault(subject, 0) + 1);
            }
        }

        for (String subject : totalMap.keySet()) {
            int total = totalMap.get(subject);
            int present = presentMap.getOrDefault(subject, 0);

            double percent = (present * 100.0) / total;

            result.put(subject, String.format("%.2f%%", percent));
        }

        return result;
    }

    // ✅ Get marks
    @GetMapping("/marks/{studentId}")
    public List<Marks> getMarks(@PathVariable int studentId) {
        return marksRepo.findByStudentId(studentId);
    }

    // ✅ Get messages
    @GetMapping("/messages/{studentId}")
    public List<Message> getMessages(@PathVariable int studentId) {
        return messageRepo.findByStudentId(studentId);
    }
}