package com.project.repository;

import com.project.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    List<Attendance> findByStudentId(int studentId);

    Attendance findByStudentIdAndDate(int studentId, String date);

    Attendance findByStudentIdAndDateAndSubject(int studentId, String date, String subject);

    List<Attendance> findByStudentIdAndSubject(int studentId, String subject);

    // 🔥 IMPORTANT (NEW FOR TIME)
    Attendance findByStudentIdAndDateAndSubjectAndLectureTime(
            int studentId,
            String date,
            String subject,
            String lectureTime
    );
}