package com.backend.service.course.controller;

import com.backend.service.course.entity.Course;
import com.backend.service.course.entity.User;
import com.backend.service.course.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cursos")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping
    public ResponseEntity<List<Course>> listCourses() {
        return new ResponseEntity<>(courseService.findAllCourses(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Course> saveCourse(@RequestBody @Valid Course course) {
        return new ResponseEntity<>(courseService.saveCourse(course), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.findCourseById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@RequestBody @Valid Course course, @PathVariable Long id) {
        return new ResponseEntity<>(courseService.updateCourse(id, course), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    @PostMapping("/{courseId}/assign-user/{userId}")
    public ResponseEntity<Course> assignUserToCourse(@PathVariable Long userId, @PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.assignUserToCourse(userId, courseId), HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}/remove-user/{userId}")
    public ResponseEntity<Course> removeUserFromCourse(@PathVariable Long userId, @PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.removeUserFromCourse(userId, courseId), HttpStatus.OK);
    }

    @PostMapping("/{courseId}/create-user-and-assign")
    public ResponseEntity<Course> createUserAndAssignToCourse(@RequestBody User user, @PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.createUserAndAssignToCourse(user, courseId), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-user/{userId}")
    public void deleteCourseUserByUserId(@PathVariable Long userId) {
        courseService.deleteCourseUserByUserId(userId);
    }



}
