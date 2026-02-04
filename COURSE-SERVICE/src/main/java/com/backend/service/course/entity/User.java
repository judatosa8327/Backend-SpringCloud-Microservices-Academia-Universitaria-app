package com.backend.service.course.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean admin;
    private LocalDate createdAt;
}
