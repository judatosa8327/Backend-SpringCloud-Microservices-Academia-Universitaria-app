package com.backend.service.course.service;

import com.backend.service.course.entity.Course;
import com.backend.service.course.entity.User;

import java.util.List;

public interface CourseService {

    List<Course> findAllCourses();

    Course saveCourse(Course course);

    Course findCourseById(Long id);

    Course updateCourse(Long id, Course courseDetails);

    void deleteCourse(Long id);


    Course assignUserToCourse(Long userId,Long courseId);
    Course removeUserFromCourse(Long userId, Long courseId);
    Course createUserAndAssignToCourse(User user, Long courseId);

    void deleteCourseUserByUserId(Long userId);






}
