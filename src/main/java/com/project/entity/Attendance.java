package com.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int studentId;

    private String date;

    private String lectureTime;   // ✅ NEW FIELD

    private String status; // Present / Absent

    private String subject; // Java / PBL / Data Structure etc.

    public Attendance() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    // ✅ NEW GETTER SETTER
    public String getLectureTime() { return lectureTime; }
    public void setLectureTime(String lectureTime) { this.lectureTime = lectureTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
}