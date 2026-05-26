package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Teacher;
import com.project.repository.TeacherRepository;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepo;

    // ADD
    public Teacher addTeacher(Teacher t) {
        return teacherRepo.save(t);
    }

    // GET ALL
    public List<Teacher> getAllTeachers() {
        return teacherRepo.findAll();
    }

    // GET BY ID (FOR EDIT)
    public Teacher getTeacherById(int id) {
        return teacherRepo.findById(id).orElse(null);
    }

    // UPDATE
    public Teacher updateTeacher(int id, Teacher newData) {

        Teacher oldTeacher = teacherRepo.findById(id).orElse(null);

        if (oldTeacher != null) {
            oldTeacher.setName(newData.getName());
            oldTeacher.setSubject(newData.getSubject());

            return teacherRepo.save(oldTeacher);
        }

        return null;
    }

    // DELETE
    public String deleteTeacher(int id) {
        if (teacherRepo.existsById(id)) {
            teacherRepo.deleteById(id);
            return "Teacher Deleted Successfully";
        }
        return "Teacher Not Found";
    }
}