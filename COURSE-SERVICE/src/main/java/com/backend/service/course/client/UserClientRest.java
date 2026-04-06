package com.backend.service.course.client;

import com.backend.service.course.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "MSVC-USERS",
        path = "/api/v1/users"
)
public interface UserClientRest {

    @GetMapping("/{id}")
    User getUsuarioById(@PathVariable Long id);

    @PostMapping
    User saveUsuario(@RequestBody User usuario);

}

