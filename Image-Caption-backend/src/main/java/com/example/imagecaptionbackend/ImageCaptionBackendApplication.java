package com.example.imagecaptionbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EnableJpaRepositories(basePackages = {"com.example.imagecaptionbackend.repository"})
@EntityScan(basePackages = {"com.example.imagecaptionbackend.entity"})
public class ImageCaptionBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageCaptionBackendApplication.class, args);
    }

}
