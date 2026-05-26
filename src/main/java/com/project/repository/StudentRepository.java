package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // Already exists
    Student findByParentUsername(String parentUsername);

    // 🔥 ADD THIS (IMPORTANT)
    List<Student> findByDivision(String division);
}