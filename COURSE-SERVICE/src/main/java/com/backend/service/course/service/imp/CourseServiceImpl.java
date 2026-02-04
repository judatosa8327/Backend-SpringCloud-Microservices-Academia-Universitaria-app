package com.backend.service.course.service.imp;

import com.backend.service.course.client.UserClientRest;
import com.backend.service.course.entity.Course;
import com.backend.service.course.entity.CourseUser;
import com.backend.service.course.entity.User;
import com.backend.service.course.exception.DuplicateCourseCodeException;
import com.backend.service.course.exception.ResourceNotFoundException;
import com.backend.service.course.repository.CourseRepository;
import com.backend.service.course.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final UserClientRest userClientRest;


    public CourseServiceImpl(CourseRepository courseRepository, UserClientRest userClientRest) {
        this.courseRepository = courseRepository;
        this.userClientRest = userClientRest;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }


    @Override
    @Transactional
    public Course saveCourse(Course course) {
        courseRepository.findByCourseCode(course.getCourseCode())
                .ifPresent(existingCourse -> {
                    throw new DuplicateCourseCodeException("Course with code " + course.getCourseCode() + " already exists");
                });

        return courseRepository.save(course);
    }

    @Override
    @Transactional(readOnly = true)
    public Course findCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Course not found with id " + id));
    }

    @Override
    @Transactional
    public Course updateCourse(Long id, Course courseDetails) {
        Course foundCourse = courseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Course not found with id " + id));

        foundCourse.setCourseCode(courseDetails.getCourseCode());
        foundCourse.setName(courseDetails.getName());
        foundCourse.setDescription(courseDetails.getDescription());
        return courseRepository.save(foundCourse);

    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        Course foundCourse = courseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Course not found with id " + id));
        courseRepository.delete(foundCourse);

    }

    @Override
    public Course assignUserToCourse(Long userId, Long courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new ResourceNotFoundException("Course not found with id " + courseId));

        User msvcUser = userClientRest.getUsuarioById(userId);
        CourseUser courseUser = new CourseUser();
        courseUser.setUserId(msvcUser.getId());
        course.addUserCourse(courseUser);
        return courseRepository.save(course);

    }

    @Override
    public Course removeUserFromCourse(Long userId, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new ResourceNotFoundException("Course not found with id " + courseId));

        User msvcUser = userClientRest.getUsuarioById(userId);
        CourseUser courseUser = new CourseUser();
        courseUser.setUserId(msvcUser.getId());
        course.removeUserCourse(courseUser);
        return courseRepository.save(course);
    }

    @Override
    public Course createUserAndAssignToCourse(User user, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() ->
                new ResourceNotFoundException("Course not found with id " + courseId));

        User msvcUser = userClientRest.saveUsuario(user);
        CourseUser courseUser = new CourseUser();
        courseUser.setUserId(msvcUser.getId());
        course.addUserCourse(courseUser);
        return courseRepository.save(course);

    }

    @Override
    @Transactional
    public void deleteCourseUserByUserId(Long userId) {
        courseRepository.deleteCourseUserByUserId(userId);
    }
}
