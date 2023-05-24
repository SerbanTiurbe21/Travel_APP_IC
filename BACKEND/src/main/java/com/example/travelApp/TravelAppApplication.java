package com.example.travelApp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "TravelApp Project",
                version = "1.0.0",
                description = "Documentation for the IC project"
        )
)
public class TravelAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelAppApplication.class, args);
    }

}
