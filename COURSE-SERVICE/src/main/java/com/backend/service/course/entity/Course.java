package com.backend.service.course.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 250, message = "Description must be less than 250 characters")
    private String description;

    @NotBlank(message = "Course code is mandatory")
    @Size(max = 20, message = "Course code must be less than 20 characters")
    private String courseCode;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<CourseUser> courseUsers;
    @Transient
    private List<User> users;
    public Course() {
        this.courseUsers = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void addUserCourse(CourseUser courseUser) {
        this.courseUsers.add(courseUser);
    }

    public void removeUserCourse(CourseUser courseUser) {
        this.courseUsers.remove(courseUser);
    }
}
