// src/main/java/com/example/carrental/CarRentalApplication.java
package com.example.carrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class CarRentalApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CarRentalApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CarRentalApplication.class, args);
    }
}


