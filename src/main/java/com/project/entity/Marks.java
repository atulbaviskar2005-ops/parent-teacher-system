package com.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "marks")
public class Marks {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private int studentId;
	    private String subject;
	    private int marks;

	    public int getId() { return id; }
	    public void setId(int id) { this.id = id; }

	    public int getStudentId() { return studentId; }
	    public void setStudentId(int studentId) { this.studentId = studentId; }

	    public String getSubject() { return subject; }
	    public void setSubject(String subject) { this.subject = subject; }

	    public int getMarks() { return marks; }
	    public void setMarks(int marks) { this.marks = marks; }

}
