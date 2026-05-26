package com.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "message")

public class Message {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private int studentId;

	    private String content;

	    // Getters & Setters
	    public int getId() { return id; }
	    public void setId(int id) { this.id = id; }

	    public int getStudentId() { return studentId; }
	    public void setStudentId(int studentId) { this.studentId = studentId; }

	    public String getContent() { return content; }
	    public void setContent(String content) { this.content = content; }
}
