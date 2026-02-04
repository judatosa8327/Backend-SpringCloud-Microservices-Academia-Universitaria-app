package com.backend.users.dto.input;

import com.backend.users.entity.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class UsuarioDtoInput {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    private String password;
    private Boolean enabled;
    private Boolean isAdmin;
    private LocalDate createdAt;
    private List<Rol> roles;

    @Override
    public String toString() {
        return "UsuarioDtoInput{" +
                "name='" + name + '\'' +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", admin=" + isAdmin +
                ", createdAt=" + createdAt +
                ", roles=" + roles +
                '}';
    }
}
