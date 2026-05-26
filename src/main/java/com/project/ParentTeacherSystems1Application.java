package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParentTeacherSystems1Application {

    public static void main(String[] args) {

        SpringApplication.run(ParentTeacherSystems1Application.class, args);

        try {
            Runtime.getRuntime().exec(
                "cmd /c start http://localhost:8082/login.html"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}