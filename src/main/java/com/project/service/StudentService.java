package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Student;
import com.project.repository.StudentRepository;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepo;

    // ADD
    public Student addStudent(Student s) {
        return studentRepo.save(s);
    }

    // GET ALL
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    // GET BY ID (IMPORTANT FOR EDIT)
    public Student getStudentById(int id) {
        return studentRepo.findById(id).orElse(null);
    }

    // UPDATE (IMPORTANT)
    public Student updateStudent(int id, Student newData) {

        Student oldStudent = studentRepo.findById(id).orElse(null);

        if (oldStudent != null) {
            oldStudent.setName(newData.getName());
            oldStudent.setStudentClass(newData.getStudentClass());
            oldStudent.setDivision(newData.getDivision());
            oldStudent.setParentUsername(newData.getParentUsername());

            return studentRepo.save(oldStudent);
        }

        return null;
    }

    // DELETE
    public String deleteStudent(int id) {
        if (studentRepo.existsById(id)) {
            studentRepo.deleteById(id);
            return "Student Deleted Successfully";
        }
        return "Student Not Found";
    }
}