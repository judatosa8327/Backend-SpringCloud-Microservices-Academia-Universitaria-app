package com.backend.users.repository;

import com.backend.users.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository  extends JpaRepository<Rol, Long> {

Optional<Rol> findByName(String name);

}
