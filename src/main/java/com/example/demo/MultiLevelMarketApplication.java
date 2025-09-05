package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class MultiLevelMarketApplication {

    public static void main(String[] args) {
        System.out.println("=== STARTING MULTI-LEVEL MARKET APPLICATION ===");
        SpringApplication.run(MultiLevelMarketApplication.class, args);
        System.out.println("=== APPLICATION STARTED SUCCESSFULLY ===");
        System.out.println("Hello world");
    }

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${springdoc.swagger-ui.path:/swagger-ui/index.html}")
    private String swaggerPath;

    @Bean
    CommandLineRunner printSwaggerUrl() {
        return args -> {
            try {
               
                String swaggerUrl = "http://localhost:" + serverPort + swaggerPath;

                System.out.println("---------------------------------------------------");
                System.out.println("Swagger UI available at: " + swaggerUrl);
                System.out.println("---------------------------------------------------");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
