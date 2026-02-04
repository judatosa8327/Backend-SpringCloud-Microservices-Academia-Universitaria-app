package com.backend.service.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "course_users")
public class CourseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CourseUser that)) return false;
        return Objects.equals(userId, that.userId);
    }
}
