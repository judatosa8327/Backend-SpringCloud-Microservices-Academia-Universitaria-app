package com.backend.users.dto.output;

import com.backend.users.entity.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UsuarioDtoOut {

    private Long id;
    private String name;
    private String username;
    //private String password;
    private Boolean enabled;
    private Boolean isAdmin;
    private LocalDate createdAt;


    private List<Rol> roles;

    @Override
    public String toString() {
        return "UsuarioDtoOut{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + username + '\'' +
                ", enabled=" + enabled +
                ", admin=" + isAdmin +
                ", createdAt=" + createdAt +
                ", roles=" + roles +
                '}';
    }
}
