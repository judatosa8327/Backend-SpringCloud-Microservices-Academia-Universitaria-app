package com.backend.users.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean isAdmin;
    private LocalDate createdAt;

    @ManyToMany()
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "role_id"})}
    )
    private List<Rol> roles;

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", admin=" + isAdmin +
                ", createdAt=" + createdAt +
                ", roles=" + roles +
                '}';
    }
}
