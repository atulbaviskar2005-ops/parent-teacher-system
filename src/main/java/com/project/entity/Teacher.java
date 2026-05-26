package com.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "teacher")

public class Teacher {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;

	    private String name;

	    private String subject;

	    // Getters & Setters
	    public int getId() { return id; }
	    public void setId(int id) { this.id = id; }

	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

	    public String getSubject() { return subject; }
	    public void setSubject(String subject) { this.subject = subject; }

}
