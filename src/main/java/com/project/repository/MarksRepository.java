package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.entity.Marks;
import java.util.List;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Integer> {

	List<Marks> findByStudentId(int studentId);
}