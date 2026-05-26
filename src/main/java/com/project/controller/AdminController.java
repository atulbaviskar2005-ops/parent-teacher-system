package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.entity.Student;
import com.project.entity.Teacher;
import com.project.repository.StudentRepository;
import com.project.repository.TeacherRepository;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private TeacherRepository teacherRepo;

    // TEST API
    @GetMapping("/test")
    public String test() {
        return "Admin Controller Working";
    }

    // ADD STUDENT
    @PostMapping("/addStudent")
    public Student addStudent(@RequestBody Student student) {
        return studentRepo.save(student);
    }

    // VIEW STUDENTS
    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentRepo.findAll();
    }

    // UPDATE STUDENT
    @PutMapping("/updateStudent/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student newData) {

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

    // DELETE STUDENT
    @DeleteMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable int id) {

        if (studentRepo.existsById(id)) {
            studentRepo.deleteById(id);
            return "Student Deleted Successfully";
        }

        return "Student Not Found";
    }

    // ADD TEACHER
    @PostMapping("/addTeacher")
    public Teacher addTeacher(@RequestBody Teacher teacher) {
        return teacherRepo.save(teacher);
    }

    // VIEW TEACHERS
    @GetMapping("/teachers")
    public List<Teacher> getTeachers() {
        return teacherRepo.findAll();
    }

    // UPDATE TEACHER
    @PutMapping("/updateTeacher/{id}")
    public Teacher updateTeacher(@PathVariable int id, @RequestBody Teacher newData) {

        Teacher oldTeacher = teacherRepo.findById(id).orElse(null);

        if (oldTeacher != null) {
            oldTeacher.setName(newData.getName());
            oldTeacher.setSubject(newData.getSubject());

            return teacherRepo.save(oldTeacher);
        }

        return null;
    }

    // DELETE TEACHER
    @DeleteMapping("/deleteTeacher/{id}")
    public String deleteTeacher(@PathVariable int id) {

        if (teacherRepo.existsById(id)) {
            teacherRepo.deleteById(id);
            return "Teacher Deleted Successfully";
        }

        return "Teacher Not Found";
    }
}