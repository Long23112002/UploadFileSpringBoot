package com.example.uploadimage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.example.uploadimage.controler", "com.example.uploadimage.repository"})
public class UploadImageApplication {

    public static void main(String[] args) {
        SpringApplication.run(UploadImageApplication.class, args);
    }

}
