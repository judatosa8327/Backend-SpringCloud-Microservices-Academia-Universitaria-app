package com.backend.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringCloudUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudUsersApplication.class, args);
	}

}
