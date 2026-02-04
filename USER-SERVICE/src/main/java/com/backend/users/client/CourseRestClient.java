package com.backend.users.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "MSVC-COURSES",
        url = "host.docker.internal:9002",
        path = "/api/v1/cursos"
)
public interface CourseRestClient {

    @DeleteMapping("/delete-user/{userId}")
    void deleteCourseUserByUserId(@PathVariable Long userId);

}
