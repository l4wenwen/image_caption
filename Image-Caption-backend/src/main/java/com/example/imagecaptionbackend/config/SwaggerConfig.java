package com.example.imagecaptionbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.imagecaptionbackend.controller"))
                .build();
    }

    @SuppressWarnings("all")
    private ApiInfo apiInfo() {
        Contact contact = new Contact("ICB", "http://localhost:8080/swagger-ui/index.html", "skydevourer@foxmail.com");
        return new ApiInfo(
                "Image Caption Backend API",
                "This is a backend service for image captioning.",
                "v1.0",
                "http://localhost:8080/swagger-ui/index.html",
                contact,
                "MIT License",
                "http://www.apache.org/licenses/MIT",
                new ArrayList()
        );
    }
}
