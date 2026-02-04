package com.backend.service.course.repository;

import com.backend.service.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);


    @Modifying
    @Query("delete from CourseUser cu where cu.userId = ?1")
    void deleteCourseUserByUserId(Long userId);


}
