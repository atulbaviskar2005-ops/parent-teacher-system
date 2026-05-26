package com.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "student_class")
    private String studentClass;

    private String parentUsername;

    // 🔥 ADD THIS
    private String division;

    // Constructor
    public Student() {}

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStudentClass() { return studentClass; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }

    public String getParentUsername() { return parentUsername; }
    public void setParentUsername(String parentUsername) { this.parentUsername = parentUsername; }

    // 🔥 NEW GETTER/SETTER
    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }
}